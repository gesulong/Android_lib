package com.bjanch.www.kinmentv.act;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bjanch.www.kinmentv.R;
import com.bjanch.www.kinmentv.base.BaseFrgActivity;
import com.bjanch.www.kinmentv.bean.ActorBean;
import com.bjanch.www.kinmentv.bean.KmProgramContentBean;
import com.bjanch.www.kinmentv.bean.MPConPlayManager;
import com.bjanch.www.kinmentv.bean.OnDemandDetailsBean;
import com.bjanch.www.kinmentv.bean.OnDemandVideoBean;
import com.bjanch.www.kinmentv.bean.TypeBean;
import com.bjanch.www.kinmentv.custom.MPlayer;
import com.bjanch.www.kinmentv.custom.RelatedMoviceVw;
import com.bjanch.www.kinmentv.http.HttpCallBack;
import com.bjanch.www.kinmentv.http.JsonParams;
import com.bjanch.www.kinmentv.http.Urls;
import com.bjanch.www.kinmentv.util.Constants;
import com.bjanch.www.kinmentv.util.HttpHelper;
import com.bjanch.www.kinmentv.util.MPConPlayUtils;
import com.bjanch.www.kinmentv.util.ToastTools;
import com.bjanch.www.kinmentv.util.UtilsTools;
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
@ContentView(R.layout.on_demand_movice_details_act)
public class OnDemandMoviceDetalisAct extends BaseFrgActivity implements View.OnClickListener, RelatedMoviceVw.RelatedCallBack {
    @ViewInject(R.id.on_demand_movice_details_iv)
    private ImageView mOnDemandMoviceIv;
    @ViewInject(R.id.on_demand_movice_details_title_tv)
    private TextView mOnDemandMoviceTitleTv;
    @ViewInject(R.id.on_demand_movice_details_abstract_tv)
    private TextView mOnDemandMoviceAbstractTv;
    @ViewInject(R.id.on_demand_movice_type_tv)
    private TextView mOnDemandTypeTv;
    @ViewInject(R.id.od_demand_movice_actor_tv)
    private TextView mOnDemandActorTv;
    @ViewInject(R.id.on_demand_movice_date_tv)
    private TextView mOnDemandDateTv;
    @ViewInject(R.id.on_demand_movice_details_play_tv)
    private TextView mOnDemandMovicePlayTv;
    @ViewInject(R.id.on_demand_movice_details_more_abstract_tv)
    private TextView mOnDemandMoviceMoreTv;
    @ViewInject(R.id.od_related_movice_vw)
    private RelatedMoviceVw mOnDemandRelatedVw;

    public static final int BACK_DETAILS_CODE = 101;
    private PopupWindow mOnDemandMovicePw;
    private KmProgramContentBean kmProgramContentBean;
    private OnDemandDetailsBean mOnDemandMoviceBean;
    private String classCode;
    private String mOnDemandMoviceAbstractStr;
    public String mOnDemandMoviceTitleStr;
    private String mOnDemandMoviceCoverPicUrl;
    private String mOnDemandDateStr;
    private String mOnDemandActorStr;
    private String mOnDemandTypeStr;
    private String relatedTypeId;//查詢相關影片type的Id
    private OnDemandMoviceHandler odMoviceHandler;
    private List<String> actors = new ArrayList<>();
    private List<String> types = new ArrayList<>();
    private List<String> typeIds = new ArrayList<>();
    private int pageIndex = 1;
    private String pageRows = "12";
    private String ids;

    @Override
    public void relatedClick(String actor, String date, String type, String typeId, String info, String title, String pUrl, String ids) {
        this.mOnDemandActorStr = actor;
        this.mOnDemandDateStr = date;
        this.mOnDemandTypeStr = type;
        this.relatedTypeId = typeId;
        this.mOnDemandMoviceAbstractStr = info;
        this.mOnDemandMoviceTitleStr = title;
        this.mOnDemandMoviceCoverPicUrl = pUrl;
        this.ids = ids;
        getNetData();
        getNetPlayUrl(Constants.MOVICE_DETAILS_RELATED_TYPE);
//        getUrlJsonData(Constants.MOVICE_DETAILS_RELATED_TYPE);
    }

    //設置相關影片點擊獲得的數據信息
    private void setRelatedText() {
        mOnDemandMoviceAbstractTv.setText("簡介：" + mOnDemandMoviceAbstractStr);
        mOnDemandMoviceTitleTv.setText(mOnDemandMoviceTitleStr);
        BitmapUtils bitmapUtils = new BitmapUtils(this);
        bitmapUtils.display(mOnDemandMoviceIv, mOnDemandMoviceCoverPicUrl);
        mOnDemandTypeTv.setText("類型：" + mOnDemandTypeStr);
        mOnDemandActorTv.setText("主演：" + mOnDemandActorStr);
        mOnDemandDateTv.setText("時間：" + mOnDemandDateStr);
    }

