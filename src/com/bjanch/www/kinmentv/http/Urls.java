package com.bjanch.www.kinmentv.http;

/**
 * Created by wxy on 2015/4/17.
 */
public class Urls {
    private static class UrlsHolder {
        public static final Urls INSTANCE = new Urls();
    }

    public static Urls getInstance() {
        return UrlsHolder.INSTANCE;
    }

//    private final String kinmens = "http://192.168.200.88:8080/jmtv/com/";
    private final String kinmens = "http://web.bjanch.com:8080/jmtv/com/";

    /**
     * 错误日志
     */
    public final String ERROR = kinmens + "";

    /**
     * 更新接口
     */
    public final String UPDATE = kinmens + "boxinterface/anchBoxInterfaceAction!checkVersion.action";

    /**
     * 节目分类与内容列表
     */
    public final String KM_PROGRAME_SORT_URL = kinmens + "boxinterface/anchBoxInterfaceAction!getTypeData.action";
    public final String KM_PROGRAME_CONTENT_URL = kinmens + "boxinterface/anchBoxInterfaceAction!getMediaData.action";
    //详情
    public final String KM_PROGRAME_DETAILS_URL = kinmens + "media/jmMediaAction!selectSourceList.action";

    //直播接口
    public final String LIVE_DATA = kinmens + "boxinterface/anchBoxInterfaceAction!getChannelData.action";

    //刊物接口
    public final String MAGAZINE_DATA = kinmens + "boxinterface/anchBoxInterfaceAction!getPublication.action";
}
