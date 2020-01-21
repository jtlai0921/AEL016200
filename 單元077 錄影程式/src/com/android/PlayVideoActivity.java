package com.android;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class PlayVideoActivity extends Activity
				implements OnErrorListener,
							OnCompletionListener {

	private VideoView mVideoView;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play_video);
		
		mVideoView = (VideoView)findViewById(R.id.videoView);
		MediaController mediaController = new MediaController(this);
		mVideoView.setMediaController(mediaController);
		mVideoView.setOnCompletionListener(this);
		mVideoView.setOnErrorListener(this);
		
		String sVideoFileName = getIntent().getStringExtra("FILE_NAME");
		Uri uri = Uri.parse(
				Environment.getExternalStorageDirectory().getPath() + 
				"/" + sVideoFileName);
		mVideoView.setVideoURI(uri);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		mVideoView.start();
	}
	
	@Override
	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "¼½©ñ§¹²¦¡I", Toast.LENGTH_LONG)
			.show();
	}
	
	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "°õ¦æ¿ù»~¡I", Toast.LENGTH_LONG)
			.show();
		
		return true;
	}

}
