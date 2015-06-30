package com.bjanch.www.kinmentv.http;

import com.bjanch.www.kinmentv.base.BaseFrgActivity;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.util.LogUtils;

public class HttpCallBack extends RequestCallBack<String> {

    @Override
    public void onStart() {
        BaseFrgActivity.getbFragment().showProgressDialog();
    }

    @Override
    public void onSuccess(ResponseInfo<String> responseInfo) {
        BaseFrgActivity.getbFragment().dismissProgressDialog();

    }

    @Override
    public void onFailure(HttpException e, String s) {
        BaseFrgActivity.getbFragment().dismissProgressDialog();
        LogUtils.e(e + "=====" + s);
    }
}





