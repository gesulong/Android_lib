package com.bjanch.www.kinmentv.bean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UpDataInfo implements Serializable {

    private String verName;
    private boolean verDate;
    private String verCode;
    private String apkUrl;
    private String verDetail;

    public String getVerDetail() {
        return verDetail;
    }

    public void setVerDetail(String verDetail) {
        this.verDetail = verDetail;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }

    public boolean isVerDate() {
        return verDate;
    }

    public void setVerDate(boolean verDate) {
        this.verDate = verDate;
    }

    public String getVerCode() {
        return verCode;
    }

    public void setVerCode(String verCode) {
        this.verCode = verCode;
    }

    public String getVerName() {
        return verName;
    }

    public void setVerName(String verName) {
        this.verName = verName;
    }
}
