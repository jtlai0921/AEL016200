package com.android;

import android.content.Context;
import android.widget.Toast;

public class JavaScriptCallFunc {

	Context mContext;

	JavaScriptCallFunc(Context c) {
		mContext = c;
	}

	@android.webkit.JavascriptInterface
	public void showToastMsg(String s) {
		Toast.makeText(mContext, s, Toast.LENGTH_LONG)
			.show();
	}
}
