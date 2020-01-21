package com.android;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends Activity
					implements SurfaceHolder.Callback {

	private static final int MENU_START_RECORDING = Menu.FIRST,
				 MENU_STOP_RECORDING = Menu.FIRST + 1,
				 MENU_VIEW_RECORDING = Menu.FIRST + 2;
	
	private final String mFileName = "recorded_video.3gp";
	
	private Camera mCamera;
	private MediaRecorder mRecorder;
	private SurfaceView mCamPreview;
	private SurfaceHolder mSurfHolder; 
	private boolean mRecording = false; 
	private int mRotateDegree;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 讓拍攝的影像佔滿整個螢幕
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
			            WindowManager.LayoutParams.FLAG_FULLSCREEN); 
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		setContentView(R.layout.activity_main);

		mCamPreview = (SurfaceView) findViewById(R.id.camPreview); 
	    mSurfHolder = mCamPreview.getHolder(); 
	    mSurfHolder.addCallback(this); 

	    // 如果程式會在Android 2.X的手機執行，必須將這二行程式碼打開。
//      if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB)
//	    	mSurfHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	@Override
	protected void onResume() {
	    mRecorder = new MediaRecorder(); 
		mCamera = Camera.open();

		super.onResume();
	}

	@Override
	protected void onPause() {
		mCamera.stopPreview();
	    mCamera.release();
	    mCamera = null;

	    super.onPause();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, MENU_START_RECORDING, 0, "開始錄影");
		menu.add(0, MENU_STOP_RECORDING, 0, "停止錄影");
		menu.add(0, MENU_VIEW_RECORDING, 0, "撥放影片");

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_START_RECORDING:
			mRecording = true;
			mCamera.stopPreview();
			mCamera.unlock();
			startRecording();
			break;
		case MENU_STOP_RECORDING:
			mRecording = false;
			mRecorder.stop();

			try {
				mCamera.reconnect();
				mCamera.startPreview();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Toast.makeText(this, "Camera 啟始錯誤！", Toast.LENGTH_LONG)
					.show();
			}

			break;
		case MENU_VIEW_RECORDING:
			Intent it = new Intent();
			it.setClass(MainActivity.this, PlayVideoActivity.class);
			it.putExtra("FILE_NAME", mFileName);
			startActivity(it);
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}

	private void startRecording() { 
		try {
			mRecorder.setCamera(mCamera);
		     mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		     mRecorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);
		   	mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
		     mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);
		   	mRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
		   	mRecorder.setOutputFile("/sdcard/" + mFileName);
		   	mRecorder.setPreviewDisplay(mSurfHolder.getSurface());
		   	mRecorder.setOrientationHint(mRotateDegree);
		   	mRecorder.prepare();
			mRecorder.start();
		}
		catch (Exception e) {
			Toast.makeText(MainActivity.this, "錄影錯誤！", Toast.LENGTH_LONG)
				.show();
		}
	} 
	 
	public void surfaceCreated(SurfaceHolder holder) { 
		try {
			mCamera.setPreviewDisplay(mSurfHolder);

			Camera.CameraInfo camInfo = 
						new Camera.CameraInfo();
			Camera.getCameraInfo(0, camInfo);

			int rotation = getWindowManager().getDefaultDisplay().getRotation();
			int degrees = 0;
			switch (rotation) {
			case Surface.ROTATION_0:
				degrees = 0; break;
			case Surface.ROTATION_90:
				degrees = 90; break;
			case Surface.ROTATION_180:
				degrees = 180; break;
			case Surface.ROTATION_270:
				degrees = 270; break;
			}

			mRotateDegree = (camInfo.orientation - degrees + 360) % 360;
			mCamera.setDisplayOrientation(mRotateDegree);
			
			mCamera.startPreview();
			
			Camera.Parameters camParas = mCamera.getParameters();
			if (camParas.getFocusMode().equals(Camera.Parameters.FOCUS_MODE_AUTO) || 
				camParas.getFocusMode().equals(Camera.Parameters.FOCUS_MODE_MACRO))
				mCamera.autoFocus(null);
			else
				Toast.makeText(this, "照相機不支援自動對焦！", Toast.LENGTH_SHORT)
					.show();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Toast.makeText(this, "照相機啟始錯誤！", Toast.LENGTH_LONG)
				.show();
		}
	} 
	 
	public void surfaceChanged(SurfaceHolder holder, int format, int width, 
	        int height) { 
	} 
	 
	public void surfaceDestroyed(SurfaceHolder holder) { 
	    if (mRecording) { 
	        mRecorder.stop(); 
	        mRecording = false; 
	    } 
	    mRecorder.release(); 
	    finish(); 
	}

}
