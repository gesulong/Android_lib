package com.bjanch.www.kinmentv.act;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import com.bjanch.www.kinmentv.R;
import com.bjanch.www.kinmentv.base.BaseFrgActivity;
import com.bjanch.www.kinmentv.custom.HomeWebView;
import com.bjanch.www.kinmentv.util.ToastTools;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

@ContentView(R.layout.magazine_web_act_layout)

public class MagazineWebAct extends BaseFrgActivity {

    private long mExitTime = 0;

    @ViewInject(R.id.magazine_wv)
    private HomeWebView magazine_wv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewUtils.inject(this);

        Intent intent = getIntent();
        loadurl(magazine_wv, intent.getStringExtra("webURL"));
    }



    public boolean onKeyDown(int keyCode, KeyEvent event) {// 捕捉返回键
        if(keyCode == KeyEvent.KEYCODE_MENU) {
            finish();
            return true;
        }

        if ((keyCode == KeyEvent.KEYCODE_BACK) && event.isLongPress()) {
            finish();
            return true;
        }

        if ((keyCode == KeyEvent.KEYCODE_BACK) && magazine_wv.canGoBack()) {

            if ((System.currentTimeMillis() - mExitTime) < 1000) {
                ToastTools.frameToast(this, getString(R.string.magazine_back_message), R.drawable.custom_toast);

            }
            mExitTime = System.currentTimeMillis();

            magazine_wv.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void loadurl(HomeWebView view, final String url) {

        view.loadURL(url);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
