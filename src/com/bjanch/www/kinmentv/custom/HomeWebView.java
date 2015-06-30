package com.bjanch.www.kinmentv.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.bjanch.www.kinmentv.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

@SuppressLint("SetJavaScriptEnabled")
public class HomeWebView extends LinearLayout {

	private View view;
	
	private Context context;

    @ViewInject(R.id.home_wv)
	private WebView home_wv;

    @ViewInject(R.id.home_pb)
	private ProgressBar home_pb;

	public HomeWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		this.context = context;

		view = inflate(context, R.layout.home_webview_layout, this);

        ViewUtils.inject(this, view);

		if (isInEditMode()) {
			return;
		}

		initUI();
	}

	private void initUI() {

        home_wv.setWebViewClient(new WebViewClient() {

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}

		});

        home_wv.requestFocusFromTouch();

//        home_wv.getSettings().setJavaScriptEnabled(true);
        home_wv.getSettings().setSupportZoom(true);
//        home_wv.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        home_wv.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        home_wv.getSettings().setAllowFileAccess(true);
        home_wv.getSettings().setNeedInitialFocus(true);
        home_wv.getSettings().setBuiltInZoomControls(false);
        home_wv.getSettings().setLoadsImagesAutomatically(true);
        home_wv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        home_wv.getSettings().setUseWideViewPort(true);
        home_wv.getSettings().setLoadWithOverviewMode(true);
        home_wv.getSettings().setPluginState(PluginState.ON);

        home_wv.setWebChromeClient(new WebChromeClient() {

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				if(newProgress == 100) {
                    home_pb.setVisibility(View.GONE);
				} else {
					if (home_pb.getVisibility() == GONE)
                        home_pb.setVisibility(VISIBLE);
                    home_pb.setProgress(newProgress);
				}
				super.onProgressChanged(view, newProgress);
			}
			
		});

        home_wv.loadUrl("http://www.chinasafety.gov.cn/newpage/");
	}
	
	public void loadURL(String url) {
        home_wv.loadUrl(url);
	}
	
	public boolean canGoBack() {
		return home_wv.canGoBack();
	}
	
	public void goBack() {
        home_wv.goBack();
	}

}
