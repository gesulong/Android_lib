package com.bjanch.www.kinmentv.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.NinePatch;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import com.bjanch.www.kinmentv.R;

public class LiveProgressView extends View {
	
	private Bitmap bmp_b, bmp_c;
	private boolean isRun = false; 
	public long startTime = 0, overTime = 0, nowTime = 0;
	public float s = 0.0f;

	public LiveProgressView(Context context, AttributeSet attrs) {
		super(context, attrs);
		bmp_b = BitmapFactory.decodeResource(getResources(), R.drawable.live_progress_bg2);
		bmp_c = BitmapFactory.decodeResource(getResources(), R.drawable.live_progress_pre2);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		drawNinepath_b(canvas);
		drawNinepath_c(canvas, s);
		super.onDraw(canvas);
	}
	
	public void drawNinepath_b(Canvas c){

		NinePatch patch = new NinePatch(bmp_b, bmp_b.getNinePatchChunk(), null);
		patch.draw(c, new Rect(0, 0, getWidth(), getHeight()));
	
	}
	
	public void drawNinepath_c(Canvas c, float s){

		NinePatch patch = new NinePatch(bmp_c, bmp_c.getNinePatchChunk(), null);
		patch.draw(c, new Rect(0, 0, (int) (getWidth()*s), getHeight()));
		
	}
	
	public void init() {
		startTime = overTime = nowTime = 0;
		s = 0.0f;

		stopProgress();
	}
	
	public void startProgress(long startTime, long overTime, long nowTime) {
		this.startTime = startTime;
		this.overTime = overTime;
		this.nowTime = nowTime;
		isRun = true;
		new Thread(runnable).start();
	}
	
	public void stopProgress() {
		isRun = false;
	}
	
	Runnable runnable = new Runnable() {
		
		@Override
		public void run() {
			while(isRun) {
				if(nowTime > overTime){
					nowTime = overTime;
					isRun = false;
				}
					
				s = (float) (nowTime - startTime) / (float) (overTime - startTime);
				nowTime += 10;
				handler.sendEmptyMessage(1);
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
	};
	
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			invalidate();
        }
	};

}