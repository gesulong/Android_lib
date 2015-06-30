package com.bjanch.www.kinmentv.act;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.TextView;

import com.bjanch.www.kinmentv.R;
import com.bjanch.www.kinmentv.adapter.LiveAdapter;
import com.bjanch.www.kinmentv.base.BaseFrgActivity;
import com.bjanch.www.kinmentv.bean.LiveList;
import com.bjanch.www.kinmentv.custom.LiveBottomMenu2;
import com.bjanch.www.kinmentv.custom.LiveLeftMenu2;
import com.bjanch.www.kinmentv.custom.MPlayer;
import com.bjanch.www.kinmentv.dialog.LiveEnCodeDialog;
import com.bjanch.www.kinmentv.http.HttpCallBack;
import com.bjanch.www.kinmentv.http.JsonParams;
import com.bjanch.www.kinmentv.http.Urls;
import com.bjanch.www.kinmentv.util.Constants;
import com.bjanch.www.kinmentv.util.HttpHelper;
import com.bjanch.www.kinmentv.util.SharePrefUtils;
import com.bjanch.www.kinmentv.util.ToastTools;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

@ContentView(R.layout.live_act_layout2)

public class LiveActivity extends BaseFrgActivity implements
        OnItemClickListener, OnItemSelectedListener {

    private Context mContext;

    @ViewInject(R.id.live_left_menu)
    private LiveLeftMenu2 live_left_menu;

    @ViewInject(value = R.id.live_lv, parentId = R.id.live_left_menu)
    private ListView live_lv;

    @ViewInject(R.id.live_bottom_menu)
    private LiveBottomMenu2 live_btm_menu;

    @ViewInject(value = R.id.live_show_channel_btm_tv, parentId = R.id.live_bottom_menu)
    private TextView live_channel_tv;

    @ViewInject(R.id.live_title_tv)
    private TextView live_title_tv;

    private LiveAdapter live_adp;

    @ViewInject(R.id.live_player)
    private MPlayer mPlayer;

    private Timer timer = null;

    private List<String> nameList;
    private List<String> urlList;
    private List<Integer> idList;

    private int type = 0; // 上次频道类型

    private int pos = 0; // 上次频道位置

    private boolean isEncoding = false; //是否认证

    private HttpHelper httpHelper;

    private LiveList liveList0, liveList2, liveList1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewUtils.inject(this);

        mContext = this;
        initControl();
        initData();

        beforeToPlay();

    }

    private void initControl() {

        live_adp = new LiveAdapter(this);
        live_lv.setAdapter(live_adp);
        live_lv.setOnItemClickListener(this);
        live_lv.setOnItemSelectedListener(this);
    }

    public void initData() {

        httpHelper = new HttpHelper(this);

        nameList = new ArrayList<>();
        urlList = new ArrayList<>();
        idList = new ArrayList<>();

        mPlayer.setPlayType(MPlayer.PLAY_TYPE_LIVE);
        mPlayer.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {

                live_left_menu.changeMenuShowingState();
                live_btm_menu.changeMenuShowingState();
                return false;
            }
        });


        live_btm_menu.startPogress(0, 600000, 10000);

    }


    //获得频道信息后进行默认播放
    public void beforeToPlay() {

        pos = SharePrefUtils.getInt(Constants.LIVE_POSITION, 0);
        type = SharePrefUtils.getInt(Constants.LIVE_TYPE, 0);

        live_adp.changeLivingState(type, pos);

        if (type == 0) {
            showData0();
        } else if (type == 1) {
            showData1();
        } else {
            showData2();
        }
    }

    public void getLiveData0() {

        if (liveList0 != null) {
            nameList.clear();
            urlList.clear();
            idList.clear();
            if (liveList0.getData().size() <= 0) {
                ToastTools.frameToast(mContext, mContext.getString(R.string.live_left_title_0_), R.drawable.custom_toast);
            } else {
                for (int i = 0; i < liveList0.getData().size(); i++) {
                    nameList.add(liveList0.getData().get(i).getMcName());
                    urlList.add(liveList0.getData().get(i).getMcUrl());
                    idList.add(liveList0.getData().get(i).getId());
                }

                mPlayer.setUrlList(urlList);
                live_adp.putProNameArray(nameList, 0);
                live_lv.requestFocus();
            }

            return;
        }

        RequestParams requestParams = new RequestParams();

        requestParams.addQueryStringParameter("type", "2");

        httpHelper.getData(Urls.getInstance().LIVE_DATA,requestParams, new HttpCallBack() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                super.onSuccess(responseInfo);
                liveList0 = JsonParams.getLiveList(responseInfo.result + "");
                if (liveList0.getData().size() <= 0) {
                    ToastTools.frameToast(mContext, mContext.getString(R.string.live_left_title_0_), R.drawable.custom_toast);
                    return;
                }

                nameList.clear();
                urlList.clear();
                idList.clear();
                for (int i = 0; i < liveList0.getData().size(); i++) {
                    nameList.add(liveList0.getData().get(i).getMcName());
                    urlList.add(liveList0.getData().get(i).getMcUrl());
                    idList.add(liveList0.getData().get(i).getId());
                }

                mPlayer.setUrlList(urlList);
                live_adp.putProNameArray(nameList, 0);
                live_lv.requestFocus();

                if (!mPlayer.Playing()) {
                    changePro(pos);
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                super.onFailure(e, s);
            }
        });

    }

    public void getLiveData1() {

        if (liveList1 != null) {
            nameList.clear();
            urlList.clear();
            idList.clear();

            if (liveList1.getData().size() <= 0) {
                ToastTools.frameToast(mContext, mContext.getString(R.string.live_left_title_1_), R.drawable.custom_toast);
            } else {
                for (int i = 0; i < liveList1.getData().size(); i++) {
                    nameList.add(liveList1.getData().get(i).getMcName());
                    urlList.add(liveList1.getData().get(i).getMcUrl());
                    idList.add(liveList1.getData().get(i).getId());
                }

                mPlayer.setUrlList(urlList);
                live_adp.putProNameArray(nameList, 1);
                live_lv.requestFocus();
            }

            return;
        }
        RequestParams requestParams = new  RequestParams();

        requestParams.addQueryStringParameter("type", "3");

        httpHelper.getData(Urls.getInstance().LIVE_DATA,requestParams, new HttpCallBack() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                super.onSuccess(responseInfo);
                liveList1 = JsonParams.getLiveList(responseInfo.result + "");
                if (liveList1.getData().size() <= 0) {
                    ToastTools.frameToast(mContext, mContext.getString(R.string.live_left_title_1_), R.drawable.custom_toast);
                    return;
                }
                nameList.clear();
                urlList.clear();
                idList.clear();
                for (int i = 0; i < liveList1.getData().size(); i++) {
                    nameList.add(liveList1.getData().get(i).getMcName());
                    urlList.add(liveList1.getData().get(i).getMcUrl());
                    idList.add(liveList1.getData().get(i).getId());
                }

                mPlayer.setUrlList(urlList);
                live_adp.putProNameArray(nameList, 1);
                live_lv.requestFocus();

                if (!mPlayer.Playing()) {
                    changePro(pos);
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                super.onFailure(e, s);
            }
        });
    }

    public void getLiveData2() {

        if (liveList2 != null) {
            nameList.clear();
            urlList.clear();
            idList.clear();

            if (liveList2.getData().size() <= 0) {
                ToastTools.frameToast(mContext, mContext.getString(R.string.live_left_title_2_), R.drawable.custom_toast);
            } else {
                for (int i = 0; i < liveList2.getData().size(); i++) {
                    nameList.add(liveList2.getData().get(i).getMcName());
                    urlList.add(liveList2.getData().get(i).getMcUrl());
                    idList.add(liveList2.getData().get(i).getId());
                }

                mPlayer.setUrlList(urlList);
                live_adp.putProNameArray(nameList, 2);
                live_lv.requestFocus();
            }

            return;
        }
        RequestParams requestParams = new RequestParams();

        requestParams.addQueryStringParameter("type", "1");

        httpHelper.getData(Urls.getInstance().LIVE_DATA,requestParams, new HttpCallBack() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                super.onSuccess(responseInfo);
                liveList2 = JsonParams.getLiveList(responseInfo.result + "");

                if (liveList2.getData().size() <= 0) {
                    ToastTools.frameToast(mContext, mContext.getString(R.string.live_left_title_2_), R.drawable.custom_toast);
                    return;
                }

                nameList.clear();
                urlList.clear();
                idList.clear();
                for (int i = 0; i < liveList2.getData().size(); i++) {
                    nameList.add(liveList2.getData().get(i).getMcName());
                    urlList.add(liveList2.getData().get(i).getMcUrl());
                    idList.add(liveList2.getData().get(i).getId());
                }

                mPlayer.setUrlList(urlList);
                live_adp.putProNameArray(nameList, 2);
                live_lv.requestFocus();

                if (!mPlayer.Playing()) {
                    changePro(pos);
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                super.onFailure(e, s);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (!live_left_menu.getMenuShowingState()
                    && !live_btm_menu.getMenuShowingState()) {
                finish();
            }
            if (live_left_menu.getMenuShowingState()) {
                live_left_menu.hiddenMenu();
            }
            if (live_btm_menu.getMenuShowingState()) {
                live_btm_menu.hiddenMenu();
            }
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {

            if (!live_left_menu.getMenuShowingState()) {
                live_left_menu.popMenu();
                live_lv.requestFocus();
            }
            if (live_btm_menu.getMenuShowingState()) {
                live_btm_menu.changeHiddenDelay();
            } else {
                live_btm_menu.popMenu();
            }
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
            live_btm_menu.popOrDelay();
            if (!live_left_menu.getMenuShowingState()) {
                if (nameList.size() > 0) {
                    pos++;
                    if (pos < nameList.size()) {
                        changePro(pos);
                    } else if (pos >= nameList.size()) {
                        pos = 0;
                        changePro(pos);
                    }
                    live_adp.changeLivingState(0, pos);
                }
            }

        } else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
            live_btm_menu.popOrDelay();
            if (!live_left_menu.getMenuShowingState()) {
                if (nameList.size() > 0) {
                    pos--;
                    if (pos >= 0) {
                        changePro(pos);
                    } else if (pos <= -1) {
                        pos = nameList.size() - 1;
                        changePro(pos);
                    }
                    live_adp.changeLivingState(0, pos);
                }
            }

        } else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            if (live_left_menu.getMenuShowingState()) {
                live_left_menu.changeHiddenDelay();
                live_btm_menu.popOrDelay();
                type++;
                if (type > 2) {
                    type = 0;
                }
                if (type == 0) {
                    showData0();
                } else if (type == 1) {
                    showData1();
                } else {
                    showData2();
                }
            }
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            if (live_left_menu.getMenuShowingState()) {
                live_left_menu.changeHiddenDelay();
                live_btm_menu.popOrDelay();
                type--;
                if (type < 0) {
                    type = 2;
                }
                if (type == 0) {
                    showData0();
                } else if (type == 1) {
                    showData1();
                } else {
                    showData2();
                }
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                               long arg3) {
        live_left_menu.changeHiddenDelay();
        live_btm_menu.popOrDelay();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View view, int position,
                            long arg3) {

        live_left_menu.changeHiddenDelay();
        live_btm_menu.popOrDelay();

        live_adp.changeLivingState(0, position);

        changePro(position);

    }

    @OnClick(value = {R.id.live_ico_left, R.id.live_ico_right}, parentId = R.id.live_left_menu)
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.live_ico_left:
                live_left_menu.changeHiddenDelay();
                live_btm_menu.popOrDelay();
                type--;
                if (type < 0) {
                    type = 2;
                }
                if (type == 0) {
                    showData0();
                } else if (type == 1) {
                    showData0();
                } else {
                    showData2();
                }
                break;
            case R.id.live_ico_right:
                live_left_menu.changeHiddenDelay();
                live_btm_menu.popOrDelay();
                type++;
                if (type > 2) {
                    type = 0;
                }
                if (type == 0) {
                    showData0();
                } else if (type == 1) {
                    showData1();
                } else {
                    showData2();
                }
                break;
        }

    }

    public void showData0() {
        live_title_tv.setText(mContext.getString(R.string.live_left_title_0));
        type = 0;
        getLiveData0();
    }

    public void showData1() {
        live_title_tv.setText(mContext.getString(R.string.live_left_title_1));
        type = 1;
        getLiveData1();
    }

    public void showData2() {
        live_title_tv.setText(mContext.getString(R.string.live_left_title_2));
        if (!isEncoding) {
            nameList.clear();
            urlList.clear();
            mPlayer.setUrlList(urlList);
            live_adp.putProNameArray(nameList, 2);
            new LiveEnCodeDialog(this, R.style.LiveEnCodeDialog, new LiveEnCodeDialog.LiveEnCodeDialogCallback() {

                @Override
                public void encodingOK() {
                    isEncoding = true;
                    live_left_menu.popOrDelay();
                    live_btm_menu.popOrDelay();
                    getLiveData2();
                }

            }).show();
        } else {
            getLiveData2();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPlayer != null) {
            mPlayer.releasePlayer();
        }
    }


    // 更换频道
    public void changePro(int position) {
        pos = position;
        if (pos >= urlList.size() || pos < 0) {
            pos = 0;
        }

        mPlayer.changeChannel(pos);
        live_channel_tv.setText(nameList.get(pos));

        SharePrefUtils.saveInt(Constants.LIVE_POSITION, pos);
        SharePrefUtils.saveInt(Constants.LIVE_TYPE, type);
        SharePrefUtils.saveInt(Constants.LIVE_ID, idList.get(pos));
        SharePrefUtils.saveInt(Constants.LIVE_ISB, type);

        live_adp.changeLivingState(type, pos);

        //改变进度条的显示
        live_btm_menu.startPogress(0, 600000, 10000);

    }

}
