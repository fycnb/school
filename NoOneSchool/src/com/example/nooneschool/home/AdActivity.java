package com.example.nooneschool.home;

import com.example.nooneschool.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class AdActivity extends Activity {

	private ImageView back;
	private ProgressBar loading;
	private WebView webView;
	private String url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ad);

		back = (ImageView) findViewById(R.id.ad_back_iv);
		webView = (WebView) findViewById(R.id.ad_webView);
		loading = (ProgressBar) findViewById(R.id.ad_Loading_bar);

		url = getIntent().getStringExtra("url");
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		loading.setMax(100);
		webView.loadUrl(url);
		webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setJavaScriptEnabled(true);   
        webView.getSettings().setSupportZoom(true); //设置可以支持缩放
        webView.getSettings().setDefaultZoom(ZoomDensity.FAR);
        webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);
		webView.loadUrl(url);

		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				// 设置加载进度条
				view.setWebChromeClient(new WebChromeClientProgress());
				return true;
			}

		});
	}

	private class WebChromeClientProgress extends WebChromeClient {
		@Override
		public void onProgressChanged(WebView view, int progress) {
			if (loading != null) {
				loading.setProgress(progress);
				if (progress == 100)
					loading.setVisibility(View.GONE);
			}
			super.onProgressChanged(view, progress);
		}
	}

}
