package com.bjanch.www.kinmentv.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bjanch.www.kinmentv.R;
import com.bjanch.www.kinmentv.util.Constants;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wxy on 2015/5/6.
 */
public class OnDemandSortListAdt extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context odSortContext;
    private List<String> odSortNames;
    public List<Boolean> states = new ArrayList<>();

    public OnDemandSortListAdt(Context context, List<String> odSortNames) {
        this.odSortContext = context;
        this.odSortNames = odSortNames;
        mInflater = LayoutInflater.from(odSortContext);
        if (odSortNames != null) {
            for (int i = 0; i < odSortNames.size(); i++) {
                states.add(false);
            }
        }
    }

    @Override
    public int getCount() {
        if (odSortNames == null)
            return 0;
        return odSortNames.size();
    }

    @Override
    public Object getItem(int i) {
        if (odSortNames == null)
            return null;
        return odSortNames.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        OnDemandSortHolder sortHolder;
        if (view == null) {
            view = mInflater.inflate(R.layout.on_demand_srot_list_frg_item, viewGroup, false);
            sortHolder = new OnDemandSortHolder();
            ViewUtils.inject(sortHolder, view);
            view.setTag(sortHolder);
        } else {
            sortHolder = (OnDemandSortHolder) view.getTag();
        }
        if (odSortNames != null) {
            sortHolder.odSortName.setTag(Constants.SORT_NAME_KEY + odSortNames.get(i));
            if (states.size() > 0) {
                if (states.get(i)) {
                    sortHolder.odSortName.setTextColor(odSortContext.getResources()
                            .getColor(R.color.green));
                } else {
                    sortHolder.odSortName.setTextColor(Color.WHITE);
                }
            }
            sortHolder.odSortName.setText(odSortNames.get(i));
        }
        return view;
    }

    private class OnDemandSortHolder {
        @ViewInject(R.id.on_demand_sort_item_tv)
        private TextView odSortName;
    }
}
