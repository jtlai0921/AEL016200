package com.android;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.IBinder;
import android.provider.MediaStore.MediaColumns;
import android.widget.Toast;

public class MediaPlayerService extends Service
								implements OnPreparedListener,
											OnErrorListener,
											OnCompletionListener,
											OnAudioFocusChangeListener {

	public static final String 
		ACTION_PLAY = "tw.android.mediaplayer.action.PLAY",
		ACTION_PAUSE = "tw.android.mediaplayer.action.PAUSE",
		ACTION_SET_REPEAT = "tw.android.mediaplayer.action.SET_REPEAT",
		ACTION_CANCEL_REPEAT = "tw.android.mediaplayer.action.CANCEL_REPEAT",
		ACTION_GOTO = "tw.android.mediaplayer.action.GOTO";

	// �{���ϥΪ�MediaPlayer����
	private MediaPlayer mMediaPlayer = null;

	// �ΨӰO���O�_MediaPlayer����ݭn����prepareAsync()
	private boolean mbIsInitial = true,
					mbAudioFileFound = false;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

		// �Ĥ@�ؼ����ɨӷ��G�{���M�ת� res/raw ��Ƨ�
//		Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.song);

		// �ĤG�ؼ����ɨӷ��Gsdcard
//		File file = new File("/sdcard/song.mp3");  
//		Uri uri = Uri.fromFile(file);

		// �ĤT�ؼ����ɨӷ��G���} http�A�o�ؤ覡�n�]�w stream type
//		Uri uri = Uri.parse("http://.../song.mp3");
//		mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

		// �ĥ|�ؼ����ɨӷ��G�q Android �t�Ϊ���Ʈw�����o������
		// MediaStore �O�Ψӫ��w�v���B���T�μv�����A�����
		// �o�ؤ覡�n�]�w stream type�A
		// �o�Ӽ����ɥ������Q�ε{���e����"�[�J mp3 ��"���s�[�J
		ContentResolver contRes = getContentResolver();
		String[] columns = {
				MediaColumns.TITLE, 
				MediaColumns._ID};
		Cursor c = contRes.query(
				android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, 
				columns, null, null, null);

		Uri uri = null;
		if (c == null) {
			Toast.makeText(MediaPlayerService.this, "Content Resolver ���~�I", Toast.LENGTH_LONG)
				.show();
			return;
		}
		else if (!c.moveToFirst()) {
			Toast.makeText(MediaPlayerService.this, "��Ʈw���S����ơI", Toast.LENGTH_LONG)
				.show();
			return;
		}
		else {
			do {
			    String title = c.getString(c.getColumnIndex(MediaColumns.TITLE));
			    if (title.equals("my mp3")) {
			    	mbAudioFileFound = true;
			    	break;
			    }
			} while(c.moveToNext());

			if (! mbAudioFileFound) {
				Toast.makeText(MediaPlayerService.this, "�䤣����w�� mp3 �ɡI", Toast.LENGTH_LONG)
					.show();
				return;
			}

			int idColumn = c.getColumnIndex(android.provider.MediaStore.Audio.Media._ID);
			long id = c.getLong(idColumn);
			uri = ContentUris.withAppendedId(
					android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
		}

		mMediaPlayer = new MediaPlayer();
		mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

		try {
			mMediaPlayer.setDataSource(this, uri);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Toast.makeText(MediaPlayerService.this, "���w�������ɿ��~�I", Toast.LENGTH_LONG)
				.show();
		}

		mMediaPlayer.setOnPreparedListener(this);
		mMediaPlayer.setOnErrorListener(this);
		mMediaPlayer.setOnCompletionListener(this);

		// �]�w Media Player �b�I������ɡA�� CPU �����B��
		// �p�G���񪺬O�Ӧۺ����� streaming audio�A
		// �٭n�]�w���������B�@
		// �u��b����]�ƤW�ϥΡA����������ɷ|���Ϳ��~
//		mMediaPlayer.setWakeMode(getApplicationContext(), 
//				PowerManager.PARTIAL_WAKE_LOCK);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		if (mbAudioFileFound) {
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
		
		stopForeground(true);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub

		if (! mbAudioFileFound) {
			stopSelf();
			return super.onStartCommand(intent, flags, startId);
		}

		if (intent.getAction().equals(ACTION_PLAY))
			if (mbIsInitial) {
				mMediaPlayer.prepareAsync();
				mbIsInitial = false;
			}
			else
				mMediaPlayer.start();
		else if (intent.getAction().equals(ACTION_PAUSE))
			mMediaPlayer.pause();
		else if (intent.getAction().equals(ACTION_SET_REPEAT))
			mMediaPlayer.setLooping(true);
		else if (intent.getAction().equals(ACTION_CANCEL_REPEAT))
			mMediaPlayer.setLooping(false);
		else if (intent.getAction().equals(ACTION_GOTO)) {
			int seconds = intent.getIntExtra("GOTO_POSITION_SECONDS", 0);
			mMediaPlayer.seekTo(seconds * 1000); // �H�@��]�d�����@��^�����
		}

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		// TODO Auto-generated method stub

		// �O�_���o audio focus
		AudioManager audioMgr = 
			(AudioManager)getSystemService(Context.AUDIO_SERVICE);
		int r = audioMgr.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
		if (r != AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
			mp.setVolume(0.1f, 0.1f);	// ���C���q

		mp.start();

		Intent it = new Intent(getApplicationContext(), MainActivity.class);

		PendingIntent penIt = PendingIntent.getActivity(
				getApplicationContext(), 0, it, 
				PendingIntent.FLAG_CANCEL_CURRENT);

    	Notification noti = new Notification.Builder(this)
		        .setSmallIcon(android.R.drawable.ic_media_play)
		        .setTicker("����I������")
		        .setContentTitle(getString(R.string.app_name))
		        .setContentText("�I�����ּ���...")
		        .setContentIntent(penIt)
		        .build();

		startForeground(1, noti);

		Toast.makeText(MediaPlayerService.this, "�}�l����", Toast.LENGTH_LONG)
		.show();
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		// TODO Auto-generated method stub
		mp.release();
		mp = null;

		Toast.makeText(MediaPlayerService.this, "�o�Ϳ��~�A�����", Toast.LENGTH_LONG)
			.show();

		return true;
	}

	@Override
	public void onAudioFocusChange(int focusChange) {
		// TODO Auto-generated method stub
		
		if (mMediaPlayer == null)
			return;

		switch (focusChange) {
		case AudioManager.AUDIOFOCUS_GAIN:
			// �{�����o�n�������v
			mMediaPlayer.setVolume(0.8f, 0.8f);
			mMediaPlayer.start();				
			break;
		case AudioManager.AUDIOFOCUS_LOSS:
			// �{���|���n�������v�A�ӥB�ɶ��i��ܤ[
			stopSelf();		// �����o��Service
			break;
		case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
			// �{���|���n�������v�A���w���ܧִN�|�A���o
			if (mMediaPlayer.isPlaying())
				mMediaPlayer.pause();
			break;
		case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
			// �{���|���n�������v�A���O�i�H�Ϋܤp�����q�~�򼽩�
			if (mMediaPlayer.isPlaying())
				mMediaPlayer.setVolume(0.1f, 0.1f);
			break;
		}
	}

	@Override
	public void onCompletion(MediaPlayer arg0) {
		// TODO Auto-generated method stub
		mMediaPlayer.release();
		mMediaPlayer = null;
		
		stopForeground(true);

		mbIsInitial = true;
	}

}
