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
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.os.Build;

public class MainActivity extends Activity {

	private Button mBtnOpenUrl;
	private EditText mEdtUrl;
	private WebView mWebView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mBtnOpenUrl = (Button)findViewById(R.id.btnOpenUrl);
		mEdtUrl = (EditText)findViewById(R.id.edtUrl);
		mWebView = (WebView)findViewById(R.id.webView);

		// 設定轉址的網頁還是由WebView開啟，不要用外部的瀏覽器。
		mWebView.setWebViewClient(new WebViewClient());

		WebSettings webSettings = mWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);

		mBtnOpenUrl.setOnClickListener(btnOpenUrlOnClick);
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

	private View.OnClickListener btnOpenUrlOnClick = new View.OnClickListener() {
		public void onClick(View v) {
			mWebView.loadUrl(mEdtUrl.getText().toString());
		}
	};

}
