package com.lonely.file;

import java.io.File;
import java.io.FileInputStream;

public class FileUtils {
	private static long getFileSize(File file) {
		long size = 0;
		if (file.exists()) {
			FileInputStream fiStream = null;
			try {
				fiStream = new FileInputStream(file);
				size = fiStream.available();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return size;
	}
}
