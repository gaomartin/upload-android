package com.pplive.media.upload.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.util.Log;

public class FileMD5 {

	private static final String Tag = FileMD5.class.getName();
	protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	protected static MessageDigest messageDigest = null;
	static {
		try {
			messageDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			Log.e(Tag, e.getMessage());
		}
	}

	/**
	 * 计算文件的MD5
	 * 
	 * @param fileName
	 *            文件的绝对路径
	 * @return
	 * @throws IOException
	 */
	public static String getFileMD5String(String fileName) throws IOException {
		File f = new File(fileName);
		return getFileMD5String(f);
	}

	/**
	 * 计算文件的MD5，重载方法
	 * 
	 * @param file
	 *            文件对象
	 * @return
	 * @throws IOException
	 */
	public static synchronized String getFileMD5String(File file) {
		if (!file.exists()) {
			return null;
		}
		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
			byte[] buffer = new byte[1024 * 1024];
			int len = 0;
			while ((len = in.read(buffer)) > 0) {
				messageDigest.update(buffer, 0, len);
			}
		} catch (FileNotFoundException e) {
			Log.e(Tag, e.getMessage());
		} catch (IOException e) {
			Log.e(Tag, e.getMessage());
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException ignore) {
				}
			}
		}
		return bufferToHex(messageDigest.digest());
	}

	public static synchronized byte[] getFileRangeMD5Bytes(File file,
			long start, long end) {
		if (!file.exists()) {
			return null;
		}
		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
			in.skip(start);
			end++;
			long current = start;
			long lstep = 1024 * 1024L;
			byte[] buffer = new byte[1024 * 1024];
			while (current < end) {
				Long step = Math.min(lstep, end - current);
				if (step < lstep) {
					buffer = new byte[step.intValue()];
				}

				int len = 0;
				if ((len = in.read(buffer)) > 0) {
					messageDigest.update(buffer, 0, len);
				} else {
					break;
				}
				current = current + lstep;
			}
		} catch (FileNotFoundException e) {
			Log.e(Tag, e.getMessage());
		} catch (IOException e) {
			Log.e(Tag, e.getMessage());
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException ignore) {
				}
			}
		}

		return messageDigest.digest();
	}

	/**
	 * 计算文件的MD5，重载方法, []
	 * 
	 * @param file
	 *            文件对象
	 * @return
	 * @throws IOException
	 */
	public static synchronized String getFileRangeMD5String(File file,
			long start, long end) {
		return bufferToHex(getFileRangeMD5Bytes(file, start, end));
	}

	public static String bufferToHex(byte bytes[]) {
		return bufferToHex(bytes, 0, bytes.length);
	}

	private static String bufferToHex(byte bytes[], int m, int n) {
		StringBuffer stringbuffer = new StringBuffer(2 * n);
		int k = m + n;
		for (int l = m; l < k; l++) {
			appendHexPair(bytes[l], stringbuffer);
		}
		return stringbuffer.toString();
	}

	private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
		char c0 = hexDigits[(bt & 0xf0) >> 4];
		char c1 = hexDigits[bt & 0xf];
		stringbuffer.append(c0);
		stringbuffer.append(c1);
	}

	public static void main(String[] args) throws IOException {
		String fileName = "e:/20fe1799a3b62616a190c14e3353fdda.mp4";
		System.out.println(FileMD5.getFileRangeMD5String(new File(fileName),
				0L, 1024 * 1024 * 1024L));
	}
}
