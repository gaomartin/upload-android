package com.pplive.media.upload.common;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import com.pplive.media.upload.util.FeathureUtil;
import com.pplive.media.upload.util.MD5Util;

public class UploadHelper {

	private String fid;
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	private UploadHelper() {
	}

	private static final UploadHelper sInstance = new UploadHelper();

	public static UploadHelper getInstance() {
		return sInstance;
	}

	public String calculatePpfeature(String path) {
		String ppFeature = "";
		try {
			ppFeature = FeathureUtil.getPPFeature(path);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ppFeature;
	}
}
