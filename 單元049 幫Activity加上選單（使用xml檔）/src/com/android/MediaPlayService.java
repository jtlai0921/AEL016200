package com.android;

import java.io.File;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;

public class MediaPlayService extends Service {

	private MediaPlayer player;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		player.stop();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Uri uriFile = Uri.fromFile(new File(Environment.getExternalStorageDirectory().getPath() + "/song.mp3"));
		player = MediaPlayer.create(this, uriFile);
		player.start();

		return super.onStartCommand(intent, flags, startId);
	}

}
