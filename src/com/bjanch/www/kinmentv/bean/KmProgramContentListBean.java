package com.bjanch.www.kinmentv.bean;

/**
 * Created by wxy on 2015/5/8.
 */
public class KmProgramContentListBean {

    private String id;
    private String crPic;
    private String crName;
    private String crDate;
    private String crDetail;
    private String className;
    private String typeId;

    public String getAtId() {
        return atId;
    }

    public void setAtId(String atId) {
        this.atId = atId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    private String atId;


    public String getCrDetail() {
        return crDetail;
    }

    public void setCrDetail(String crDetail) {
        this.crDetail = crDetail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCrPic() {
        return crPic;
    }

    public void setCrPic(String crPic) {
        this.crPic = crPic;
    }

    public String getCrName() {
        return crName;
    }

    public void setCrName(String crName) {
        this.crName = crName;
    }

    public String getCrDate() {
        return crDate;
    }

    public void setCrDate(String crDate) {
        this.crDate = crDate;
    }
}
