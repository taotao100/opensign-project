using System;
using System.IO;
using Org.BouncyCastle.X509;
using System.Drawing;
using System.Runtime.InteropServices;
using System.Collections.Generic;
using System.Collections;

namespace RP.Implementation.Owasp.Crypto
{
	// 1. Get a handle to the certificate icon resource
	/// <summary>
	/// This class is generic and takes a type which will default to CodeSigningCertificate
	/// TODO: put on constraint on this to ensure that all other types of certificate can be created as seperate store
	/// </summary>
	/// <typeparam name="T">By default a CodeSigningCertificate but may be used for SSL or other purposes</typeparam>
	public class CertificateStore<T> : IEnumerable<T> where T: ICertificate
	{
		private static CertificateStore<T> store;
		private string certificateStorePath;
		public const string CertificateFileExtensionFilter = "*.cer";
		public const string PrivateKeyFileExtensionFilter = "*.pvk";
		public string certMgrPath = Path.Combine(Environment.SystemDirectory, "certmgr.dll");
		public Icon certificateIcon;
		private List<T> storePersist = new List<T>();
		
		private CertificateStore(string path)
		{
			certificateStorePath = path;
			GetCertificateIcon();
		}

		/// <summary>
		/// Gets a singleton instance of the certificate store 
		/// </summary>
		/// <param name="path">the path to the store</param>
		/// <returns>a singleton certificate store object</returns>
		public static CertificateStore<T> GetInstance(string path)
		{
			// change this to double checked locking pattern and close down the certificate store gracefully
			if (store == null || ((store != null) && (path != store.CertificateStorePath)))
			{
				store = new CertificateStore<T>(path);
			}
			return store;
		}

		/// <summary>
		/// Gets the blank certificate icon which is the first entry in the certmgr.dll
		/// </summary>
		public void GetCertificateIcon()
		{
			// we only need to get the first icon in the file!! 
			//SHFILEINFO shInfo = new SHFILEINFO();
			//IntPtr hImgSmall = NativeMethods.SHGetFileInfo(certMgrPath, 0, ref shInfo, (uint)Marshal.SizeOf(shInfo), NativeMethods.SHGFI_ICON | NativeMethods.SHGFI_SMALLICON);
			//certificateIcon = Icon.FromHandle(shInfo.hIcon);
		}

		public bool DummyWin32Callback(IntPtr hModule, IntPtr pType, IntPtr pName, IntPtr param)
		{
			return true;
		}

		/// <summary>
		/// Reads the certificate files in a particular directory and build up the store
		/// </summary>
		/// <typeparam name="T"></typeparam>
		public void AddCertificateFile(T cert)
		{
			storePersist.Add(cert);
		}

		/// <summary>
		/// Gets the instance of the certificate store defaulting to the C:\ drive if no other path is et
		/// </summary>
		public static CertificateStore<T> CurrentInstance
		{
			get
			{
				if (store == null)
				{
					store = new CertificateStore<T>(@"C:\");
				}
				return store;
			}
		}

		/// <summary>
		/// Gets the path to the certificate store
		/// </summary>
		public string CertificateStorePath
		{
			get
			{
				return certificateStorePath;
			}
		}

		#region IEnumerable<T> Members

		public IEnumerator<T> GetEnumerator()
		{
			lock (this)
			{
				foreach (T item in storePersist)
				{
					yield return item;
				}
			}
		}

		#endregion

		#region IEnumerable Members

		IEnumerator IEnumerable.GetEnumerator()
		{
			return GetEnumerator();
		}

		#endregion
	}

	/// <summary>
	/// Struct to hold pointer to icons from DllFile
	/// </summary>
	[StructLayout(LayoutKind.Sequential)]
	public struct SHFILEINFO
	{
		public IntPtr hIcon;
		public IntPtr iIcon;
		public uint dwAttributes;
		[MarshalAs(UnmanagedType.ByValTStr, SizeConst = 260)]
		public string szDisplayName;
		[MarshalAs(UnmanagedType.ByValTStr, SizeConst = 80)]
		public string szTypeName;
	};

	[StructLayout(LayoutKind.Sequential, Pack = 2)]
	public struct MEMICONDIR
	{
		public ushort wReserved;
		public ushort wType;
		public ushort wCount;
		public MEMICONDIRENTRY arEntries; // inline array
	}

	[StructLayout(LayoutKind.Sequential, Pack = 2)]
	public struct MEMICONDIRENTRY
	{
		public byte bWidth;
		public byte bHeight;
		public byte bColorCount;
		public byte bReserved;
		public ushort wPlanes;
		public ushort wBitCount;
		public uint dwBytesInRes;
		public ushort wId;
	}

	public class CodeSigningStoreBuilder : IStoreBuilder
	{
		private CertificateStore<CodeSigningCertificate> store;

		public CodeSigningStoreBuilder(CertificateStore<CodeSigningCertificate> store)
		{
			this.store = store;
		}

		public void BuildStore()
		{
			DirectoryInfo info = new DirectoryInfo(store.CertificateStorePath);
			FileInfo[] fileInfos = info.GetFiles(CertificateStore<CodeSigningCertificate>.CertificateFileExtensionFilter);
			for (int i = 0; i < fileInfos.Length; i++)
			{
				CodeSigningCertificate cert = CodeSigningCertificate.GetNewInstance(fileInfos[i].FullName);
				store.AddCertificateFile(cert);
				// add to collection here
			}
		}
	}

	public interface IStoreBuilder
	{
		void BuildStore();
	}

	public enum CertificateType
	{
		CodeSigningCertificate,
		SslCertificate
	}

}