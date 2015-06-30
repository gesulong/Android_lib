package com.bjanch.www.kinmentv.util;

import com.bjanch.www.kinmentv.KinmenTVApplication;

/**
 * Created by Joker on 2015/4/7.
 */
public class Constants {

    public static final String TAG = "kinmentv";
    //包名
    public static final String PACKAGE_NAME = KinmenTVApplication.getInstance().getPackageName();

    //连接超时时间
    public static final int HTTP_CONNECT_TIME_OUT = 10 * 1000;

    //用户名
    public static final String SETTING_USERNAME = "SETTING_USERNAME";

    //用户密码
    public static final String SETTING_PASSWORD = "SETTING_PASSWORD";

    //是否记住密码
    public static final String SETTING_CHECKED = "SETTING_CHECKED";

    //是否记住设备
    public static final String DEVICE_CHECK = "DEVICE_CHECK";
    //当前网络连接状态
    public static final String CONNECTIVITY_STATE_TYPENAME = "CONNECTIVITY_STATE_TYPENAME";
    //设备ID
    public static final String DEVICE_ID = "DEVICE_ID";
    //虚拟设备ID
    public static final String EMULATOR_DEVICE_ID = "EMULATOR_DEVICE_ID";
    //更新版本键
    public static final String VERSION_CODE = "verCode";

    /**
     * 首页跳转标签
     */
    public static String SKIP_ID = "";

    /**
     * 请求成功失败判断码
     */
    public static final int SUCCESS = 1;
    public static final int N_SUCCESS = 101;
    public static final int FAIL = 0;
    public static final int N_FAIL = 100;

    public static String SORT_NAME_KEY = "SORT_NAME_KEY";
    //节目列表传递键
    public static String SORT_CLASS_KEY = "classCode";
    public static String SORT_FILTRATE_TYPE_KEY = "typeId";
    public static String SORT_FILTRATE_AREA_KEY = "proCode";
    public static String SORT_FILTRATE_TIME_KEY = "date";

    //请求网络数据（动漫、电影、电视剧、少儿、游戏、综艺）传递参数键
    public static final String type = "type";
    //区分筛选类别
    public static final int FILTRATE_TYPE = 12580;
    public static final int FILTRATE_AREA = 12581;
    public static final int FILTRATE_TIME = 12582;
    //节目列表
    public static final int PROGRAME_SORT = 12583;
    public static String JUDGE_SHOW_POP = "";

    public final static int PROGRAME_ACTOR = 0000000;
    public final static int PROGRAME_TYPE = 0000001;


    public final static int MOVICE_DETAILS_RELATED_TYPE = 000100;
    public final static int MOVICE_DETAILS_TYPE = 000200;

    //课件内容id用于获取课件详情
    public static String CONTENT_ID_KEY = "ids";
    public static String CONTENT_ABSTRACT_KEY = "CONTENT_ABSTRACT_KEY";
    public static String CONTENT_NAME_KEY = "CONTENT_NAME_KEY";
    public static String CONTENT_COVER_PIC_KEY = "CONTENT_COVER_PIC_KEY";
    public static String CONTENT_DATE_KEY = "CONTENT_DATE_KEY";
    public static String CONTENT_ACTOR_KEY = "CONTENT_ACTOR_KEY";
    public static String CONTENT_TYPE_KEY = "CONTENT_TYPE_KEY";

    //點播進播放器傳鍵
    public static String PLAY_TYPE_KEY = "PLAY_TYPE_KEY";
    public static String PLAY_INDEX_KEY = "PLAY_INDEX_KEY";
    public static String PLAY_PARENT_NAME_KEY = "PLAY_PARENT_NAME_KEY";
    public static String PLAY_URLS_KEY = "PLAY_URLS_KEY";
    /**
     * 筛选出来的内容请求参数
     */
    public static final String PAGE = "page";
    public static final String ROWS = "rows";
    public static final String CLASSCODE = "param.classCode";
    public static final String TYPEID = "param.typeId";
    public static final String PROCODE = "param.proCode";
    public static final String DATE = "param.date";

    /**
     * 分类TYPE
     */
    public enum TYPE {
        TV_DRAMA("電視劇", "1"), MOVICE("電影", "2"), ANIME("動漫", "3"), VARIETY("綜藝", "4"),
        CHILDREN("少兒", "9"), GAME("遊戲", "6");
        private String name;
        private String type;

        TYPE(String name, String type) {
            this.name = name;
            this.type = type;
        }

