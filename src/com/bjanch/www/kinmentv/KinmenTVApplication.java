package com.bjanch.www.kinmentv;

import android.app.Application;
import android.support.v4.app.FragmentActivity;

import com.bjanch.www.kinmentv.util.Constants;
import com.bjanch.www.kinmentv.util.CrashHandlerUtils;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.util.LogUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Joker on 2015/4/2.
 */

public class KinmenTVApplication extends Application {
    private static KinmenTVApplication instance;

    public DbUtils dbUtils;//数据库工具类

    private List<FragmentActivity> activityList = new LinkedList<>();

    public static KinmenTVApplication getInstance()
    {
        return instance;
    }

    private static void setInstance(KinmenTVApplication instance) {
        if(KinmenTVApplication.instance==null)
            KinmenTVApplication.instance = instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setInstance(this);
        LogUtils.customTagPrefix= Constants.TAG;//kinmentv
        initDB();
        setCrashHandler();
    }

    /**
     * 全局异常捕捉
     */
    private void setCrashHandler() {
        CrashHandlerUtils crashHandler = CrashHandlerUtils.getInstance();
        // 注册crashHandler
        crashHandler.init(this);
        // 发送以前没发送的报告(可选)
        // crashHandler.sendPreviousReportsToServer();
    }

    /**
     * 初始化数据库
     */
    private void initDB() {
        dbUtils = DbUtils.create(this);
    }

    /**
     * 添加Activity到容器中
     * @param activity
     */
    public void addActivity(FragmentActivity activity)
    {
        activityList.add(activity);
    }

    /**
     * 从容器中移除Activity
     * @param activity
     */
    public void removeActivity(FragmentActivity activity){
        activityList.remove(activity);
    }
    /**
     * 遍历所有Activity并finish
     */
    private void exit()
    {
        for(FragmentActivity activity:activityList)
        {
            activity.finish();
            LogUtils.d(activity.getLocalClassName());
        }
        System.exit(0);
    }

    public void SystemExit(){
        exit();
    }
}
