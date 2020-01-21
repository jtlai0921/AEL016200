package com.android;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Build;
import android.provider.MediaStore.MediaColumns;

public class MainActivity extends Activity
							implements OnClickListener {

	private Button mBtnAddToMediaStore,
				   mBtnStart, mBtnPause,
				   mBtnStop, mBtnSetRepeat,
				   mBtnCancelRepeat, mBtnGoto;

	private EditText mEdtGoto;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mBtnStart = (Button)findViewById(R.id.btnStart);
		mBtnPause = (Button)findViewById(R.id.btnPause);
		mBtnStop = (Button)findViewById(R.id.btnStop);
		mBtnSetRepeat = (Button)findViewById(R.id.btnSetRepeat);
		mBtnCancelRepeat = (Button)findViewById(R.id.btnCancelRepeat);
		mBtnGoto = (Button)findViewById(R.id.btnGoto);
		mBtnAddToMediaStore = (Button)findViewById(R.id.btnAddToMediaStore);
		mEdtGoto = (EditText)findViewById(R.id.edtGoto);

		mBtnStart.setOnClickListener(this);
		mBtnPause.setOnClickListener(this);
		mBtnStop.setOnClickListener(this);
		mBtnSetRepeat.setOnClickListener(this);
		mBtnCancelRepeat.setOnClickListener(this);
		mBtnGoto.setOnClickListener(this);
    	mBtnAddToMediaStore.setOnClickListener(btnAddToMediaStoreOnClick);
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

		Intent it;

		switch(v.getId()) {
		case R.id.btnStart:
			it = new Intent(MainActivity.this, MediaPlayerService.class);
			it.setAction(MediaPlayerService.ACTION_PLAY);
			startService(it);
			break;
		case R.id.btnPause:
			it = new Intent(MainActivity.this, MediaPlayerService.class);
			it.setAction(MediaPlayerService.ACTION_PAUSE);
			startService(it);
			break;
		case R.id.btnStop:
			it = new Intent(MainActivity.this, MediaPlayerService.class);
			stopService(it);
			break;
		case R.id.btnSetRepeat:
			it = new Intent(MainActivity.this, MediaPlayerService.class);
			it.setAction(MediaPlayerService.ACTION_SET_REPEAT);
			startService(it);
			break;
		case R.id.btnCancelRepeat:
			it = new Intent(MainActivity.this, MediaPlayerService.class);
			it.setAction(MediaPlayerService.ACTION_CANCEL_REPEAT);
			startService(it);
			break;
		case R.id.btnGoto:
			if (mEdtGoto.getText().toString().equals("")) {
				Toast.makeText(MainActivity.this, 
					"請先輸入要播放的位置（以秒為單位）", 
					Toast.LENGTH_LONG)
					.show();
				break;
			}

			int seconds = Integer.parseInt(mEdtGoto.getText().toString());

			it = new Intent(MainActivity.this, MediaPlayerService.class);
			it.setAction(MediaPlayerService.ACTION_GOTO);
			it.putExtra("GOTO_POSITION_SECONDS", seconds);
			startService(it);
			break;
		}
	}

    private View.OnClickListener btnAddToMediaStoreOnClick = new View.OnClickListener() {
		public void onClick(View v) {
			ContentValues val = new ContentValues();   
	        val.put(MediaColumns.TITLE, "my mp3");   
	        val.put(MediaColumns.MIME_TYPE, "audio/mp3");   
	        val.put(MediaColumns.DATA, "/sdcard/song.mp3");   
	        ContentResolver contRes = getContentResolver();   
	        Uri newUri = contRes.insert(
	        		android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, 
	        		val);   
	        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, newUri));			
		}
	};

}
