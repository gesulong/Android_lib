package com.bjanch.www.kinmentv.bean;

import java.util.List;

/**
 * Created by Administrator on 2015/4/17.
 */
public class KPros {

    private List<KinmenProgram> data;
    private String success;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<KinmenProgram> getData() {
        return data;
    }

    public void setData(List<KinmenProgram> data) {
        this.data = data;
    }
}
