package com.bjanch.www.kinmentv.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.bjanch.www.kinmentv.R;
import com.bjanch.www.kinmentv.act.OnDemandAct;
import com.bjanch.www.kinmentv.act.OnDemandTvDetalisAct;
import com.bjanch.www.kinmentv.act.OnDemandMoviceDetalisAct;
import com.bjanch.www.kinmentv.adapter.OnDemandSlContentAdt;
import com.bjanch.www.kinmentv.bean.ActorBean;
import com.bjanch.www.kinmentv.bean.KmProgramContentBean;
import com.bjanch.www.kinmentv.bean.KmProgramContentListBean;
import com.bjanch.www.kinmentv.bean.TypeBean;
import com.bjanch.www.kinmentv.http.HttpCallBack;
import com.bjanch.www.kinmentv.http.JsonParams;
import com.bjanch.www.kinmentv.http.Urls;
import com.bjanch.www.kinmentv.util.Constants;
import com.bjanch.www.kinmentv.util.HttpHelper;
import com.bjanch.www.kinmentv.util.ToastTools;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by wxy on 2015/4/14.
 */
public class OnDemandSlContentFg extends Fragment implements AdapterView.OnItemClickListener, AbsListView.OnScrollListener {
    @ViewInject(R.id.on_demand_content_counts_tv)
    private TextView mCountsTv;
    @ViewInject(R.id.on_demand_content_gv)
    private GridView mListContentGv;

