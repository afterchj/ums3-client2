package com.utils;

import java.io.IOException;
import java.util.Properties;

public class Config {

	private static Config cfg;
	private Properties properties;

	private Config() throws IOException {
		properties = new Properties();
		properties.load(Config.class.getClassLoader().getResourceAsStream("setup.properties"));
	}

	public static Config getInstance() throws IOException {
		if (cfg == null) {
			synchronized (Config.class) {
				if(cfg == null) {
					cfg = new Config();
				}
			}
		}
		return cfg;
	}
	
	public String get(String key) {
		return properties.getProperty(key);
	}
}
