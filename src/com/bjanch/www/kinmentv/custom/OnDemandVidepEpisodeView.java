package com.bjanch.www.kinmentv.custom;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import com.bjanch.www.kinmentv.R;
import com.bjanch.www.kinmentv.bean.OnDemandVideoBean;

import java.util.List;

/**
 * 自定义播放器剧集列表
 * 
 * @author wxy
 * 
 */
public class OnDemandVidepEpisodeView extends FrameLayout {
	private View view;
	private Context mContext;
	private LayoutInflater inflater;
	private Animation sildeIn;
	private Animation sildeOut;
	private List<OnDemandVideoBean> mBeans;

	public OnDemandVidepEpisodeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.on_demand_video_episode_list_vw, this);
		if (isInEditMode())
			return;
		initControl(view);
	}

	private void initControl(View view) {
		sildeIn = AnimationUtils.loadAnimation(mContext, R.anim.in_from_right);
		sildeOut = AnimationUtils.loadAnimation(mContext, R.anim.out_to_right);
	}

	/**
	 * 分享布局显示
	 */
	public void episodeShow() {
		if (getVisibility() == View.INVISIBLE) {
			setVisibility(View.VISIBLE);
			startAnimation(sildeIn);
		}
	}

	/**
	 * 分享布局隐藏
	 */
	public void episodeHide() {
		if (getVisibility() == View.VISIBLE) {
			startAnimation(sildeOut);
			setVisibility(View.INVISIBLE);
		}
	}
}
