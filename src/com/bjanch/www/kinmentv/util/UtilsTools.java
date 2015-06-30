package com.bjanch.www.kinmentv.util;

import android.content.Context;

/**
 * Created by wxy on 2015/4/14.
 */
public class UtilsTools {

    /**
     * 获得屏幕的宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获得屏幕的高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }
}
