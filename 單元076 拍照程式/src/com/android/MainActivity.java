package com.android;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends Activity {

	private static final int MENU_TAKE_PICTURE = Menu.FIRST,
				 			MENU_SHOW_PICTURE = Menu.FIRST + 1;
	
	private Camera mCamera;
	private CameraPreview mCamPreview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFormat(PixelFormat.TRANSLUCENT);

        // 讓相機畫面使用整個螢幕。
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        					 WindowManager.LayoutParams.FLAG_FULLSCREEN); 

        mCamPreview = new CameraPreview(this);
        setContentView(mCamPreview);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		mCamera = Camera.open();
		mCamPreview.set(this, mCamera);

		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		mCamera.stopPreview();
	    mCamera.release();
	    mCamera = null;

	    super.onPause();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, MENU_TAKE_PICTURE, 0, "拍照");
		menu.add(0, MENU_SHOW_PICTURE, 0, "顯示照片");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_TAKE_PICTURE:
			mCamera.takePicture(camShutterCallback, camRawDataCallback, 
					camJpegCallback);
			break;
		case MENU_SHOW_PICTURE:
			Intent it = new Intent(Intent.ACTION_VIEW);
			File file = new File("/sdcard/photo.jpg");
			it.setDataAndType(Uri.fromFile(file), "image/*");
			startActivity(it);
			break;
		}
		
		return super.onOptionsItemSelected(item);

	}

	private ShutterCallback camShutterCallback = new ShutterCallback() {
		public void onShutter() {
			// 通知使用者已完成拍照,例如發出一個模擬快門的聲音。
		}
	};

	private PictureCallback camRawDataCallback = new PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
			// 接收原始的影像資料。
		}
	};

	private PictureCallback camJpegCallback = new PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
			// 接收壓縮成jpeg格式的影像資料。
			FileOutputStream outStream = null;
			try {
				outStream = new FileOutputStream(
						Environment.getExternalStorageDirectory().getPath() + "/photo.jpg");
				outStream.write(data);
				outStream.close();
			} catch (IOException e) {
				Toast.makeText(MainActivity.this, "影像檔儲存錯誤！", Toast.LENGTH_SHORT)
					.show();
			}

			mCamera.startPreview();
		}
	};

}
