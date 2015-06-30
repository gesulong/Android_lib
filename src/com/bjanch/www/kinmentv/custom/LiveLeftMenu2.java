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
import android.widget.ListView;

import com.bjanch.www.kinmentv.R;

public class LiveLeftMenu2 extends LinearLayout {

    private View view;

    private static MHandler handler;

    private boolean isMenuShowing = true;

    private LayoutInflater inflater;

    private ListView live_lv;

    public LiveLeftMenu2(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.live_left_menu_layout2, this);
        if (isInEditMode())
            return;
        initMenu();
    }

    private void initMenu() {

        handler = new MHandler();
        handler.sendEmptyMessageDelayed(1, 10000);
        live_lv = (ListView) view.findViewById(R.id.live_lv);
    }

    // 隐藏左侧节目单
    public void hiddenMenu() {
        Animation translateAnimation = new TranslateAnimation(getScaleX(),
                -getWidth(), getScaleY(), getScaleY());
        translateAnimation.setDuration(300);

        startAnimation(translateAnimation);

        setVisibility(View.GONE);

        isMenuShowing = false;

        handler.removeMessages(1);
    }

    // 显示左侧节目单
    public void popMenu() {

        Animation translateAnimation = new TranslateAnimation(-getWidth(),
                getScaleX(), getScaleY(), getScaleY());
        translateAnimation.setDuration(300);

        startAnimation(translateAnimation);

        setVisibility(View.VISIBLE);

        live_lv.requestFocus();

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
        if(isMenuShowing)
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

    class MHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 1:
                    hiddenMenu();
                    break;
                case 2:
                    setVisibility(View.VISIBLE);
                    break;
                case 3:
                    setVisibility(View.GONE);
                    break;

                default:
                    break;
            }

            super.handleMessage(msg);
        }
    }
}
