package com.bjanch.www.kinmentv.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjanch.www.kinmentv.R;
import com.bjanch.www.kinmentv.bean.KmProgramContentListBean;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by wxy on 2015/4/14.
 */
public class OnDemandSlContentAdt extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context mContext;
    private List<KmProgramContentListBean> kmContentListBeans;

    public OnDemandSlContentAdt(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    public void addData(List<KmProgramContentListBean> contentListBeans){
        this.kmContentListBeans = contentListBeans;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (kmContentListBeans == null) return 0;
        return kmContentListBeans.size();
    }

    @Override
    public Object getItem(int i) {
        if (kmContentListBeans == null) return null;
        return kmContentListBeans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        SortContentHolder mHolder;
        if (view == null) {
            mHolder = new SortContentHolder();
            view = mInflater.inflate(R.layout.on_demand_srot_list_content_item,
                    viewGroup, false);
            ViewUtils.inject(mHolder, view);
            view.setTag(mHolder);
        } else {
            mHolder = (SortContentHolder) view.getTag();
        }

        if (kmContentListBeans != null) {
            KmProgramContentListBean contentListBean = kmContentListBeans.get(i);
            String pUrl = contentListBean.getCrPic();
            if (pUrl != null) {
                BitmapUtils bitmapUtils = new BitmapUtils(mContext);
                bitmapUtils.display(mHolder.mContentItemPicIv, pUrl);
            } else {
                mHolder.mContentItemPicIv.setImageResource(R.mipmap.default_pic);
            }
            mHolder.mContentItemNameTv.setText(contentListBean.getCrName());
        }
        return view;
    }

    private class SortContentHolder {
        @ViewInject(R.id.on_demand_content_item_pic_iv)
        ImageView mContentItemPicIv;
        @ViewInject(R.id.on_demand_content_item_name_tv)
        TextView mContentItemNameTv;
    }
}
