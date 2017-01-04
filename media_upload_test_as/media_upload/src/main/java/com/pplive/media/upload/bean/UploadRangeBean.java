package com.pplive.media.upload.bean;

import java.util.List;

/**
 * Created by weihu on 2016/8/2.
 */
public class UploadRangeBean {

    /**
     * status : 0
     * fileSize : 54227147
     * finished : 0
     * ranges : [{"end":4194303,"start":0,"bid":"0-MmMwNDVkOTUtNzQxNC00Njc4LTg5ZTMtNDIyYjBjZTc0ZTg3","upload_url":"http://10.200.10.148/v1/AUTH_9960b6f099f249e5b6d726dd878aafe2/video121/75302/54227147-54227147_f0472860eee1382bae0686bd1727963f8d81531e.ppc/0-MmMwNDVkOTUtNzQxNC00Njc4LTg5ZTMtNDIyYjBjZTc0ZTg3?temp_url_sig=1f879436c51423e008087438e3eb1e47a9cb8617&temp_url_expires=1470126256"},{"end":8388607,"start":4194304,"bid":"1-MTMxMGYyNTktNGY0Ny00MWVlLWJiMTMtNjgyZjY0ZTg4NmVl","upload_url":"http://10.200.10.148/v1/AUTH_9960b6f099f249e5b6d726dd878aafe2/video121/75302/54227147-54227147_f0472860eee1382bae0686bd1727963f8d81531e.ppc/1-MTMxMGYyNTktNGY0Ny00MWVlLWJiMTMtNjgyZjY0ZTg4NmVl?temp_url_sig=d5dbe2c4657016d341b133e64ea38a02c0463298&temp_url_expires=1470126256"},{"end":46137343,"start":41943040,"bid":"10-OTE5NmFjNWEtZmZiYS00NTRjLTg5YTQtYmY4MDc1YWZiZjZl","upload_url":"http://10.200.10.148/v1/AUTH_9960b6f099f249e5b6d726dd878aafe2/video121/75302/54227147-54227147_f0472860eee1382bae0686bd1727963f8d81531e.ppc/10-OTE5NmFjNWEtZmZiYS00NTRjLTg5YTQtYmY4MDc1YWZiZjZl?temp_url_sig=7670b8b35bac226422aff5854740261f3a7eec42&temp_url_expires=1470126256"}]
     * needMD5 : true
     * needGCID : false
     * needCID : false
     */

    private DataBean data;
    /**
     * data : {"status":0,"fileSize":54227147,"finished":0,"ranges":[{"end":4194303,"start":0,"bid":"0-MmMwNDVkOTUtNzQxNC00Njc4LTg5ZTMtNDIyYjBjZTc0ZTg3","upload_url":"http://10.200.10.148/v1/AUTH_9960b6f099f249e5b6d726dd878aafe2/video121/75302/54227147-54227147_f0472860eee1382bae0686bd1727963f8d81531e.ppc/0-MmMwNDVkOTUtNzQxNC00Njc4LTg5ZTMtNDIyYjBjZTc0ZTg3?temp_url_sig=1f879436c51423e008087438e3eb1e47a9cb8617&temp_url_expires=1470126256"},{"end":8388607,"start":4194304,"bid":"1-MTMxMGYyNTktNGY0Ny00MWVlLWJiMTMtNjgyZjY0ZTg4NmVl","upload_url":"http://10.200.10.148/v1/AUTH_9960b6f099f249e5b6d726dd878aafe2/video121/75302/54227147-54227147_f0472860eee1382bae0686bd1727963f8d81531e.ppc/1-MTMxMGYyNTktNGY0Ny00MWVlLWJiMTMtNjgyZjY0ZTg4NmVl?temp_url_sig=d5dbe2c4657016d341b133e64ea38a02c0463298&temp_url_expires=1470126256"},{"end":46137343,"start":41943040,"bid":"10-OTE5NmFjNWEtZmZiYS00NTRjLTg5YTQtYmY4MDc1YWZiZjZl","upload_url":"http://10.200.10.148/v1/AUTH_9960b6f099f249e5b6d726dd878aafe2/video121/75302/54227147-54227147_f0472860eee1382bae0686bd1727963f8d81531e.ppc/10-OTE5NmFjNWEtZmZiYS00NTRjLTg5YTQtYmY4MDc1YWZiZjZl?temp_url_sig=7670b8b35bac226422aff5854740261f3a7eec42&temp_url_expires=1470126256"}],"needMD5":true,"needGCID":false,"needCID":false}
     * err : 0
     */

    private int err;

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

    public static class DataBean {
        private int status;
        private int fileSize;
        private int finished;
        private boolean needMD5;
        private boolean needGCID;
        private boolean needCID;
        /**
         * end : 4194303
         * start : 0
         * bid : 0-MmMwNDVkOTUtNzQxNC00Njc4LTg5ZTMtNDIyYjBjZTc0ZTg3
         * upload_url : http://10.200.10.148/v1/AUTH_9960b6f099f249e5b6d726dd878aafe2/video121/75302/54227147-54227147_f0472860eee1382bae0686bd1727963f8d81531e.ppc/0-MmMwNDVkOTUtNzQxNC00Njc4LTg5ZTMtNDIyYjBjZTc0ZTg3?temp_url_sig=1f879436c51423e008087438e3eb1e47a9cb8617&temp_url_expires=1470126256
         */

        private List<RangesBean> ranges;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getFileSize() {
            return fileSize;
        }

        public void setFileSize(int fileSize) {
            this.fileSize = fileSize;
        }

        public int getFinished() {
            return finished;
        }

        public void setFinished(int finished) {
            this.finished = finished;
        }

        public boolean isNeedMD5() {
            return needMD5;
        }

        public void setNeedMD5(boolean needMD5) {
            this.needMD5 = needMD5;
        }

        public boolean isNeedGCID() {
            return needGCID;
        }

        public void setNeedGCID(boolean needGCID) {
            this.needGCID = needGCID;
        }

        public boolean isNeedCID() {
            return needCID;
        }

        public void setNeedCID(boolean needCID) {
            this.needCID = needCID;
        }

        public List<RangesBean> getRanges() {
            return ranges;
        }

        public void setRanges(List<RangesBean> ranges) {
            this.ranges = ranges;
        }

        public static class RangesBean {
            private int end;
            private int start;
            private String bid;
            private String upload_url;

            public int getEnd() {
                return end;
            }

            public void setEnd(int end) {
                this.end = end;
            }

            public int getStart() {
                return start;
            }

            public void setStart(int start) {
                this.start = start;
            }

            public String getBid() {
                return bid;
            }

            public void setBid(String bid) {
                this.bid = bid;
            }

            public String getUpload_url() {
                return upload_url;
            }

            public void setUpload_url(String upload_url) {
                this.upload_url = upload_url;
            }

			@Override
			public String toString() {
				return "RangesBean [end=" + end + ", start=" + start + ", bid="
						+ bid + ", upload_url=" + upload_url + "]";
			}
            
        }

		@Override
		public String toString() {
			return "DataBean [status=" + status + ", fileSize=" + fileSize
					+ ", finished=" + finished + ", needMD5=" + needMD5
					+ ", needGCID=" + needGCID + ", needCID=" + needCID
					+ ", ranges=" + ranges + "]";
		}
    }

	@Override
	public String toString() {
		return "UploadRangeBean [data=" + data + ", err=" + err + "]";
	}
    
}
