package com.bjanch.www.kinmentv.bean;

import java.io.Serializable;

/**
 * @author wxy
 */
public class ProgramSelectParamBean implements Serializable {
    //区分媒体 例如：电视剧、电影、动漫、音乐 1 2 3 4
    private Integer classCode;
    //视频上映时间 code
    private String date;
    //主演ID
    private Integer acId;
    //视频类型code
    private Integer typeId;
    //出产地code
    private Integer proCode;

    public Integer getClassCode() {
        return classCode;
    }

    public void setClassCode(Integer classCode) {
        this.classCode = classCode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getProCode() {
        return proCode;
    }

    public void setProCode(Integer proCode) {
        this.proCode = proCode;
    }

    public Integer getAcId() {
        return acId;
    }

    public void setAcId(Integer acId) {
        this.acId = acId;
    }

}
