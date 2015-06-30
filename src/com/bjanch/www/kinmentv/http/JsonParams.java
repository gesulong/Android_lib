package com.bjanch.www.kinmentv.http;

import com.bjanch.www.kinmentv.bean.KPros;
import com.bjanch.www.kinmentv.bean.KmProgramContentBean;
import com.bjanch.www.kinmentv.bean.KmProgramSortBean;
import com.bjanch.www.kinmentv.bean.LiveList;
import com.bjanch.www.kinmentv.bean.MagazineList;
import com.bjanch.www.kinmentv.bean.OnDemandDetailsBean;
import com.bjanch.www.kinmentv.bean.OnDemandSlContentBean;
import com.bjanch.www.kinmentv.bean.UpDataInfo;
import com.google.gson.Gson;

/**
 * Created by Administrator on 2015/4/9.
 */
public class JsonParams {

    private static Gson gson = new Gson();

    //所有视频Bean
    public static OnDemandSlContentBean getOnDemandContent(String json) {
        return gson.fromJson(json, OnDemandSlContentBean.class);
    }

    //点播详情Bean
    public static OnDemandDetailsBean getOnDemandDetail(String json) {
        return gson.fromJson(json, OnDemandDetailsBean.class);
    }

    //节目列表筛选Bean
    public static KmProgramSortBean getKmProgramSortBean(String json) {
        return gson.fromJson(json, KmProgramSortBean.class);
    }

    //节目内容Bean
    public static KmProgramContentBean getKmProgramContentBean(String json) {
        return gson.fromJson(json, KmProgramContentBean.class);
    }

    public static LiveList getLiveList(String json) {
        return gson.fromJson(json, LiveList.class);
    }

    public static KPros getKPros(String json) {
        return gson.fromJson(json, KPros.class);
    }

    public static MagazineList getMagazineList(String json) {
        return gson.fromJson(json, MagazineList.class);
    }

    //更新
    public static UpDataInfo getUpdataInfo(String json) {
        return gson.fromJson(json, UpDataInfo.class);
    }
}
