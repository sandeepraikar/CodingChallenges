package com.xad.util;

import java.util.Map;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class Constants {

	public static final int DEFAULT_THREAD_POOL_SIZE;
	public static final String DIMENSIONS_DEVICE_TYPE_PATH;
	public static final String DIMENSIONS_CONNECTION_TYPE_PATH;
	public static final String FACTS_IMP_PATH;
	public static final String FACTS_CLICKS_PATH;
	public static final String OUTPUT_DIR_PATH;
	public static final Map<String, String> DEVICE_TYPES;
	public static final Map<String, String> CONNECTION_TYPES;

	static {

		Configuration config = null;
		try {

			config = new PropertiesConfiguration("config.properties");

		} catch (ConfigurationException e) {
			e.printStackTrace();
		}

		DEFAULT_THREAD_POOL_SIZE = config.getInt("xad.etl.default.thread.pool.size");
		DIMENSIONS_DEVICE_TYPE_PATH = config.getString("xad.device.type.path");
		DIMENSIONS_CONNECTION_TYPE_PATH = config
				.getString("xad.connection.type.path");
		FACTS_IMP_PATH = config.getString("xad.facts.imps.path");
		FACTS_CLICKS_PATH = config.getString("xad.facts.clicks.path");
		OUTPUT_DIR_PATH = config.getString("xad.output.dir.path");
		
		//Loading Dimensions into HashMap
		DEVICE_TYPES = ParseUtil.jsonArrayToMap(DIMENSIONS_DEVICE_TYPE_PATH);
		CONNECTION_TYPES = ParseUtil.jsonArrayToMap(DIMENSIONS_CONNECTION_TYPE_PATH);
	}
}
