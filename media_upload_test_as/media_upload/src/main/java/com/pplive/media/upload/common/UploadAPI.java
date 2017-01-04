package com.pplive.media.upload.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import okhttp3.Call;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;

import com.pplive.media.upload.UploadManager;
import com.pplive.media.upload.bean.RespBean;
import com.pplive.media.upload.bean.UploadInfo;
import com.pplive.media.upload.bean.UploadRangeBean.DataBean.RangesBean;
import com.pplive.media.upload.bean.UploadingProgressResp;
import com.pplive.media.upload.util.Base64;
import com.pplive.media.upload.util.FileMD5;
import com.pplive.media.upload.util.GsonUtil;
import com.pplive.media.upload.util.LogUtils;
import com.pplive.media.upload.util.StringUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

public class UploadAPI {
	public static final String TAG = UploadAPI.class.getName();
	public static final int EXCEPTION_CODE = -1;
	public static final int NULL_RANGE_CODE = -2;
	private static final String FEATURE_PPLIVE = "feature_pplive";
	private static final String PPFEATURE = "ppfeature";
	private static final String SEGS = "segs";
	private static final String AUTHORIZATION = "Authorization";
	private static final String ETAG = "Etag";
	private static final String INNER = "inner";
	private static final String RANGE_MD5 = "range_md5";
	private static final String UPLOADID = "uploadid";
	private static final String MD5 = "md5";
	private static final String APITK = "apitk";
	private static final String CATEGORYID = "categoryid";
	private static final String FROMCP = "fromcp";

	private UploadAPI() {
	}

	private static final UploadAPI sInstance = new UploadAPI();

	public static UploadAPI getInstance() {
		return sInstance;
	}

	public void getUploadProgress(final UploadInfo info) {
		UploadAPI.getInstance().getUploadProgress(new StringCallback() {

			@Override
			public void onResponse(String arg0, int arg1) {
				LogUtils.error("getUploadProgress response =" + arg0.toString());
				UploadingProgressResp resp = GsonUtil.fromJson(arg0, UploadingProgressResp.class);
				int finish = resp.getData().getFinished();
				info.setProgress(finish);
				UploadManager.getInstance().notifyUploadInfo(info);
			}

			@Override
			public void onError(Call arg0, Exception arg1, int arg2) {
				LogUtils.error("getUploadProgress e =" + arg1.toString());
			}
		}, info);
	}

	// 获取FID
	public void getFid(StringCallback callback, UploadInfo info) {
		OkHttpUtils.get().url("http://115.231.44.26:8081/uploadtest/uptoken").addParams("name", info.getName())
				.addParams("length", info.getSize()).addParams(PPFEATURE, info.getPpfeature()).build()
				.execute(callback);
	}

	// 上传范围
	public void getUploadRange(StringCallback callback, UploadInfo info) {
		OkHttpUtils.get().url(Constants.PPCLOUD_PUBLIC_UPLOADRANGE_URL + info.getFid() + Constants.UPLOADRANGE_END)
				.addHeader(AUTHORIZATION, info.getToken()).addParams(FEATURE_PPLIVE, info.getPpfeature())
				.addParams(SEGS, "1").addParams("fromcp", "ppcloud").addParams(INNER, "false").build()
				.execute(callback);
	}

	// 上传分段文件
	public int uploadFile(List<RangesBean> ranges, final UploadInfo info) throws IOException {
		LogUtils.error("上传分段文件 : ==uploadFile(ranges)== fid:" + info.getFid());
		File file = new File(info.getLocalPath());
		BasicHttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, 1000 * 20);
		HttpConnectionParams.setSoTimeout(httpParameters, 1000 * 20);
		HttpClient httpClient = new DefaultHttpClient(httpParameters);

		int code = EXCEPTION_CODE;

