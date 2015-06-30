package com.bjanch.www.kinmentv.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bjanch.www.kinmentv.R;
import com.bjanch.www.kinmentv.KinmenTVApplication;


/**
 * Created by Joker on 2015/4/2.
 */
public class BaseFrgActivity extends FragmentActivity {

    protected Dialog mProgressDialog;
    private static BaseFrgActivity bFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        KinmenTVApplication.getInstance().addActivity(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        setbFragment(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        KinmenTVApplication.getInstance().removeActivity(this);
    }

    public static BaseFrgActivity getbFragment() {
        return bFragment;
    }

    public static void setbFragment(BaseFrgActivity bFragment) {
        BaseFrgActivity.bFragment = bFragment;
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = progressFactory(this);
        }

        mProgressDialog.show();
    }

    public void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    /**
     * 得到自定义的progressDialog
     */
    public Dialog progressFactory(Context context) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dialog_onlyimage, null);// 得到加载view
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dl_ol_ll);// 加载布局
        // main.xml中的ImageView
        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.dl_ol_iv);
        // 加载动画
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
                context, R.anim.loading);
        // 使用ImageView显示动画
        spaceshipImage.startAnimation(hyperspaceJumpAnimation);

        Dialog loadingDialog = new Dialog(context, R.style.dialog_only_image);// 创建自定义样式dialog

        loadingDialog.setCancelable(true);// 不可以用“返回键”取消
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));// 设置布局
        return loadingDialog;

    }

}
