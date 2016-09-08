package com.xad.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;
import com.xad.model.Transformer;

public class ParseUtil {

	private static Logger LOGGER = LoggerFactory.getLogger(ParseUtil.class);

	public static Map<String, String> jsonArrayToMap(String path) {
		Gson gson = new Gson();
		Map<String, String> mappedArray = new HashMap<String, String>();
		try {
			String str = FileUtils.readFileToString(new File(path), "UTF-8");
			Type type = new TypeToken<List<String[]>>() {
			}.getType();
			List<String[]> list = gson.fromJson(str, type);
			for (String[] string : list) {
				mappedArray.put(string[0], string[1]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return mappedArray;
	}

	public static String getISO8601DateFormat(String epochTime) {
		return DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mmX")
				.withZone(ZoneOffset.UTC)
				.format(Instant.ofEpochSecond(Long.parseLong(epochTime)));
	}

	public static boolean validateImpRecord(String[] record) {

		boolean result = true;
		if (!record[2].isEmpty() && !record[3].isEmpty()
				&& !record[4].isEmpty()) {
			if (NumberUtils.isNumber(record[2])
					&& NumberUtils.isNumber(record[3])
					&& NumberUtils.isNumber(record[3])) {
				if (!Constants.DEVICE_TYPES.containsKey(record[2])
						&& !Constants.CONNECTION_TYPES.containsKey(record[3])) {
					result = false;
					LOGGER.error("Device Type and Connection Type are not found for the record with transaction ID: "
							+ record[2]);
				}
			} else {
				result = false;
				LOGGER.error("The record contains alpha numeric characters for connection type, device type, etc.. with the transaction ID: "
						+ record[2]);
			}
		} else {
			result = false;
			LOGGER.error("The record contains empty values for connection type, device type, etc.. with the transaction ID: "
					+ record[2]);
		}

		return result;
	}

	public static synchronized void writeToFile(List<Transformer> list, String fileName) {
		LOGGER.info("Starting write operation ");
		Writer writer = null;
		try {
			writer = new FileWriter(Constants.OUTPUT_DIR_PATH + fileName
					+ ".json", true);
			Gson gson = new GsonBuilder().create();
			for (Transformer transformer : list) {
				gson.toJson(transformer, writer);
				writer.append('\n');
			}
		} catch (JsonIOException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		LOGGER.info("Completed write operation ");
	}
}