        public static String findType(String name) {
            String t = "";
            for (TYPE n : TYPE.values()) {
                if (name.equals(n.getName()))
                    t = n.getType();
            }
            return t;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    /**
     * 直播上次播放位置
     */
    public static String LIVE_POSITION = "LIVE_POSITION";
    public static String LIVE_TYPE = "LIVE_TYPE";
    public static String LIVE_ID = "LIVE_ID";
    public static String LIVE_ISB = "LIVE_ISB";

    //动漫分类Json
    public static String ANIME_SORT_JSON = "{\"proList\":[{\"classId\":3,\"className\":\"動漫\",\"id\":8,\"proName\":\"日本\",\"remarks\":null},{\"classId\":3,\"className\":\"動漫\",\"id\":9,\"proName\":\"內地\",\"remarks\":null},{\"classId\":3,\"className\":\"動漫\",\"id\":10,\"proName\":\"歐美\",\"remarks\":null},{\"classId\":3,\"className\":\"動漫\",\"id\":11,\"proName\":\"台灣\",\"remarks\":null},{\"classId\":3,\"className\":\"動漫\",\"id\":12,\"proName\":\"其他\",\"remarks\":null}],\"classList\":[{\"classId\":3,\"className\":\"動漫\",\"id\":7,\"remarks\":null,\"typeName\":\"內地動漫\",\"typePosition\":0},{\"classId\":3,\"className\":\"動漫\",\"id\":8,\"remarks\":null,\"typeName\":\"港台動漫\",\"typePosition\":0},{\"classId\":3,\"className\":\"動漫\",\"id\":9,\"remarks\":null,\"typeName\":\"日韓動漫\",\"typePosition\":0},{\"classId\":3,\"className\":\"動漫\",\"id\":10,\"remarks\":null,\"typeName\":\"歐美動漫\",\"typePosition\":0}],\"typeList\":[{\"classId\":3,\"className\":\"動漫\",\"id\":11,\"remarks\":null,\"typeName\":\"動畫\",\"typePosition\":1},{\"classId\":3,\"className\":\"動漫\",\"id\":12,\"remarks\":null,\"typeName\":\"動漫\",\"typePosition\":1},{\"classId\":3,\"className\":\"動漫\",\"id\":13,\"remarks\":null,\"typeName\":\"搞笑\",\"typePosition\":1},{\"classId\":3,\"className\":\"動漫\",\"id\":14,\"remarks\":null,\"typeName\":\"冒險\",\"typePosition\":1},{\"classId\":3,\"className\":\"動漫\",\"id\":15,\"remarks\":null,\"typeName\":\"熱血\",\"typePosition\":1},{\"classId\":3,\"className\":\"動漫\",\"id\":16,\"remarks\":null,\"typeName\":\"科幻\",\"typePosition\":1},{\"classId\":3,\"className\":\"動漫\",\"id\":17,\"remarks\":null,\"typeName\":\"劇情\",\"typePosition\":1},{\"classId\":3,\"className\":\"動漫\",\"id\":18,\"remarks\":null,\"typeName\":\"奇幻\",\"typePosition\":1},{\"classId\":3,\"className\":\"動漫\",\"id\":19,\"remarks\":null,\"typeName\":\"動作\",\"typePosition\":1},{\"classId\":3,\"className\":\"動漫\",\"id\":20,\"remarks\":null,\"typeName\":\"校園\",\"typePosition\":1},{\"classId\":3,\"className\":\"動漫\",\"id\":21,\"remarks\":null,\"typeName\":\"益智\",\"typePosition\":1},{\"classId\":3,\"className\":\"動漫\",\"id\":22,\"remarks\":null,\"typeName\":\"少女\",\"typePosition\":1},{\"classId\":3,\"className\":\"動漫\",\"id\":23,\"remarks\":null,\"typeName\":\"童心\",\"typePosition\":1},{\"classId\":3,\"className\":\"動漫\",\"id\":24,\"remarks\":null,\"typeName\":\"喜劇\",\"typePosition\":1},{\"classId\":3,\"className\":\"動漫\",\"id\":25,\"remarks\":null,\"typeName\":\"青春\",\"typePosition\":1},{\"classId\":3,\"className\":\"動漫\",\"id\":26,\"remarks\":null,\"typeName\":\"勵志\",\"typePosition\":1},{\"classId\":3,\"className\":\"動漫\",\"id\":27,\"remarks\":null,\"typeName\":\"愛情\",\"typePosition\":1},{\"classId\":3,\"className\":\"動漫\",\"id\":28,\"remarks\":null,\"typeName\":\"機戰\",\"typePosition\":1},{\"classId\":3,\"className\":\"動漫\",\"id\":29,\"remarks\":null,\"typeName\":\"親子\",\"typePosition\":1},{\"classId\":3,\"className\":\"動漫\",\"id\":30,\"remarks\":null,\"typeName\":\"神魔\",\"typePosition\":1},{\"classId\":3,\"className\":\"動漫\",\"id\":31,\"remarks\":null,\"typeName\":\"輕鬆\",\"typePosition\":1},{\"classId\":3,\"className\":\"動漫\",\"id\":32,\"remarks\":null,\"typeName\":\"震撼\",\"typePosition\":1},{\"classId\":3,\"className\":\"動漫\",\"id\":33,\"remarks\":null,\"typeName\":\"戰爭\",\"typePosition\":1},{\"classId\":3,\"className\":\"動漫\",\"id\":34,\"remarks\":null,\"typeName\":\"教育\",\"typePosition\":1},{\"classId\":3,\"className\":\"動漫\",\"id\":35,\"remarks\":null,\"typeName\":\"懸疑\",\"typePosition\":1},{\"classId\":3,\"className\":\"動漫\",\"id\":36,\"remarks\":null,\"typeName\":\"推理\",\"typePosition\":1},{\"classId\":3,\"className\":\"動漫\",\"id\":37,\"remarks\":null,\"typeName\":\"卡通\",\"typePosition\":1},{\"classId\":3,\"className\":\"動漫\",\"id\":38,\"remarks\":null,\"typeName\":\"運動\",\"typePosition\":1},{\"classId\":3,\"className\":\"動漫\",\"id\":39,\"remarks\":null,\"typeName\":\"友情\",\"typePosition\":1},{\"classId\":3,\"className\":\"動漫\",\"id\":40,\"remarks\":null,\"typeName\":\"幽默\",\"typePosition\":1},{\"classId\":3,\"className\":\"動漫\",\"id\":41,\"remarks\":null,\"typeName\":\"經典\",\"typePosition\":1},{\"classId\":3,\"className\":\"動漫\",\"id\":42,\"remarks\":null,\"typeName\":\"魔幻\",\"typePosition\":1},{\"classId\":3,\"className\":\"動漫\",\"id\":43,\"remarks\":null,\"typeName\":\"未來\",\"typePosition\":1},{\"classId\":3,\"className\":\"動漫\",\"id\":44,\"remarks\":null,\"typeName\":\"格鬥\",\"typePosition\":1},{\"classId\":3,\"className\":\"動漫\",\"id\":45,\"remarks\":null,\"typeName\":\"動物\",\"typePosition\":1},{\"classId\":3,\"className\":\"動漫\",\"id\":46,\"remarks\":null,\"typeName\":\"成長\",\"typePosition\":1},{\"classId\":3,\"className\":\"動漫\",\"id\":47,\"remarks\":null,\"typeName\":\"後宮\",\"typePosition\":1},{\"classId\":3,\"className\":\"動漫\",\"id\":48,\"remarks\":null,\"typeName\":\"學生\",\"typePosition\":1},{\"classId\":3,\"className\":\"動漫\",\"id\":49,\"remarks\":null,\"typeName\":\"歷史\",\"typePosition\":1},{\"classId\":3,\"className\":\"動漫\",\"id\":50,\"remarks\":null,\"typeName\":\"其他\",\"typePosition\":1}],\"success\":true}";
    //电影分类Json
    public static String MOVICE_SORT_JSON = "{\"proList\":[{\"classId\":2,\"className\":\"電影\",\"id\":14,\"proName\":\"內地\",\"remarks\":null},{\"classId\":2,\"className\":\"電影\",\"id\":15,\"proName\":\"歐美\",\"remarks\":null},{\"classId\":2,\"className\":\"電影\",\"id\":16,\"proName\":\"香港\",\"remarks\":null},{\"classId\":2,\"className\":\"電影\",\"id\":17,\"proName\":\"韓國\",\"remarks\":null},{\"classId\":2,\"className\":\"電影\",\"id\":18,\"proName\":\"日本\",\"remarks\":null},{\"classId\":2,\"className\":\"電影\",\"id\":19,\"proName\":\"台灣\",\"remarks\":null},{\"classId\":2,\"className\":\"電影\",\"id\":20,\"proName\":\"意大利\",\"remarks\":null},{\"classId\":2,\"className\":\"電影\",\"id\":21,\"proName\":\"印度\",\"remarks\":null},{\"classId\":2,\"className\":\"電影\",\"id\":22,\"proName\":\"西班牙\",\"remarks\":null},{\"classId\":2,\"className\":\"電影\",\"id\":23,\"proName\":\"泰國\",\"remarks\":null},{\"classId\":2,\"className\":\"電影\",\"id\":24,\"proName\":\"東南亞\",\"remarks\":null},{\"classId\":2,\"className\":\"電影\",\"id\":25,\"proName\":\"俄羅斯\",\"remarks\":null},{\"classId\":2,\"className\":\"電影\",\"id\":26,\"proName\":\"其他\",\"remarks\":null}],\"classList\":[{\"classId\":2,\"className\":\"電影\",\"id\":51,\"remarks\":null,\"typeName\":\"愛情至上\",\"typePosition\":0},{\"classId\":2,\"className\":\"電影\",\"id\":52,\"remarks\":null,\"typeName\":\"時空科幻\",\"typePosition\":0},{\"classId\":2,\"className\":\"電影\",\"id\":53,\"remarks\":null,\"typeName\":\"末世災難\",\"typePosition\":0},{\"classId\":2,\"className\":\"電影\",\"id\":54,\"remarks\":null,\"typeName\":\"燒腦嫌疑\",\"typePosition\":0},{\"classId\":2,\"className\":\"電影\",\"id\":55,\"remarks\":null,\"typeName\":\"爆笑喜劇\",\"typePosition\":0},{\"classId\":2,\"className\":\"電影\",\"id\":56,\"remarks\":null,\"typeName\":\"劇場動畫\",\"typePosition\":0},{\"classId\":2,\"className\":\"電影\",\"id\":57,\"remarks\":null,\"typeName\":\"恐怖驚悚\",\"typePosition\":0},{\"classId\":2,\"className\":\"電影\",\"id\":58,\"remarks\":null,\"typeName\":\"內地電影\",\"typePosition\":0},{\"classId\":2,\"className\":\"電影\",\"id\":59,\"remarks\":null,\"typeName\":\"港台電影\",\"typePosition\":0},{\"classId\":2,\"className\":\"電影\",\"id\":60,\"remarks\":null,\"typeName\":\"日韓電影\",\"typePosition\":0},{\"classId\":2,\"className\":\"電影\",\"id\":61,\"remarks\":null,\"typeName\":\"歐美電影\",\"typePosition\":0}],\"typeList\":[{\"classId\":2,\"className\":\"電影\",\"id\":62,\"remarks\":null,\"typeName\":\"劇情\",\"typePosition\":1},{\"classId\":2,\"className\":\"電影\",\"id\":63,\"remarks\":null,\"typeName\":\"喜劇\",\"typePosition\":1},{\"classId\":2,\"className\":\"電影\",\"id\":64,\"remarks\":null,\"typeName\":\"動作\",\"typePosition\":1},{\"classId\":2,\"className\":\"電影\",\"id\":65,\"remarks\":null,\"typeName\":\"愛情\",\"typePosition\":1},{\"classId\":2,\"className\":\"電影\",\"id\":66,\"remarks\":null,\"typeName\":\"犯罪\",\"typePosition\":1},{\"classId\":2,\"className\":\"電影\",\"id\":67,\"remarks\":null,\"typeName\":\"懸疑\",\"typePosition\":1},{\"classId\":2,\"className\":\"電影\",\"id\":68,\"remarks\":null,\"typeName\":\"恐怖\",\"typePosition\":1},{\"classId\":2,\"className\":\"電影\",\"id\":69,\"remarks\":null,\"typeName\":\"驚悚\",\"typePosition\":1},{\"classId\":2,\"className\":\"電影\",\"id\":70,\"remarks\":null,\"typeName\":\"冒險\",\"typePosition\":1},{\"classId\":2,\"className\":\"電影\",\"id\":71,\"remarks\":null,\"typeName\":\"情感\",\"typePosition\":1},{\"classId\":2,\"className\":\"電影\",\"id\":72,\"remarks\":null,\"typeName\":\"微電影\",\"typePosition\":1},{\"classId\":2,\"className\":\"電影\",\"id\":73,\"remarks\":null,\"typeName\":\"戰爭\",\"typePosition\":1},{\"classId\":2,\"className\":\"電影\",\"id\":74,\"remarks\":null,\"typeName\":\"男生聚會\",\"typePosition\":1},{\"classId\":2,\"className\":\"電影\",\"id\":75,\"remarks\":null,\"typeName\":\"科幻\",\"typePosition\":1},{\"classId\":2,\"className\":\"電影\",\"id\":76,\"remarks\":null,\"typeName\":\"動畫\",\"typePosition\":1},{\"classId\":2,\"className\":\"電影\",\"id\":77,\"remarks\":null,\"typeName\":\"奇幻\",\"typePosition\":1},{\"classId\":2,\"className\":\"電影\",\"id\":78,\"remarks\":null,\"typeName\":\"幽默\",\"typePosition\":1},{\"classId\":2,\"className\":\"電影\",\"id\":79,\"remarks\":null,\"typeName\":\"文藝\",\"typePosition\":1},{\"classId\":2,\"className\":\"電影\",\"id\":80,\"remarks\":null,\"typeName\":\"青春\",\"typePosition\":1},{\"classId\":2,\"className\":\"電影\",\"id\":81,\"remarks\":null,\"typeName\":\"感人\",\"typePosition\":1},{\"classId\":2,\"className\":\"電影\",\"id\":82,\"remarks\":null,\"typeName\":\"震撼\",\"typePosition\":1},{\"classId\":2,\"className\":\"電影\",\"id\":83,\"remarks\":null,\"typeName\":\"傳記\",\"typePosition\":1},{\"classId\":2,\"className\":\"電影\",\"id\":84,\"remarks\":null,\"typeName\":\"古裝\",\"typePosition\":1},{\"classId\":2,\"className\":\"電影\",\"id\":85,\"remarks\":null,\"typeName\":\"家庭\",\"typePosition\":1},{\"classId\":2,\"className\":\"電影\",\"id\":86,\"remarks\":null,\"typeName\":\"武俠\",\"typePosition\":1},{\"classId\":2,\"className\":\"電影\",\"id\":87,\"remarks\":null,\"typeName\":\"歷史\",\"typePosition\":1},{\"classId\":2,\"className\":\"電影\",\"id\":88,\"remarks\":null,\"typeName\":\"其他\",\"typePosition\":1}],\"success\":true}";
    //电视剧分类Json
    public static String TV_TRAMA_SORT_JSON = "{\"proList\":[{\"classId\":1,\"className\":\"電視劇\",\"id\":27,\"proName\":\"內地\",\"remarks\":null},{\"classId\":1,\"className\":\"電視劇\",\"id\":28,\"proName\":\"韓國\",\"remarks\":null},{\"classId\":1,\"className\":\"電視劇\",\"id\":29,\"proName\":\"日本\",\"remarks\":null},{\"classId\":1,\"className\":\"電視劇\",\"id\":30,\"proName\":\"台灣\",\"remarks\":null},{\"classId\":1,\"className\":\"電視劇\",\"id\":31,\"proName\":\"香港\",\"remarks\":null},{\"classId\":1,\"className\":\"電視劇\",\"id\":32,\"proName\":\"泰國\",\"remarks\":null},{\"classId\":1,\"className\":\"電視劇\",\"id\":33,\"proName\":\"美國\",\"remarks\":null},{\"classId\":1,\"className\":\"電視劇\",\"id\":34,\"proName\":\"英國\",\"remarks\":null},{\"classId\":1,\"className\":\"電視劇\",\"id\":35,\"proName\":\"新加坡\",\"remarks\":null},{\"classId\":1,\"className\":\"電視劇\",\"id\":36,\"proName\":\"其他\",\"remarks\":null}],\"classList\":[{\"classId\":1,\"className\":\"電視劇\",\"id\":89,\"remarks\":null,\"typeName\":\"情感大戲\",\"typePosition\":0},{\"classId\":1,\"className\":\"電視劇\",\"id\":90,\"remarks\":null,\"typeName\":\"懸疑科幻\",\"typePosition\":0},{\"classId\":1,\"className\":\"電視劇\",\"id\":91,\"remarks\":null,\"typeName\":\"家庭倫理\",\"typePosition\":0},{\"classId\":1,\"className\":\"電視劇\",\"id\":92,\"remarks\":null,\"typeName\":\"軍事戰爭\",\"typePosition\":0},{\"classId\":1,\"className\":\"電視劇\",\"id\":93,\"remarks\":null,\"typeName\":\"宮廷古裝\",\"typePosition\":0},{\"classId\":1,\"className\":\"電視劇\",\"id\":94,\"remarks\":null,\"typeName\":\"爆笑喜劇\",\"typePosition\":0},{\"classId\":1,\"className\":\"電視劇\",\"id\":95,\"remarks\":null,\"typeName\":\"動作犯罪\",\"typePosition\":0},{\"classId\":1,\"className\":\"電視劇\",\"id\":96,\"remarks\":null,\"typeName\":\"內地劇\",\"typePosition\":0},{\"classId\":1,\"className\":\"電視劇\",\"id\":97,\"remarks\":null,\"typeName\":\"港台劇\",\"typePosition\":0},{\"classId\":1,\"className\":\"電視劇\",\"id\":98,\"remarks\":null,\"typeName\":\"日韓劇\",\"typePosition\":0},{\"classId\":1,\"className\":\"電視劇\",\"id\":99,\"remarks\":null,\"typeName\":\"歐美劇\",\"typePosition\":0}],\"typeList\":[{\"classId\":1,\"className\":\"電視劇\",\"id\":100,\"remarks\":null,\"typeName\":\"劇情\",\"typePosition\":1},{\"classId\":1,\"className\":\"電視劇\",\"id\":101,\"remarks\":null,\"typeName\":\"愛情\",\"typePosition\":1},{\"classId\":1,\"className\":\"電視劇\",\"id\":102,\"remarks\":null,\"typeName\":\"言情\",\"typePosition\":1},{\"classId\":1,\"className\":\"電視劇\",\"id\":103,\"remarks\":null,\"typeName\":\"家庭\",\"typePosition\":1},{\"classId\":1,\"className\":\"電視劇\",\"id\":104,\"remarks\":null,\"typeName\":\"懸疑\",\"typePosition\":1},{\"classId\":1,\"className\":\"電視劇\",\"id\":105,\"remarks\":null,\"typeName\":\"古裝\",\"typePosition\":1},{\"classId\":1,\"className\":\"電視劇\",\"id\":106,\"remarks\":null,\"typeName\":\"都市\",\"typePosition\":1},{\"classId\":1,\"className\":\"電視劇\",\"id\":107,\"remarks\":null,\"typeName\":\"情感\",\"typePosition\":1},{\"classId\":1,\"className\":\"電視劇\",\"id\":108,\"remarks\":null,\"typeName\":\"喜劇\",\"typePosition\":1},{\"classId\":1,\"className\":\"電視劇\",\"id\":109,\"remarks\":null,\"typeName\":\"時裝\",\"typePosition\":1},{\"classId\":1,\"className\":\"電視劇\",\"id\":110,\"remarks\":null,\"typeName\":\"偶像\",\"typePosition\":1},{\"classId\":1,\"className\":\"電視劇\",\"id\":111,\"remarks\":null,\"typeName\":\"歷史\",\"typePosition\":1},{\"classId\":1,\"className\":\"電視劇\",\"id\":112,\"remarks\":null,\"typeName\":\"青春\",\"typePosition\":1},{\"classId\":1,\"className\":\"電視劇\",\"id\":113,\"remarks\":null,\"typeName\":\"戰爭\",\"typePosition\":1},{\"classId\":1,\"className\":\"電視劇\",\"id\":114,\"remarks\":null,\"typeName\":\"現代\",\"typePosition\":1},{\"classId\":1,\"className\":\"電視劇\",\"id\":115,\"remarks\":null,\"typeName\":\"倫理\",\"typePosition\":1},{\"classId\":1,\"className\":\"電視劇\",\"id\":116,\"remarks\":null,\"typeName\":\"武俠\",\"typePosition\":1},{\"classId\":1,\"className\":\"電視劇\",\"id\":117,\"remarks\":null,\"typeName\":\"搞笑\",\"typePosition\":1},{\"classId\":1,\"className\":\"電視劇\",\"id\":118,\"remarks\":null,\"typeName\":\"動作\",\"typePosition\":1},{\"classId\":1,\"className\":\"電視劇\",\"id\":119,\"remarks\":null,\"typeName\":\"勵志\",\"typePosition\":1},{\"classId\":1,\"className\":\"電視劇\",\"id\":120,\"remarks\":null,\"typeName\":\"偶像劇\",\"typePosition\":1},{\"classId\":1,\"className\":\"電視劇\",\"id\":121,\"remarks\":null,\"typeName\":\"犯罪\",\"typePosition\":1},{\"classId\":1,\"className\":\"電視劇\",\"id\":122,\"remarks\":null,\"typeName\":\"男生聚會\",\"typePosition\":1},{\"classId\":1,\"className\":\"電視劇\",\"id\":123,\"remarks\":null,\"typeName\":\"警匪\",\"typePosition\":1},{\"classId\":1,\"className\":\"電視劇\",\"id\":124,\"remarks\":null,\"typeName\":\"科幻\",\"typePosition\":1},{\"classId\":1,\"className\":\"電視劇\",\"id\":125,\"remarks\":null,\"typeName\":\"軍事\",\"typePosition\":1},{\"classId\":1,\"className\":\"電視劇\",\"id\":126,\"remarks\":null,\"typeName\":\"約會\",\"typePosition\":1},{\"classId\":1,\"className\":\"電視劇\",\"id\":127,\"remarks\":null,\"typeName\":\"輕鬆\",\"typePosition\":1},{\"classId\":1,\"className\":\"電視劇\",\"id\":128,\"remarks\":null,\"typeName\":\"溫情\",\"typePosition\":1},{\"classId\":1,\"className\":\"電視劇\",\"id\":129,\"remarks\":null,\"typeName\":\"奇幻\",\"typePosition\":1},{\"classId\":1,\"className\":\"電視劇\",\"id\":130,\"remarks\":null,\"typeName\":\"經典\",\"typePosition\":1},{\"classId\":1,\"className\":\"電視劇\",\"id\":131,\"remarks\":null,\"typeName\":\"感人\",\"typePosition\":1},{\"classId\":1,\"className\":\"電視劇\",\"id\":132,\"remarks\":null,\"typeName\":\"女性\",\"typePosition\":1},{\"classId\":1,\"className\":\"電視劇\",\"id\":133,\"remarks\":null,\"typeName\":\"神話\",\"typePosition\":1},{\"classId\":1,\"className\":\"電視劇\",\"id\":134,\"remarks\":null,\"typeName\":\"革命\",\"typePosition\":1},{\"classId\":1,\"className\":\"電視劇\",\"id\":135,\"remarks\":null,\"typeName\":\"其他\",\"typePosition\":1}],\"success\":true}";
    //综艺分类Json
    public static String VARIETY_SORT_JSON = "{\"proList\":[{\"classId\":4,\"className\":\"綜藝\",\"id\":37,\"proName\":\"內地\",\"remarks\":null},{\"classId\":4,\"className\":\"綜藝\",\"id\":38,\"proName\":\"韓國\",\"remarks\":null},{\"classId\":4,\"className\":\"綜藝\",\"id\":39,\"proName\":\"日本\",\"remarks\":null},{\"classId\":4,\"className\":\"綜藝\",\"id\":40,\"proName\":\"香港\",\"remarks\":null},{\"classId\":4,\"className\":\"綜藝\",\"id\":41,\"proName\":\"台灣\",\"remarks\":null},{\"classId\":4,\"className\":\"綜藝\",\"id\":42,\"proName\":\"歐美\",\"remarks\":null},{\"classId\":4,\"className\":\"綜藝\",\"id\":43,\"proName\":\"其他\",\"remarks\":null}],\"classList\":[{\"classId\":4,\"className\":\"綜藝\",\"id\":136,\"remarks\":null,\"typeName\":\"相親約會\",\"typePosition\":0},{\"classId\":4,\"className\":\"綜藝\",\"id\":137,\"remarks\":null,\"typeName\":\"語言訪談\",\"typePosition\":0},{\"classId\":4,\"className\":\"綜藝\",\"id\":138,\"remarks\":null,\"typeName\":\"真人秀場\",\"typePosition\":0},{\"classId\":4,\"className\":\"綜藝\",\"id\":139,\"remarks\":null,\"typeName\":\"幽默搞笑\",\"typePosition\":0},{\"classId\":4,\"className\":\"綜藝\",\"id\":140,\"remarks\":null,\"typeName\":\"歌舞選秀\",\"typePosition\":0},{\"classId\":4,\"className\":\"綜藝\",\"id\":141,\"remarks\":null,\"typeName\":\"互動遊戲\",\"typePosition\":0},{\"classId\":4,\"className\":\"綜藝\",\"id\":142,\"remarks\":null,\"typeName\":\"晚會盛典\",\"typePosition\":0},{\"classId\":4,\"className\":\"綜藝\",\"id\":143,\"remarks\":null,\"typeName\":\"內地綜藝\",\"typePosition\":0},{\"classId\":4,\"className\":\"綜藝\",\"id\":144,\"remarks\":null,\"typeName\":\"港台綜藝\",\"typePosition\":0},{\"classId\":4,\"className\":\"綜藝\",\"id\":145,\"remarks\":null,\"typeName\":\"日韓綜藝\",\"typePosition\":0},{\"classId\":4,\"className\":\"綜藝\",\"id\":146,\"remarks\":null,\"typeName\":\"歐美綜藝\",\"typePosition\":0}],\"typeList\":[{\"classId\":4,\"className\":\"綜藝\",\"id\":147,\"remarks\":null,\"typeName\":\"綜藝\",\"typePosition\":1},{\"classId\":4,\"className\":\"綜藝\",\"id\":148,\"remarks\":null,\"typeName\":\"娛樂\",\"typePosition\":1},{\"classId\":4,\"className\":\"綜藝\",\"id\":149,\"remarks\":null,\"typeName\":\"訪談\",\"typePosition\":1},{\"classId\":4,\"className\":\"綜藝\",\"id\":150,\"remarks\":null,\"typeName\":\"生活\",\"typePosition\":1},{\"classId\":4,\"className\":\"綜藝\",\"id\":151,\"remarks\":null,\"typeName\":\"音樂\",\"typePosition\":1},{\"classId\":4,\"className\":\"綜藝\",\"id\":152,\"remarks\":null,\"typeName\":\"真人秀\",\"typePosition\":1},{\"classId\":4,\"className\":\"綜藝\",\"id\":153,\"remarks\":null,\"typeName\":\"選秀\",\"typePosition\":1},{\"classId\":4,\"className\":\"綜藝\",\"id\":154,\"remarks\":null,\"typeName\":\"晚會\",\"typePosition\":1},{\"classId\":4,\"className\":\"綜藝\",\"id\":155,\"remarks\":null,\"typeName\":\"播報\",\"typePosition\":1},{\"classId\":4,\"className\":\"綜藝\",\"id\":156,\"remarks\":null,\"typeName\":\"互動\",\"typePosition\":1},{\"classId\":4,\"className\":\"綜藝\",\"id\":157,\"remarks\":null,\"typeName\":\"紀實\",\"typePosition\":1},{\"classId\":4,\"className\":\"綜藝\",\"id\":158,\"remarks\":null,\"typeName\":\"旅遊\",\"typePosition\":1},{\"classId\":4,\"className\":\"綜藝\",\"id\":159,\"remarks\":null,\"typeName\":\"美食\",\"typePosition\":1},{\"classId\":4,\"className\":\"綜藝\",\"id\":160,\"remarks\":null,\"typeName\":\"情感\",\"typePosition\":1},{\"classId\":4,\"className\":\"綜藝\",\"id\":161,\"remarks\":null,\"typeName\":\"其他\",\"typePosition\":1}],\"success\":true}";
    //动漫内容分页1Json
    public static String ANIME_CONTENT_PATE1_JSON = "{\"total\":17,\"pageSize\":2,\"success\":true,\"rows\":[{\"atId\":\"6|周星驰\",\"classId\":3,\"className\":\"動漫\",\"crDate\":\"2015-05-05\",\"crDetail\":\"第三方\",\"crName\":\"何以笙箫默\",\"crPic\":\"http:\\/\\/192.168.200.88:8080\\/jmtv\\/upload\\/jmtv\\/jmimg\\/20150508185440-51a68a67-16df-41dc-9e3c-c3acb8f96e2b.jpg\",\"id\":14,\"proId\":8,\"typeId\":\"42|魔幻\"},{\"atId\":\"6,5|周星驰,刘德华\",\"classId\":3,\"className\":\"動漫\",\"crDate\":\"2015-05-27\",\"crDetail\":\"阿斯蒂芬个\",\"crName\":\"沈阳飞机场\",\"crPic\":\"http:\\/\\/192.168.200.88:8080\\/jmtv\\/upload\\/jmtv\\/jmimg\\/20150508190541-0494383d-10f3-4d15-ae72-71a5d39e3996.jpg\",\"id\":17,\"proId\":9,\"typeId\":\"47|後宮\"},{\"atId\":\"6,5|周星驰,刘德华\",\"classId\":3,\"className\":\"動漫\",\"crDate\":\"2015-05-12\",\"crDetail\":\"as刚刚\",\"crName\":\"之遥\",\"crPic\":\"http:\\/\\/192.168.200.88:8080\\/jmtv\\/upload\\/jmtv\\/jmimg\\/20150508190707-ef0af776-43b3-449d-997a-96194b404142.jpg\",\"id\":18,\"proId\":8,\"typeId\":\"47|後宮\"},{\"atId\":\"6,5|周星驰,刘德华\",\"classId\":3,\"className\":\"動漫\",\"crDate\":\"2015-05-17\",\"crDetail\":\"啊哈过分的话\",\"crName\":\"古镜\",\"crPic\":\"http:\\/\\/192.168.200.88:8080\\/jmtv\\/upload\\/jmtv\\/jmimg\\/20150508190936-b302f8af-cf98-4894-9b9d-0a6b937082f1.jpg\",\"id\":20,\"proId\":8,\"typeId\":\"36|推理\"},{\"atId\":\"6,5|周星驰,刘德华\",\"classId\":3,\"className\":\"動漫\",\"crDate\":\"2015-05-12\",\"crDetail\":\"阿森股份的\",\"crName\":\"火影忍者\",\"crPic\":\"http:\\/\\/192.168.200.88:8080\\/jmtv\\/upload\\/jmtv\\/jmimg\\/20150508191028-26d0914b-a666-4465-8bef-f2ce28e6e5dd.jpg\",\"id\":21,\"proId\":8,\"typeId\":\"50|其他\"},{\"atId\":\"6|周星驰\",\"classId\":3,\"className\":\"動漫\",\"crDate\":\"2015-05-25\",\"crDetail\":\"阿萨德根深蒂固\",\"crName\":\"七龙珠\",\"crPic\":\"http:\\/\\/192.168.200.88:8080\\/jmtv\\/upload\\/jmtv\\/jmimg\\/20150508191100-3eb3a396-37d2-4365-8ed2-ef68b56606fb.jpg\",\"id\":22,\"proId\":8,\"typeId\":\"11|動畫\"},{\"atId\":\"6,5|周星驰,刘德华\",\"classId\":3,\"className\":\"動漫\",\"crDate\":\"2015-05-19\",\"crDetail\":\"撒公司\",\"crName\":\"海贼王\",\"crPic\":\"http:\\/\\/192.168.200.88:8080\\/jmtv\\/upload\\/jmtv\\/jmimg\\/20150508191204-879f63cf-7272-45f2-8f6a-3df8ea832b13.jpg\",\"id\":23,\"proId\":8,\"typeId\":\"9|日韓動漫\"},{\"atId\":\"6|周星驰\",\"classId\":3,\"className\":\"動漫\",\"crDate\":\"2015-05-11\",\"crDetail\":\"阿森个第三方个\",\"crName\":\"死神\",\"crPic\":\"http:\\/\\/192.168.200.88:8080\\/jmtv\\/upload\\/jmtv\\/jmimg\\/20150508191235-4619a18d-760e-4d29-a4fa-e85a307eb658.jpg\",\"id\":24,\"proId\":11,\"typeId\":\"10|歐美動漫\"},{\"atId\":\"6|周星驰\",\"classId\":3,\"className\":\"動漫\",\"crDate\":\"2015-05-12\",\"crDetail\":\"阿森股份的后果\",\"crName\":\"犬夜叉\",\"crPic\":\"http:\\/\\/192.168.200.88:8080\\/jmtv\\/upload\\/jmtv\\/jmimg\\/20150508191408-0e61464f-0190-4d00-b721-bcef3849665a.png\",\"id\":25,\"proId\":9,\"typeId\":\"7|內地動漫\"},{\"atId\":\"5|刘德华\",\"classId\":3,\"className\":\"動漫\",\"crDate\":\"2015-05-17\",\"crDetail\":\"公司的方式\",\"crName\":\"感到十分\",\"crPic\":\"http:\\/\\/192.168.200.88:8080\\/jmtv\\/upload\\/jmtv\\/jmimg\\/20150508191449-ad978898-17c6-4c97-b383-bcd9c4943333.png\",\"id\":26,\"proId\":9,\"typeId\":\"7|內地動漫\"},{\"atId\":\"6,5|周星驰,刘德华\",\"classId\":3,\"className\":\"動漫\",\"crDate\":\"2015-05-19\",\"crDetail\":\"隧道股份的个\",\"crName\":\"干活斯蒂芬\",\"crPic\":\"http:\\/\\/192.168.200.88:8080\\/jmtv\\/upload\\/jmtv\\/jmimg\\/20150508191522-f5b8ce6a-3a45-4bfd-bfbc-8a450bf4f6fb.png\",\"id\":27,\"proId\":11,\"typeId\":\"8|港台動漫\"},{\"atId\":\"6|周星驰\",\"classId\":3,\"className\":\"動漫\",\"crDate\":\"2015-05-24\",\"crDetail\":\"是个负担\",\"crName\":\"三等功\",\"crPic\":\"http:\\/\\/192.168.200.88:8080\\/jmtv\\/upload\\/jmtv\\/jmimg\\/20150508191550-76fd559d-bfaa-48d5-8ed7-ef318bbcf84a.png\",\"id\":28,\"proId\":9,\"typeId\":\"7|內地動漫\"}]}";
    //动漫内容分页2Json
    public static String ANIME_CONTENT_PATE2_JSON = "{\"total\":17,\"pageSize\":2,\"success\":true,\"rows\":[{\"atId\":\"6|周星驰\",\"classId\":3,\"className\":\"動漫\",\"crDate\":\"2015-04-27\",\"crDetail\":\"隧道股份的个\",\"crName\":\"股份的个好\",\"crPic\":\"http:\\/\\/192.168.200.88:8080\\/jmtv\\/upload\\/jmtv\\/jmimg\\/20150508191728-29f35b5b-9f6c-4191-99d6-2399ddac12c9.png\",\"id\":30,\"proId\":10,\"typeId\":\"10,7|歐美動漫,內地動漫\"},{\"atId\":\"6,5|周星驰,刘德华\",\"classId\":3,\"className\":\"動漫\",\"crDate\":\"2015-05-15\",\"crDetail\":\"测试2\",\"crName\":\"测试2\",\"crPic\":\"http:\\/\\/localhost:8080\\/jmtv\\/upload\\/jmtv\\/jmimg\\/20150508152736-a88246b9-7682-4801-a4a7-e4a76a3f0848.jpg\",\"id\":13,\"proId\":9,\"typeId\":\"45,40,38|動物,幽默,運動\"},{\"atId\":\"6|周星驰\",\"classId\":3,\"className\":\"動漫\",\"crDate\":\"2010-05-11\",\"crDetail\":\"撒旦发射点发\",\"crName\":\"速度与激情\",\"crPic\":\"http:\\/\\/192.168.200.88:8080\\/jmtv\\/upload\\/jmtv\\/jmimg\\/20150508185914-cc1a82a2-ae00-41fd-899c-0aa5f553014f.jpg\",\"id\":16,\"proId\":9,\"typeId\":\"11|動畫\"},{\"atId\":\"5|刘德华\",\"classId\":3,\"className\":\"動漫\",\"crDate\":\"2008-05-19\",\"crDetail\":\"地方官放火\",\"crName\":\"斯巴达勇士\",\"crPic\":\"http:\\/\\/192.168.200.88:8080\\/jmtv\\/upload\\/jmtv\\/jmimg\\/20150508185745-9698d902-9510-4d22-8717-c3ab3b9feb5f.jpg\",\"id\":15,\"proId\":8,\"typeId\":\"11|動畫\"},{\"atId\":\"6,5|周星驰,刘德华\",\"classId\":3,\"className\":\"動漫\",\"crDate\":\"2015-05-05\",\"crDetail\":\"分的后果发挥\",\"crName\":\"三个速度\",\"crPic\":\"http:\\/\\/192.168.200.88:8080\\/jmtv\\/upload\\/jmtv\\/jmimg\\/20150508191656-2493ece2-a95a-4e0c-a5ed-0267d921e49c.png\",\"id\":29,\"proId\":9,\"typeId\":\"7|內地動漫\"}]}";
    //电影内容Json
    public static String MOVICE_CONTENT_JSON = "{\"total\":7,\"pageSize\":1,\"success\":true,\"rows\":[{\"atId\":\"7|芙蓉姐姐\",\"classId\":2,\"className\":\"電影\",\"crDate\":\"2015-05-04\",\"crDetail\":\"123\",\"crName\":\"陈测试\",\"crPic\":\"http:\\/\\/192.168.200.88:8080\\/jmtv\\/upload\\/jmtv\\/jmimg\\/20150514161831-dcbc54b9-70f3-414e-887d-9260318f92f8.png\",\"id\":61,\"proId\":15,\"typeId\":\"61,68,67,54,69|歐美電影,恐怖,懸疑,燒腦嫌疑,驚悚\"},{\"atId\":\"6,5|周星驰,刘德华\",\"classId\":2,\"className\":\"電影\",\"crDate\":\"2015-05-19\",\"crDetail\":\"阿森敢死队风格\",\"crName\":\"水月洞天\",\"crPic\":\"http:\\/\\/192.168.200.88:8080\\/jmtv\\/upload\\/jmtv\\/jmimg\\/20150508190809-d21b7353-33ba-4ded-ad7a-5d9312c7ecfa.jpg\",\"id\":19,\"proId\":14,\"typeId\":\"84|古裝\"},{\"atId\":\"6,5|周星驰,刘德华\",\"classId\":2,\"className\":\"電影\",\"crDate\":\"2015-05-13\",\"crDetail\":\"123\",\"crName\":\"test\",\"crPic\":\"http:\\/\\/localhost:8080\\/jmtv\\/upload\\/jmtv\\/jmimg\\/20150511143435-caca62a4-0909-4039-9925-655605967cee.jpg\",\"id\":39,\"proId\":16,\"typeId\":\"87,82,77,76|歷史,震撼,奇幻,動畫\"},{\"atId\":\"5|刘德华\",\"classId\":2,\"className\":\"電影\",\"crDate\":\"2015-05-04\",\"crDetail\":\"哒哒哒\",\"crName\":\"点点点点\",\"crPic\":\"http:\\/\\/192.168.200.88:8080\\/jmtv\\/upload\\/jmtv\\/jmimg\\/20150511143835-d6041fcd-6fc9-4c97-8124-b11a81f13be1.jpg\",\"id\":40,\"proId\":14,\"typeId\":\"85,82,81,79,78|家庭,震撼,感人,文藝,幽默\"},{\"atId\":\"6|周星驰\",\"classId\":2,\"className\":\"電影\",\"crDate\":\"2015-05-13\",\"crDetail\":\"china功夫\",\"crName\":\"电影\",\"crPic\":\"http:\\/\\/192.168.200.88:8080\\/jmtv\\/upload\\/jmtv\\/jmimg\\/20150511133624-8016da68-5d0b-4d7d-b27b-a664b49dd3e7.jpg\",\"id\":31,\"proId\":16,\"typeId\":\"78|幽默\"},{\"atId\":\"5|刘德华\",\"classId\":2,\"className\":\"電影\",\"crDate\":\"2015-05-07\",\"crDetail\":\"电影2\",\"crName\":\"电影2\",\"crPic\":\"http:\\/\\/192.168.200.88:8080\\/jmtv\\/upload\\/jmtv\\/jmimg\\/20150511135518-3fe5f3c2-96fa-4246-a645-8c08192b2405.jpg\",\"id\":32,\"proId\":15,\"typeId\":\"84|古裝\"},{\"atId\":\"7|芙蓉姐姐\",\"classId\":2,\"className\":\"電影\",\"crDate\":\"2015-05-01\",\"crDetail\":\"合格后个人\",\"crName\":\"官方广泛地\",\"crPic\":\"http:\\/\\/192.168.200.88:8080\\/jmtv\\/upload\\/jmtv\\/jmimg\\/20150512094409-62c140db-f663-4c75-aa69-78c52a0e8b47.jpg\",\"id\":41,\"proId\":15,\"typeId\":\"87,86,83,81,79|歷史,武俠,傳記,感人,文藝\"}]}";
    //电视剧内容Json
    public static String TV_TRAMA_CONTENT_JSON = "{\"total\":2,\"pageSize\":1,\"success\":true,\"rows\":[{\"atId\":\"6,5|周星驰,刘德华\",\"classId\":1,\"className\":\"電視劇\",\"crDate\":\"2015-05-21\",\"crDetail\":null,\"crName\":\"测试课件\",\"crPic\":\"http:\\/\\/localhost:8080\\/jmtv\\/upload\\/jmtv\\/jmimg\\/20150508152651-f7cb2d86-282f-4c86-8d1a-5790be118028.jpg\",\"id\":12,\"proId\":29,\"typeId\":\"131,126,122,112,108|感人,約會,男生聚會,青春,喜劇\"},{\"atId\":\"7,5|芙蓉姐姐,刘德华\",\"classId\":1,\"className\":\"電視劇\",\"crDate\":\"2015-05-08\",\"crDetail\":null,\"crName\":\"何以笙箫默新\",\"crPic\":\"http:\\/\\/192.168.200.88:8080\\/jmtv\\/upload\\/jmtv\\/jmimg\\/20150515105218-4db2cc6c-65b0-4573-8f9d-3eb2b95db560.jpg\",\"id\":62,\"proId\":27,\"typeId\":\"103,102,96|家庭,言情,內地劇\"}]}";
    //综艺内容Json
    public static String VARIETY_CONTENT_JSON = "{\"total\":0,\"pageSize\":0,\"success\":true,\"rows\":[]}";
    //带剧集的详情Json
    public static String TV_DRAMA_DETAILS_JSON = "{\"rows\":[{\"crId\":25,\"id\":49,\"srDetail\":null,\"srName\":\"测试10\",\"srUrl\":\"http:\\/\\/wms01.bjanch.com:1935\\/BJAC_VOD\\/mp4:20150514131059-4272ac54-fe34-4919-9cba-d918fb468f0e.mp4\\/playlist.m3u8\"}]}";
    //电影相关剧集Json
    public static String MOVICE_RELATED_DETAILS_JSON = "{\"total\":2,\"pageSize\":1,\"success\":true,\"rows\":[{\"atId\":\"6,5|周星驰,刘德华\",\"classId\":2,\"className\":\"電影\",\"crDate\":\"2015-05-19\",\"crDetail\":\"阿森敢死队风格\",\"crName\":\"水月洞天\",\"crPic\":\"http:\\/\\/192.168.200.88:8080\\/jmtv\\/upload\\/jmtv\\/jmimg\\/20150508190809-d21b7353-33ba-4ded-ad7a-5d9312c7ecfa.jpg\",\"id\":19,\"proId\":14,\"typeId\":\"84|古裝\"},{\"atId\":\"5|刘德华\",\"classId\":2,\"className\":\"電影\",\"crDate\":\"2015-05-07\",\"crDetail\":\"电影2\",\"crName\":\"电影2\",\"crPic\":\"http:\\/\\/192.168.200.88:8080\\/jmtv\\/upload\\/jmtv\\/jmimg\\/20150511135518-3fe5f3c2-96fa-4246-a645-8c08192b2405.jpg\",\"id\":32,\"proId\":15,\"typeId\":\"84|古裝\"}]}";
    //电影相关剧集URL的Json
    public static String MOVICE_RELATED_URLS_JSON = "{\"rows\":[{\"crId\":19,\"id\":55,\"srDetail\":null,\"srName\":\"测试16\",\"srUrl\":\"http:\\/\\/wms01.bjanch.com:1935\\/BJAC_VOD\\/mp4:20150514131059-4272ac54-fe34-4919-9cba-d918fb468f0e.mp4\\/playlist.m3u8\"}]}";
    //电影单集的详情Json
    public static String MOVICE_URL_DETAILS_JSON = "{\"rows\":[{\"crId\":32,\"id\":20,\"srDetail\":\"松下高雄演讲\",\"srName\":\"松下高雄演讲\",\"srUrl\":\"http:\\/\\/wms01.bjanch.com:1935\\/BJAC_VOD\\/mp4:小城大爱.mp4\\/playlist.m3u8\"}]}";
}
