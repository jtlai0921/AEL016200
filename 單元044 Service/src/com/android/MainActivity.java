package com.android;

import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	private Button mBtnStartMyService,
					mBtnStopMyService,
					mBtnBindMyService,
					mBtnUnbindMyService,
					mBtnCallMyServiceMethod;
	
	private MyService mMyServ = null;
	
	private final String LOG_TAG = "service demo";

	private ServiceConnection mServConn = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			Log.d(LOG_TAG, "onServiceConnected() " + name.getClassName());
			mMyServ = ((MyService.LocalBinder)service).getService();
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			Log.d(LOG_TAG, "onServiceDisconnected()" + name.getClassName());
		}
		
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mBtnStartMyService = (Button) findViewById(R.id.btnStartMyService);
        mBtnStopMyService = (Button) findViewById(R.id.btnStopMyService);
        mBtnBindMyService = (Button) findViewById(R.id.btnBindMyService);
        mBtnUnbindMyService = (Button) findViewById(R.id.btnUnbindMyService);
        mBtnCallMyServiceMethod = (Button) findViewById(R.id.btnCallMyServiceMethod);
		
		mBtnStartMyService.setOnClickListener(btnStartMyServiceOnClick);
		mBtnStopMyService.setOnClickListener(btnStopMyServiceOnClick);
		mBtnBindMyService.setOnClickListener(btnBindMyServiceOnClick);
		mBtnUnbindMyService.setOnClickListener(btnUnbindMyServiceOnClick);
		mBtnCallMyServiceMethod.setOnClickListener(btnCallMyServiceMethodOnClick);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private View.OnClickListener btnStartMyServiceOnClick = new View.OnClickListener() {
    	public void onClick(View v) {
    		mMyServ = null;
    		Intent it = new Intent(MainActivity.this, MyService.class);
    		startService(it);
    	}
    };
    
    private View.OnClickListener btnStopMyServiceOnClick = new View.OnClickListener() {
    	public void onClick(View v) {
    		mMyServ = null;
    		Intent it = new Intent(MainActivity.this, MyService.class);
    		stopService(it);
    	}
    };
   
    private View.OnClickListener btnBindMyServiceOnClick = new View.OnClickListener() {
    	public void onClick(View v) {
    		mMyServ = null;
    		Intent it = new Intent(MainActivity.this, MyService.class);
    		bindService(it, mServConn, BIND_AUTO_CREATE);
    	}
    };
    
    private View.OnClickListener btnUnbindMyServiceOnClick = new View.OnClickListener() {
    	public void onClick(View v) {
    		mMyServ = null;
    		unbindService(mServConn);
    	}
    };
	
    private View.OnClickListener btnCallMyServiceMethodOnClick = new View.OnClickListener() {
    	public void onClick(View v) {
    		if (mMyServ != null)
    			mMyServ.myMethod();
    	}
    };

}
