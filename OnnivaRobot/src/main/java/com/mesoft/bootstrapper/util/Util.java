package com.mesoft.bootstrapper.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Util {

	static Properties configProp = null;
	
	final static Logger logger = LoggerFactory.getLogger(Util.class);
	
	
	
	public String getConfig(String param) {

		String result = "";

		if (configProp == null) {

			try {
				initConfig();

				
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("error while reading file", e);
			}
		}
		result = configProp.getProperty(param);

		return result;
	}

	private void initConfig() throws IOException {
		configProp = new Properties();

		InputStream in = this.getClass().getClassLoader()
				.getResourceAsStream("config.properties");

		configProp.load(in);
	}
	
}
