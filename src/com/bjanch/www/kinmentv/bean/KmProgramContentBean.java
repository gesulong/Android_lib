package com.bjanch.www.kinmentv.bean;

import java.util.List;

/**
 * Created by wxy on 2015/5/8.
 */
public class KmProgramContentBean {

    private String total;//总条数
    private String pageSize;//总页数
    private List<KmProgramContentListBean> rows;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public List<KmProgramContentListBean> getRows() {
        return rows;
    }

    public void setRows(List<KmProgramContentListBean> rows) {
        this.rows = rows;
    }
}
