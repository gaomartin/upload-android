package com.pplive.media.upload.util;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.annotation.SuppressLint;

@SuppressLint("DefaultLocale")
public class FeathureUtil {

	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	@SuppressWarnings("resource")
	public static String getXunleiCid(String path) throws IOException,
			NoSuchAlgorithmException {
		String result = "";
		RandomAccessFile ranFile = new RandomAccessFile(path, "r");
		long size = ranFile.length();
		MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
		if (size < 0xF000) {
			messageDigest.update(ranFile.readByte());
		} else {
			byte[] eachRead = new byte[0x5000];
			ranFile.read(eachRead);
			messageDigest.update(eachRead);

			ranFile.seek(size / 3);
			ranFile.read(eachRead);
			messageDigest.update(eachRead);

			ranFile.seek(size - 0x5000);
			ranFile.read(eachRead);
			messageDigest.update(eachRead);
		}
		String dig = getFormattedText(messageDigest.digest());
		result = String.format("%s_%s", size + System.nanoTime(),
				dig + MD5.MD5_16(path));
		return result;
	}

	@SuppressWarnings("resource")
	public static String getXunleiGcid(String path) throws IOException,
			NoSuchAlgorithmException, DigestException {
		String result = "";
		RandomAccessFile ranFile = new RandomAccessFile(path, "r");
		long size = ranFile.length();
		int psize = 0x40000;
		MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
		while (size / psize > 0x200) {
			psize = psize << 1;
		}

		MessageDigest md = MessageDigest.getInstance("SHA1");
		byte[] eachRead = new byte[psize];
		int readLen = ranFile.read(eachRead);
		while (readLen > 0) {
			md.reset();
			md.update(eachRead, 0, readLen);
			byte[] dgt = md.digest();
			messageDigest.update(dgt);
			readLen = ranFile.read(eachRead);
		}
		String dig = getFormattedText(messageDigest.digest());
		dig = dig.toLowerCase();
		result = String.format("%s_%s", size + System.nanoTime(),
				dig + MD5.MD5_16(path));
		return result;
	}

	@SuppressWarnings("resource")
	public static String getPPFeature(String path) throws IOException,
			NoSuchAlgorithmException {
		String result = "";
		RandomAccessFile ranFile = new RandomAccessFile(path, "r");
		long size = ranFile.length();
		MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
		if (size < 0xFFFF) {
			messageDigest.update(ranFile.readByte());
		} else {
			byte[] eachRead = new byte[0x3000];
			ranFile.read(eachRead);
			messageDigest.update(eachRead);

			ranFile.seek(size / 5);
			ranFile.read(eachRead);
			messageDigest.update(eachRead);

			ranFile.seek(2 * size / 5);
			ranFile.read(eachRead);
			messageDigest.update(eachRead);

			ranFile.seek(3 * size / 5);
			ranFile.read(eachRead);
			messageDigest.update(eachRead);

			ranFile.seek(size - 0x3000);
			ranFile.read(eachRead);
			messageDigest.update(eachRead);
		}
		String dig = getFormattedText(messageDigest.digest());
		// result = String.format("%s_%s", size + System.nanoTime(), dig);
		result = String.format("%s_%s", size, dig);
		return result;
	}

	private static String getFormattedText(byte[] bytes) {
		int len = bytes.length;
		StringBuilder buf = new StringBuilder(len * 2);
		// 把密文转换成十六进制的字符串形式
		for (int j = 0; j < len; j++) {
			buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
			buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
		}
		return buf.toString();
	}
}
