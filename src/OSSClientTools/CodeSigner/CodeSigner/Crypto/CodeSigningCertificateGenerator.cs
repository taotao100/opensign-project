using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;
using System.Security;
using System.Security.Cryptography;
using Org.BouncyCastle.X509;
using Org.BouncyCastle.Asn1;
using Org.BouncyCastle.Asn1.X509;
using Org.BouncyCastle.X509.Extension;
using Org.BouncyCastle.Math;
using Org.BouncyCastle.Crypto;


namespace RP.Implementation.Owasp.Crypto
{
	public class CodeSigningCertificateGenerator
	{
		#region Variables

		internal const string codeSigningOid = "1.3.6.1.5.5.7.3.3";
		private X509V3CertificateGenerator generator;
		private DateTime start, end;
		private long serialNumber;
		private string issuerDn, subjectDn, signatureAlgorithm, certificateFileName, privateKeyFilename;

		#endregion

		public CodeSigningCertificateGenerator()
		{
			generator = new X509V3CertificateGenerator();
			GenerateSerialNumber();
		}

		/// <summary>
		/// sets the start date as long as it is not before today's date and checks whether the date has been set 
		/// </summary>
		public DateTime StartDate
		{
			set
			{
				start = value;
				if (value < DateTime.Today)
				{
					throw new ApplicationException("certificate cannont be backdated");
				}
				generator.SetNotBefore(value);
			}
			get
			{
				if (start == DateTime.MinValue)
				{
					throw new ApplicationException("start date has not been set yet");
				}
				return start;
			}
		}

		public DateTime EndDate
		{
			set
			{
				end = value;
				if (value < DateTime.Today)
				{
					throw new ApplicationException("certificate cannot expire before today");
				}

				if (start == DateTime.MinValue)
				{
					throw new ApplicationException("set start date before setting expiry");
				}

				if ((value.Year - start.Year) < 1)
				{
					throw new ApplicationException("certificate lifetime must be a minimu of a year old");
				}

				generator.SetNotAfter(value);
			}
			get
			{
				if (end == DateTime.MaxValue)
				{
					throw new ApplicationException("expiry date has not been set yet");
				}
				return end;
			}
		}

		public long SerialNumber
		{
			get
			{
				if (serialNumber == 0)
				{
					throw new ApplicationException("serial number not set yet");
				}

				return serialNumber;
			}
		}

		private void GenerateSerialNumber()
		{
			BigInteger serial = new BigInteger(64, new Random());
			generator.SetSerialNumber(serial);
			serialNumber = serial.LongValue;
		}

		public string SubjectDistinguishedName
		{
			set
			{
				X509Name name = new X509Name(value);
				subjectDn = value;
				generator.SetSubjectDN(name);
			}
			get
			{
				if (subjectDn == null)
				{
					throw new ApplicationException("value not set yet");
				}

				return subjectDn;
			}
		}

		public string IssuerDistinguishedName
		{
			set
			{
				X509Name name = new X509Name(value);
				issuerDn = value;
				generator.SetIssuerDN(name);
			}
			get
			{
				if (issuerDn == null)
				{
					throw new ApplicationException("value not set yet");
				}

				return issuerDn;
			}
		}

		/// <summary>
		/// Gets the list of supported algorithms and filters by RSA
		/// </summary>
		public static List<string> Algorithms
		{
			get
			{
				// for the time being we should limit anything which isn't RSA!!
				List<string> list = new List<string>();
				X509V3CertificateGenerator algs = new X509V3CertificateGenerator();
				foreach (object alg in algs.SignatureAlgNames)
				{
					if (((string)alg).IndexOf("RSA") > 0)
					{
						list.Add((string)alg);
					}
				}
				return list;
			}
		}

		public string SignatureAlgorithm
		{
			set
			{
				signatureAlgorithm = value;
				generator.SetSignatureAlgorithm(value);
			}
			get
			{
				if (signatureAlgorithm == null)
				{
					throw new ApplicationException("value not set yet");
				}

				return signatureAlgorithm;
			}
		}

		public void AddCodeSigningOid()
		{
			generator.AddExtension(codeSigningOid, true, new byte[]{});
		}

		public void AddBasicContraintsOid(bool isCA, int pathLenConstraint)
		{
			BasicConstraints constraints = new BasicConstraints(pathLenConstraint);
			generator.AddExtension(X509Extensions.BasicConstraints, true, constraints);
		}

		private void AddAuthorityKeyIdentifierOid(SubjectPublicKeyInfo subjectPublicKeyInfo)
		{
			AuthorityKeyIdentifier identifier = new AuthorityKeyIdentifier(subjectPublicKeyInfo);
			generator.AddExtension(X509Extensions.AuthorityKeyIdentifier, false, identifier);
		}

		public string CertificateFilename
		{
			get
			{
				return certificateFileName;
			}
		}

		/// <summary>
		/// Used to generate the certificate with specific params which affect signing
		/// </summary>
		/// <param name="paramCertificate"></param>
		public void GenerateCertificate(CertificateGenerationParameters paramCertificate)
		{
			// set defaults or otherwise
			int keyStrength = (paramCertificate.keyStrength == 0) ? RSAKeyPair.defaultKeyStrength : paramCertificate.keyStrength;
			string keyPhrase = (paramCertificate.passphrase == null) ? RSAKeyPair.defaultPassphrase : paramCertificate.passphrase;
			// generate the RSA key pair 
			RSAKeyPair pair = new RSAKeyPair();
			pair.GenerateKeys(keyStrength);
			//set defaults or otherwise
			AsymmetricKeyParameter localKeyParam = (paramCertificate.keyParam == null) ? pair.KeyPair.Private : paramCertificate.keyParam;
			// set the public key which will be the newly generated one
			generator.SetPublicKey(pair.KeyPair.Public); 
			// set the private key for the current certificate
			X509Certificate cert = generator.Generate(localKeyParam);
			// create the file name 
			string filename = Path.GetFileNameWithoutExtension(Path.GetRandomFileName());
			certificateFileName = String.Concat(filename, ".cer");
			privateKeyFilename = String.Concat(filename, ".pvk");
			string pathCertificate = Path.Combine(CertificateStore<CodeSigningCertificate>.CurrentInstance.CertificateStorePath, certificateFileName);
			string pathPrivateKey = Path.Combine(CertificateStore<CodeSigningCertificate>.CurrentInstance.CertificateStorePath, privateKeyFilename);
			// persist the DER encoded version of the certificate
			FileStream fileStream = File.Open(pathCertificate, FileMode.Create, FileAccess.Write);
			fileStream.Write(cert.GetEncoded(), 0, cert.GetEncoded().Length);
			fileStream.Close();
			// persist the DER encoded version of the private key
			fileStream = File.Open(pathPrivateKey, FileMode.Create, FileAccess.Write);
			byte[] pvk = pair.ExportPrivateKey(keyPhrase);
			fileStream.Write(pvk, 0, pvk.Length);
			fileStream.Close();
		}

		/// <summary>
		/// Generate with the default key size 
		/// </summary>
		public void GenerateCertificate()
		{
			CertificateGenerationParameters param = new CertificateGenerationParameters();
			param.keyParam = null;
			param.keyStrength = RSAKeyPair.defaultKeyStrength;
			param.passphrase = RSAKeyPair.defaultPassphrase;
			GenerateCertificate(param);
		}
	}

	public struct CertificateGenerationParameters
	{
		public int keyStrength;
		public AsymmetricKeyParameter keyParam;
		public string passphrase;
	}
}
