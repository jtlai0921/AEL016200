package com.android;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.support.v4.app.Fragment;
import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends Activity {

	private int iAppWindowHeight;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenWidth = dm.widthPixels;
		int screenHeight = dm.heightPixels;
		
		Toast.makeText(MainActivity.this, 
				String.valueOf(screenWidth) +", " + String.valueOf(screenHeight), Toast.LENGTH_LONG)
				.show();

		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.LinearLayout1);

		linearLayout.post(new Runnable() {

			@Override
			public void run() {
				// 取得App畫面區域的大小
				Rect r = new Rect();
				Window window = getWindow();
				window.getDecorView().getWindowVisibleDisplayFrame(r);
				int iStatusBarHeight = r.top;	// 也可以取得status bar的高度
				int iStatusBarPlusActionBarHeight = 
                        getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();

				DisplayMetrics dm = new DisplayMetrics();
				getWindowManager().getDefaultDisplay().getMetrics(dm);
				iAppWindowHeight = dm.heightPixels - iStatusBarPlusActionBarHeight;

				Toast.makeText(MainActivity.this, 
						String.valueOf(iAppWindowHeight), Toast.LENGTH_LONG)
						.show();
			}
		});
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

}