    private class OnDemandMoviceHandler extends Handler {
        WeakReference<Activity> mActivityReference;

        OnDemandMoviceHandler(Activity activity) {
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
                        break;
                    case Constants.FAIL:
                        ToastTools.frameToast(OnDemandMoviceDetalisAct.this, "訪問失敗", R.drawable.custom_toast);
                        break;
                    case Constants.N_SUCCESS:
                        setRelatedText();
                        break;
                }
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
        odMoviceHandler = new OnDemandMoviceHandler(this);
        getIntentData();
        setListener();
//        getRelatedJsonData();
//        getUrlJsonData(Constants.MOVICE_DETAILS_TYPE);
        getNetData();
        getNetPlayUrl(Constants.MOVICE_DETAILS_TYPE);
    }

    private void getIntentData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            this.ids = bundle.getString(Constants.CONTENT_ID_KEY);
            this.classCode = bundle.getString(Constants.CLASSCODE);
            this.mOnDemandMoviceTitleStr = bundle.getString(Constants.CONTENT_NAME_KEY);
            this.mOnDemandMoviceAbstractStr = bundle.getString(Constants.CONTENT_ABSTRACT_KEY);
            this.mOnDemandMoviceCoverPicUrl = bundle.getString(Constants.CONTENT_COVER_PIC_KEY);
            this.mOnDemandDateStr = bundle.getString(Constants.CONTENT_DATE_KEY);
            Object[] cobjActors = (Object[]) bundle.getSerializable(Constants.CONTENT_ACTOR_KEY);
            for (Object cobjActor : cobjActors) {
                ActorBean actorBean = (ActorBean) cobjActor;
                actors.add(actorBean.getName());
            }
            Object[] cobjTypes = (Object[]) bundle.getSerializable(Constants.CONTENT_TYPE_KEY);
            for (Object cobjType : cobjTypes) {
                TypeBean typeBean = (TypeBean) cobjType;
                types.add(typeBean.getName());
                typeIds.add(typeBean.getId());
            }
            this.relatedTypeId = typeIds.get(0);
        }
    }

    private void setData() {
        mOnDemandMoviceAbstractTv.setText("簡介：" + mOnDemandMoviceAbstractStr);
        mOnDemandMoviceTitleTv.setText(mOnDemandMoviceTitleStr);
        BitmapUtils bitmapUtils = new BitmapUtils(this);
        bitmapUtils.display(mOnDemandMoviceIv, mOnDemandMoviceCoverPicUrl);
        String typeStr = "";
        for (int i = 0; i < types.size(); i++) {
            if (i < types.size() - 1) {
                typeStr = typeStr + types.get(i) + ",";
            } else {
                typeStr = typeStr + types.get(i);
            }
        }
        this.mOnDemandTypeStr = typeStr;
        mOnDemandTypeTv.setText("類型：" + typeStr);
        String actorStr = "";
        for (int i = 0; i < actors.size(); i++) {
            if (i < actors.size() - 1) {
                actorStr = actorStr + actors.get(i) + ",";
            } else {
                actorStr = actorStr + actors.get(i);
            }
        }
        this.mOnDemandActorStr = actorStr;
        mOnDemandActorTv.setText("主演：" + actorStr);
        mOnDemandDateTv.setText("時間：" + mOnDemandDateStr);

        mOnDemandRelatedVw.setView(kmProgramContentBean.getRows(), this);
        mOnDemandMovicePlayTv.requestFocus();
    }


    private void setListener() {
        mOnDemandMovicePlayTv.setOnClickListener(this);
        mOnDemandMoviceMoreTv.setOnClickListener(this);
    }

    private void getRelatedJsonData() {
        String json = Constants.MOVICE_RELATED_DETAILS_JSON;
        kmProgramContentBean = JsonParams.getKmProgramContentBean(json);
        if (kmProgramContentBean == null) return;
        odMoviceHandler.sendEmptyMessage(Constants.SUCCESS);
    }

    private void getNetData() {
        if (typeIds.size() > 0) {
            RequestParams params = new RequestParams();
            params.addBodyParameter(Constants.PAGE, String.valueOf(pageIndex));
            params.addBodyParameter(Constants.ROWS, pageRows);
            params.addBodyParameter(Constants.CLASSCODE, classCode);
            params.addBodyParameter(Constants.TYPEID, relatedTypeId);
            new HttpHelper(this).postData(Urls.getInstance().KM_PROGRAME_CONTENT_URL, params, new RequestCallBack() {
                @Override
                public void onSuccess(ResponseInfo responseInfo) {
                    if (responseInfo.statusCode == 200) {
                        kmProgramContentBean = JsonParams.getKmProgramContentBean(responseInfo.result.toString());
                        if (kmProgramContentBean == null) {
                            return;
                        }
                        odMoviceHandler.sendEmptyMessage(Constants.SUCCESS);
                    }
                }

                @Override
                public void onFailure(HttpException e, String s) {
                    odMoviceHandler.sendEmptyMessage(Constants.FAIL);
                }
            });
        }
    }

    private void getUrlJsonData(final int type) {
        String json = "";
        switch (type) {
            case Constants.MOVICE_DETAILS_RELATED_TYPE:
                json = Constants.MOVICE_RELATED_URLS_JSON;
                break;
            case Constants.MOVICE_DETAILS_TYPE:
                json = Constants.MOVICE_URL_DETAILS_JSON;
                break;
        }
        mOnDemandMoviceBean = JsonParams.getOnDemandDetail(json);
        if (mOnDemandMoviceBean == null) return;
        odMoviceHandler.sendEmptyMessage(Constants.N_SUCCESS);
    }

    private void getNetPlayUrl(final int type) {
        RequestParams params = new RequestParams();
        params.addBodyParameter(Constants.CONTENT_ID_KEY, ids);
        new HttpHelper(this).postData(Urls.getInstance().KM_PROGRAME_DETAILS_URL, params, new HttpCallBack() {
            @Override
            public void onSuccess(ResponseInfo responseInfo) {
                super.onSuccess(responseInfo);
                if (responseInfo.statusCode == 200) {
                    mOnDemandMoviceBean = JsonParams.getOnDemandDetail(responseInfo.result.toString());
                    if (mOnDemandMoviceBean == null) {
                        return;
                    }
                    switch (type) {
                        case Constants.MOVICE_DETAILS_RELATED_TYPE:
                            odMoviceHandler.sendEmptyMessage(Constants.N_SUCCESS);
                            break;
                        case Constants.MOVICE_DETAILS_TYPE:
                            break;
                    }
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                odMoviceHandler.sendEmptyMessage(Constants.FAIL);
                super.onFailure(e, s);
            }
        });
    }

    /**
     * 播放Urls集合
     */
    private ArrayList<OnDemandVideoBean> playUrls() {
        if (mOnDemandMoviceBean.getRows() != null) {
            ArrayList<OnDemandVideoBean> playVideoList = new ArrayList<>();
            for (int i = 0; i < mOnDemandMoviceBean.getRows().size(); i++) {
                OnDemandVideoBean videoBean = new OnDemandVideoBean();
                videoBean.setPlayUrl(mOnDemandMoviceBean.getRows().get(i).getSrUrl());
                videoBean.setPlayName(mOnDemandMoviceBean.getRows().get(i).getSrName());
                playVideoList.add(videoBean);
            }
            return playVideoList;
        }
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.on_demand_movice_details_play_tv:
                if (mOnDemandMoviceBean != null) {
                    if (mOnDemandMoviceBean.getRows().size() > 0) {
                        int playPos = 0;
                        MPConPlayManager mcpm = MPConPlayUtils.findLastTimePlay(mOnDemandMoviceTitleStr);
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
            case R.id.on_demand_movice_details_more_abstract_tv:
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
        detailsMoreTv.setText(mOnDemandMoviceAbstractTv.getText().toString().trim());
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
        if (mOnDemandMovicePw == null) {
            mOnDemandMovicePw = new PopupWindow(view, UtilsTools.getScreenWidth(getApplicationContext()),
                    UtilsTools.getScreenHeight(getApplicationContext()) / 2);
        }
        mOnDemandMovicePw.setFocusable(true);
        mOnDemandMovicePw.setTouchable(true);
        mOnDemandMovicePw.setBackgroundDrawable(getResources().getDrawable(
                R.mipmap.details_pop));// 点击空白时popupwindow关闭,设置背景图片，不能在布局中设置，要通过代码来设置
        mOnDemandMovicePw.setOutsideTouchable(true);// 触摸popupwindow外部，popupwindow消失。这个要求你的popupwindow要有背景图片才可以成功
        mOnDemandMovicePw.setAnimationStyle(R.style.AnimOnDemandDetails);// 设置窗口显示的动画效果
        mOnDemandMovicePw.showAtLocation(mOnDemandMoviceMoreTv, Gravity.BOTTOM, 0, 0);
        mOnDemandMovicePw.update();
        mOnDemandMovicePw.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case OnDemandMoviceDetalisAct.BACK_DETAILS_CODE:
                break;
        }
    }

    private void skip(int pos) {
        Intent intent = new Intent(OnDemandMoviceDetalisAct.this, OnDemandVideoAct.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.PLAY_TYPE_KEY, MPlayer.PLAY_TYPE_ONDEMAND);
        bundle.putInt(Constants.PLAY_INDEX_KEY, pos);
        bundle.putString(Constants.PLAY_PARENT_NAME_KEY, mOnDemandMoviceTitleStr);
        bundle.putSerializable(Constants.PLAY_URLS_KEY, playUrls().toArray());
        intent.putExtras(bundle);
        OnDemandMoviceDetalisAct.this.startActivityForResult(intent,
                BACK_DETAILS_CODE);
    }
}
