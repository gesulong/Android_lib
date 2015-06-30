package com.bjanch.www.kinmentv.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bjanch.www.kinmentv.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wxy on 2015/5/11.
 */
public class OnDemandDetailsEpisodeAdt extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private int counts;
    public List<Boolean> states = new ArrayList<>();

    public OnDemandDetailsEpisodeAdt(Context context, int counts) {
        this.mContext = context;
        this.counts = counts;
        for (int i = 0; i < counts; i++) {
            states.add(false);
        }
        mInflater = LayoutInflater.from(mContext);

    }

    @Override
    public int getCount() {
        return counts;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        OnDemandEpisodeHolder odEpisodeHolder;
        if (view == null) {
            view = mInflater.inflate(R.layout.on_demand_details_episode_item, viewGroup, false);
            odEpisodeHolder = new OnDemandEpisodeHolder();
            ViewUtils.inject(odEpisodeHolder, view);
            view.setTag(odEpisodeHolder);
        } else {
            odEpisodeHolder = (OnDemandEpisodeHolder) view.getTag();
        }
        if (states.size() > 0) {
            if (states.get(i)) {
                odEpisodeHolder.odEpisodeTv.setTextColor(mContext.getResources()
                        .getColor(R.color.light_yellow));
            } else {
                odEpisodeHolder.odEpisodeTv.setTextColor(Color.WHITE);
            }
        }
        odEpisodeHolder.odEpisodeTv.setText("第" + (i + 1) + "集");
        return view;
    }

    private class OnDemandEpisodeHolder {
        @ViewInject(R.id.episodeItem)
        private TextView odEpisodeTv;
    }
}
