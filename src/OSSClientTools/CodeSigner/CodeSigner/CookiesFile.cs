using System;
using System.IO;
using System.IO.IsolatedStorage;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Reflection;

namespace RP.Implementation.Owasp
{
	public class CookiesFile
	{
		private static CookiesFile cookiesFile;
		public const string fileName = "cookies.store";
		private FileStream stream;
		private readonly string localPath;

		private const char seperator = '=';
		public static string key_StoreLocation = "STORE_LOCATION";

		private CookiesFile()
		{
			localPath = Path.GetDirectoryName(Assembly.GetExecutingAssembly().Location);
		}

		// TODO: The stream keeps being garbage collected we should keep this alive 
		private void ReinvigorateStream()
		 {
			if (stream != null)
			{
				// we can try using stream.Unlock() here
				stream.Close();
			}
			stream = new FileStream(
				Path.Combine(localPath, fileName), FileMode.OpenOrCreate, FileAccess.ReadWrite);
		}


		public static CookiesFile GetInstance()
		{
			if (cookiesFile == null)
			{
				cookiesFile = new CookiesFile();
			}
			cookiesFile.ReinvigorateStream();
			return cookiesFile;
		}

		public void WriteKeyValue(string key, string val)
		{
			SortedList<string, string> list = ParseStoreAndRemoveChaff(key);
			list.Add(key, val);

			StreamWriter writer = new StreamWriter(stream);
			foreach (string innerKey in list.Keys)
			{
				writer.WriteLine(innerKey + "=" + list[innerKey] + "\n");
			}
			writer.Close();
		}

		public string StoreLocation
		{
			get
			{
                return this.localPath;
				//return SearchFileForKey(key_StoreLocation);
			}
		}

		// TODO: Write this better - it's crap, need to wrap this up in a using 
		private string SearchFileForKey(string key)
		{
			string line;
			stream.Seek(0, SeekOrigin.Begin);
			StreamReader reader = new StreamReader(stream);
			while ((line = reader.ReadLine()) != null)
			{
				string[] parts = line.Split(new char[] {seperator});

				if (parts[0] == key)
				{
					return parts[1];
				}
			}

			return null;
		}

		// have to add error checking here to make sure that there are two arguments and an equals present!
		private SortedList<string, string> ParseStoreAndRemoveChaff(string key)
		{
			string line;
			StreamReader reader = new StreamReader(stream);
			SortedList<string, string> list = new SortedList<string, string>();

			while ((line = reader.ReadLine()) != null)
			{
				string[] parts = line.Split(new char[] { seperator });
				list.Add(parts[0], parts[1]);
				
				if (parts[0] == key)
				{
					list.Remove(key);
				}
			}

			stream.Seek(0, SeekOrigin.Begin);

			return list;
		}
	}
}
