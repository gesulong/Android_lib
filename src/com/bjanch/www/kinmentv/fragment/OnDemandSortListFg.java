package com.bjanch.www.kinmentv.fragment;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bjanch.www.kinmentv.R;
import com.bjanch.www.kinmentv.act.OnDemandAct;
import com.bjanch.www.kinmentv.adapter.OnDemandSortListAdt;
import com.bjanch.www.kinmentv.adapter.VdFiltrateAdt;
import com.bjanch.www.kinmentv.bean.FiltrateAreaSortBean;
import com.bjanch.www.kinmentv.bean.FiltrateTypeSortBean;
import com.bjanch.www.kinmentv.bean.KmProgramSortBean;
import com.bjanch.www.kinmentv.bean.ProgramSortBean;
import com.bjanch.www.kinmentv.custom.ArrowListView;
import com.bjanch.www.kinmentv.http.HttpCallBack;
import com.bjanch.www.kinmentv.http.JsonParams;
import com.bjanch.www.kinmentv.http.Urls;
import com.bjanch.www.kinmentv.util.Constants;
import com.bjanch.www.kinmentv.util.HttpHelper;
import com.bjanch.www.kinmentv.util.ToastTools;
import com.bjanch.www.kinmentv.util.UtilsTools;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by wxy on 2015/4/14.
 */
public class OnDemandSortListFg extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener, OnScrollListener {

    private FragmentManager manager;
    @ViewInject(R.id.on_demand_title_tv)
    private TextView odSortTitleTv;
    @ViewInject(R.id.on_demand_sort_lv)
    private ListView odSortLv;
    @ViewInject(R.id.up_arrows)
    private TextView upArrows;
    @ViewInject(R.id.down_arrows)
    private TextView downArrows;

    private OnDemandSortListAdt odSortAdt;
    private String[] vdFiltrateTimes = {"全部", "2015", "2014", "2013", "2012", "2011", "2010", "更早"};
    private String vdFiltrateAll = "全部";
    private String vdFiltrateTimeEarlier = "更早";
    private List<String> odSortNames = new ArrayList<>();
    private List<String> vdFiltrateTypeNames = new ArrayList<>();
    private List<String> vdFiltrateAreaNames = new ArrayList<>();
    private List<String> vdFiltrateTimeNames = new ArrayList<>();
    private PopupWindow odPopWindow;
    private View filtrateView;
    private GridView filtrateTypeGv, filtrateAreaGv, filtrateTimeGv;
    private LinearLayout filtrateCleanLl;
    private String type;
    private KmProgramSortBean kmProgramSortBean;
    private List<ProgramSortBean> programSortBeans;
    private List<FiltrateTypeSortBean> filtrateTypeSortBeans;
    private List<FiltrateAreaSortBean> filtrateAreaSortBeans;
    private OnDemandSortHandler odSortHandler;
    private VdFiltrateAdt filtrateTypeAdt, filtrateAreaAdt, filtrateTimeAdt;
    private String filetrateTypeId = null, filtrateAreaId = null, filtrateTime = null;

