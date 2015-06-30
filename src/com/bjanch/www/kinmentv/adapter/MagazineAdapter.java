package com.bjanch.www.kinmentv.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjanch.www.kinmentv.R;
import com.bjanch.www.kinmentv.act.MagazineWebAct;
import com.bjanch.www.kinmentv.bean.MagazineBean;
import com.bjanch.www.kinmentv.util.AnimTools;
import com.bjanch.www.kinmentv.util.IntentUtils;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;

public class MagazineAdapter extends RecyclerView.Adapter<MagazineAdapter.ViewHolder> {

    private Activity context;
    private List<MagazineBean> magList; // 需要显示的报刊数据
    private BitmapUtils bitmapUtils;

    private int a = 0;

    public MagazineAdapter(Activity context) {

        this.context = context;

        magList = new ArrayList<>();

        bitmapUtils = new BitmapUtils(context);

    }

    // 得到显示数据
    public void addMagazines(List<MagazineBean> magList) {
        this.magList = magList;
        a = 0;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = View.inflate(viewGroup.getContext(), R.layout.magazine_item, null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.magazine_item_name_tv.setText(magList.get(i).getPubName());
        bitmapUtils.configDefaultLoadFailedImage(R.mipmap.news_img_default);
        bitmapUtils.display(viewHolder.magazine_item_iv, magList.get(i).getPubCoverUrl());
        viewHolder.magazine_item_layout.setTag(i);
        if (a == 0) {
            viewHolder.magazine_item_layout.requestFocus();
            a = 1;
        }

    }

    @Override
    public int getItemCount() {
        return magList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView magazine_item_name_tv;
        public LinearLayout magazine_item_layout;
        public ImageView magazine_item_iv;

        public ViewHolder(View view) {
            super(view);
            magazine_item_layout = (LinearLayout) view.findViewById(R.id.magazine_item_layout);
            magazine_item_name_tv = (TextView) view.findViewById(R.id.magazine_item_name_tv);
            magazine_item_iv = (ImageView) view.findViewById(R.id.magazine_item_iv);

            magazine_item_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    IntentUtils.skipString(context, MagazineWebAct.class, "webURL", magList.get((int) view.getTag()).getPubUrl(), false);
                }
            });

            magazine_item_layout.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if (b) {
                        magazine_item_iv.setBackgroundResource(R.drawable.gridview_item_focuse);
                        magazine_item_name_tv.setText(magList.get((int) view.getTag()).getPubName());
                        AnimTools.magnifyAnim(view, 1.05f, true);
                    } else {
                        magazine_item_iv.setBackgroundColor(0x00000000);
                        AnimTools.shrinkAnim(view, 1.05f, true);
                    }
                }
            });

        }
    }

}
