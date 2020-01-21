package com.android;

import java.net.URLDecoder;

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
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.os.Build;

public class MainActivity extends Activity
							implements OnClickListener {

	private Button mBtnLoadHtml,
				   mBtnShowImage,
				   mBtnBuildHtml;
	private WebView mWebView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mBtnLoadHtml = (Button)findViewById(R.id.btnLoadHtml);
		mBtnShowImage = (Button)findViewById(R.id.btnShowImage);
		mBtnBuildHtml = (Button)findViewById(R.id.btnBuildHtml);
		mWebView = (WebView)findViewById(R.id.webView);

		WebSettings webSettings = mWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);

		mWebView.addJavascriptInterface(new JavaScriptCallFunc(this), "Android");

		mBtnLoadHtml.setOnClickListener(this);
		mBtnShowImage.setOnClickListener(this);
		mBtnBuildHtml.setOnClickListener(this);
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
		case R.id.btnLoadHtml:
			mWebView.loadUrl("file:///android_asset/my_web_page.html");
			break;
		case R.id.btnShowImage:
			mWebView.loadUrl("javascript:showImage()");
			break;
		case R.id.btnBuildHtml:
			String sHtml = null;
			try {
				sHtml = URLDecoder.decode(
					"<html>" + 
					"<META http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">" + 
					"<body>這是由程式碼建立的網頁。</body>" +
					"</html>", "utf-8");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// 在有些版本的Android平台，loadData()無法正常顯示中文，
			// 如果遇到這種情況，可以換成使用loadDataWithBaseURL()。
//			mWebView.loadData(sHtml, "text/html", "utf-8");
			mWebView.loadDataWithBaseURL(null, sHtml, "text/html", "utf-8", null);

			break;
		}
	}

}
