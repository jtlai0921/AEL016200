package com.android;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.os.Build;

public class MainActivity extends Activity
							implements OnPreparedListener,
										OnErrorListener,
										OnCompletionListener {

	private ImageButton mBtnMediaPlayPause,
						mBtnMediaStop,
						mBtnMediaGoto;
	
	private ToggleButton mBtnMediaRepeat;
	
	private EditText mEdtMediaGoto;
	
	// 程式使用的MediaPlayer物件
	private MediaPlayer mMediaPlayer = null;
	
	// 用來記錄是否MediaPlayer物件需要執行prepareAsync()
	private boolean mbIsInitial = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mBtnMediaPlayPause = (ImageButton)findViewById(R.id.btnMediaPlayPause);
    	mBtnMediaStop = (ImageButton)findViewById(R.id.btnMediaStop);
    	mBtnMediaRepeat = (ToggleButton)findViewById(R.id.btnMediaRepeat);
    	mBtnMediaGoto = (ImageButton)findViewById(R.id.btnMediaGoto);
    	mEdtMediaGoto = (EditText)findViewById(R.id.edtMediaGoto);

    	mBtnMediaPlayPause.setOnClickListener(btnMediaPlayPauseOnClick);
    	mBtnMediaStop.setOnClickListener(btnMediaStopOnClick);
    	mBtnMediaRepeat.setOnClickListener(btnMediaRepeatOnClick);
    	mBtnMediaGoto.setOnClickListener(btnMediaGotoOnClick);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		mMediaPlayer = new MediaPlayer();

		Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.song);

		try {
			mMediaPlayer.setDataSource(this, uri);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Toast.makeText(MainActivity.this, "指定的音樂檔錯誤！", Toast.LENGTH_LONG)
				.show();
		}

		mMediaPlayer.setOnPreparedListener(this);
		mMediaPlayer.setOnErrorListener(this);
		mMediaPlayer.setOnCompletionListener(this);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

		mMediaPlayer.release();
		mMediaPlayer = null;
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

	private View.OnClickListener btnMediaPlayPauseOnClick = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (mMediaPlayer.isPlaying()) {
				mBtnMediaPlayPause.setImageResource(android.R.drawable.ic_media_play);
				mMediaPlayer.pause();
			} else {
				mBtnMediaPlayPause.setImageResource(android.R.drawable.ic_media_pause);

				if (mbIsInitial) {
					mMediaPlayer.prepareAsync();
					mbIsInitial = false;
				} else
					mMediaPlayer.start();
			}
		}
	};

	private View.OnClickListener btnMediaStopOnClick = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			mMediaPlayer.stop();
			
			// 停止播放後必須再執行 prepareAsync() 
			// 或 prepare() 才能重新播放。
			mbIsInitial = true;
			mBtnMediaPlayPause.setImageResource(android.R.drawable.ic_media_play);
		}
	}; 

	private View.OnClickListener btnMediaRepeatOnClick = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (((ToggleButton)v).isChecked())
				mMediaPlayer.setLooping(true);
			else
				mMediaPlayer.setLooping(false);
		}
	};

	private View.OnClickListener btnMediaGotoOnClick = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (mEdtMediaGoto.getText().toString().equals("")) {
				Toast.makeText(MainActivity.this, 
					"請先輸入要播放的位置（以秒為單位）", 
					Toast.LENGTH_LONG)
					.show();
				return;
			}

			int seconds = Integer.parseInt(mEdtMediaGoto.getText().toString());
			mMediaPlayer.seekTo(seconds * 1000); // 以毫秒（千分之一秒）為單位
		}
	};

	@Override
	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub
		mBtnMediaPlayPause.setImageResource(android.R.drawable.ic_media_play);
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		// TODO Auto-generated method stub
		mp.release();
		mp = null;

		Toast.makeText(MainActivity.this, "發生錯誤，停止播放", Toast.LENGTH_LONG)
			.show();

		return true;
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		// TODO Auto-generated method stub
		mp.seekTo(0);
		mp.start();

		Toast.makeText(MainActivity.this, "開始播放", Toast.LENGTH_LONG)
			.show();
	}

}
