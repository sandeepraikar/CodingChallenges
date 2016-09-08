package com.xad.etl;

import java.io.File;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xad.util.Constants;

public class App {

	private static Logger LOGGER = LoggerFactory.getLogger(App.class);
	public static int noOfThreads = Constants.DEFAULT_THREAD_POOL_SIZE; //default 5 threads

	public static void main(String[] args) {
		LOGGER.info("Starting applicaiton");

		if (args.length == 1 && args[0].equals("-h")) {
			System.out
					.println("usage: [-p] [no. of threads]. Example: java -jar xad-coding-challenge-0.0.1-SNAPSHOT-jar-with-dependencies.jar -p 10");
			System.exit(-1);
		} else if (args.length == 2 && args[0].equals("-p")) {
			noOfThreads = Integer.parseInt(args[1]);
		} else {
			System.out
					.println("usage: [-p] [no. of threads]. Example: java -jar xad-coding-challenge-0.0.1-SNAPSHOT-jar-with-dependencies.jar -p 10");
			LOGGER.info("Running this application with default thread pool size -> 5!");
		}

		LOGGER.info("Number of threads chosen: " + noOfThreads);
		ExecutorService executor = Executors.newFixedThreadPool(noOfThreads);

		try {
			File impsDir = new File(Constants.FACTS_IMP_PATH);
			if (impsDir.isDirectory()) {
				Collection<File> files = FileUtils.listFiles(impsDir,
						new String[] { "csv" }, false);
				for (File currFile : files) {
					Runnable worker = new ETL(currFile);
					executor.execute(worker);
				}
			} else {
				LOGGER.error("imps Directory does not exist!");
			}
			executor.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}