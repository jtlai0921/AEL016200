package com.android;

import java.io.File;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	private Button mBtnBrowseWWW,
				   mBtnPlayMP3,
				   mBtnViewImg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mBtnBrowseWWW = (Button)findViewById(R.id.btnBrowseWWW);
		mBtnPlayMP3 = (Button)findViewById(R.id.btnPlayMP3);
		mBtnViewImg = (Button)findViewById(R.id.btnViewImg);
				   
    	mBtnBrowseWWW.setOnClickListener(btnBrowseWWWOnClick);
		mBtnPlayMP3.setOnClickListener(btnPlayMP3OnClick);
		mBtnViewImg.setOnClickListener(btnViewImgOnClick);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

    private View.OnClickListener btnBrowseWWWOnClick = new View.OnClickListener() {
		public void onClick(View v) {
			Uri uri = Uri.parse("http://developer.android.com/");
			Intent it = new Intent(Intent.ACTION_VIEW, uri);
			startActivity(it);
		}
	};

	private View.OnClickListener btnPlayMP3OnClick = new View.OnClickListener() {
		public void onClick(View v) {
			Intent it = new Intent(Intent.ACTION_VIEW);
			File file = new File("/sdcard/song.mp3");  
			it.setDataAndType(Uri.fromFile(file), "audio/*"); 
			startActivity(it);
		}
	};

	private View.OnClickListener btnViewImgOnClick = new View.OnClickListener() {
		public void onClick(View v) {
			Intent it = new Intent(Intent.ACTION_VIEW);
			File file = new File("/sdcard/image.png");  
			it.setDataAndType(Uri.fromFile(file), "image/*"); 
			startActivity(it);
		}
	};

}
