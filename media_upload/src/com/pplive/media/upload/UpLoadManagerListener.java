package com.pplive.media.upload;

import com.pplive.media.upload.bean.UploadInfo;

public interface UpLoadManagerListener {
	public void onStateChange(UploadInfo info);

	public void onUploadError(UploadInfo info);

	public void onUploadSuccess(UploadInfo info);

	public void onAddUploadTask(boolean isAddSucces);
}
