package com.android;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_main_activity, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.menuItemPlayBackgroundMusic:
			Intent it = new Intent(MainActivity.this, MediaPlayService.class);
	  		startService(it);
			break;
		case R.id.menuItemStopBackgroundMusic:
			it = new Intent(MainActivity.this, MediaPlayService.class);
	  		stopService(it);
			break;
		case R.id.menuItemAbout:
			new AlertDialog.Builder(MainActivity.this)
				.setTitle("關於這個程式")
				.setMessage("選單範例程式")
				.setCancelable(false)
				.setIcon(android.R.drawable.star_big_on)
				.setPositiveButton("確定",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub							
						}
					})
				.show();
				
			break;
		case R.id.menuItemExit:
			finish();
			break;
		}

		return super.onOptionsItemSelected(item);
	}

}
