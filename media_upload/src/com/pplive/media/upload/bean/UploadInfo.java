package com.pplive.media.upload.bean;

public class UploadInfo {

	public static final Integer NEW_ADD_STATUS = 0; // 表示新文件
	public static final Integer UPLOADING_STATUS = 50; // 上传中
	public static final Integer UPLOADED_STATUS = 100; // 上传完成
	public static final Integer OMS_CODING_NOTIFIED_STATUS = 101; // 通知转码完成
	public static final Integer OMS_CODING_FAILED_STATUS = 150; // 通知转码失败
	public static final Integer FINISH_CODING = 200;

	public static final int STATE_PAUSE = 0;// 暂停
	public static final int STATE_UPLOADING = 1;// 正在上传
	public static final int STATE_WAIT = 2;// 等待中
	public static final int STATE_UPLOAD_FALI = 3;// 上传失败
	public static final int STATE_UPLOAD_SUCCESS = 4;// 上传成功
	public static final int STATE_HAS_UPLOAD = 5;// 已上传

	private int finishSize;
	private int status;
	private String ppfeature;
	private String fid;
	private long pid;
	private String userName;
	private int categoryId;
	private int state;
	private String size;
	private String name;
	private String localPath;
	private long id;
	private int progress;
	private String token;
	private String apitk;
	private boolean isPause;
	private String channel_web_id;
	private int length;

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getChannel_web_id() {
		return channel_web_id;
	}

	public void setChannel_web_id(String channel_web_id) {
		this.channel_web_id = channel_web_id;
	}

	public boolean isPause() {
		return isPause;
	}

	public void setPause(boolean isPause) {
		this.isPause = isPause;
	}

	public String getApitk() {
		return apitk;
	}

	public void setApitk(String apitk) {
		this.apitk = apitk;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public int getFinishSize() {
		return finishSize;
	}

	public void setFinishSize(int finishSize) {
		this.finishSize = finishSize;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getPpfeature() {
		return ppfeature;
	}

	public void setPpfeature(String ppfeature) {
		this.ppfeature = ppfeature;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public long getPid() {
		return pid;
	}

	public void setPid(long pid) {
		this.pid = pid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocalPath() {
		return localPath;
	}

	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}

	@Override
	public String toString() {
		return "UploadInfo [finishSize=" + finishSize + ", status=" + status + ", ppfeature=" + ppfeature + ", fid="
				+ fid + ", pid=" + pid + ", userName=" + userName + ", categoryId=" + categoryId + ", state=" + state
				+ ", size=" + size + ", name=" + name + ", localPath=" + localPath + ", id=" + id + ", progress="
				+ progress + ", token=" + token + ", apitk=" + apitk + "]";
	}

}
