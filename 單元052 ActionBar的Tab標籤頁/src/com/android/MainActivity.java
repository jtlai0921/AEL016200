package com.android;

import android.support.v4.app.Fragment;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ActionBar actBar = getActionBar();

        // 設定Action Bar為Tab標籤頁模式
        actBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // 設定第一個Tab標籤頁
        MyTabListener<MarriSugFragment> tabListenerMainFrag = 
        	new  MyTabListener<MarriSugFragment>(
        		MainActivity.this, "Marriage Suggestion Fragment", MarriSugFragment.class);
        actBar.addTab(actBar.newTab().setText("婚姻建議")
        .setIcon(getResources().getDrawable(android.R.drawable.ic_lock_idle_alarm))
        		.setTabListener(tabListenerMainFrag));

        // 設定第二個Tab標籤頁
        MyTabListener<GameFragment> tabListenerPersInfoFrag = 
            	new  MyTabListener<GameFragment>(
            		MainActivity.this, "Game Fragment", GameFragment.class);
        actBar.addTab(actBar.newTab().setText("電腦猜拳遊戲")
        .setIcon(getResources().getDrawable(android.R.drawable.ic_dialog_alert))
        		.setTabListener(tabListenerPersInfoFrag));
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
