package com.bjanch.www.kinmentv.custom;


import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjanch.www.kinmentv.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class LiveBottomMenu2 extends LinearLayout {

    private View view;

    private static MHandler handler;

    private boolean isMenuShowing = true;

    private LayoutInflater inflater;

    @ViewInject(R.id.live_show_btm_progress)
    private LiveProgressView live_progress;

    @ViewInject(R.id.live_show_channel_btm_tv)
    private TextView live_btm_channel_tv;

    @ViewInject(R.id.live_show_nowpro_btm_tv)
    private TextView live_btm_nowpro_tv;

    @ViewInject(R.id.live_show_nextpro_btm_tv)
    private TextView live_btm_nextpro_tv;

    public LiveBottomMenu2(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.live_bottom_menu_layout2, this);

        ViewUtils.inject(this, view);

        if (isInEditMode())
            return;
        initMenu();
    }

    private void initMenu() {

        handler = new MHandler();
        handler.sendEmptyMessageDelayed(1, 10000);
    }

    //改变bottom的文本显示信息
    public void changeViewText(String t1, String t2, String t3) {
        live_btm_channel_tv.setText(t1);
        live_btm_nowpro_tv.setText(t2);
        live_btm_nextpro_tv.setText(t3);
    }

    // 隐藏底部节目单
    public void hiddenMenu() {
        Animation translateAnimation = new TranslateAnimation(getScaleX(),
                getScaleX(), getScaleY(), getScaleY() + getHeight());
        translateAnimation.setDuration(300);

        startAnimation(translateAnimation);

        setVisibility(View.GONE);

        isMenuShowing = false;

        handler.removeMessages(1);
    }

    // 显示底部节目单
    public void popMenu() {

        Animation translateAnimation = new TranslateAnimation(getScaleX(),
                getScaleX(), getScaleY() + getHeight(), getScaleY());
        translateAnimation.setDuration(300);

        startAnimation(translateAnimation);

        setVisibility(View.VISIBLE);

        isMenuShowing = true;

        handler.removeMessages(1);
        handler.sendEmptyMessageDelayed(1, 10000);

    }

    public boolean getMenuShowingState() {
        return isMenuShowing;
    }

    public void setMenuShowingState(boolean isMenuShowing) {
        this.isMenuShowing = isMenuShowing;
    }

    public void changeMenuShowingState() {
        if (isMenuShowing)
            hiddenMenu();
        else {
            popMenu();
        }
    }

    public void popOrDelay() {
        if (isMenuShowing)
            changeHiddenDelay();
        else {
            popMenu();
        }
    }

    public void changeHiddenDelay() {
        handler.removeMessages(1);
        handler.sendEmptyMessageDelayed(1, 10000);
    }

    /**
     * 显示进度条
     *
     * @param st
     *            节目开始的时间
     * @param ot
     *            节目结束的时间
     * @param nt
     *            当前播放到的时间
     */
    public void startPogress(long st, long ot, long nt) {
        live_progress.startProgress(st, ot, nt);
    }

    class MHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 1:
                    hiddenMenu();
                    break;
                default:
                    break;
            }

            super.handleMessage(msg);
        }
    }

}
