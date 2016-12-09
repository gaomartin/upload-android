package com.pplive.upload.test.util;

import java.io.File;

public class FileUtils {
	public static long getVideoFileSize(String path) {
		File file = new File(path);
		if (file.exists())
			return file.length();
		else {
			return 0;
		}
	}

	public static boolean deleteFile(String path) {
		File file = new File(path);
		if (file.exists()) {
			return file.delete();
		} else {
			return false;
		}
	}
}
