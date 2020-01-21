package com.android;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends Activity
						implements OnPreparedListener,
						OnErrorListener,
						OnCompletionListener {

	private final String mFileName = "my_recorded_audio.3gp";

	private Button mBtnAudioRecoOnOff,
				   mBtnPlayAudioOnOff;

	private boolean mBoolRecording = false,
					mBoolPlaying = false;

	private MediaRecorder mRecorder = null;
	private MediaPlayer mPlayer = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mBtnAudioRecoOnOff = (Button)findViewById(R.id.btnAudioRecoOnOff);
    	mBtnPlayAudioOnOff = (Button)findViewById(R.id.btnPlayAudioOnOff);

    	mBtnAudioRecoOnOff.setOnClickListener(btnAudioRecoOnOffOnClick);
    	mBtnPlayAudioOnOff.setOnClickListener(btnPlayAudioOnOffOnClick);
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

	private View.OnClickListener btnAudioRecoOnOffOnClick = new 
			View.OnClickListener() {
		public void onClick(View v) {
			if (mBoolRecording) {
				mRecorder.stop();
				mRecorder.release();
				mRecorder = null;

				mBoolRecording = false;
				mBtnAudioRecoOnOff.setText("開始錄音");
			} else {
				mRecorder = new MediaRecorder();
				mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);

				mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
				mRecorder.setOutputFile(
       			   Environment.getExternalStorageDirectory().getPath() + 
				   "/" + mFileName);
				mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
				
				try {
					mRecorder.prepare();
					mRecorder.start();
					mBoolRecording = true;
					mBtnAudioRecoOnOff.setText("停止錄音");
				} catch (Exception e) {
					Toast.makeText(MainActivity.this, "MediaRecorder 錯誤!",
							Toast.LENGTH_LONG)
						.show();
				}
			}
		}
	};

	private View.OnClickListener btnPlayAudioOnOffOnClick = new 
			View.OnClickListener() {
		public void onClick(View v) {
			if (mBoolRecording) {
				mRecorder.stop();
				mRecorder.release();
				mRecorder = null;

				mBoolRecording = false;
				mBtnAudioRecoOnOff.setText("開始錄音");
			}
			
			if (mBoolPlaying) {
				mPlayer.stop();
				mPlayer.release();
				mPlayer = null;

				mBoolPlaying = false;
				mBtnPlayAudioOnOff.setText("開始播放");
			} else {
				mPlayer = new MediaPlayer();
				
				try {
					mPlayer.setDataSource(
					Environment.getExternalStorageDirectory().getAbsolutePath() + 
							"/" + mFileName);
				} catch (Exception e) {
					Toast.makeText(MainActivity.this, "MediaPlayer 錯誤!", 
								Toast.LENGTH_LONG)
						.show();
				}

				mPlayer.setOnPreparedListener(MainActivity.this);
				mPlayer.setOnErrorListener(MainActivity.this);
				mPlayer.setOnCompletionListener(MainActivity.this);

				mPlayer.prepareAsync();

				mBoolPlaying = true;
				mBtnPlayAudioOnOff.setText("停止播放");
			}
		}
	};

	@Override
	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub
		mPlayer.release();
		mPlayer = null;

		mBoolPlaying = false;
		mBtnPlayAudioOnOff.setText("開始播放");
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		// TODO Auto-generated method stub
		mPlayer.release();
		mPlayer = null;

		Toast.makeText(MainActivity.this, "MediaPlayer錯誤!", Toast.LENGTH_LONG)
			.show();

		return true;
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		// TODO Auto-generated method stub
		mPlayer.setVolume(1.0f, 1.0f);
		mPlayer.start();

		Toast.makeText(MainActivity.this, "開始播放...", Toast.LENGTH_LONG)
						.show();
	}
}
