package com.android;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	private Button mBtnRegReceiver,
					 mBtnUnregReceiver,
					 mBtnSendBroadcast1,
					 mBtnSendBroadcast2;

	private MyBroadcastReceiver2 mMyReceiver2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mBtnRegReceiver = (Button)findViewById(R.id.btnRegReceiver);
		mBtnUnregReceiver = (Button)findViewById(R.id.btnUnregReceiver);
    	mBtnSendBroadcast1 = (Button)findViewById(R.id.btnSendBroadcast1);
		mBtnSendBroadcast2 = (Button)findViewById(R.id.btnSendBroadcast2);
				   
    	mBtnRegReceiver.setOnClickListener(btnRegReceiverOnClick);
		mBtnUnregReceiver.setOnClickListener(btnUnregReceiverOnClick);
    	mBtnSendBroadcast1.setOnClickListener(btnSendBroadcast1OnClick);
		mBtnSendBroadcast2.setOnClickListener(btnSendBroadcast2OnClick);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private View.OnClickListener btnRegReceiverOnClick = new View.OnClickListener() {
		public void onClick(View v) {
			IntentFilter itFilter = new IntentFilter("com.android.MY_BROADCAST2");
			mMyReceiver2 = new MyBroadcastReceiver2();
			registerReceiver(mMyReceiver2, itFilter);
		}
	};

    private View.OnClickListener btnUnregReceiverOnClick = new View.OnClickListener() {
		public void onClick(View v) {
			unregisterReceiver(mMyReceiver2);	
	     }
	};

    private View.OnClickListener btnSendBroadcast1OnClick = new View.OnClickListener() {
		public void onClick(View v) {
			Intent it = new Intent("com.android.MY_BROADCAST1");
			it.putExtra("sender_name", "主程式");
			sendBroadcast(it);
		}
	};

    private View.OnClickListener btnSendBroadcast2OnClick = new View.OnClickListener() {
		public void onClick(View v) {
			Intent it = new Intent("com.android.MY_BROADCAST2");
			it.putExtra("sender_name", "主程式");
			sendBroadcast(it);
		}
	};

}
