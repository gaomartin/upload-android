package com.pplive.media.upload.common;

public class Constants {
	public static String UPLOADRANGE_END = "/action/uploadrange";
	public static String UPLOADRANGE_REPORT_END = "/action/uploaded";
	public static String REPORT_MD5_END = "/md5";
	public static String UPLOAD_PROGRESS_END = "/uploading";
	public static final String PPCLOUD_HOST = "http://svc.pptvyun.com";
	public static final String PPCLOUD_PUBLIC_HOST = "http://api.cloudplay.pptv.com";
	public static final String PPCLOUD_URL = Constants.PPCLOUD_HOST + "/svc/v1/api/channel/upload";
	public static final String PPCLOUD_TOKEN_URL = Constants.PPCLOUD_HOST + "/svc/v1/api/token/uptoken";
	public static final String PPCLOUD_PUBLIC_URL = Constants.PPCLOUD_PUBLIC_HOST + "/svc/v1/api/channel/upload";
	public static final String PPCLOUD_PUBLIC_UPLOADRANGE_URL = Constants.PPCLOUD_PUBLIC_HOST + "/fsvc/3/file/";
	public static final String PPCLOUD_PUBLIC_UPLOADRANGE_REPORT_URL = Constants.PPCLOUD_PUBLIC_HOST + "/fsvc/3/file/";
	public static final String PPCLOUD_PUBLIC_REPORT_MD5_URL = Constants.PPCLOUD_PUBLIC_HOST + "/fsvc/3/file/";
	public static final String PPCLOUD_PUBLIC_UPLOAD_PROGRESS_URL = Constants.PPCLOUD_PUBLIC_HOST + "/fsvc/3/file/";
	public static String PPCLOUC_PUBLIC_UPTOKEN;
}
