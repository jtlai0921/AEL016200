package com.android;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static final int MENU_MUSIC = Menu.FIRST,
				            MENU_PLAY_MUSIC = Menu.FIRST + 1,
				            MENU_STOP_PLAYING_MUSIC = Menu.FIRST + 2,
				            MENU_ABOUT = Menu.FIRST + 3,
				            MENU_EXIT = Menu.FIRST + 4;

	private RelativeLayout mRelativeLayout;
	private TextView mTxtView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mRelativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        registerForContextMenu(mRelativeLayout);
        mTxtView = (TextView) findViewById(R.id.txtView);
        registerForContextMenu(mTxtView);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);

		if (v == mRelativeLayout) {
			if (menu.size() == 0) {
		    	SubMenu subMenu = menu.addSubMenu(0, MENU_MUSIC, 0, "�I������");
				subMenu.add(0, MENU_PLAY_MUSIC, 0, "����I������");
				subMenu.add(0, MENU_STOP_PLAYING_MUSIC, 1, "�����I������");
				menu.add(0, MENU_ABOUT, 1, "����o�ӵ{��...");
				menu.add(0, MENU_EXIT, 2, "����");
			}
		}
		else if (v == mTxtView) {
			menu.add(0, MENU_ABOUT, 1, "����o�ӵ{��...");
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		onOptionsItemSelected(item);

		return super.onContextItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		SubMenu subMenu = menu.addSubMenu(0, MENU_MUSIC, 0, "�I������")
				.setIcon(android.R.drawable.ic_media_ff);
		subMenu.add(0, MENU_PLAY_MUSIC, 0, "����I������");
		subMenu.add(0, MENU_STOP_PLAYING_MUSIC, 1, "�����I������");
		menu.add(0, MENU_ABOUT, 1, "����o�ӵ{��...")
			.setIcon(android.R.drawable.ic_dialog_info);
		menu.add(0, MENU_EXIT, 2, "����")
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
               .setTitle("����o�ӵ{��")
               .setMessage("���d�ҵ{��")
               .setCancelable(false)
               .setIcon(android.R.drawable.star_big_on)
               .setPositiveButton("�T�w",
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
