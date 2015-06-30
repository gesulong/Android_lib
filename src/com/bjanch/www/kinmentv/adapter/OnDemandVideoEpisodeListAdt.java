package com.bjanch.www.kinmentv.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.bjanch.www.kinmentv.R;
import com.bjanch.www.kinmentv.bean.OnDemandVideoBean;

/**
 * 自定义剧集适配器
 * 
 * @author wxy
 * 
 */
public class OnDemandVideoEpisodeListAdt extends BaseAdapter {
	private Context mContext;
	private LayoutInflater mInflater;
	private List<OnDemandVideoBean> mBeans;
	public List<Boolean> states = new ArrayList<>();

	public OnDemandVideoEpisodeListAdt(Context context, List<OnDemandVideoBean> beans) {
		this.mContext = context;
		this.mBeans = beans;
		mInflater = LayoutInflater.from(mContext);
		for (int i = 0; i < mBeans.size(); i++) {
			states.add(false);
		}
	}

	@Override
	public int getCount() {
		if (mBeans != null) {
			return mBeans.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int pos) {
		if (mBeans != null) {
			return mBeans.get(pos);
		}
		return null;
	}

	@Override
	public long getItemId(int pos) {
		return pos;
	}

	@Override
	public View getView(int pos, View convertView, ViewGroup parent) {
		EpisodeViewHolder mHolder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.on_demand_video_episode_list_vw_item,
					parent, false);
			mHolder = new EpisodeViewHolder();
			mHolder.mEpisodeText = (TextView) convertView
					.findViewById(R.id.on_demand_video_episode_tv);
			convertView.setTag(mHolder);
		} else {
			mHolder = (EpisodeViewHolder) convertView.getTag();
		}

		if (mBeans != null) {
			mHolder.mEpisodeText.setText(mBeans.get(pos).getPlayName());
			if (states.size() > 0) {
				if (states.get(pos)) {
					mHolder.mEpisodeText.setTextColor(mContext.getResources()
							.getColor(R.color.light_yellow));
				} else {
					mHolder.mEpisodeText.setTextColor(Color.WHITE);
				}
			}
		}
		return convertView;
	}

	class EpisodeViewHolder {
		TextView mEpisodeText;
	}

}
