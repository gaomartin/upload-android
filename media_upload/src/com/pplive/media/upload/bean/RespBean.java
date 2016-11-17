package com.pplive.media.upload.bean;

/**
 * Created by weihu on 2016/8/2.
 */
public class RespBean {

    /**
     * data : MzgyM2YzZjEtNzQyNi00MmEwLTg2NTQtMWIwMmQ1ODEzYjA2:eyJzY29wZSI6IjU0MjI3MTQ3X2YwNDcyODYwZWVlMTM4MmJhZTA2ODZiZDE3Mjc5NjNmOGQ4MTUzMWUiLCJkZWFkbGluZSI6MTQ3MDE5NDE5OX0:OGU5Zjg0YmZjNzdlNTIzM2VmNmI5NWY4MDEyOGIwMjViNDBhOTc5OQ
     * err : 0
     * msg : success
     */

    private String data;
    private int err;
    private String msg;

    public String getData() {
        return data;
    }

    public void setData(String data) {
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
}
