package com.pplive.media.upload.bean;

/**
 * Created by weihu on 2016/8/4.
 */
public class UploadingProgressResp {

    /**
     * status : 50
     * fileSize : 32274672
     * finished : 11303152
     */

    private DataBean data;
    /**
     * data : {"status":50,"fileSize":32274672,"finished":11303152}
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
    }
}
