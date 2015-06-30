package com.bjanch.www.kinmentv.custom;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import com.bjanch.www.kinmentv.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.ResType;
import com.lidroid.xutils.view.annotation.ResInject;
import com.lidroid.xutils.view.annotation.ViewInject;

public class MPControlBar extends LinearLayout {

    private View view;
    private LayoutInflater inflater;
    @ResInject(id= R.anim.slide_in_up,type = ResType.Animation)
    private Animation sildeIn;
    @ResInject(id=R.anim.slide_out_down,type = ResType.Animation)
    private Animation sildeOut;
    @ViewInject(value=R.id.mp_control_tool_ly,parentId = R.id.mp_control_tool_parent_rl)
    private LinearLayout mpCtrl;

    public MPControlBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(isInEditMode())
            return;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.mp_controlbar, this);
        ViewUtils.inject(this, view);

    }

    /**
     * 显示效果
     */
    public void controlShow(){


        if(mpCtrl.getVisibility()==View.INVISIBLE){
            mpCtrl.setVisibility(View.VISIBLE);
            mpCtrl.startAnimation(sildeIn);
        }
    }
    /**
     * 隐藏效果
     */
    public void controlHide(){
        if(mpCtrl.getVisibility()==View.VISIBLE){
            mpCtrl.startAnimation(sildeOut);
            mpCtrl.setVisibility(View.INVISIBLE);
        }
    }
}
