package com.android;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.os.Build;

public class MainActivity extends Activity
						implements OnClickListener {

	private Button mBtnOpenUrl,
		   mBtnGoBack,
		   mBtnGoForward,
		   mBtnStop,
		   mBtnReload;
	private EditText mEdtUrl;
	private WebView mWebView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 設定在標題列顯示橫式進度列，或是環狀等待迴圈。
		requestWindowFeature(Window.FEATURE_PROGRESS);
//		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_main);

		// 隱藏環狀等待迴圈。
//		setProgressBarIndeterminateVisibility(false);

		mBtnOpenUrl = (Button)findViewById(R.id.btnOpenUrl);
		mBtnGoBack = (Button)findViewById(R.id.btnGoBack);
		mBtnGoForward = (Button)findViewById(R.id.btnGoForward);
		mBtnStop = (Button)findViewById(R.id.btnStop);
		mBtnReload = (Button)findViewById(R.id.btnReload);
		mEdtUrl = (EditText)findViewById(R.id.edtUrl);
		mWebView = (WebView)findViewById(R.id.webView);

		// 使用自訂的 MyWebViewClient 可以篩選在程式中
		// 瀏覽的網頁，或是啟動外部的瀏覽器。
		mWebView.setWebViewClient(new MyWebViewClient()
						.setupViewComponent(this, 
											mWebView,
											mBtnGoBack, 
											mBtnGoForward, 
											mBtnReload,
											mBtnStop));
		mWebView.setWebChromeClient(new WebChromeClient() {
			   public void onProgressChanged(WebView view, int progress) {
				     // Activity和WebViews的進度值使用不同的表示值，
				     // 所以必須乘上100，當到達100%進度列會自動消失。
				     setProgress(progress * 100);
				   }
				 });

		WebSettings webSettings = mWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setSupportZoom(true);
		webSettings.setBuiltInZoomControls(true);

		mBtnOpenUrl.setOnClickListener(this);
    	mBtnGoBack.setOnClickListener(this);
    	mBtnGoForward.setOnClickListener(this);
    	mBtnStop.setOnClickListener(this);
		mBtnReload.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
    	case R.id.btnOpenUrl:
    		mWebView.loadUrl(mEdtUrl.getText().toString());
    		break;
    	case R.id.btnGoBack:
    		mWebView.goBack();
    		mEdtUrl.setText(mWebView.getUrl());
    		break;
    	case R.id.btnGoForward:
    		mWebView.goForward();
    		mEdtUrl.setText(mWebView.getUrl());
    		break;
    	case R.id.btnReload:
    		mWebView.reload();
    		break;
    	case R.id.btnStop:
    		mWebView.stopLoading();

    		// 隱藏環狀等待迴圈。
//    		setProgressBarIndeterminateVisibility(false);
    		setTitle(R.string.app_name);
    		mBtnReload.setEnabled(true);
    		mBtnStop.setEnabled(false);
    		break;
		}
	}

}
