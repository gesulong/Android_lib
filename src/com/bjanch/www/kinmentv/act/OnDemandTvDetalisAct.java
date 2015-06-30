package com.bjanch.www.kinmentv.act;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bjanch.www.kinmentv.adapter.OnDemandDetailsEpisodeAdt;
import com.bjanch.www.kinmentv.base.BaseFrgActivity;
import com.bjanch.www.kinmentv.bean.ActorBean;
import com.bjanch.www.kinmentv.bean.MPConPlayManager;
import com.bjanch.www.kinmentv.bean.OnDemandDetailsBean;
import com.bjanch.www.kinmentv.bean.OnDemandVideoBean;
import com.bjanch.www.kinmentv.bean.TypeBean;
import com.bjanch.www.kinmentv.custom.MPlayer;
import com.bjanch.www.kinmentv.http.HttpCallBack;
import com.bjanch.www.kinmentv.http.JsonParams;
import com.bjanch.www.kinmentv.http.Urls;
import com.bjanch.www.kinmentv.util.Constants;
import com.bjanch.www.kinmentv.util.FileUtils;
import com.bjanch.www.kinmentv.util.HttpHelper;
import com.bjanch.www.kinmentv.util.MPConPlayUtils;
import com.bjanch.www.kinmentv.util.ToastTools;
import com.bjanch.www.kinmentv.util.UtilsTools;
import com.bjanch.www.kinmentv.R;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wxy on 2015/4/14.
 */
