package com.bjanch.www.kinmentv.custom;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.bjanch.www.kinmentv.R;
import com.bjanch.www.kinmentv.bean.MPConPlayManager;
import com.bjanch.www.kinmentv.util.DateUtils;
import com.bjanch.www.kinmentv.util.DialogUtils;
import com.bjanch.www.kinmentv.util.MPConPlayUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MPlayer extends RelativeLayout implements Callback,
        OnErrorListener, OnPreparedListener, OnBufferingUpdateListener,
        OnSeekCompleteListener, OnCompletionListener, OnClickListener,
        OnSeekBarChangeListener, OnInfoListener {
    private Context mContext;

    @ViewInject(value= R.id.mp_sf,parentId = R.id.mp_parent_rl)
    private SurfaceView mSurface;

    private SurfaceHolder mHolder;

    private MediaPlayer mPlayer;

    @ViewInject(value = R.id.mp_ctrlBar,parentId = R.id.mp_parent_rl)
    private MPControlBar mCtrlBr;// 控制工具栏

    @ViewInject(value = R.id.mp_control_tool_top_tv_cnttl,parentId = R.id.mp_ctrlBar)
    private TextView currentInfo;// 当前播放内容

    @ViewInject(value = R.id.mp_control_tool_top_tv_cnttime,parentId = R.id.mp_ctrlBar)
    private TextView mTime;// 持续时间

    @ViewInject(value = R.id.mp_control_tool_top_tv_lentime,parentId = R.id.mp_ctrlBar)
    private TextView mLength;// 总时间

    @ViewInject(value = R.id.mp_control_tool_bom_sk,parentId = R.id.mp_ctrlBar)
    private SeekBar mSeekBar;// 播放器的播放进度

    @ViewInject(value = R.id.mp_control_tool_bom_ib,parentId = R.id.mp_ctrlBar)
    private ImageButton mPause;// 暂停按钮

    private boolean isPause = false;
    private boolean isPrepare = false;

    @ViewInject(value = R.id.mp_control_tool_center_pause_ib,parentId = R.id.mp_ctrlBar)
    private ImageButton mPauseCenter;// 暂停时屏幕中间的图标

    @ViewInject(value = R.id.mp_buf_lv,parentId = R.id.mp_parent_rl)
    private LinearLayout mProgressBar;// 缓冲进度

    @ViewInject(value =R.id.mp_flow_text,parentId = R.id.mp_buf_lv)
    private MPFlowText mFlowText;

    public Handler mHandler;
    private int progress;
    private String url;// 播放地址

    private int playType;// 播放模式

    private List<String> urlList = new ArrayList<>();

    private List<String> infoList = new ArrayList<>();

    private String parentName = null;

    private String childName = null;

    private int currentPosition = 0;// 当前视频播放的位置

    private Dialog mDialog = null;
    private String mErrorInfo = "未知错误";
    private boolean isError = false;
    private static final int CONTROL_TIMEOUT = 10000;
    private static final int UPDATA_PLAY_TIME = 500;
    private static final int CHANGE_CHANNEL_TIME = 1000;
    public static final int SHOW_CONTROL_ANIMATION = 101;
    public static final int HIDE_CONTROL_ANIMATION = 102;
    public static final int SHOW_OR_HIDE_PAUSE = 103;
    public static final int PLAY_TYPE_LOD = 201;// 本地模式
    public static final int PLAY_TYPE_ONDEMAND = 202;//在线点播
    public static final int PLAY_TYPE_LIVE = 203;// 直播模式
    public static final int PLAY_TYPE_EMERGENCY = 204;//
    public static final int PLAY_TYPE_MEET = 205;//视频会议
    private static final int ERROR_MESSAGE = 301;
    private static final int CHANGE_CHANNEL = 401;// 换台
    private static final int SEEKTO_TIME = 30000;

    private PosCallback posCallback;

    public MPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (isInEditMode())
            return;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.mplayer_layout, this);
        ViewUtils.inject(this, view);
        this.mContext = context;
        initControl();
        initPlayer();
        initSurface();
    }

    private void initPlayer() {
        mPlayer = new MediaPlayer();
        mPlayer.setAudioSessionId(mPlayer.getAudioSessionId());
        mPlayer.setOnErrorListener(this);
        mPlayer.setOnInfoListener(this);
        mPlayer.setOnPreparedListener(this);
        mPlayer.setOnBufferingUpdateListener(this);
        mPlayer.setOnSeekCompleteListener(this);
        mPlayer.setOnCompletionListener(this);
    }

    private void initSurface(){
        mHandler = new MsgHandler(this);
        mHolder = mSurface.getHolder();
        mHolder.setKeepScreenOn(true);
        mHolder.setFormat(PixelFormat.RGBX_8888);
        mHolder.addCallback(this);
    }

    private void initControl(){
        mSeekBar.setOnSeekBarChangeListener(this);
        mSeekBar.setLongClickable(true);
        mPause.setOnClickListener(this);
    }

    private static class MsgHandler extends Handler {
        private WeakReference<MPlayer> mp;
        private int position;
        private int duration;

        MsgHandler(MPlayer player) {
            mp = new WeakReference<>(player);
        }
        @Override
        public void handleMessage(Message msg) {
            final MPlayer mp = this.mp.get();
            if (mp != null) {
                switch (msg.what) {
                    case UPDATA_PLAY_TIME:
                        if (mp.mPlayer.isPlaying()) {
                            position = mp.mPlayer.getCurrentPosition();
                            duration = mp.mPlayer.getDuration();
                            mp.mTime.setText(DateUtils.millisToString(position));
                            mp.mLength.setText(DateUtils.millisToString(duration));

                            if (duration > 0) {
                                long pos = mp.mSeekBar.getMax() * position
                                        / duration;
                                mp.mSeekBar.setProgress((int) pos);
                            }
                        }
                        sendEmptyMessageDelayed(UPDATA_PLAY_TIME, 500);
                        break;
                    case SHOW_CONTROL_ANIMATION:
                        mp.controlAnimationShow();
                        break;
                    case HIDE_CONTROL_ANIMATION:
                        mp.controlAnimationHide();
                        break;
                    case SHOW_OR_HIDE_PAUSE:
                        mp.showOrHindImage();
                        mp.controlAnimationShow();
                        break;
                    case ERROR_MESSAGE:
//                        mp.mErrorInfo = msg.obj.toString();
                        if (mp.mProgressBar != null) {
                            mp.mProgressBar.setVisibility(View.INVISIBLE);
                        }

                        if (mp.mDialog == null) {
                            mp.mDialog = DialogUtils.dialog_twobtn(mp.mContext,
                                    "播放失败!", new DialogUtils.Btn_callback() {
                                        @Override
                                        public void onBtn1Click() {

                                            mp.changeChannel(mp.currentPosition);
                                        }

                                        @Override
                                        public void onBtn2Click() {
                                        }

                                    });
                            mp.mDialog.show();
                        } else {
                            mp.mDialog.show();
                        }

                        //					ToastTools.frameToast(mp.mContext, mp.mErrorInfo,
                        //							R.drawable.custom_toast);
                        break;
                    case CHANGE_CHANNEL:
                        mp.start(mp.currentPosition);
                        break;
                }
            }
        }

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if(isError)
            return;
        if (playType==PLAY_TYPE_ONDEMAND||playType==PLAY_TYPE_LOD) {
            mHandler.removeMessages(UPDATA_PLAY_TIME);
            currentPosition += 1;
            if(posCallback!=null)
                posCallback.CallBack(currentPosition);
            if (currentPosition >= getUrlListSize()) {
                currentPosition = getUrlListSize()-1;
                ((Activity)mContext).finish();
                MPConPlayUtils.deletePlayData(getParentName(), getChildName());
                return;
            }
            MPConPlayUtils.deletePlayData(getParentName(), getChildName());
            changeChannel(currentPosition);
        }
    }

    public interface PosCallback{
        void CallBack(int postion);
    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {
        if (mp != null && mp.isPlaying()) {
            mp.start();
        }

    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        if (percent != 0) {
            mSeekBar.setSecondaryProgress(percent);
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {

        if(isPrepare){
            if(playType==PLAY_TYPE_LOD||playType == PLAY_TYPE_ONDEMAND){
                //TODO 数据库操作 记忆续播
                MPConPlayManager cPm = MPConPlayUtils.findConnectionPlay(currentPosition, parentName, getChildName());
                if(cPm!=null&&cPm.getEpisodePos()==currentPosition&&cPm.getPlayIndex()!=0){
                    mp.seekTo(cPm.getPlayIndex());
                }

                mHandler.sendEmptyMessage(SHOW_CONTROL_ANIMATION);
            }

            mp.start();
            isPrepare = false;

            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
            //判断是否是本地播放或在线点播
            if(playType==PLAY_TYPE_LOD||playType == PLAY_TYPE_ONDEMAND){
                if (mp.isPlaying() && !mSeekBar.isPressed()) {

                    mHandler.sendEmptyMessage(UPDATA_PLAY_TIME);
                }
            }

            mProgressBar.setVisibility(View.INVISIBLE);

            mPlayer.setScreenOnWhilePlaying(true);

        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        isError = true;
        mHandler.sendMessage(Message.obtain(mHandler, ERROR_MESSAGE));
        return false;
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {

        switch (what) {
            // 701
            case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                showFlow();
                mSurface.requestLayout();
                requestLayout();
                break;
            // 702
            case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                if(playType!=PLAY_TYPE_LOD)
                    mProgressBar.setVisibility(View.INVISIBLE);
                mSurface.requestLayout();
                requestLayout();
                break;
        }
        return false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if(playType!=PLAY_TYPE_LOD)
            mFlowText.start();

        if(mPlayer!=null)
            mPlayer.setDisplay(holder);
        if (getUrlListSize() > 0 && mPlayer != null && !mPlayer.isPlaying()) {
            if (isPause) {
                mPlayer.start();
                isPause = false;
            }
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        rePlayPosition();
        pausePlayer();
        if(playType!=PLAY_TYPE_LOD)
            mFlowText.stop();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress,
                                  boolean fromUser) {
        this.progress = progress * mPlayer.getDuration() / seekBar.getMax();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (mPlayer.isPlaying()) {
            mPlayer.seekTo(progress);
        }
    }

    /**
     * 播放
     *
     * @param position
     */
    private void start(int position) {
        this.currentPosition = position;
        isError = false;
        String url = null;
        if (getUrlList() != null && getUrlList().size() > 0) {
            url = getUrlList().get(position);
        }
        if (getInfoList() != null && getInfoList().size() > 0) {
            currentInfo.setText(getInfoList().get(position));
        }
        if (mPlayer != null && url != null) {
            try {
                mPlayer.setDataSource(url);
                mSurface.requestLayout();
                requestLayout();
                mPlayer.prepareAsync();
                isPrepare = true;
                showFlow();

            } catch (IllegalArgumentException | IOException | IllegalStateException | SecurityException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    /**
     * 释放player
     */
    public void releasePlayer()
    {
        if (mHolder != null)
        {
            mHolder = null;
        }

        if (mPlayer != null)
        {
            mHandler.removeMessages(UPDATA_PLAY_TIME);
            mPlayer.reset();
            mPlayer.release();
            mPlayer = null;
        }

    }


    /**
     * 暂停播放
     */
    public void pausePlayer() {

        {
            if ((mPlayer != null) && !isPrepare)
            {
                if (mPlayer.isPlaying())
                {
                    mPlayer.pause();
                    isPause = true;
                } else {
                    isPrepare = false;
                }
            }
        }
    }

    /**
     * 停止播放
     */
    public void stopPlayer() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }


    /**
     * 暂停/播放
     */
    public void showOrHindImage() {
        if (mPlayer.isPlaying()) {
            mPlayer.pause();
            isPause = true;
            controlAnimationShow();
            mPauseCenter.setVisibility(View.VISIBLE);
            mPauseCenter.requestLayout();// 刷新view
            mPause.setBackgroundResource(R.drawable.mp_ctrl_play_sl);
        } else {
            mPlayer.start();
            isPause = false;
            controlAnimationHide();
            mPauseCenter.setVisibility(View.INVISIBLE);
            mPause.setBackgroundResource(R.drawable.mp_ctrl_pause_sl);

        }
    }

    /**
     * 快进
     */
    public void fastForward() {
        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.seekTo(mPlayer.getCurrentPosition() + SEEKTO_TIME);
        }
    }

    /**
     * 快退
     */
    public void rewind() {
        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.seekTo(mPlayer.getCurrentPosition() - SEEKTO_TIME);
        }
    }

    public boolean Playing() {
        return (mPlayer != null) && !isPrepare && mPlayer.isPlaying();

    }

    /**
     * 控制栏显示与隐藏
     */
    public void controlAnimationShow() {
        mCtrlBr.setVisibility(View.VISIBLE);
        mCtrlBr.controlShow();
        mHandler.removeMessages(HIDE_CONTROL_ANIMATION);
        if (!isPause) {
            mHandler.sendEmptyMessageDelayed(HIDE_CONTROL_ANIMATION,
                    CONTROL_TIMEOUT);
        }
    }

    public void controlAnimationHide() {
        mCtrlBr.setVisibility(View.VISIBLE);
        mCtrlBr.controlHide();
    }



    /**
     * 点击列表ITEM切换频道
     *
     * @param position
     *            频道在列表中的位置
     */
    public void changeChannel(int position) {
        this.currentPosition = position;
        mPlayer.reset();
        mHandler.sendEmptyMessageDelayed(CHANGE_CHANNEL, CHANGE_CHANNEL_TIME);
    }

    /**
     * 显示流量进度条
     */
    private void showFlow(){
        if(playType!=PLAY_TYPE_LOD)
            mProgressBar.setVisibility(View.VISIBLE);
    }

    /**
     * 记录退出后播放位置
     */
    private void rePlayPosition(){
        //TODO 待解决
        if (mPlayer != null) {
            if(playType==PLAY_TYPE_LOD||playType == PLAY_TYPE_ONDEMAND){
                MPConPlayUtils.updateLastTime(getParentName(),getChildName(),currentPosition,mPlayer.getCurrentPosition());
            }
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPlayType() {
        return playType;
    }

    public void setPlayType(int playType) {
        this.playType = playType;
    }

    public List<String> getUrlList() {
        return urlList;
    }

    public void setUrlList(List<String> urlList) {
        this.urlList.clear();
        this.urlList .addAll(urlList);
    }

    public int getUrlListSize() {
        if (getUrlList() != null)
            return getUrlList().size();
        return 0;
    }

    public void updataUrList(List<String> urlList){
        this.urlList.clear();
        this.urlList.addAll(urlList);
    }

    public List<String> getInfoList() {
        return infoList;
    }

    public void setInfoList(List<String> infoList) {
        this.infoList.clear();
        this.infoList .addAll(infoList);
    }

    public void updataInfoLIst(List<String> infoList){
        this.infoList.clear();
        this.infoList.addAll(infoList);
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getChildName() {
        return getInfoList().get(currentPosition);
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public PosCallback getPosCallback() {
        return posCallback;
    }

    public void setPosCallback(PosCallback posCallback) {
        this.posCallback = posCallback;
    }
}
