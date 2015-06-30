package com.bjanch.www.kinmentv.util;

import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bjanch.www.kinmentv.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.PreferencesCookieStore;

import java.io.File;

/**
 * Created by Joker on 2015/4/7.
 */
public class HttpHelper {

    private static HttpUtils client;
    private Context mContext;


    /**
     * 返回文本的编码，默认编码UTF-8
     */
    private HttpUtils httpUtils;
    /**
     * 请求参数，默认编码UTF-8
     */
    private RequestParams requestParams;

    public HttpHelper(Context context) {
        this.mContext = context;
        this.httpUtils = getInstence(context);
    }

    /**
     * GET方式请求数据
     * @param url 数据的url地址
     * @param requestCallBack 回调函数
     */
    public void getData(String url,RequestParams requestParams,RequestCallBack<String> requestCallBack){
//        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET,url,requestParams,requestCallBack);
    }


    /**
     * POST方式请求数据
     * @param url 数据的url地址
     * @param requestParams 参数的键值对
     * @param requestCallBack 回调函数
     */
    public void postData(String url,RequestParams requestParams,RequestCallBack requestCallBack){

        httpUtils.send(HttpRequest.HttpMethod.POST, url, requestParams, requestCallBack);
    }



    public void downloadAPK(String url,final  String saveName,final ProgressBar pb,final TextView tv,final Dialog dialog, final Button button) {
        httpUtils.download(url, saveName, new RequestCallBack<File>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onLoading(long total, long current, boolean isUploading) {
                int progress = (int) ((float) current / (float) total * 100);
                pb.setProgress(progress);
                tv.setText(progress + "%");
                button.setText(mContext.getString(R.string.download_backgroud));
            }

            @Override
            public void onSuccess(ResponseInfo<File> arg0) {
                dialog.dismiss();
                UpdataUtils.installNewApk(saveName);
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                arg0.printStackTrace();
                ToastTools.frameToast(mContext, mContext.getString(R.string.download_failure_retry), R.drawable.custom_toast);
                button.setText(mContext.getString(R.string.button_cancle));
            }
        });
    }

    /**
     * 单例模式获取HttpUtils实例对象
     * @param context 应用程序上下文
     * @return HttpUtils对象实例
     */
    private synchronized static HttpUtils getInstence(Context context) {
        if (client == null) {
            client = new HttpUtils();
            client.configSoTimeout(Constants.HTTP_CONNECT_TIME_OUT);
            client.configResponseTextCharset("UTF-8");
            //保存服务端（Session）的Cookie
            PreferencesCookieStore cookieStore = new PreferencesCookieStore(context);
            cookieStore.clear();//清除原来的cookie；
            client.configCookieStore(cookieStore);
        }
        return client;
    }

}