package com.bjanch.www.kinmentv.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjanch.www.kinmentv.act.OnDemandMoviceDetalisAct;
import com.bjanch.www.kinmentv.bean.KmProgramContentListBean;
import com.bjanch.www.kinmentv.R;
import com.bjanch.www.kinmentv.util.AnimTools;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * Created by wxy on 2015/5/12.
 */
public class RelatedMoviceVw extends LinearLayout {
    private Context mContext;
    private OnDemandMoviceDetalisAct odMoviceAct;
    private List<KmProgramContentListBean> kmProgramListBeans;

    public RelatedMoviceVw(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public void setView(List<KmProgramContentListBean> kmProgramListBeans, OnDemandMoviceDetalisAct odMoviceAct) {
        this.odMoviceAct = odMoviceAct;
        this.kmProgramListBeans = kmProgramListBeans;
        removeAllViews();
        if (kmProgramListBeans.size() > 7) {
            for (int i = 0; i < 7; i++) {
                if (kmProgramListBeans.get(i).getCrName().equals(odMoviceAct.mOnDemandMoviceTitleStr)) {
                    kmProgramListBeans.remove(i);
                } else {
                    addView(i);
                }
            }
        } else {
            for (int i = 0; i < kmProgramListBeans.size(); i++) {
                if (kmProgramListBeans.get(i).getCrName().equals(odMoviceAct.mOnDemandMoviceTitleStr)) {
                    kmProgramListBeans.remove(i);
                } else {
                    addView(i);
                }
            }
        }
    }

    private void addView(int index) {
        if (odMoviceAct != null) {
            if (kmProgramListBeans != null) {
                View view = LayoutInflater.from(odMoviceAct).inflate(R.layout.od_demand_movice_details_item, null);
                ImageView relatedIv = (ImageView) view.findViewById(R.id.related_movice_iv);
                TextView relatedTv = (TextView) view.findViewById(R.id.related_movice_tv);
                relatedIv.setOnClickListener(new RelatedMoviceListener(index));
                relatedIv.setOnFocusChangeListener(new OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean b) {
                        if (b) {
                            AnimTools.magnifyAnim(view, 1.1f, true);
                        } else {
                            AnimTools.shrinkAnim(view, 1.1f, true);
                        }
                    }
                });
                String relatedUrl = kmProgramListBeans.get(index).getCrPic();
                BitmapUtils bitmapUtils = new BitmapUtils(odMoviceAct);
                bitmapUtils.display(relatedIv, relatedUrl);
                relatedTv.setText(kmProgramListBeans.get(index).getCrName());
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(getWidth() / 7, ViewGroup.LayoutParams.MATCH_PARENT);
                view.setLayoutParams(params);
                addView(view);
            }
        }
    }

    private class RelatedMoviceListener implements OnClickListener {
        private int index;

        public RelatedMoviceListener(int pos) {
            this.index = pos;
        }

        @Override
        public void onClick(View v) {
            String actor = splitStrType(kmProgramListBeans.get(index).getAtId());
            String type = splitStrType(kmProgramListBeans.get(index).getTypeId());
            String typeId = splitStrTypeId(kmProgramListBeans.get(index).getTypeId());
            String date = kmProgramListBeans.get(index).getCrDate();
            String info = kmProgramListBeans.get(index).getCrDetail();
            String pUrl = kmProgramListBeans.get(index).getCrPic();
            String ids = kmProgramListBeans.get(index).getId();
            String title = kmProgramListBeans.get(index).getCrName();
            odMoviceAct.relatedClick(actor, date, type, typeId, info, title, pUrl, ids);
        }
    }

    private String splitStrType(String str) {
        String[] datas = str.split("\\|");
        return datas[1];
    }

    private String splitStrTypeId(String str) {
        String[] datas = str.split("\\|");
        String[] ids = datas[0].split(",");
        return ids[0];
    }

    public interface RelatedCallBack {
        void relatedClick(String actor, String date, String type, String typeId, String info, String title, String pUrl, String ids);
    }
}
