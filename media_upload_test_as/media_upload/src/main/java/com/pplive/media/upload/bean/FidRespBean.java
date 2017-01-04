package com.pplive.media.upload.bean;

/**
 * Created by weihu on 2016/11/30.
 */
public class FidRespBean {

    /**
     * id : 1933797
     * length : 89888993
     * ppfeature : 89888993_4f1b4b6c720f0fd5d68f4fffff2bf3e85bad6475
     * duration : 0
     * screenshot :
     * category_id : 328
     * user_id : 151
     * sub_user_id : null
     * channel_name : VIDEO0003.mp4
     * channel_summary :
     * cover_image :
     * fid : 5394351
     * channel_id : 158646741
     * create_time : 1480494708000
     * channel_web_id : 0a2dnq6bpKajnaeL4K2dnqfhoamfnK2epw
     * file_status : 0
     * up_token : 4bbM33331e774A4582dD24849R05df2e:eyJzY29wZSI6Ijg5ODg4OTkzXzRmMWI0YjZjNzIwZjBmZDVkNjhmNGZmZmZmMmJmM2U4NWJhZDY0NzUiLCJkZWFkbGluZSI6MTQ4MDU4MjE0MH0:M2E0YTlhMjI1NmY0MmI1NDE2MjdkMDdlZjFmOWVlZDM2YTE2YzhiOQ
     */

    private DataBean data;
    /**
     * data : {"id":1933797,"length":89888993,"ppfeature":"89888993_4f1b4b6c720f0fd5d68f4fffff2bf3e85bad6475","duration":0,"screenshot":"","category_id":328,"user_id":151,"sub_user_id":null,"channel_name":"VIDEO0003.mp4","channel_summary":"","cover_image":"","fid":5394351,"channel_id":158646741,"create_time":1480494708000,"channel_web_id":"0a2dnq6bpKajnaeL4K2dnqfhoamfnK2epw","file_status":0,"up_token":"4bbM33331e774A4582dD24849R05df2e:eyJzY29wZSI6Ijg5ODg4OTkzXzRmMWI0YjZjNzIwZjBmZDVkNjhmNGZmZmZmMmJmM2U4NWJhZDY0NzUiLCJkZWFkbGluZSI6MTQ4MDU4MjE0MH0:M2E0YTlhMjI1NmY0MmI1NDE2MjdkMDdlZjFmOWVlZDM2YTE2YzhiOQ"}
     * err : 0
     * msg : success
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

    public static class DataBean {
        private int id;
        private int length;
        private String ppfeature;
        private int duration;
        private String screenshot;
        private int category_id;
        private int user_id;
        private Object sub_user_id;
        private String channel_name;
        private String channel_summary;
        private String cover_image;
        private int fid;
        private int channel_id;
        private long create_time;
        private String channel_web_id;
        private int file_status;
        private String up_token;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public String getScreenshot() {
            return screenshot;
        }

        public void setScreenshot(String screenshot) {
            this.screenshot = screenshot;
        }

        public int getCategory_id() {
            return category_id;
        }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public Object getSub_user_id() {
            return sub_user_id;
        }

        public void setSub_user_id(Object sub_user_id) {
            this.sub_user_id = sub_user_id;
        }

        public String getChannel_name() {
            return channel_name;
        }

        public void setChannel_name(String channel_name) {
            this.channel_name = channel_name;
        }

        public String getChannel_summary() {
            return channel_summary;
        }

        public void setChannel_summary(String channel_summary) {
            this.channel_summary = channel_summary;
        }

        public String getCover_image() {
            return cover_image;
        }

        public void setCover_image(String cover_image) {
            this.cover_image = cover_image;
        }

        public int getFid() {
            return fid;
        }

        public void setFid(int fid) {
            this.fid = fid;
        }

        public int getChannel_id() {
            return channel_id;
        }

        public void setChannel_id(int channel_id) {
            this.channel_id = channel_id;
        }

        public long getCreate_time() {
            return create_time;
        }

        public void setCreate_time(long create_time) {
            this.create_time = create_time;
        }

        public String getChannel_web_id() {
            return channel_web_id;
        }

        public void setChannel_web_id(String channel_web_id) {
            this.channel_web_id = channel_web_id;
        }

        public int getFile_status() {
            return file_status;
        }

        public void setFile_status(int file_status) {
            this.file_status = file_status;
        }

        public String getUp_token() {
            return up_token;
        }

        public void setUp_token(String up_token) {
            this.up_token = up_token;
        }
    }
}
