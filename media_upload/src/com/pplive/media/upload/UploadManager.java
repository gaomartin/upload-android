package com.pplive.media.upload;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Call;
import android.content.Context;

import com.pplive.media.upload.bean.FidRespBean;
import com.pplive.media.upload.bean.RespBean;
import com.pplive.media.upload.bean.UploadInfo;
import com.pplive.media.upload.bean.UploadRangeBean;
import com.pplive.media.upload.bean.UploadRangeBean.DataBean.RangesBean;
import com.pplive.media.upload.common.Constants;
import com.pplive.media.upload.common.UploadAPI;
import com.pplive.media.upload.common.UploadHelper;
import com.pplive.media.upload.db.UploadDataBaseManager;
import com.pplive.media.upload.util.ApplogManager;
import com.pplive.media.upload.util.DirectoryManager;
import com.pplive.media.upload.util.FeathureUtil;
import com.pplive.media.upload.util.FileMD5;
import com.pplive.media.upload.util.GsonUtil;
import com.pplive.media.upload.util.LogUtils;
import com.pplive.media.upload.util.MD5Util;
import com.zhy.http.okhttp.callback.StringCallback;

public class UploadManager {
	private static Context mContext;
	public static String PPYUN_USERNAME = Constants.PPYUN_USERNAME;
	private static final UploadManager sInstance = new UploadManager();
	private static UploadDataBaseManager mUploadManager;
	private String mVideopath;
	private String apitk;
	private String ppfeature;
	private String size;
	private static ExecutorService ex;
	private List<UploadThread> threads = new ArrayList<UploadThread>();
	public static final String version = "1.0.20160902.0";
	private int fileStatus;
	
	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getPpfeature() {
		return ppfeature;
	}

	public void setPpfeature(String ppfeature) {
		this.ppfeature = ppfeature;
	}

	public String getApitk() {
		return apitk;
	}

	public void setApitk(String apitk) {
		this.apitk = apitk;
	}

	public String getVideopath() {
		return mVideopath;
	}

	public void setVideopath(String videopath) {
		this.mVideopath = videopath;
	}

	public static UploadManager init(Context context) {
		mContext = context;
		ex = Executors.newCachedThreadPool();
		mUploadManager = UploadDataBaseManager.getInstance(mContext);
		initLog(context);
		return sInstance;
	}

	private static void initLog(Context context) {
		File f = context.getExternalCacheDir();
		if (f == null) {
			LogUtils.error("getExternalCacheDir invalid");
			f = context.getCacheDir();
		}
		if (!f.canWrite() || f.getUsableSpace() < 1048576) {
			LogUtils.error("logPath invalid");
		}
		String logPath = f.getAbsolutePath();// /storage/emulated/0/Android/data/com.pplive.videoplayer.test/cache
		ApplogManager.getInstance().init(mContext, logPath);
		LogUtils.error("media_upload_sdk init success. version=" + version
				+ ", logPath=" + logPath);
	}

	public static UploadManager getInstance() {
		return sInstance;
	}

	private UploadManager() {
	}

	public void insertVideo(String videoPath, String name) {
		mVideopath = videoPath;
		UploadInfo info = new UploadInfo();
		info.setSize(DirectoryManager.getVideoFileSize(videoPath) + "");
		info.setPpfeature(calculatePpfeature(videoPath));
		info.setLocalPath(videoPath);
		info.setState(UploadInfo.STATE_WAIT);
		info.setUserName(Constants.PPYUN_USERNAME);
		info.setName(name);
		info.setCategoryId(Constants.CATEGORY_ID);
		getFid(info);
	}

	private UpLoadManagerListener listener;

	public UpLoadManagerListener getListener() {
		return listener;
	}

	public void setListener(UpLoadManagerListener listener) {
		this.listener = listener;
	}

	public synchronized void notifyUploadInfo(UploadInfo info) {
		mUploadManager.updateUpload(info);
		listener.onStateChange(info);
	}

	public void getFid(final UploadInfo info) {
		UploadAPI.getInstance().getFid(new StringCallback() {
			@Override
			public void onError(Call arg0, Exception arg1, int arg2) {
				LogUtils.error("getFid e =" + arg1.toString());
				listener.onAddUploadTask(false);
			}

			@Override
			public void onResponse(String arg0, int arg1) {
				FidRespBean fidRespBean = GsonUtil.fromJson(arg0,
						FidRespBean.class);
				if (fidRespBean != null) {
					LogUtils.error("getFid fid = "
							+ fidRespBean.getData().getFId()
							+ "  file_status = "
							+ fidRespBean.getData().getFile_status());
					fileStatus = fidRespBean.getData().getFile_status();
					UploadHelper.getInstance().setFid(
							fidRespBean.getData().getFId() + "");
					getToken(info);// 获取token
				} else {
					listener.onAddUploadTask(false);
				}
			}
		}, info);
	}

