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
public class VdFiltrateAdt extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context vdFiltrateSortContext;
    private List<String> vdFiltrateSortNames;
    public List<Boolean> typeStates = new ArrayList<>();
    public List<Boolean> areaStates = new ArrayList<>();
    public List<Boolean> timeStates = new ArrayList<>();
    private int filtrateType;

    public VdFiltrateAdt(Context context, List<String> vdFiltrateSortNames, int type) {
        this.vdFiltrateSortContext = context;
        this.vdFiltrateSortNames = vdFiltrateSortNames;
        this.filtrateType = type;
        mInflater = LayoutInflater.from(vdFiltrateSortContext);
        if (vdFiltrateSortNames != null) {
            switch (type) {
                case Constants.FILTRATE_TYPE://类型
                    addStates(typeStates, vdFiltrateSortNames);
                    break;
                case Constants.FILTRATE_AREA://区域
                    addStates(areaStates, vdFiltrateSortNames);
                    break;
                case Constants.FILTRATE_TIME://时间
                    addStates(timeStates, vdFiltrateSortNames);
                    break;
            }
        }
    }

    //初始化筛选类型、区域、时间状态
    private void addStates(List<Boolean> states, List<String> filtrateSortNames) {
        for (int i = 0; i < filtrateSortNames.size(); i++) {
            if (i == 0)
                states.add(true);
            states.add(false);
        }
    }

    //清理筛选
    public void clean(int type){
        switch (type) {
            case Constants.FILTRATE_TYPE://类型
                typeStates.clear();
                addStates(typeStates, vdFiltrateSortNames);
                break;
            case Constants.FILTRATE_AREA://区域
                areaStates.clear();
                addStates(areaStates, vdFiltrateSortNames);
                break;
            case Constants.FILTRATE_TIME://时间
                timeStates.clear();
                addStates(timeStates, vdFiltrateSortNames);
                break;
        }
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        if (vdFiltrateSortNames == null)
            return 0;
        return vdFiltrateSortNames.size();
    }

    @Override
    public Object getItem(int i) {
        if (vdFiltrateSortNames == null)
            return null;
        return vdFiltrateSortNames.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final VdFiltrateSortHolder sortHolder;
        if (view == null) {
            view = mInflater.inflate(R.layout.vd_filtrate_pop_item, viewGroup, false);
            sortHolder = new VdFiltrateSortHolder();
            ViewUtils.inject(sortHolder, view);
            view.setTag(sortHolder);
        } else {
            sortHolder = (VdFiltrateSortHolder) view.getTag();
        }
        if (vdFiltrateSortNames != null) {
            switch (filtrateType) {
                case Constants.FILTRATE_TYPE:
                    notifiSelectTextColorState(typeStates, sortHolder.vdFiltrateSortName, i);
                    break;
                case Constants.FILTRATE_AREA:
                    notifiSelectTextColorState(areaStates, sortHolder.vdFiltrateSortName, i);
                    break;
                case Constants.FILTRATE_TIME:
                    notifiSelectTextColorState(timeStates, sortHolder.vdFiltrateSortName, i);
                    break;
            }
            sortHolder.vdFiltrateSortName.setText(vdFiltrateSortNames.get(i));
        }
        return view;
    }

    private void notifiSelectTextColorState(List<Boolean> states, TextView filtrateNameTv, int pos) {
        if (states.size() > 0) {
            if (states.get(pos)) {
                filtrateNameTv.setTextColor(vdFiltrateSortContext.getResources()
                        .getColor(R.color.green));
            } else {
                filtrateNameTv.setTextColor(Color.WHITE);
            }
        }
    }

    private class VdFiltrateSortHolder {
        @ViewInject(R.id.vd_filtrate_item_tv)
        private TextView vdFiltrateSortName;
    }
}
