package com.bjanch.www.kinmentv.act;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.bjanch.www.kinmentv.R;
import com.bjanch.www.kinmentv.base.BaseFrgActivity;
import com.bjanch.www.kinmentv.custom.AppHomePageLlView;
import com.bjanch.www.kinmentv.http.Urls;
import com.bjanch.www.kinmentv.util.DateUtils;
import com.bjanch.www.kinmentv.util.ToastTools;
import com.bjanch.www.kinmentv.util.UpdataUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.lang.ref.WeakReference;

/**
 * 首页
 * Created by wxy on 2015/4/13.
 * @author  wxy
 */
@ContentView(R.layout.app_main)
public class AppMainAct extends BaseFrgActivity{

    private AppHomePageLlView mCustomLl;
    public static AppMainAct mAppManiContext;
    private MHandler mHandler;
    private long mExitTime = 0;
    @ViewInject(R.id.main_date_tv)
    private TextView date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
        mHandler = new MHandler(AppMainAct.this);
        mAppManiContext = this;
        mCustomLl = (AppHomePageLlView) findViewById(R.id.app_main_custom_ll);
        mCustomLl.initListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        mCustomLl.initListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
            }
        });
        new UpdataUtils(AppMainAct.this).checkedVer(Urls.getInstance().UPDATE);
        upDate();
    }
    private void upDate(){
    mHandler.sendEmptyMessageAtTime(0,1000);
    }

    private static class  MHandler extends Handler{

        private WeakReference<AppMainAct> am;

        public MHandler(AppMainAct activity) {
            am = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            AppMainAct outer = am.get();
            if (outer != null) {
                outer.date.setText(DateUtils.getDateTwa(DateUtils.getCurUnixTime()));
                outer.mHandler.sendEmptyMessageDelayed(0, 1000);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeMessages(0);
    }


    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            ToastTools.frameToast(this, "再按一次返回键退出程序", R.drawable.custom_toast);
            mExitTime = System.currentTimeMillis();
            return;
        }
        super.onBackPressed();
    }
}
