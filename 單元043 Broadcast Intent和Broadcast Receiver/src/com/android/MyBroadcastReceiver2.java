package com.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyBroadcastReceiver2 extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String sender = intent.getStringExtra("sender_name");
		Toast.makeText(context, "BroadcastReceiver2����" + sender + "�o�e��Broadcase�T��", 
				Toast.LENGTH_LONG).show();
	}

}
