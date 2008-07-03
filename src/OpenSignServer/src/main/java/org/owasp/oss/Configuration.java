package org.owasp.oss;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * This class reads the configuration file and provides the read values to the system. 
 */
public class Configuration {

	private static String CONFIGURATION_FILE_NAME = "config.properties";
	
	private static Configuration _instance = null;
	
	static {
		try {
			_instance = new Configuration();
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
			System.exit(-1);
		} catch (IOException e) {			
			e.printStackTrace();
			System.exit(-1);
		}		
	}

	private Properties _properties = null;

	private Configuration() throws FileNotFoundException, IOException {
		_properties = new Properties();
		_properties.load(new FileInputStream(CONFIGURATION_FILE_NAME));
	}

	public static Configuration getInstance() {
		return _instance;
	}

	public String getValue(String key) {
		return _properties.getProperty(key);
	}

}
