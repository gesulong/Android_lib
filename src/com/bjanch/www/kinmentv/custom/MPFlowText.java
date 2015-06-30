package com.bjanch.www.kinmentv.custom;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.TrafficStats;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.TextView;


import com.bjanch.www.kinmentv.util.Constants;

import java.lang.ref.WeakReference;

public class MPFlowText extends TextView {
    private Context mContext;
    private FlowHandler fHandler;


    public MPFlowText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        fHandler = new FlowHandler(this);
    }


    public long getUidRxBytes(){ //获取总的接受字节数，包含Mobile和WiFi等
        PackageManager pm = mContext.getPackageManager();
        ApplicationInfo ai = null;
        try {
            ai = pm.getApplicationInfo(Constants.PACKAGE_NAME, PackageManager.GET_ACTIVITIES);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        if(ai!=null)
            return TrafficStats.getUidRxBytes(ai.uid)==TrafficStats.UNSUPPORTED?0:(TrafficStats.getTotalRxBytes()/1024);

        return TrafficStats.UNSUPPORTED;
    }




    private class FlowHandler extends Handler{
        private WeakReference<MPFlowText> ft;
        private long new_KB=0,old_KB=0;
        private boolean isfalg = false;
        private FlowHandler(MPFlowText flowText) {
            ft = new WeakReference<>(flowText);
        }
        @Override
        public void handleMessage(Message msg) {
            MPFlowText mFlowText = ft.get();

            switch(msg.what){
                case 0 :
                    new_KB = getUidRxBytes() - old_KB;
                    old_KB=getUidRxBytes();
                    if(isfalg)
                        mFlowText.setText(new_KB+"KB/S");
                    isfalg=true;
                    sendEmptyMessageDelayed(0, 1000);
                    break;
            }
        }

    }

    public void start(){
        fHandler.sendEmptyMessage(0);
    }

    public void stop(){
        fHandler.removeMessages(0);
    }
}
