package com.bjanch.www.kinmentv.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bjanch.www.kinmentv.R;
import com.bjanch.www.kinmentv.act.AppMainAct;
import com.bjanch.www.kinmentv.act.LiveActivity;
import com.bjanch.www.kinmentv.act.MagazineAct;
import com.bjanch.www.kinmentv.act.OnDemandAct;
import com.bjanch.www.kinmentv.util.AnimTools;
import com.bjanch.www.kinmentv.util.Constants;
import com.bjanch.www.kinmentv.util.ImageReflect;
import com.bjanch.www.kinmentv.util.IntentUtils;

/**
 * 自定义首页
 *
 * @author wxy
 *
 */
public class AppHomePageLlView extends LinearLayout implements
        OnClickListener, OnFocusChangeListener {
    private OnFocusChangeListener mFocusChangeListener;
    private OnClickListener mOnClickListener;
    private RelativeLayout[] mRelativeLayouts = new RelativeLayout[8];
    private ImageView[] mShadowBackgrounds = new ImageView[8];
    private FrameLayout[] mFrameLayouts = new FrameLayout[8];
    private ImageView[] mReflectImgs = new ImageView[4];// 倒影
    private Context mContext;
    private ScaleAnimEffect animEffect;

    public AppHomePageLlView(Context mContext, AttributeSet attrs) {
        super(mContext, attrs);
        if (isInEditMode()) {
            return;
        }
        this.mContext = mContext;
        this.animEffect = new ScaleAnimEffect();
        addView(LayoutInflater.from(this.mContext).inflate(R.layout.app_layout,
                null));
        initControl();
        setListener();
    }

    public void initListener(OnClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }

    public void initListener(OnFocusChangeListener mFocusChangeListener) {
        this.mFocusChangeListener = mFocusChangeListener;
    }

    private void initControl() {
        mRelativeLayouts[0] = (RelativeLayout) findViewById(R.id.app_live_rl);
        mRelativeLayouts[1] = (RelativeLayout) findViewById(R.id.app_anime_rl);
        mRelativeLayouts[2] = (RelativeLayout) findViewById(R.id.app_game_rl);
        mRelativeLayouts[3] = (RelativeLayout) findViewById(R.id.app_publication_rl);
        mRelativeLayouts[4] = (RelativeLayout) findViewById(R.id.app_movie_rl);
        mRelativeLayouts[5] = (RelativeLayout) findViewById(R.id.app_tv_drama_rl);
        mRelativeLayouts[6] = (RelativeLayout) findViewById(R.id.app_variety_rl);
        mRelativeLayouts[7] = (RelativeLayout) findViewById(R.id.app_children_rl);
        mFrameLayouts[0] = (FrameLayout) findViewById(R.id.app_live_fl);
        mFrameLayouts[1] = (FrameLayout) findViewById(R.id.app_anime_fl);
        mFrameLayouts[2] = (FrameLayout) findViewById(R.id.app_game_fl);
        mFrameLayouts[3] = (FrameLayout) findViewById(R.id.app_publication_fl);
        mFrameLayouts[4] = (FrameLayout) findViewById(R.id.app_movie_fl);
        mFrameLayouts[5] = (FrameLayout) findViewById(R.id.app_tv_drama_fl);
        mFrameLayouts[6] = (FrameLayout) findViewById(R.id.app_variety_fl);
        mFrameLayouts[7] = (FrameLayout) findViewById(R.id.app_children_fl);
        mShadowBackgrounds[0] = (ImageView) findViewById(R.id.app_live_shadow_iv);
        mShadowBackgrounds[1] = (ImageView) findViewById(R.id.app_anime_shadow_iv);
        mShadowBackgrounds[2] = (ImageView) findViewById(R.id.app_game_shadow_iv);
        mShadowBackgrounds[3] = (ImageView) findViewById(R.id.app_publication_shadow_iv);
        mShadowBackgrounds[4] = (ImageView) findViewById(R.id.app_movie_shadow_iv);
        mShadowBackgrounds[5] = (ImageView) findViewById(R.id.app_tv_drama_shadow_iv);
        mShadowBackgrounds[6] = (ImageView) findViewById(R.id.app_variety_shadow_iv);
        mShadowBackgrounds[7] = (ImageView) findViewById(R.id.app_children_shadow_iv);

        mReflectImgs[0] = ((ImageView) findViewById(R.id.app_live_reflect_iv));
        mReflectImgs[1] = ((ImageView) findViewById(R.id.app_anime_reflect_iv));
        mReflectImgs[2] = ((ImageView) findViewById(R.id.app_game_reflect_iv));
        mReflectImgs[3] = ((ImageView) findViewById(R.id.app_children_reflect_iv));

        mReflectImgs[0].setImageBitmap(ImageReflect.createCutReflectedImage(
                ImageReflect.convertViewToBitmap(mFrameLayouts[0]), 0));
        mReflectImgs[1].setImageBitmap(ImageReflect.createCutReflectedImage(
                ImageReflect.convertViewToBitmap(mFrameLayouts[1]), 0));
        mReflectImgs[2].setImageBitmap(ImageReflect.createCutReflectedImage(
                ImageReflect.convertViewToBitmap(mFrameLayouts[2]), 0));
        mReflectImgs[3].setImageBitmap(ImageReflect.createCutReflectedImage(
                ImageReflect.convertViewToBitmap(mFrameLayouts[3]), 0));
    }

    private void setListener() {
        for (int i = 0; i < mRelativeLayouts.length; i++) {
            mShadowBackgrounds[i].setVisibility(View.GONE);
            mRelativeLayouts[i].setOnClickListener(this);
            mRelativeLayouts[i].setOnFocusChangeListener(this);
        }
    }

    @Override
    public void onFocusChange(View view, boolean isFouce) {
        if (mFocusChangeListener != null) {
            mFocusChangeListener.onFocusChange(view, isFouce);
        }
        int index = -1;
        switch (view.getId()) {
            case R.id.app_live_rl:
                index = 0;
                break;
            case R.id.app_anime_rl:
                index = 1;
                break;
            case R.id.app_game_rl:
                index = 2;
                break;
            case R.id.app_publication_rl:
                index = 3;
                break;
            case R.id.app_movie_rl:
                index = 4;
                break;
            case R.id.app_tv_drama_rl:
                index = 5;
                break;
            case R.id.app_variety_rl:
                index = 6;
                break;
            case R.id.app_children_rl:
                index = 7;
                break;
        }
        if (isFouce) {
            showOnFocusAnimation(index);
        } else {
            showLooseFocusAinimation(index);
        }
    }

    private void showLooseFocusAinimation(int paramInt) {
        this.animEffect.setAttributs(1.075F, 1.0F, 1.075F, 1.0F, 100L);
        mRelativeLayouts[paramInt].startAnimation(this.animEffect.createAnimation());
        this.mShadowBackgrounds[paramInt].setVisibility(View.GONE);
        this.mShadowBackgrounds[paramInt].clearAnimation();// 手动结束动画
    }

    private void showOnFocusAnimation(final int paramInt) {
        mFrameLayouts[paramInt].bringToFront();
        mRelativeLayouts[paramInt].requestLayout();
        mFrameLayouts[paramInt].invalidate();
        this.animEffect.setAttributs(1.0F, 1.075F, 1.0F, 1.075F, 100L);
        Animation localAnimation = this.animEffect.createAnimation();
        localAnimation.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                AnimTools.alphaAnim(mShadowBackgrounds[paramInt], 0.6F, 0.3F);
                mShadowBackgrounds[paramInt].setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationStart(Animation animation) {
            }
        });
        mRelativeLayouts[paramInt].startAnimation(localAnimation);
    }

    @Override
    public void onClick(View view) {
        if (mOnClickListener != null) {
            mOnClickListener.onClick(view);
        }
        if (AppMainAct.mAppManiContext != null) {
            switch (view.getId()) {
                case R.id.app_live_rl:
                    IntentUtils.skip(AppMainAct.mAppManiContext, LiveActivity.class,
                            false);
                    break;
                case R.id.app_anime_rl:
                    Constants.SKIP_ID="動漫";
                    IntentUtils.skip(AppMainAct.mAppManiContext, OnDemandAct.class,
                            false);
                    break;
                case R.id.app_game_rl:
                    Constants.SKIP_ID="遊戲";
                    IntentUtils.skip(AppMainAct.mAppManiContext, OnDemandAct.class,
                            false);
                    break;
                case R.id.app_publication_rl:
                    IntentUtils.skip(AppMainAct.mAppManiContext, MagazineAct.class,
                            false);
                    break;
                case R.id.app_movie_rl:
                    Constants.SKIP_ID="電影";
                    IntentUtils.skip(AppMainAct.mAppManiContext, OnDemandAct.class,
                            false);
                    break;
                case R.id.app_tv_drama_rl:
                    Constants.SKIP_ID="電視劇";
                    IntentUtils.skip(AppMainAct.mAppManiContext, OnDemandAct.class,
                            false);
                    break;
                case R.id.app_variety_rl:
                    Constants.SKIP_ID="綜藝";
                    IntentUtils.skip(AppMainAct.mAppManiContext, OnDemandAct.class,
                            false);
                    break;
                case R.id.app_children_rl:
                    Constants.SKIP_ID="少兒";
                    IntentUtils.skip(AppMainAct.mAppManiContext, OnDemandAct.class,
                            false);
                    break;
            }
        }
    }
}