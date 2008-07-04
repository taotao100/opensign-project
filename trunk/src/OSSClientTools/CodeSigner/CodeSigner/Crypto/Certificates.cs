using System;
using System.IO;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Org.BouncyCastle.X509;

namespace RP.Implementation.Owasp.Crypto
{

	public interface ICertificate
	{
	}

	/// <summary>
	/// The root class for the code signing certificate. This class is abstract.
	/// </summary>
	public abstract class CodeSigningCertificate : ICertificate
	{
		protected X509Certificate codeSigningCertificate;
		protected string fileName;

		protected CodeSigningCertificate(X509Certificate cert)
		{
			codeSigningCertificate = cert;
		}

		/// <summary>
		/// Gets a new instance of a code signing certificate from a file 
		/// </summary>
		/// <param name="path">path to the certificate file</param>
		/// <returns>a codesigningcertificate object</returns>
		public static CodeSigningCertificate GetNewInstance(string path)
		{
			X509CertificateParser parser = new X509CertificateParser();
			X509Certificate cert;

			try
			{
				FileStream f = File.OpenRead(path);
				cert = parser.ReadCertificate(f);
			}
			catch(Exception)
			{
				throw new ApplicationException("certificate not found at specified location or not certificate file");
			}

			if (IsSelfSigned(cert))
			{
				return new CodeSigningCertificateRoot(cert, path);
			}
			return new CodeSigningCertificateChild(cert, path);
		}

		/// <summary>
		/// Checks to see whether the certificate is self-signed so that it can be determined whether it is root or not
		/// </summary>
		/// <param name="cert">The BouncyCastle certificate parameter</param>
		/// <returns>a boolean to denote whether it is self-signed or not</returns>
		private static bool IsSelfSigned(X509Certificate cert)
		{
			bool isSelfSigned = true;

			try
			{
				cert.Verify(cert.GetPublicKey());
			}
			catch (Exception)
			{
				isSelfSigned = false;
			}

			return isSelfSigned;
		}

		public string Filename
		{
			get
			{
				return Path.GetFileNameWithoutExtension(fileName);
			}
		}
	}

	/// <summary>
	/// A root certificate. This class cannot be inherited.
	/// </summary>
	public sealed class CodeSigningCertificateRoot : CodeSigningCertificate
	{
		public CodeSigningCertificateRoot(X509Certificate cert) : base(cert)
		{
		}

		internal CodeSigningCertificateRoot(X509Certificate cert, string path) : this(cert)
		{
			fileName = path;
		}
	}

	/// <summary>
	/// A child certificate. This class cannot be inherited.
	/// </summary>
	public sealed class CodeSigningCertificateChild : CodeSigningCertificate
	{
		public CodeSigningCertificateChild(X509Certificate cert) : base(cert)
		{
		}

		internal CodeSigningCertificateChild(X509Certificate cert, string path) : this(cert)
		{
			fileName = path;
		}
	}
}
