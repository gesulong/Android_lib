package com.bjanch.www.kinmentv.bean;

import java.util.List;

/**
 * Created by wxy on 2015/5/8.
 */
public class KmProgramSortBean {
    private List<ProgramSortBean> classList;
    private List<FiltrateAreaSortBean> proList;
    private List<FiltrateTypeSortBean> typeList;

    public List<ProgramSortBean> getClassList() {
        return classList;
    }

    public void setClassList(List<ProgramSortBean> classList) {
        this.classList = classList;
    }

    public List<FiltrateAreaSortBean> getProList() {
        return proList;
    }

    public void setProList(List<FiltrateAreaSortBean> proList) {
        this.proList = proList;
    }

    public List<FiltrateTypeSortBean> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<FiltrateTypeSortBean> typeList) {
        this.typeList = typeList;
    }
}
