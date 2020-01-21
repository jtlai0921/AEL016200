package com.android;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

public class MainActivity extends Activity {

	private static final int MENU_MUSIC = Menu.FIRST,
				            MENU_PLAY_MUSIC = Menu.FIRST + 1,
				            MENU_STOP_PLAYING_MUSIC = Menu.FIRST + 2,
				            MENU_ABOUT = Menu.FIRST + 3,
				            MENU_EXIT = Menu.FIRST + 4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		SubMenu subMenu = menu.addSubMenu(0, MENU_MUSIC, 0, "背景音樂")
				.setIcon(android.R.drawable.ic_media_ff);
		subMenu.add(0, MENU_PLAY_MUSIC, 0, "播放背景音樂");
		subMenu.add(0, MENU_STOP_PLAYING_MUSIC, 1, "停止播放背景音樂");
		menu.add(0, MENU_ABOUT, 1, "關於這個程式...")
			.setIcon(android.R.drawable.ic_dialog_info);
		menu.add(0, MENU_EXIT, 2, "結束")
			.setIcon(android.R.drawable.ic_menu_close_clear_cancel);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case MENU_PLAY_MUSIC:
			Intent it = new Intent(MainActivity.this, MediaPlayService.class);
    		startService(it);
			break;
		case MENU_STOP_PLAYING_MUSIC:
			it = new Intent(MainActivity.this, MediaPlayService.class);
    		stopService(it);
			break;
		case MENU_ABOUT:
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
		case MENU_EXIT:
			finish();
			break;
		}

		return super.onOptionsItemSelected(item);
	}

}
