package com.bjanch.www.kinmentv.bean;

import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;


/**
 * 点播数据库建表
 *
 */
@SuppressWarnings("serial")
@Table(name = "MPConPlayManager")
public class MPConPlayManager implements Serializable {
    private long id;

    private int playIndex;// 播放到的位置
    private int episodePos;// 播放的集数位置
    private String parentName;// 剧集的父项名称
    private String childName;//当前剧集名称
    private String  lastTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPlayIndex() {
        return playIndex;
    }

    public void setPlayIndex(int playIndex) {
        this.playIndex = playIndex;
    }

    public int getEpisodePos() {
        return episodePos;
    }

    public void setEpisodePos(int episodePos) {
        this.episodePos = episodePos;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getChildName() {
        return childName;
    }
    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }
}
