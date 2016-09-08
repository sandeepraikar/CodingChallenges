package com.xad.etl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.LineIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xad.model.Transformer;
import com.xad.util.Constants;
import com.xad.util.ParseUtil;

public class ETL implements Runnable {

	private static Logger LOGGER = LoggerFactory.getLogger(App.class);
	private File currFile;

	public ETL() {
	}

	public ETL(File currFile) {
		super();
		this.currFile = currFile;
	}

	public void run() {
		long startTime = System.currentTimeMillis();
		LOGGER.info("Hour " + FilenameUtils.removeExtension(currFile.getName())
				+ " ETL start");
		List<Transformer> list = transform(currFile);
		load(list, currFile.getName());
		long endTime = System.currentTimeMillis();
		LOGGER.info("Hour " + FilenameUtils.removeExtension(currFile.getName())
				+ " ETL complete for file: " + currFile.getName()
				+ ", elapsed time: " + ((endTime - startTime)) + " ms");

	}

	public synchronized void load(List<Transformer> list, String fileName) {
		ParseUtil.writeToFile(list, FilenameUtils.removeExtension(fileName));
	}

	public List<Transformer> transform(File inputFile) {

		List<Transformer> result = new ArrayList<Transformer>();
		try {
			LineIterator it = FileUtils.lineIterator(inputFile, "UTF-8");
			try {
				while (it.hasNext()) {
					String[] line = it.nextLine().split(",");
					if (line.length != 5) {
						LOGGER.error("corupt data!! Ignoring record!");
						continue;
					} else {
						if (ParseUtil.validateImpRecord(line)) {
							int clickCount = extractCount(inputFile.getName(),
									line[1]);
							if (clickCount > 0) {
								LOGGER.info("============================================================");
								LOGGER.info("Constructing Transformer object...");
								Transformer targetObject = new Transformer(
										ParseUtil.getISO8601DateFormat(line[0]),
										line[1], Constants.CONNECTION_TYPES
												.get(line[2]),
										Constants.DEVICE_TYPES.get(line[3]),
										Long.parseLong(line[4]), clickCount);
								LOGGER.info("Adding Transformer objects to ArrayList....");
								result.add(targetObject);
							} else {
								LOGGER.warn("Click Count not available for the transaction ID :  "
										+ line[1] + ", so skipping this record");
								continue;
							}
						} else {
							LOGGER.warn("Validation failed so skipping the record with transaction ID: "
									+ line[1]);
							continue;
						}
					}
				}
			} finally {
				it.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;

	}

	protected int extractCount(String clickFileName, String transactionId) {
		int clickCount = -1;
		try {
			LineIterator it = FileUtils.lineIterator(new File(
					Constants.FACTS_CLICKS_PATH + clickFileName), "UTF-8");
			try {
				while (it.hasNext()) {
					String[] line = it.nextLine().split(",");
					if (line.length != 3) {
						LOGGER.warn("Corupt data! Ignoring record from clicks !");
						continue;
					} else {
						if (line[1].equals(transactionId)) {
							LOGGER.info("The click count for the transaction ID :"
									+ transactionId + " is :" + line[2]);
							return Integer.parseInt(line[2]);
						}
					}
				}
			} finally {
				it.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return clickCount;
	}
}
