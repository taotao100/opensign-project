﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Org.BouncyCastle.Crypto;
using Org.BouncyCastle.Security;
using Org.BouncyCastle.Crypto.Generators;
using Org.BouncyCastle.Crypto.Parameters;
using Org.BouncyCastle.Math;
using Org.BouncyCastle.Pkcs;
using Org.BouncyCastle.Asn1;
using Org.BouncyCastle.Asn1.Pkcs;



// http://www.codeproject.com/KB/cpp/X509Certificate.aspx
namespace RP.Implementation.Owasp.Crypto
{
	internal class RSAKeyPair
	{
		internal const int defaultKeyStrength = 2048;
		internal const string defaultPassphrase = "crypto";
		private RsaKeyPairGenerator generator;
		private AsymmetricCipherKeyPair keyPair;

		internal RSAKeyPair()
		{
			generator = new RsaKeyPairGenerator();
		}

		/// <summary>
		/// Generates a new RSA key pair for the certificate and subsequent signing operation with the key strength parameter
		/// </summary>
		/// <param name="keyStrength">Can take values of 1024, 2048, 3072 or 4096</param>
		internal void GenerateKeys(int keyStrength)
		{
			if (keyStrength % 1024 != 0 && keyStrength > 4096)
			{
				throw new ApplicationException("bit strengths greater than 4096 not supported and key size must be a multiple of 1024");
			}

			SecureRandom random = new SecureRandom();
			RsaKeyGenerationParameters parameters = new RsaKeyGenerationParameters(
				BigInteger.One, random, keyStrength, 25);
			
			generator.Init(parameters);
			keyPair = generator.GenerateKeyPair();
		}

		/// <summary>
		/// Returns a bouncy castle AsymmetricCipherKeyPair generated by this class
		/// </summary>
		internal AsymmetricCipherKeyPair KeyPair
		{
			get
			{
				return keyPair;
			}
		}

		/// <summary>
		/// Generates keys with the default 2048 bit key strength
		/// </summary>
		internal void GenerateKeys()
		{
			GenerateKeys(defaultKeyStrength);
		}

		/// <summary>
		/// Exports the private key to a byte array which can be persisted 
		/// </summary>
		/// <param name="passphrase">a passphrase which is used for encrypting keys</param>
		/// <returns>encrypted byte array containing private key structure</returns>
		internal byte[] ExportPrivateKey(string passphrase)
		{
			EncryptedPrivateKeyInfo info = EncryptedPrivateKeyInfoFactory.CreateEncryptedPrivateKeyInfo(
				PkcsObjectIdentifiers.PbeWithSha1AndRC2Cbc, passphrase.ToCharArray(),
				new byte[] { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07 }, 10, keyPair.Private);

			return info.GetEncryptedData(); 
		}

		/// <summary>
		/// Uses the defaults to generate the encrypted private key SHA1 and RC2 CBC
		/// </summary>
		/// <returns>encrypted data bytes which can be persisted</returns>
		internal byte[] ExportPrivateKey()
		{
			return ExportPrivateKey(defaultPassphrase);
		}
	}
	

	public enum KeyType
	{
		RSA,
		DSA
	}
}
