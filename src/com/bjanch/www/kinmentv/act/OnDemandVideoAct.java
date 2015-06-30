package com.bjanch.www.kinmentv.act;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;

import com.bjanch.www.kinmentv.adapter.OnDemandVideoEpisodeListAdt;
import com.bjanch.www.kinmentv.base.BaseFrgActivity;
import com.bjanch.www.kinmentv.bean.OnDemandVideoBean;
import com.bjanch.www.kinmentv.custom.OnDemandVidepEpisodeView;
import com.bjanch.www.kinmentv.custom.MPlayer;
import com.bjanch.www.kinmentv.util.Constants;
import com.bjanch.www.kinmentv.util.SharePrefUtils;
import com.bjanch.www.kinmentv.R;

/**
 * 播放下载完成和点播视频
 *
 * @author wxy
 */
public class OnDemandVideoAct extends BaseFrgActivity implements OnItemClickListener,
        OnItemSelectedListener, OnTouchListener{

    private static final int TIMEOUT_HIDE_LAYOUT = 100;// 超过 10秒隐藏布局

    private MPlayer mPlayer;
    private OnDemandVidepEpisodeView mEpisodeView;
    private ListView mEpisodeListView;
    private OnDemandVideoEpisodeListAdt mAdapter;
    private int currentPos, playType;
    private String mParentName;
    private List<OnDemandVideoBean> mBeans = new ArrayList<>();
    private List<String> mUrls = new ArrayList<>();
    private List<String> mInfo = new ArrayList<>();
    private boolean mClickState = false;

    Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case TIMEOUT_HIDE_LAYOUT:// 超时隐藏侧边栏
                    mEpisodeView.episodeHide();
                    mClickState = true;
                    mHandler.removeMessages(TIMEOUT_HIDE_LAYOUT);
                    break;
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.on_demand_video_act);
        initControl();
        initData();
        setListener();
    }

    private void initControl() {
        mPlayer = (MPlayer) findViewById(R.id.on_demand_video_mp);
        mEpisodeView = (OnDemandVidepEpisodeView) findViewById(R.id.on_demand_video_episode_ls_vw);
        mEpisodeListView = (ListView) mEpisodeView
                .findViewById(R.id.on_demand_video_list_lv);
        mEpisodeView.requestFocus();
    }

    private void setListener(){
        mEpisodeListView.setOnItemClickListener(this);
        mEpisodeListView.setOnItemSelectedListener(this);
        mPlayer.setOnTouchListener(this);
    }
    /**
     * 点播视频播放集合
     */
    private void initData() {
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            playType = bundle.getInt(Constants.PLAY_TYPE_KEY);
            currentPos = bundle.getInt(Constants.PLAY_INDEX_KEY);
            mParentName = bundle.getString(Constants.PLAY_PARENT_NAME_KEY);
            mPlayer.setPlayType(playType);
            Object[] cobjs = (Object[]) bundle.getSerializable(Constants.PLAY_URLS_KEY);
            for (Object cobj : cobjs) {
                OnDemandVideoBean videoUrls = (OnDemandVideoBean) cobj;
                mUrls.add(videoUrls.getPlayUrl());
                mInfo.add(videoUrls.getPlayName());
                mBeans.add(videoUrls);
            }
        }
        mPlayer.setUrlList(mUrls);
        mPlayer.setInfoList(mInfo);
        if (mParentName != null) {
            mPlayer.setParentName(mParentName);
            mPlayer.changeChannel(currentPos);
        }
        updateAdapter(mBeans);
        if (mEpisodeView.getVisibility() == View.VISIBLE) {
            mHandler.sendEmptyMessageDelayed(TIMEOUT_HIDE_LAYOUT, 13000);
        }
        mPlayer.setPosCallback(new MPlayer.PosCallback() {
            @Override
            public void CallBack(int postion) {
                currentPos = postion;
                mEpisodeListView.setSelection(currentPos);
                notifyData(postion);
                addScrollPos();
            }
        });
    }

    private void addScrollPos(){
        mEpisodeListView.post(new Runnable() {
            @Override
            public void run() {
                mEpisodeListView.smoothScrollToPosition(currentPos);
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP:
                if (!mEpisodeListView.isFocused()){
                    mEpisodeView.episodeShow();// 显示侧边栏
                    mEpisodeView.requestFocus();// 侧边栏拿到焦点
                    updateAdapter(mBeans);
                    mHandler.sendEmptyMessageDelayed(TIMEOUT_HIDE_LAYOUT, 13000);
                }
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if (!mEpisodeListView.isFocused()){
                    mEpisodeView.episodeShow();// 显示侧边栏
                    mEpisodeView.requestFocus();// 侧边栏拿到焦点
                    updateAdapter(mBeans);
                    mHandler.sendEmptyMessageDelayed(TIMEOUT_HIDE_LAYOUT, 13000);
                }
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                mPlayer.mHandler.sendEmptyMessage(MPlayer.SHOW_CONTROL_ANIMATION);
                mPlayer.rewind();
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                mPlayer.mHandler.sendEmptyMessage(MPlayer.SHOW_CONTROL_ANIMATION);
                mPlayer.fastForward();
                break;
            case KeyEvent.KEYCODE_DPAD_CENTER:
                mPlayer.showOrHindImage();
                break;
            case KeyEvent.KEYCODE_BACK:// 返回
                if (mEpisodeView.getVisibility() == View.VISIBLE) {
                    mHandler.removeMessages(TIMEOUT_HIDE_LAYOUT);
                    mEpisodeView.episodeHide();// 隐藏侧边栏
                    mPlayer.requestFocus();// 播放器拿到焦点
                    // mEpisodeView.applyRotation(mEpisodeView, -1, 180, 90);
                    return true;
                } else {
                    setResult(OnDemandTvDetalisAct.BACK_DETAILS_CODE);
                    OnDemandVideoAct.this.finish();
                }
                break;
            default:
                Log.v("wxy", "移除消息");
                mHandler.removeMessages(TIMEOUT_HIDE_LAYOUT);
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayer.releasePlayer();
    }

    /**
     * 更新点播列表适配器
     */
    public void updateAdapter(List<OnDemandVideoBean> beans) {
        mAdapter = new OnDemandVideoEpisodeListAdt(getApplicationContext(), beans);
        mEpisodeListView.setAdapter(mAdapter);
        defaultSelect();
    }

    private void defaultSelect() {
        mAdapter.states.set(currentPos, true);
        mEpisodeListView.setSelection(currentPos);
        addScrollPos();
    }

    private void notifyData(int pos) {
        // 点击位置更新UI
        for (int i = 0; i < mAdapter.states.size(); i++) {
            if (i == pos) {
                mAdapter.states.set(i, true);
            } else {
                mAdapter.states.set(i, false);
            }
        }
        // 更新适配器
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
        this.currentPos = pos;
        notifyData(pos);
        mHandler.removeMessages(TIMEOUT_HIDE_LAYOUT);
        mHandler.sendEmptyMessageDelayed(TIMEOUT_HIDE_LAYOUT, 13000);// 当选择时就开始计时隐藏过程
        if (mUrls.size() > 0) {
            SharePrefUtils.saveInt("VIDEO_POS", pos);
            mPlayer.changeChannel(pos);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos,
                               long id) {
        mHandler.removeMessages(TIMEOUT_HIDE_LAYOUT);
        mHandler.sendEmptyMessageDelayed(TIMEOUT_HIDE_LAYOUT, 13000);// 当选择时就开始计时隐藏过程
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if (mClickState) {
            mEpisodeView.episodeShow();// 显示侧边栏
            mEpisodeView.requestFocus();// 侧边栏拿到焦点
            mHandler.sendEmptyMessageDelayed(TIMEOUT_HIDE_LAYOUT, 13000);
            mClickState = false;
        } else {
            mEpisodeView.episodeHide();
            mHandler.removeMessages(TIMEOUT_HIDE_LAYOUT);
            mClickState = true;
        }
        return false;
    }
}