    private OnDemandSlContentAdt mODContentAdapter;
    private OnDemandContentHandler odContentHandler;
    private KmProgramContentBean kmProgramContentBean;
    private List<KmProgramContentListBean> kmProgramContentListBeans = new ArrayList<>();
    private String type, filtrateTypeId, filtrateAreaId, filtrateTime;
    private int pageIndex = 1;
    private String pageRows = "12";
    private ArrayList<ActorBean> actorBeans = new ArrayList<>();
    private ArrayList<TypeBean> typeBeans = new ArrayList<>();

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        int residueItem = totalItemCount - visibleItemCount;
        if (residueItem == 0 || firstVisibleItem < residueItem) {
        } else {
            // 分页加载数据
            pageDown();
        }
    }

    private void pageDown() {
        if (pageIndex >= Integer.valueOf(kmProgramContentBean.getPageSize()))
            return;
        pageIndex = this.pageIndex + 1;
        getNetData();
//        getJsonData();
    }

    private class OnDemandContentHandler extends Handler {
        WeakReference<Activity> mActivityReference;

        OnDemandContentHandler(Activity activity) {
            mActivityReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            Activity activity = mActivityReference.get();
            if (activity != null) {
                switch (msg.what) {
                    case Constants.SUCCESS:
                        kmProgramContentListBeans.addAll(kmProgramContentBean.getRows());
                        setAdapter();
                        setText();
                        break;
                    case Constants.FAIL:
                        ToastTools.frameToast(OnDemandAct.mOnDemandContext, "請求失敗", R.drawable.custom_toast);
                        break;
                }
            }
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        odContentHandler = new OnDemandContentHandler(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View sortContentView = inflater.inflate(R.layout.on_demand_srot_list_content_frg, container, false);
        //注入
        ViewUtils.inject(this, sortContentView);

        mODContentAdapter = new OnDemandSlContentAdt(OnDemandAct.mOnDemandContext);
        mListContentGv.setAdapter(mODContentAdapter);
        getBundle();
//        getJsonData();
        getNetData();
        setListener();
        return sortContentView;
    }

    private void getBundle() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getString(Constants.SORT_CLASS_KEY);
            filtrateTypeId = bundle.getString(Constants.SORT_FILTRATE_TYPE_KEY);
            filtrateAreaId = bundle.getString(Constants.SORT_FILTRATE_AREA_KEY);
            filtrateTime = bundle.getString(Constants.SORT_FILTRATE_TIME_KEY);
        }
    }

    private void setListener() {
        mListContentGv.setOnItemClickListener(this);
        mListContentGv.setOnScrollListener(this);
    }

    private void setAdapter() {
        if (kmProgramContentListBeans != null) {
            if (mODContentAdapter != null) {
                mODContentAdapter.addData(kmProgramContentListBeans);
            }
        }
    }

    private void splitStr(String str, int type) {
        String[] datas = str.split("\\|");
        String[] ids = datas[0].split(",");
        String[] names = datas[1].split(",");
        switch (type) {
            case Constants.PROGRAME_ACTOR:
                actorBeans.clear();
                for (int i = 0; i < ids.length; i++) {
                    ActorBean actorBean = new ActorBean();
                    actorBean.setId(ids[i]);
                    actorBean.setName(names[i]);
                    actorBeans.add(actorBean);
                }
                break;
            case Constants.PROGRAME_TYPE:
                typeBeans.clear();
                for (int i = 0; i < ids.length; i++) {
                    TypeBean typeBean = new TypeBean();
                    typeBean.setId(ids[i]);
                    typeBean.setName(names[i]);
                    typeBeans.add(typeBean);
                }
                break;
        }
    }

    private void setText() {
        if (kmProgramContentBean != null) {
            mCountsTv.setText(kmProgramContentBean.getTotal() + "");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        splitStr(kmProgramContentListBeans.get(i).getAtId(), Constants.PROGRAME_ACTOR);
        splitStr(kmProgramContentListBeans.get(i).getTypeId(), Constants.PROGRAME_TYPE);
        skipDetails(i);//跳到详情页
    }

    private void skipDetails(int pos) {
        if (OnDemandAct.mOnDemandContext != null) {
            if (Constants.TYPE.findType(Constants.SKIP_ID).equals("2")) {
                Intent intent = new Intent(OnDemandAct.mOnDemandContext, OnDemandMoviceDetalisAct.class);
                Bundle bundle = new Bundle();
                bundle.putString(Constants.CONTENT_ID_KEY, kmProgramContentListBeans.get(pos).getId());
                bundle.putString(Constants.CONTENT_DATE_KEY, kmProgramContentListBeans.get(pos).getCrDate());
                bundle.putString(Constants.CONTENT_ABSTRACT_KEY, kmProgramContentListBeans.get(pos).getCrDetail());
                bundle.putString(Constants.CONTENT_NAME_KEY, kmProgramContentListBeans.get(pos).getCrName());
                bundle.putString(Constants.CONTENT_COVER_PIC_KEY, kmProgramContentListBeans.get(pos).getCrPic());
                bundle.putSerializable(Constants.CONTENT_ACTOR_KEY, actorBeans.toArray());
                bundle.putSerializable(Constants.CONTENT_TYPE_KEY, typeBeans.toArray());
                bundle.putString(Constants.CLASSCODE,Constants.TYPE.findType(Constants.SKIP_ID));
                intent.putExtras(bundle);
                OnDemandAct.mOnDemandContext.startActivity(intent);
            } else {
                Intent intent = new Intent(OnDemandAct.mOnDemandContext, OnDemandTvDetalisAct.class);
                Bundle bundle = new Bundle();
                bundle.putString(Constants.CONTENT_ID_KEY, kmProgramContentListBeans.get(pos).getId());
                bundle.putString(Constants.CONTENT_DATE_KEY, kmProgramContentListBeans.get(pos).getCrDate());
                bundle.putString(Constants.CONTENT_ABSTRACT_KEY, kmProgramContentListBeans.get(pos).getCrDetail());
                bundle.putString(Constants.CONTENT_NAME_KEY, kmProgramContentListBeans.get(pos).getCrName());
                bundle.putString(Constants.CONTENT_COVER_PIC_KEY, kmProgramContentListBeans.get(pos).getCrPic());
                bundle.putSerializable(Constants.CONTENT_ACTOR_KEY, actorBeans.toArray());
                bundle.putSerializable(Constants.CONTENT_TYPE_KEY, typeBeans.toArray());
                intent.putExtras(bundle);
                OnDemandAct.mOnDemandContext.startActivity(intent);
            }
        }
    }

    private void getJsonData(){
        String json = "";
        int type = Integer.valueOf(Constants.TYPE.findType(Constants.SKIP_ID));
        switch (type){
            case 1:
                json = Constants.TV_TRAMA_CONTENT_JSON;
                break;
            case 2:
                json = Constants.MOVICE_CONTENT_JSON;
                break;
            case 3:
                if (pageIndex==1){
                    json = Constants.ANIME_CONTENT_PATE1_JSON;
                }else {
                    json = Constants.ANIME_CONTENT_PATE2_JSON;
                }
                break;
            case 4:
                json = Constants.VARIETY_CONTENT_JSON;
                break;
        }
        kmProgramContentBean = JsonParams.getKmProgramContentBean(json);
        if (kmProgramContentBean == null) return;
        odContentHandler.sendEmptyMessage(Constants.SUCCESS);
    }

    /**
     * 获取网络数据
     */
    private void getNetData() {
        RequestParams params = new RequestParams();
        params.addBodyParameter(Constants.PAGE, String.valueOf(pageIndex));
        params.addBodyParameter(Constants.ROWS, pageRows);
        params.addBodyParameter(Constants.CLASSCODE, type);
        if (filtrateTypeId != null)
            params.addBodyParameter(Constants.TYPEID, filtrateTypeId);
        if (filtrateAreaId != null)
            params.addBodyParameter(Constants.PROCODE, filtrateAreaId);
        if (filtrateTime != null)
            params.addBodyParameter(Constants.DATE, filtrateTime);
        if (OnDemandAct.mOnDemandContext != null) {
            new HttpHelper(OnDemandAct.mOnDemandContext).postData(Urls.getInstance().KM_PROGRAME_CONTENT_URL, params, new HttpCallBack() {
                @Override
                public void onSuccess(ResponseInfo responseInfo) {
                    super.onSuccess(responseInfo);
                    if (responseInfo.statusCode == 200) {
                        kmProgramContentBean = JsonParams.getKmProgramContentBean(responseInfo.result.toString());
                        if (kmProgramContentBean == null) return;
                        odContentHandler.sendEmptyMessage(Constants.SUCCESS);
                    }
                }

                @Override
                public void onFailure(HttpException e, String s) {
                    odContentHandler.sendEmptyMessage(Constants.FAIL);
                    super.onFailure(e, s);
                }
            });
        }
    }
}
