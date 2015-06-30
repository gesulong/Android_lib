package com.bjanch.www.kinmentv.util;


import com.bjanch.www.kinmentv.KinmenTVApplication;
import com.bjanch.www.kinmentv.bean.MPConPlayManager;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import java.util.List;

public class MPConPlayUtils {


    private static DbUtils dbUtils = KinmenTVApplication.getInstance().dbUtils;

    /**
     * 根据episodePos和父项名称查找数据
     *
     */
    public static MPConPlayManager findConnectionPlay(int episodePos, String parentName, String childName) {
        MPConPlayManager mcpm = null;
        try {
            List<MPConPlayManager> manager = dbUtils.findAll(Selector.from(MPConPlayManager.class).where("parentName", "=", parentName).and("childName", "=", childName));
            if(manager!=null&&manager.size()>0){
                mcpm = manager.get(0);
            }else{
                mcpm = new MPConPlayManager();
                mcpm.setParentName(parentName);
                mcpm.setPlayIndex(0);
                mcpm.setEpisodePos(episodePos);
                mcpm.setChildName(childName);
                mcpm.setLastTime("0");
                dbUtils.save(mcpm);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return mcpm;
    }

    public static MPConPlayManager findLastTimePlay(String parentName){
        MPConPlayManager mcpm = null;
        try {
            List<MPConPlayManager> managers = dbUtils.findAll(Selector.from(MPConPlayManager.class).where("lastTime", "=", "1").and("parentName","=",parentName));
            if(managers!=null&&managers.size()>0){
                mcpm = managers.get(0);
            }

        } catch (DbException e) {
            e.printStackTrace();
        }
        return mcpm;
    }

    public static void updateLastTime(String parentName,String childName,int episodePos,int playIndex){
        List<MPConPlayManager> managers;
        try {
            managers = dbUtils.findAll(Selector.from(MPConPlayManager.class).where("parentName", "=", parentName));
            if(managers!=null&&managers.size()>0){
                for(int i=0;i<managers.size();i++){
                    if(managers.get(i).getChildName()!=null&&managers.get(i).getChildName().equals(childName)){
                        managers.get(i).setLastTime("1");
                        managers.get(i).setEpisodePos(episodePos);
                        managers.get(i).setParentName(parentName);
                        managers.get(i).setPlayIndex(playIndex);
                        managers.get(i).setChildName(childName);
                    }else{
                        managers.get(i).setLastTime("0");
                    }
                }
            }
            dbUtils.saveOrUpdateAll(managers);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }


    public static void deletePlayData(String parentName,String childName){
        MPConPlayManager mcpm;
        try {
            List<MPConPlayManager> manager = dbUtils.findAll(Selector.from(MPConPlayManager.class).where("parentName", "=", parentName).and("childName", "=", childName));
            if(manager!=null&&manager.size()>0){
                mcpm = manager.get(0);
                dbUtils.delete(mcpm);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }

    }
}