@ContentView(R.layout.on_demand_tv_drama_details_act)
public class OnDemandTvDetalisAct extends BaseFrgActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    @ViewInject(R.id.on_demand_details_iv)
    private ImageView mOnDemandDetailsIv;
    @ViewInject(R.id.on_demand_details_title_tv)
    private TextView mOnDemandDetailsTitleTv;
    @ViewInject(R.id.on_demand_details_counts_tv)
    private TextView mOnDemandDetailsCountsTv;
    @ViewInject(R.id.on_demand_details_abstract_tv)
    private TextView mOnDemandDetailsAbstractTv;
    @ViewInject(R.id.on_demand_type_tv)
    private TextView mOnDemandTypeTv;
    @ViewInject(R.id.od_demand_actor_tv)
    private TextView mOnDemandActorTv;
    @ViewInject(R.id.on_demand_date_tv)
    private TextView mOnDemandDateTv;
    @ViewInject(R.id.on_demand_details_play_tv)
    private TextView mOnDemandDetailsPlayTv;
    @ViewInject(R.id.on_demand_details_more_abstract_tv)
    private TextView mOnDemandDetailsMoreTv;
    @ViewInject(R.id.on_demand_episode_gv)
    private GridView mOnDemandEpisodeGv;

    public static final int BACK_DETAILS_CODE = 101;
    private PopupWindow mOnDemandDetailsPw;
    private OnDemandDetailsEpisodeAdt mOnDemandEpisodeAdt;
    private OnDemandDetailsBean mOnDemandDetailsBean;
    private String id;
    private String mOnDemandDetailsAbstractStr;
    private String mOnDemandDetailsTitleStr;
    private String mOnDemandDetailsCoverPicUrl;
    private String mOnDemandDateStr;
    private OnDemandDetailsHandler odDetailsHandler;
    private List<String> actors = new ArrayList<>();
    private List<String> types = new ArrayList<>();

    private class OnDemandDetailsHandler extends Handler {
        WeakReference<Activity> mActivityReference;

        OnDemandDetailsHandler(Activity activity) {
            mActivityReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Activity activity = mActivityReference.get();
            if (activity != null) {
                switch (msg.what) {
                    case Constants.SUCCESS:
                        setData();
                        setAdapter();
                        break;
                    case Constants.FAIL:
                        ToastTools.frameToast(OnDemandTvDetalisAct.this, "訪問失敗", R.drawable.custom_toast);
                        break;
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
        mOnDemandDetailsPlayTv.requestFocus();
        odDetailsHandler = new OnDemandDetailsHandler(this);
        getIntentData();
        setListener();
//        getJsonData();
        getNetData();
    }


    private void setAdapter() {
        mOnDemandEpisodeAdt = new OnDemandDetailsEpisodeAdt(this, mOnDemandDetailsBean.getRows().size());
        mOnDemandEpisodeGv.setAdapter(mOnDemandEpisodeAdt);
    }

    private void updateTextColor(int i) {
        // 点击位置更新UI
        for (int position = 0; position < mOnDemandEpisodeAdt.states.size(); position++) {
            if (position == i) {
                mOnDemandEpisodeAdt.states.set(position, true);
            } else {
                mOnDemandEpisodeAdt.states.set(position, false);
            }
        }
        // 更新适配器
        mOnDemandEpisodeAdt.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        updateTextColor(i);
        skip(i);
    }

    private void getIntentData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id = bundle.getString(Constants.CONTENT_ID_KEY);
            mOnDemandDetailsTitleStr = bundle.getString(Constants.CONTENT_NAME_KEY);
            mOnDemandDetailsAbstractStr = bundle.getString(Constants.CONTENT_ABSTRACT_KEY);
            mOnDemandDetailsCoverPicUrl = bundle.getString(Constants.CONTENT_COVER_PIC_KEY);
            mOnDemandDateStr = bundle.getString(Constants.CONTENT_DATE_KEY);
            Object[] cobjActors = (Object[]) bundle.getSerializable(Constants.CONTENT_ACTOR_KEY);
            for (Object cobjActor : cobjActors) {
                ActorBean actorBean = (ActorBean) cobjActor;
                actors.add(actorBean.getName());
            }
            Object[] cobjTypes = (Object[]) bundle.getSerializable(Constants.CONTENT_TYPE_KEY);
            for (Object cobjType : cobjTypes) {
                TypeBean typeBean = (TypeBean) cobjType;
                types.add(typeBean.getName());
            }
        }
    }

    private void setData() {
        mOnDemandDetailsAbstractTv.setText("簡介：" + mOnDemandDetailsAbstractStr);
        mOnDemandDetailsTitleTv.setText(mOnDemandDetailsTitleStr);
        if (mOnDemandDetailsBean != null) {
            mOnDemandDetailsCountsTv.setText("(共" + mOnDemandDetailsBean.getRows().size() + "集)");
            BitmapUtils bitmapUtils = new BitmapUtils(this);
            bitmapUtils.display(mOnDemandDetailsIv, mOnDemandDetailsCoverPicUrl);
        }
        String typeStr = "";
        for (int i = 0; i < types.size(); i++) {
            if (i < types.size() - 1) {
                typeStr = typeStr + types.get(i) + ",";
            } else {
                typeStr = typeStr + types.get(i);
            }
        }
        mOnDemandTypeTv.setText("類型：" + typeStr);
        String actorStr = "";
        for (int i = 0; i < actors.size(); i++) {
            if (i < actors.size() - 1) {
                actorStr = actorStr + actors.get(i) + ",";
            } else {
                actorStr = actorStr + actors.get(i);
            }
        }
        mOnDemandActorTv.setText("主演：" + actorStr);
        mOnDemandDateTv.setText("時間：" + mOnDemandDateStr);
    }

    /**
     * 播放Urls集合
     */
    private ArrayList<OnDemandVideoBean> playUrls() {
        if (mOnDemandDetailsBean.getRows() != null) {
            ArrayList<OnDemandVideoBean> playVideoList = new ArrayList<>();
            for (int i = 0; i < mOnDemandDetailsBean.getRows().size(); i++) {
                OnDemandVideoBean videoBean = new OnDemandVideoBean();
                videoBean.setPlayUrl(mOnDemandDetailsBean.getRows().get(i).getSrUrl());
                videoBean.setPlayName(mOnDemandDetailsBean.getRows().get(i).getSrName());
                playVideoList.add(videoBean);
            }
            return playVideoList;
        }
        return null;
    }

    private void setListener() {
        mOnDemandDetailsPlayTv.setOnClickListener(this);
        mOnDemandDetailsMoreTv.setOnClickListener(this);
        mOnDemandEpisodeGv.setOnItemClickListener(this);
    }

    private void getJsonData() {
        String json = Constants.TV_DRAMA_DETAILS_JSON;
        mOnDemandDetailsBean = JsonParams.getOnDemandDetail(json);
        if (mOnDemandDetailsBean == null) return;
        odDetailsHandler.sendEmptyMessage(Constants.SUCCESS);
    }

    private void getNetData() {
        RequestParams params = new RequestParams();
        params.addBodyParameter(Constants.CONTENT_ID_KEY, id);
        if (OnDemandAct.mOnDemandContext != null) {
            new HttpHelper(OnDemandAct.mOnDemandContext).postData(Urls.getInstance().KM_PROGRAME_DETAILS_URL, params, new HttpCallBack() {
                @Override
                public void onSuccess(ResponseInfo responseInfo) {
                    super.onSuccess(responseInfo);
                    if (responseInfo.statusCode == 200) {
                        FileUtils.writeFile(responseInfo.result.toString(), FileUtils.FileTempDir + "jsonFolder/", "/OnDemandDetailsJson.txt");
                        mOnDemandDetailsBean = JsonParams.getOnDemandDetail(responseInfo.result.toString());
                        if (mOnDemandDetailsBean == null) {
                            return;
                        }
                        odDetailsHandler.sendEmptyMessage(Constants.SUCCESS);
                    }
                }

                @Override
                public void onFailure(HttpException e, String s) {
                    odDetailsHandler.sendEmptyMessage(Constants.FAIL);
                    super.onFailure(e, s);
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.on_demand_details_play_tv:
                if (mOnDemandDetailsBean != null) {
                    if (mOnDemandDetailsBean.getRows().size() > 0) {
                        int playPos = 0;
                        MPConPlayManager mcpm = MPConPlayUtils.findLastTimePlay(mOnDemandDetailsTitleStr);
                        try {
                            if (mcpm != null) {
                                playPos = mcpm.getEpisodePos();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            playPos = 0;
                        }
                        skip(playPos);
                    } else {
                        ToastTools.frameToast(this, "未找到視頻源", R.drawable.custom_toast);
                    }
                }
                break;
            case R.id.on_demand_details_more_abstract_tv:
                View v = LayoutInflater.from(this).inflate(R.layout.on_demand_details_popup,
                        null);
                TextView detailsMoreTv = (TextView) v.findViewById(R.id.on_demand_details_more_pop_tv);
                TextView detailsTypeTv = (TextView) v.findViewById(R.id.od_details_type_tv);
                TextView detailsDateTv = (TextView) v.findViewById(R.id.od_details_date_tv);
                TextView detailsActorTv = (TextView) v.findViewById(R.id.od_details_actor_tv);
                setPopText(detailsMoreTv, detailsTypeTv, detailsDateTv, detailsActorTv);
                showPop(v);
                break;
        }
    }

    private void setPopText(TextView detailsMoreTv, TextView detailsTypeTv, TextView detailsDateTv, TextView detailsActorTv) {
        detailsMoreTv.setText(mOnDemandDetailsAbstractTv.getText().toString().trim());
        detailsTypeTv.setText(mOnDemandTypeTv.getText().toString().trim());
        detailsDateTv.setText(mOnDemandDateTv.getText().toString().trim());
        detailsActorTv.setText(mOnDemandActorTv.getText().toString().trim());
    }

    /**
     * PopupWindow 弹出详情介绍
     *
     * @param view
     */
    public void showPop(View view) {
        if (mOnDemandDetailsPw == null) {
            mOnDemandDetailsPw = new PopupWindow(view, UtilsTools.getScreenWidth(getApplicationContext()),
                    UtilsTools.getScreenHeight(getApplicationContext()) / 2);
        }
        mOnDemandDetailsPw.setFocusable(true);
        mOnDemandDetailsPw.setTouchable(true);
        mOnDemandDetailsPw.setBackgroundDrawable(getResources().getDrawable(
                R.mipmap.details_pop));// 点击空白时popupwindow关闭,设置背景图片，不能在布局中设置，要通过代码来设置
        mOnDemandDetailsPw.setOutsideTouchable(true);// 触摸popupwindow外部，popupwindow消失。这个要求你的popupwindow要有背景图片才可以成功
        mOnDemandDetailsPw.setAnimationStyle(R.style.AnimOnDemandDetails);// 设置窗口显示的动画效果
        mOnDemandDetailsPw.showAtLocation(mOnDemandDetailsMoreTv, Gravity.BOTTOM, 0, 0);
        mOnDemandDetailsPw.update();
        mOnDemandDetailsPw.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case OnDemandTvDetalisAct.BACK_DETAILS_CODE:
                break;
        }
    }

    private void skip(int pos) {
        Intent intent = new Intent(OnDemandTvDetalisAct.this, OnDemandVideoAct.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.PLAY_TYPE_KEY, MPlayer.PLAY_TYPE_ONDEMAND);
        bundle.putInt(Constants.PLAY_INDEX_KEY, pos);
        bundle.putString(Constants.PLAY_PARENT_NAME_KEY, mOnDemandDetailsTitleStr);
        bundle.putSerializable(Constants.PLAY_URLS_KEY, playUrls().toArray());
        intent.putExtras(bundle);
        OnDemandTvDetalisAct.this.startActivityForResult(intent,
                BACK_DETAILS_CODE);
    }
}
