package com.bjanch.www.kinmentv.util;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bjanch.www.kinmentv.KinmenTVApplication;
import com.bjanch.www.kinmentv.R;
import com.bjanch.www.kinmentv.bean.UpDataInfo;
import com.bjanch.www.kinmentv.http.JsonParams;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * 版本更新
 *
 * @author liu 2014-2-18
 */
public class UpdataUtils {

    private Context mContext;
    private UpDataInfo uu;
    private HttpHelper httpHelper;

    public UpdataUtils(Context context) {
        this.mContext = context;
        httpHelper = new HttpHelper(mContext);
    }
    public void checkedVer(String url) {
        int vercode;
        RequestParams params = new RequestParams();
        vercode = DeviceInfoUtils.getVerCode(mContext);
        params.addBodyParameter(Constants.VERSION_CODE, String.valueOf(vercode));
        httpHelper.postData(url, params, new RequestCallBack() {
            @Override
            public void onSuccess(ResponseInfo responseInfo) {
                if (responseInfo.statusCode == 200) {
                    try {
                        JSONObject update = new JSONObject(responseInfo.result.toString());
                        if(update.getBoolean("success")){
                            JSONObject obj = update.getJSONObject("obj");
                            uu = JsonParams.getUpdataInfo(obj.toString());
                            downFile(uu.getApkUrl(),FileUtils.UPDATE_SAVENAME);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    private void downFile(final String downUrl, String saveName) {

        FileUtils.createFolder(FileUtils.UPDATE_SAVEDIRNAME);
        File file = new File(saveName);
        if (file.exists()) {
            file.delete();
        }

        final String path = saveName;


        final Dialog dialog = new Dialog(mContext, R.style.UpdateDialog);
        dialog.setContentView(R.layout.update_versions);


        TextView updateNewNumber = (TextView) dialog
                .findViewById(R.id.updateNewNumber);
        LinearLayout updateContentLin = (LinearLayout) dialog
                .findViewById(R.id.updateContentLin);
        Button sure = (Button) dialog.findViewById(R.id.rightAwayUpdateTv);
        final Button cancle= (Button) dialog.findViewById(R.id.laterOnUpdateTv);


        updateNewNumber.setText(mContext.getString(R.string.version_code)+" "+uu.getVerName());
        final ProgressBar update_pg = (ProgressBar) dialog.findViewById(R.id.update_pg);
        final TextView update_pg_tv = (TextView) dialog.findViewById(R.id.update_pg_tv);
        String[] updateCountStrings = uu.getVerDetail().split(" ");

        for (String updateCountString : updateCountStrings) {
            TextView updateContentTv = new TextView(mContext);
            final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            updateContentTv.setLayoutParams(lp);
            updateContentTv.setTextSize(20);
            updateContentTv.setTextColor(Color.WHITE);
            updateContentTv.setLineSpacing(3.1f, 1f);
            updateContentTv.setText(updateCountString);
            updateContentLin.addView(updateContentTv);
        }

        dialog.setCanceledOnTouchOutside(false);// 点击窗体外不关闭
        dialog.show();
        sure.setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        httpHelper.downloadAPK(downUrl, path, update_pg, update_pg_tv,dialog,cancle);
                    }
                });
        cancle.setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

    }

    // 安装新的应用
    public static void installNewApk(String path) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(path)),
                "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        KinmenTVApplication.getInstance().startActivity(intent);
    }

}
