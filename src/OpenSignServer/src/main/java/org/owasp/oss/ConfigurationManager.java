package org.owasp.oss;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationManager {

	private static String CONFIGURATION_FILE_NAME = "config.properties";
	
	private static ConfigurationManager _instance = null;
	
	static {
		try {
			_instance = new ConfigurationManager();
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
			System.exit(-1);
		} catch (IOException e) {			
			e.printStackTrace();
			System.exit(-1);
		}		
	}

	private Properties _properties = null;

	private ConfigurationManager() throws FileNotFoundException, IOException {
		_properties = new Properties();
		_properties.load(new FileInputStream(CONFIGURATION_FILE_NAME));
	}

	public static ConfigurationManager getInstance() throws IOException {
		return _instance;
	}

	public String getValue(String key) {
		return _properties.getProperty(key);
	}

}
