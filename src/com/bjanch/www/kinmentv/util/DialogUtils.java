package com.bjanch.www.kinmentv.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjanch.www.kinmentv.R;
import com.lidroid.xutils.ViewUtils;

/**
 * 对话框工具类
 *
 * @author liurui 2014-3-13
 */
public class DialogUtils {

    /***
     * 加载提示
     */
    private static Dialog pd;


    /**
     * 自定义progressDialog
     */
    public static Dialog createLoadingDialog(Context context, String msg) {
    //TODO 注解有疑问待解决 测试
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dialog_utils_loading, null);// 得到加载view
        ViewUtils.inject(context, v);
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dl_ld_prt);// 加载布局
        // main.xml中的ImageView
        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.dl_ld_iv);
        TextView tipTextView = (TextView) v.findViewById(R.id.dl_ld_tv);// 提示文字
        // 加载动画
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
                context, R.anim.loading);
        // 使用ImageView显示动画
        spaceshipImage.startAnimation(hyperspaceJumpAnimation);
        tipTextView.setText(msg);// 设置加载信息

        Dialog loadingDialog = new Dialog(context, R.style.dalog_utils_loading);// 创建自定义样式dialog

        loadingDialog.setCancelable(false);// 不可以用“返回键”取消
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
        return loadingDialog;

    }


    public static Dialog dialog_twobtn(Context context,String text,final Btn_callback callback){
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.dialog_two_btn, null);
        final Dialog dialog = new Dialog(context,R.style.dalog_utils_loading);
        dialog.setCancelable(false);

//        final AlertDialog dialog = new AlertDialog.Builder(context).setView(layout).create();

        TextView textview = (TextView) layout.findViewById(R.id.dl_two_tv_title);
        textview.setText(text);
        Button btn_sure = (Button) layout.findViewById(R.id.dl_two_btn_sure);
//		btn_sure.setText(btn1_text);

        Button btn_cancel = (Button) layout.findViewById(R.id.dl_two_btn_cancel);
//		btn_cancel.setText(btn2_text);

        dialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));// 设置布局
        btn_sure.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                callback.onBtn1Click();
                dialog.dismiss();
            }

        });

        btn_cancel.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                callback.onBtn2Click();
                dialog.dismiss();
            }

        });

        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnKeyListener(keyListener);
        return dialog;
    }


    /***
     * 按钮点击之后的回调
     * @author duan
     */
    public interface Btn_callback {
        void onBtn1Click();

        void onBtn2Click();
    }

    public interface Btn_Single_callbak{

        void onBtn_Single_Click();
    }
    static OnKeyListener keyListener = new OnKeyListener() {

        @Override
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            return false;
        }
    };


    /***
     * 显示自定义内容的加载提示
     *
     * @param message
     */
    public static void showPd(Context mContext,String message) {
        if (!NetworkUtils.isNetworkAvailable(mContext) ) {
            return;
        }
        if (pd != null && pd.isShowing()) {
            return;
        }
        pd = DialogUtils.createLoadingDialog(mContext, message);
        pd.setCancelable(true);
        try {
            if (!pd.isShowing()) {
                pd.show();
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    /***
     * 隐藏加载提示
     */
    public static void dismissPd() {
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
        }
    }
}