    private Rect rTop = new Rect();
    private Rect rBottom = new Rect();
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (odSortLv.getChildCount() > 0) {
            View firstChild = odSortLv.getChildAt(0);
            firstChild.getLocalVisibleRect(rTop);
            View lastChild = odSortLv.getChildAt(odSortLv.getChildCount() - 1);
            lastChild.getLocalVisibleRect(rBottom);

            if (odSortLv.getFirstVisiblePosition() > 0 || odSortLv
                    .getFirstVisiblePosition() == 0
                    && rTop.top > 0) {
                upArrows.setVisibility(View.VISIBLE);
            } else {
                upArrows.setVisibility(View.INVISIBLE);
            }
            if (odSortLv.getLastVisiblePosition() < odSortLv.getAdapter()
                    .getCount() - 1 || odSortLv.getLastVisiblePosition() == odSortLv
                    .getAdapter().getCount() - 1
                    && rBottom.bottom < lastChild.getHeight()) {
                downArrows.setVisibility(View.VISIBLE);
            } else {
                downArrows.setVisibility(View.INVISIBLE);
            }
        }
    }

    private class OnDemandSortHandler extends Handler {
        WeakReference<Activity> reference;

        OnDemandSortHandler(Activity activity) {
            reference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Activity activity = reference.get();
            if (activity != null) {
                if (OnDemandAct.mOnDemandContext != null) {
                    switch (msg.what) {
                        case Constants.SUCCESS:
                            programSortName();
                            filtrateTypeName();
                            filtrateAreaName();
                            filtrateTimeName();
                            setListViewAdapter();
                            defultSelect();
                            break;
                        case Constants.FAIL:
                            ToastTools.frameToast(OnDemandAct.mOnDemandContext, "請求失敗", R.drawable.custom_toast);
                            break;
                    }
                }
            }
        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        odSortHandler = new OnDemandSortHandler(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View sortListView = inflater.inflate(R.layout.on_demand_srot_list_frg, container, false);
        ViewUtils.inject(this, sortListView);
        init(sortListView);
        getIntentData();
        setTitle();
//        getJsonData();
        getNetData();
        setListener();
        return sortListView;
    }

    private void getIntentData() {
        type = Constants.TYPE.findType(Constants.SKIP_ID);
    }

    private void setTitle() {
        odSortTitleTv.setText(Constants.SKIP_ID);
    }

    /**
     * 节目分类列表
     */
    private void programSortName() {
        if (OnDemandAct.mOnDemandContext != null) {
            String[] programFixedLableNames = {OnDemandAct.mOnDemandContext.getResources().getString(R.string.all), OnDemandAct.mOnDemandContext.getResources().getString(R.string.filtrate)};
            programSortBeans = kmProgramSortBean.getClassList();
            if (programSortBeans == null) return;
            Collections.addAll(odSortNames, programFixedLableNames);
            for (ProgramSortBean pSortBean : programSortBeans) {
                odSortNames.add(pSortBean.getTypeName());
            }
        }
    }

    /**
     * 筛选节目类型列表
     */
    private void filtrateTypeName() {
        filtrateTypeSortBeans = kmProgramSortBean.getTypeList();
        if (filtrateTypeSortBeans == null) return;
        vdFiltrateTypeNames.add(vdFiltrateAll);
        for (FiltrateTypeSortBean fTypeSortBean : filtrateTypeSortBeans) {
            vdFiltrateTypeNames.add(fTypeSortBean.getTypeName());
        }
    }

    /**
     * 筛选节目区域列表
     */
    private void filtrateAreaName() {
        filtrateAreaSortBeans = kmProgramSortBean.getProList();
        if (filtrateAreaSortBeans == null) return;
        vdFiltrateAreaNames.add(vdFiltrateAll);
        for (FiltrateAreaSortBean fAreaSortBean : filtrateAreaSortBeans) {
            vdFiltrateAreaNames.add(fAreaSortBean.getProName());
        }
    }

    /**
     * 筛选节目时间列表
     */
    private void filtrateTimeName() {
        Collections.addAll(vdFiltrateTimeNames, vdFiltrateTimes);
    }

    /**
     * 填充节目列表适配器
     */
    private void setListViewAdapter() {
        if (OnDemandAct.mOnDemandContext != null) {
            odSortAdt = new OnDemandSortListAdt(OnDemandAct.mOnDemandContext, odSortNames);
            odSortLv.setAdapter(odSortAdt);
        }
    }

    /**
     * 填充筛选类型，区域，时间适配器
     */
    private void setGridViewAdapter() {
        if (OnDemandAct.mOnDemandContext != null) {
            filtrateTypeAdt = new VdFiltrateAdt(OnDemandAct.mOnDemandContext, vdFiltrateTypeNames, Constants.FILTRATE_TYPE);
            filtrateAreaAdt = new VdFiltrateAdt(OnDemandAct.mOnDemandContext, vdFiltrateAreaNames, Constants.FILTRATE_AREA);
            filtrateTimeAdt = new VdFiltrateAdt(OnDemandAct.mOnDemandContext, vdFiltrateTimeNames, Constants.FILTRATE_TIME);
            filtrateTypeGv.setAdapter(filtrateTypeAdt);
            filtrateAreaGv.setAdapter(filtrateAreaAdt);
            filtrateTimeGv.setAdapter(filtrateTimeAdt);
        }
    }

    private void init(View view) {
        if (OnDemandAct.mOnDemandContext != null) {
            manager = OnDemandAct.mOnDemandContext.getSupportFragmentManager();
        }
        filtrateView = LayoutInflater.from(getActivity()).inflate(R.layout.vd_filtrate_pop, null);
        filtrateTypeGv = (GridView) filtrateView.findViewById(R.id.vd_filtrate_type_gv);
        filtrateAreaGv = (GridView) filtrateView.findViewById(R.id.vd_filtrate_area_gv);
        filtrateTimeGv = (GridView) filtrateView.findViewById(R.id.vd_filtrate_time_gv);
        filtrateCleanLl = (LinearLayout) filtrateView.findViewById(R.id.vd_filtrate_clean_ll);
        filtrateTypeGv.setChoiceMode(1);
        filtrateAreaGv.setChoiceMode(1);
        filtrateTimeGv.setChoiceMode(1);
    }

    private void setListener() {
        odSortLv.setOnItemClickListener(this);
        odSortLv.setOnScrollListener(this);
        filtrateTypeGv.setOnItemClickListener(this);
        filtrateAreaGv.setOnItemClickListener(this);
        filtrateTimeGv.setOnItemClickListener(this);
        filtrateCleanLl.setOnClickListener(this);
    }

    /**
     * 去掉字符串空格
     *
     * @param text
     * @return
     */
    private String splitblankStr(String text) {
        String newStr;
        newStr = text.replaceAll("　", "");
        return newStr;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView.equals(odSortLv)) {
            updateSortNameColor(i, Constants.PROGRAME_SORT);
            TextView sortNameTv = (TextView) view.findViewWithTag(Constants.SORT_NAME_KEY + odSortNames.get(i));
            //判断除了点击筛选外的ListView的item清空筛选内容
            if (!splitblankStr(sortNameTv.getText().toString()).equals(splitblankStr(getActivity().getResources().getString(R.string.filtrate)))) {
                Constants.JUDGE_SHOW_POP = "";
            }
            //全部
            if (splitblankStr(sortNameTv.getText().toString()).equals(splitblankStr(getActivity().getResources().getString(R.string.all)))) {
                this.filetrateTypeId = null;
                this.filtrateAreaId = null;
                this.filtrateTime = null;
                addContentFrg(type, null, null, null);
                return;
            }
            //筛选
            if (splitblankStr(sortNameTv.getText().toString()).equals(splitblankStr(getActivity().getResources().getString(R.string.filtrate)))) {
                //弹筛选框
                showPop(filtrateView, filtrateView);
                if (Constants.JUDGE_SHOW_POP.equals("")) {
                    setGridViewAdapter();
                    Constants.JUDGE_SHOW_POP = "JUDGE_SHOW_POP";
                }
                return;
            }
            //其他
//            if (splitblankStr(sortNameTv.getText().toString()).equals(splitblankStr(getActivity().getResources().getString(R.string.other)))) {
//                this.filetrateTypeId = null;
//                this.filtrateAreaId = null;
//                this.filtrateTime = null;
//                addContentFrg(type, filetrateTypeId, filtrateAreaId, filtrateTime);
//                return;
//            }
            ProgramSortBean programSortBean = programSortBeans.get(i - 2);
            if (programSortBean == null) return;
            filetrateTypeId = programSortBean.getId();
            addContentFrg(type, filetrateTypeId, filtrateAreaId, filtrateTime);
        }
        //类型GridView
        if (adapterView.equals(filtrateTypeGv)) {
            updateSortNameColor(i, Constants.FILTRATE_TYPE);
            ToastTools.frameToast(getActivity(), vdFiltrateTypeNames.get(i), R.drawable.custom_toast);
            if (splitblankStr(vdFiltrateTypeNames.get(i)).equals(vdFiltrateAll)) {//全部
                this.filetrateTypeId = null;
                addContentFrg(type, null, filtrateAreaId, filtrateTime);
                return;
            } else {
                FiltrateTypeSortBean filtrateTypeSortBean = filtrateTypeSortBeans.get(i - 1);
                if (filtrateTypeSortBean == null) return;
                filetrateTypeId = filtrateTypeSortBean.getId();
                addContentFrg(type, filetrateTypeId, filtrateAreaId, filtrateTime);
            }
        }
        //地区GridView
        if (adapterView.equals(filtrateAreaGv)) {
            updateSortNameColor(i, Constants.FILTRATE_AREA);
            ToastTools.frameToast(getActivity(), vdFiltrateAreaNames.get(i), R.drawable.custom_toast);
            if (vdFiltrateAreaNames.get(i).equals(vdFiltrateAll)) {//全部
                this.filtrateAreaId = null;
                addContentFrg(type, filetrateTypeId, null, filtrateTime);
                return;
            } else {
                FiltrateAreaSortBean filtrateAreaSortBean = filtrateAreaSortBeans.get(i - 1);
                if (filtrateAreaSortBean == null) return;
                filtrateAreaId = filtrateAreaSortBean.getId();
                addContentFrg(type, filetrateTypeId, filtrateAreaId, filtrateTime);
            }
        }
        //时间GridView
        if (adapterView.equals(filtrateTimeGv)) {
            updateSortNameColor(i, Constants.FILTRATE_TIME);
            ToastTools.frameToast(getActivity(), vdFiltrateTimeNames.get(i), R.drawable.custom_toast);
            //全部
            if (vdFiltrateTimeNames.get(i).equals(vdFiltrateAll)) {
                this.filtrateTime = null;
                addContentFrg(type, filetrateTypeId, filtrateAreaId, null);
                return;
            }
            //更早
            if (vdFiltrateTimeNames.get(i).equals(vdFiltrateTimeEarlier)) {
                this.filtrateTime = "2009";
                addContentFrg(type, filetrateTypeId, filtrateAreaId, filtrateTime);
                return;
            }
            filtrateTime = vdFiltrateTimeNames.get(i).trim();
            addContentFrg(type, filetrateTypeId, filtrateAreaId, filtrateTime);
        }
    }

    /**
     * 添加右侧显示内容Frg
     *
     * @param classCode
     * @param typeId
     * @param areaId
     * @param dataId
     */
    private void addContentFrg(String classCode, String typeId, String areaId, String dataId) {
        OnDemandSlContentFg odContentFg = new OnDemandSlContentFg();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.SORT_CLASS_KEY, classCode);
        bundle.putString(Constants.SORT_FILTRATE_TYPE_KEY, typeId);
        bundle.putString(Constants.SORT_FILTRATE_AREA_KEY, areaId);
        bundle.putString(Constants.SORT_FILTRATE_TIME_KEY, dataId);
        odContentFg.setArguments(bundle);
        manager.beginTransaction().replace(R.id.on_demand_sort_list_content_fl, odContentFg).commit();
    }

    //默認選中
    private void defultSelect() {
        if (odSortAdt.states == null && odSortAdt.states.size() == 0) return;
        odSortAdt.states.set(0, true);
        addContentFrg(type, filetrateTypeId, filtrateAreaId, filtrateTime);
    }

    /**
     * 选择分类颜色变化
     *
     * @param i
     */
    private void updateSortNameColor(int i, int type) {
        switch (type) {
            case Constants.PROGRAME_SORT:
                updateClickNameColor(odSortAdt.states, i);
                odSortAdt.notifyDataSetChanged();
                break;
            case Constants.FILTRATE_TYPE:
                updateClickNameColor(filtrateTypeAdt.typeStates, i);
                filtrateTypeAdt.notifyDataSetChanged();
                break;
            case Constants.FILTRATE_AREA:
                updateClickNameColor(filtrateAreaAdt.areaStates, i);
                filtrateAreaAdt.notifyDataSetChanged();
                break;
            case Constants.FILTRATE_TIME:
                updateClickNameColor(filtrateTimeAdt.timeStates, i);
                filtrateTimeAdt.notifyDataSetChanged();
                break;
        }
    }

    /**
     * 更新点击位置名称颜色变化
     *
     * @param states
     * @param i
     */
    private void updateClickNameColor(List<Boolean> states, int i) {
        // 点击位置更新UI
        for (int position = 0; position < states.size(); position++) {
            if (position == i) {
                states.set(position, true);
            } else {
                states.set(position, false);
            }
        }
    }

    //清空筛选
    private void cleanFiltrate() {
        this.filetrateTypeId = null;
        this.filtrateAreaId = null;
        this.filtrateTime = null;
        filtrateTypeAdt.clean(Constants.FILTRATE_TYPE);
        filtrateAreaAdt.clean(Constants.FILTRATE_AREA);
        filtrateTimeAdt.clean(Constants.FILTRATE_TIME);
        addContentFrg(type, filetrateTypeId, filtrateAreaId, filtrateTime);
    }

    /**
     * PopupWindow 弹出筛选
     *
     * @param view
     */
    public void showPop(View showView, View view) {
        if (odPopWindow == null) {
            odPopWindow = new PopupWindow(
                    view,
                    UtilsTools.getScreenWidth(getActivity()),
                    UtilsTools.getScreenHeight(getActivity()) / 2);
            odPopWindow.setFocusable(true);
            odPopWindow.setTouchable(true);
            odPopWindow.setBackgroundDrawable(getResources().getDrawable(
                    R.mipmap.filtrate_pop));// 点击空白时popupwindow关闭,设置背景图片，不能在布局中设置，要通过代码来设置
            odPopWindow.setOutsideTouchable(true);// 触摸popupwindow外部，popupwindow消失。这个要求你的popupwindow要有背景图片才可以成功
            odPopWindow.setAnimationStyle(R.style.AnimOnDemandDetails);// 设置窗口显示的动画效果
        }
        odPopWindow.update();
        odPopWindow.showAtLocation(showView, Gravity.BOTTOM, 0, 0);
        odPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
            }
        });
    }

    private void getJsonData() {
        String json = "";
        int type = Integer.valueOf(Constants.TYPE.findType(Constants.SKIP_ID));
        switch (type) {
            case 1:
                json = Constants.TV_TRAMA_SORT_JSON;
                break;
            case 2:
                json = Constants.MOVICE_SORT_JSON;
                break;
            case 3:
                json = Constants.ANIME_SORT_JSON;
                break;
            case 4:
                json = Constants.VARIETY_SORT_JSON;
                break;
        }
        kmProgramSortBean = JsonParams.getKmProgramSortBean(json);
        if (kmProgramSortBean == null) return;
        odSortHandler.sendEmptyMessage(Constants.SUCCESS);
    }

    private void getNetData() {
        RequestParams params = new RequestParams();
        params.addBodyParameter(Constants.type, type);
        new HttpHelper(getActivity()).postData(Urls.getInstance().KM_PROGRAME_SORT_URL, params, new HttpCallBack() {
            @Override
            public void onSuccess(ResponseInfo responseInfo) {
                super.onSuccess(responseInfo);
                if (responseInfo.statusCode == 200) {
                    kmProgramSortBean = JsonParams.getKmProgramSortBean(responseInfo.result.toString());
                    if (kmProgramSortBean == null) return;
                    odSortHandler.sendEmptyMessage(Constants.SUCCESS);
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                super.onFailure(e, s);
                odSortHandler.sendEmptyMessage(Constants.FAIL);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.vd_filtrate_clean_ll:
                cleanFiltrate();
                break;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Constants.JUDGE_SHOW_POP = "";
    }
}
