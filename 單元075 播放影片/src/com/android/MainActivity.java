package com.android;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;
import android.os.Build;

public class MainActivity extends Activity
							implements OnErrorListener,
										OnCompletionListener {

	private VideoView mVideoView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mVideoView = (VideoView)findViewById(R.id.videoView);
		MediaController mediaController = new MediaController(this);
		mVideoView.setMediaController(mediaController);
		mVideoView.setOnCompletionListener(this);
		mVideoView.setOnErrorListener(this);

		Uri uri = Uri.parse("android.resource://" + 
					getPackageName() + "/" + R.raw.video);
    	mVideoView.setVideoURI(uri);
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
	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub
		Toast.makeText(MainActivity.this, "播放完畢！", Toast.LENGTH_LONG)
		         .show();
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		// TODO Auto-generated method stub
		Toast.makeText(MainActivity.this, "發生錯誤！", Toast.LENGTH_LONG)
		         .show();
		return true;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		mVideoView.start();
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		mVideoView.stopPlayback();
		super.onPause();
	}

}