		if (ranges != null) {
			for (int i = 0; i < ranges.size(); i++) {
				final int range = i;
				String segMd5 = UploadManager.getInstance().getRangeMd5(info, ranges.get(i).getStart(),
						ranges.get(i).getEnd());
				long start = ranges.get(i).getStart();
				long end = ranges.get(i).getEnd();
				String uploadUrl = ranges.get(i).getUpload_url();
				byte[] rangeMd5Bytes = FileMD5.getFileRangeMD5Bytes(file, start, end);
				String rangeMd5 = FileMD5.bufferToHex(rangeMd5Bytes);
				HttpPut putAzureRequest = new HttpPut(uploadUrl);
				putAzureRequest.addHeader(AUTHORIZATION, info.getToken());
				putAzureRequest.addHeader(ETAG, segMd5);
				putAzureRequest.addHeader("Content-MD5", Base64.encode(rangeMd5Bytes));
				FileInputStream sourceStream = null;
				try {
					sourceStream = new FileInputStream(file);
					sourceStream.skip(start);
					InputStreamEntity entity = new InputStreamEntity(sourceStream, end - start + 1);
					putAzureRequest.setEntity(entity);
					HttpResponse response = httpClient.execute(putAzureRequest);
					StatusLine statusLine = response.getStatusLine();
					code = statusLine.getStatusCode();
					LogUtils.error("fid:" + info.getFid() + "code = " + code + " ,range = " + ranges.get(i).getStart()
							+ " , " + ranges.get(i).getEnd());
					if (code == 201) {
						LogUtils.error("上传成功 fid:" + info.getFid() + " range " + range);
						// if (range == ranges.size() - 1) {
						// int finishSize = ranges.get(i).getEnd()
						// - ranges.get(i).getStart();
						// info.setProgress(finishSize +
						// info.getProgress());
						// UploadManager.getInstance().notifyUploadInfo(info);

						getUploadProgress(info);

						String uploadId = null;
						Header header = response.getFirstHeader(ETAG);
						uploadId = header.getValue();
						// 上传汇报
						uploadReport(rangeMd5, ranges.get(i).getBid(), uploadId, new StringCallback() {

							@Override
							public void onResponse(String arg0, int arg1) {
								LogUtils.error("uploadReport fid:" + info.getFid() + "response =" + arg0.toString());
								RespBean respBean = GsonUtil.fromJson(arg0, RespBean.class);
								if (respBean != null) {
									if (respBean.getErr() == 0) {
										LogUtils.error("汇报成功：fid:" + info.getFid() + " range " + range);
									}
								}
							}

							@Override
							public void onError(Call arg0, Exception arg1, int arg2) {
								LogUtils.error("uploadReport e =" + arg1.toString());
								LogUtils.error("汇报失败：" + " range " + range);
							}
						}, info);
						code = 4;
					} else {
						LogUtils.error("上传失败" + " range " + range);
						code = 3;
					}
				} catch (Exception e) {
					LogUtils.error("uploadFile fid:" + info.getFid() + " e = " + e.toString());
					code = 3;
				} finally {
					if (sourceStream != null) {
						sourceStream.close();
					}
				}
			}
			// 进行下一轮上传
			UploadManager.getInstance().uploadFile(info);
			LogUtils.error("进行下一轮上传  fid:" + info.getFid());
		}
		return code;
	}

	// 上传汇报
	private void uploadReport(String rangeMD5, String bid, String uploadid, StringCallback callback, UploadInfo info) {
		OkHttpUtils.post()
				.url(Constants.PPCLOUD_PUBLIC_UPLOADRANGE_REPORT_URL + info.getFid() + Constants.UPLOADRANGE_REPORT_END)
				.addHeader(AUTHORIZATION, info.getToken()).addParams(RANGE_MD5, rangeMD5).addParams("bid", bid)
				.addParams(UPLOADID, uploadid).build().execute(callback);
	}

	// 提交MD5
	public void sendMd5(StringCallback callback, UploadInfo info) {
		String filePath = info.getLocalPath();
		if (StringUtil.isEmpty(filePath)) {
			return;
		}
		String fileMd5 = FileMD5.getFileMD5String(new File(filePath));
		if (StringUtil.isEmpty(fileMd5)) {
			return;
		}
		LogUtils.error("fileMd5 =" + fileMd5);
		OkHttpUtils.post().url(Constants.PPCLOUD_PUBLIC_REPORT_MD5_URL + info.getFid() + Constants.REPORT_MD5_END)
				.addHeader(AUTHORIZATION, info.getToken()).addParams(FEATURE_PPLIVE, info.getPpfeature())
				.addParams(MD5, fileMd5).build().execute(callback);
	}

	// 上传进度
	public void getUploadProgress(StringCallback callback, UploadInfo info) {
		OkHttpUtils.get()
				.url(Constants.PPCLOUD_PUBLIC_UPLOAD_PROGRESS_URL + info.getFid() + Constants.UPLOAD_PROGRESS_END)
				.addHeader(AUTHORIZATION, info.getToken()).addParams(FROMCP, "ppcloud").build().execute(callback);
	}
}
