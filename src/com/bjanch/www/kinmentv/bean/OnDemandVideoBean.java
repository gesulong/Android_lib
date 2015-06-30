package com.bjanch.www.kinmentv.bean;

import java.io.Serializable;

/**
 * 播放视频类
 * 
 * @author wxy
 * 
 */
public class OnDemandVideoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String playUrl;
	private String playName;

	public String getPlayUrl() {
		return playUrl;
	}

	public void setPlayUrl(String playUrl) {
		this.playUrl = playUrl;
	}

	public String getPlayName() {
		return playName;
	}

	public void setPlayName(String playName) {
		this.playName = playName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "PlayVideoBean [playUrl=" + playUrl + ", playName=" + playName
				+ "]";
	}

	public OnDemandVideoBean(String playUrl, String playName) {
		super();
		this.playUrl = playUrl;
		this.playName = playName;
	}

	public OnDemandVideoBean() {
		super();
	}

}
