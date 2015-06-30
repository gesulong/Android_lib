package com.bjanch.www.kinmentv.bean;

import java.util.List;

public class LiveList {

    private List<LiveBean> data;
    private String success;

    public List<LiveBean> getData() {
        return data;
    }

    public void setData(List<LiveBean> data) {
        this.data = data;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
