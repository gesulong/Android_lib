package com.bjanch.www.kinmentv.bean;

import java.util.List;

public class MagazineList {

	private List<MagazineBean> data;
	private String success;

	public List<MagazineBean> getData() {
		return data;
	}

	public void setData(List<MagazineBean> data) {
		this.data = data;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}
}
