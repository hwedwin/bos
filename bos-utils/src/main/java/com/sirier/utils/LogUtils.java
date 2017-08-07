package com.sirier.utils;

import org.apache.log4j.Logger;

public class LogUtils {
	// public static Logger log = Logger.getLogger(LogUtils.class);
	private static Logger log;

	private LogUtils() {
	}

	public static synchronized Logger getInstance() {
		if (log == null) {
			synchronized (LogUtils.class) {
				if (log == null) {
					log = Logger.getLogger(LogUtils.class);
				}
			}
		}
		return log;
	}

}
