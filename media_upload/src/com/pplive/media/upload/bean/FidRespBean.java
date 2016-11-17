package com.pplive.media.upload.bean;

public class FidRespBean {

	/**
	 * id : 1465688 categoryId : 1247 userId : 594 subUserId : 594 channelName :
	 * ppuploadTest channelSummary : null coverImage : null fId : 75302 isPlay :
	 * 1 channelId : 156592369 channelType : 1 createTime : 1470105449560
	 * channelWebId : 0a2dnqyaqaKfn6-L4K2hoqrhoaSinqydqA length : 54227147
	 * ppfeature : 54227147_f0472860eee1382bae0686bd1727963f8d81531e userName :
	 * null transcodeStatus : 0 liveStatus : null duration : 0 screenshot : null
	 * vod_status : 0 file_status : 0 extend :
	 * {"username":"jiahuixu@pptv.com","title":"ppuploadTest","cata":"科教"}
	 * epg_category_id : null info_status : null pptvsPlayStr :
	 * pptvs://4NzN4tvXr%2BTl2dui4ODi2NqL09jN1%2BTK3M%2FVzbOWpaahoqiYpqk%3D
	 */

	private DataBean data;
	/**
	 * data : {"id":1465688,"categoryId":1247,"userId":594,"subUserId":594,
	 * "channelName"
	 * :"ppuploadTest","channelSummary":null,"coverImage":null,"fId"
	 * :75302,"isPlay"
	 * :1,"channelId":156592369,"channelType":1,"createTime":1470105449560
	 * ,"channelWebId"
	 * :"0a2dnqyaqaKfn6-L4K2hoqrhoaSinqydqA","length":54227147,"ppfeature"
	 * :"54227147_f0472860eee1382bae0686bd1727963f8d81531e"
	 * ,"userName":null,"transcodeStatus"
	 * :0,"liveStatus":null,"duration":0,"screenshot"
	 * :null,"vod_status":0,"file_status"
	 * :0,"extend":{"username":"jiahuixu@pptv.com"
	 * ,"title":"ppuploadTest","cata":
	 * "科教"},"epg_category_id":null,"info_status":null,"pptvsPlayStr":
	 * "pptvs://4NzN4tvXr%2BTl2dui4ODi2NqL09jN1%2BTK3M%2FVzbOWpaahoqiYpqk%3D"}
	 * err : 0 msg : addUserChannel success
	 */

	private int err;
	private String msg;

	public DataBean getData() {
		return data;
	}

	public void setData(DataBean data) {
		this.data = data;
	}

	public int getErr() {
		return err;
	}

	public void setErr(int err) {
		this.err = err;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public class DataBean {
		private int id;
		private int categoryId;
		private int userId;
		private int subUserId;
		private String channelName;
		private Object channelSummary;
		private Object coverImage;
		private int fId;
		private int isPlay;
		private int channelId;
		private int channelType;
		private long createTime;
		private String channelWebId;
		private int length;
		private String ppfeature;
		private Object userName;
		private int transcodeStatus;
		private Object liveStatus;
		private int duration;
		private Object screenshot;
		private int vod_status;
		private int file_status;
		/**
		 * username : jiahuixu@pptv.com title : ppuploadTest cata : 科教
		 */

		private ExtendBean extend;
		private Object epg_category_id;
		private Object info_status;
		private String pptvsPlayStr;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getCategoryId() {
			return categoryId;
		}

		public void setCategoryId(int categoryId) {
			this.categoryId = categoryId;
		}

		public int getUserId() {
			return userId;
		}

		public void setUserId(int userId) {
			this.userId = userId;
		}

		public int getSubUserId() {
			return subUserId;
		}

		public void setSubUserId(int subUserId) {
			this.subUserId = subUserId;
		}

		public String getChannelName() {
			return channelName;
		}

		public void setChannelName(String channelName) {
			this.channelName = channelName;
		}

		public Object getChannelSummary() {
			return channelSummary;
		}

		public void setChannelSummary(Object channelSummary) {
			this.channelSummary = channelSummary;
		}

		public Object getCoverImage() {
			return coverImage;
		}

		public void setCoverImage(Object coverImage) {
			this.coverImage = coverImage;
		}

		public int getFId() {
			return fId;
		}

		public void setFId(int fId) {
			this.fId = fId;
		}

		public int getIsPlay() {
			return isPlay;
		}

		public void setIsPlay(int isPlay) {
			this.isPlay = isPlay;
		}

		public int getChannelId() {
			return channelId;
		}

		public void setChannelId(int channelId) {
			this.channelId = channelId;
		}

		public int getChannelType() {
			return channelType;
		}

		public void setChannelType(int channelType) {
			this.channelType = channelType;
		}

		public long getCreateTime() {
			return createTime;
		}

		public void setCreateTime(long createTime) {
			this.createTime = createTime;
		}

		public String getChannelWebId() {
			return channelWebId;
		}

		public void setChannelWebId(String channelWebId) {
			this.channelWebId = channelWebId;
		}

		public int getLength() {
			return length;
		}

		public void setLength(int length) {
			this.length = length;
		}

		public String getPpfeature() {
			return ppfeature;
		}

		public void setPpfeature(String ppfeature) {
			this.ppfeature = ppfeature;
		}

		public Object getUserName() {
			return userName;
		}

		public void setUserName(Object userName) {
			this.userName = userName;
		}

		public int getTranscodeStatus() {
			return transcodeStatus;
		}

		public void setTranscodeStatus(int transcodeStatus) {
			this.transcodeStatus = transcodeStatus;
		}

		public Object getLiveStatus() {
			return liveStatus;
		}

		public void setLiveStatus(Object liveStatus) {
			this.liveStatus = liveStatus;
		}

		public int getDuration() {
			return duration;
		}

		public void setDuration(int duration) {
			this.duration = duration;
		}

		public Object getScreenshot() {
			return screenshot;
		}

		public void setScreenshot(Object screenshot) {
			this.screenshot = screenshot;
		}

		public int getVod_status() {
			return vod_status;
		}

		public void setVod_status(int vod_status) {
			this.vod_status = vod_status;
		}

		public int getFile_status() {
			return file_status;
		}

		public void setFile_status(int file_status) {
			this.file_status = file_status;
		}

		public ExtendBean getExtend() {
			return extend;
		}

		public void setExtend(ExtendBean extend) {
			this.extend = extend;
		}

		public Object getEpg_category_id() {
			return epg_category_id;
		}

		public void setEpg_category_id(Object epg_category_id) {
			this.epg_category_id = epg_category_id;
		}

		public Object getInfo_status() {
			return info_status;
		}

		public void setInfo_status(Object info_status) {
			this.info_status = info_status;
		}

		public String getPptvsPlayStr() {
			return pptvsPlayStr;
		}

		public void setPptvsPlayStr(String pptvsPlayStr) {
			this.pptvsPlayStr = pptvsPlayStr;
		}

		public class ExtendBean {
			private String username;
			private String title;
			private String cata;

			public String getUsername() {
				return username;
			}

			public void setUsername(String username) {
				this.username = username;
			}

			public String getTitle() {
				return title;
			}

			public void setTitle(String title) {
				this.title = title;
			}

			public String getCata() {
				return cata;
			}

			public void setCata(String cata) {
				this.cata = cata;
			}
		}
	}
}