	public void getToken(final UploadInfo info) {
		UploadAPI.getInstance().getToken(new StringCallback() {
			@Override
			public void onError(Call arg0, Exception arg1, int arg2) {
				LogUtils.error("getToken e =" + arg1.toString());
				listener.onAddUploadTask(false);
			}

			@Override
			public void onResponse(String arg0, int arg1) {
				LogUtils.error("getToken response =" + arg0.toString());
				RespBean respBean = GsonUtil.fromJson(arg0, RespBean.class);
				if (respBean != null && "success".equals(respBean.getMsg())) {
					LogUtils.error("token = " + respBean.getData());
					UploadHelper.getInstance().setToken(respBean.getData());
					listener.onAddUploadTask(true);
					info.setToken(respBean.getData());
					info.setFid(UploadHelper.getInstance().getFid());
					LogUtils.error("insertUpload info = " + info.toString());
					mUploadManager.insertUpload(info);

					
					if (fileStatus < 100) {
						// 提交MD5
						UploadAPI.getInstance().sendMd5(new StringCallback() {
							@Override
							public void onResponse(String arg0, int arg1) {
								LogUtils.error("sendMd5 response ="
										+ arg0.toString());
								RespBean respBean = GsonUtil.fromJson(arg0,
										RespBean.class);
								if (respBean != null) {
									if (respBean.getErr() == 0) {
										LogUtils.error("MD5提交成功 fid:"
												+ info.getFid());
									} else {
										LogUtils.error("MD5提交失败");
									}
								}
							}

							@Override
							public void onError(Call arg0, Exception arg1,
									int arg2) {
								LogUtils.error("sendMd5 e =" + arg1.toString());
								LogUtils.error("MD5提交失败");
							}
						}, info);
					}
				} else {
					listener.onAddUploadTask(false);
				}
			}
		}, info);
	}

	public void updataUploadThread(UploadInfo info) {
		for (UploadThread ut : threads) {
			if (ut.getInfo().getId() == info.getId()) {
				ut.getInfo().setPause(info.isPause());
			}
		}
	}

	public void uploadFile(final UploadInfo info) {
		LogUtils.error("===uploadFile(info)=== fid =" + info.getFid()
				+ " state =" + info.getState());
		for (UploadThread ut : threads) {
			if (ut.getInfo().getId() == info.getId()) {
				if (ut.getInfo().isPause()) {
					return;
				}
			}
		}
		LogUtils.error("===uploadFile(info)=== fid =" + info.getFid());
		UploadAPI.getInstance().getUploadRange(new StringCallback() {
			@Override
			public void onError(Call arg0, Exception arg1, int arg2) {
				LogUtils.error("getUploadRange e =" + arg1.toString());
			}

			@Override
			public void onResponse(String arg0, int arg1) {
				LogUtils.error("getUploadRange fid:" + info.getFid()
						+ "response =" + arg0.toString());
				UploadRangeBean uploadRangeBean = GsonUtil.fromJson(arg0,
						UploadRangeBean.class);
				if (uploadRangeBean != null) {
					final List<RangesBean> ranges = uploadRangeBean.getData()
							.getRanges();
					final int status = uploadRangeBean.getData().getStatus();
					if (status >= 100) {
						info.setState(UploadInfo.STATE_UPLOAD_SUCCESS);
						info.setProgress(Integer.valueOf(info.getSize()));
						listener.onUploadSuccess(info);
						mUploadManager.updateUpload(info);
						return;
					}
					start(ranges, info);
				}
			}
		}, info);
	}

	public String getApitk(String key, String url) {
		StringBuffer sbf = new StringBuffer(key);
		sbf.append(url);
		return MD5Util.getStringMD5(sbf.toString());
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

	public String getRangeMd5(UploadInfo info, long start, long end) {
		return FileMD5.getFileRangeMD5String(new File(info.getLocalPath()),
				start, end);
	}

	private void start(List<RangesBean> mRanges, UploadInfo info) {
		if (info.getState() == UploadInfo.STATE_UPLOADING) {
			UploadThread ut = new UploadThread(info);
			ut.setRanges(mRanges);
			ut.start();
			threads.add(ut);
			mUploadManager.updateUpload(info);
		}
	}

	public void onDestory() {
		ex.shutdownNow();
	}

	class UploadThread {

		private UploadInfo info;

		private List<RangesBean> mRanges;

		public UploadInfo getInfo() {
			return info;
		}

		public void setInfo(UploadInfo info) {
			this.info = info;
		}

		public UploadThread(UploadInfo info) {
			this.info = info;
		}

		private void start() {
			ex.execute(new Runnable() {
				public void run() {
					try {
						if (info.getState() == UploadInfo.STATE_UPLOADING) {
							int code = UploadAPI.getInstance().uploadFile(
									mRanges, info);
							LogUtils.error("uploadfile resp code = " + code);
							if (code == 3) {
								info.setState(UploadInfo.STATE_UPLOAD_FALI);
								listener.onUploadError(info);
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
		}

		public List<RangesBean> getRanges() {
			return mRanges;
		}

		public void setRanges(List<RangesBean> mRanges) {
			this.mRanges = mRanges;
		}

		public long getInfoId() {
			return info == null ? 0 : info.getId();
		}
	}

	public void updateUpload(UploadInfo info) {
		mUploadManager.updateUpload(info);
	}

	public void deleteUploadById(int id) {
		mUploadManager.deleteUploadById(id);
	}

	public List<UploadInfo> searchAllUploads() {
		return mUploadManager.searchAllUploads(PPYUN_USERNAME);
	}

	public List<String> searchVideoPaths() {
		return mUploadManager.searchVideoPaths(PPYUN_USERNAME);
	}
}
