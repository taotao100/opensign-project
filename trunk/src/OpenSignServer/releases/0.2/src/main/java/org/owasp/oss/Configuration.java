package org.owasp.oss;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * This class reads the configuration file and provides the read values to the system. 
 */
public class Configuration {
	
	public enum ConfMode {
		LIVE,
		TEST
	}
	
	private static String CONFIGURATION_FILE_NAME = "config.properties";
	
	private static String CONFIGURATION_FILES_PATH = "conf/";
	
	private static String RESOURCES_FILE_PATH = "resources/";
	
	private static String RESOURCES_FILE_PATH_TEST = "src/test/resources/";
	
	private static Configuration _instance = null;
	
	private ConfMode _confMode = ConfMode.LIVE;
	
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
		_properties.load(new FileInputStream(CONFIGURATION_FILES_PATH + CONFIGURATION_FILE_NAME));
	}

	public static Configuration getInstance() {
		return _instance;
	}
	
	public void setMode(ConfMode mode) {
		_confMode = mode;
	}

	public String getValue(String key) {
		return _properties.getProperty(key);
	}
	
	public String getResourcePath() {
		if (_confMode == ConfMode.LIVE)
			return RESOURCES_FILE_PATH;
		else
			return RESOURCES_FILE_PATH_TEST;
	}
	
	public String getConfigurationFileAndPath(String key) {
		return CONFIGURATION_FILES_PATH + _properties.getProperty(key);
	}
	
	public String getResourceFileAndPath(String key) {
		if (_confMode == ConfMode.LIVE)
			return RESOURCES_FILE_PATH + _properties.getProperty(key);
		else
			return RESOURCES_FILE_PATH_TEST + _properties.getProperty(key);
	}	
	
	private void reload(String fileName) throws FileNotFoundException, IOException {		
		_properties.load(new FileInputStream(CONFIGURATION_FILES_PATH + fileName));
	}

}
