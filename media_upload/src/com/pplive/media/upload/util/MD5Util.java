package com.pplive.media.upload.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.http.protocol.HTTP;

import android.text.TextUtils;

public class MD5Util {

	protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6',
		'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	
	protected static MessageDigest messageDigest = null;

	static {
		try {
			messageDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	public static String getStringMD5(String passwd) {
		if (TextUtils.isEmpty(passwd)) {
			return null;
		}
		try {
			return getStringMD5(passwd.getBytes(HTTP.UTF_8));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getStringMD5(byte[] passwd) {

//		StringBuffer strbuf = new StringBuffer();
		messageDigest.update(passwd);
		byte[] digest = messageDigest.digest();

		return bufferToHex(digest);
//		for (int i = 0; i < digest.length; i++) {
//			strbuf.append(byte2Hex(digest[i]));
//		}
//
//		return strbuf.toString();
	}

//	private static String byte2Hex(byte b) {
//		int value = (b & 0x7F) + (b < 0 ? 0x80 : 0);
//		return (value < 0x10 ? "0" : "")
//				+ Integer.toHexString(value).toLowerCase();
//	}
	
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
}
