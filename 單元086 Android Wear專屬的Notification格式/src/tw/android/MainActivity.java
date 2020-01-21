package tw.android;

import java.io.File;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.BigTextStyle;
import android.support.v4.app.NotificationCompat.InboxStyle;
import android.support.v4.app.NotificationManagerCompat;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static final int NOTI_ID = 100;

	// 新增統計遊戲局數和輸贏的變數
	private int miCountSet = 0,
				    miCountPlayerWin = 0,
				    miCountComWin = 0,
				    miCountDraw = 0;

	private Button mBtnShowResult;

	private TextView mTxtResult;
	private ImageView mImgViewComPlay;
    private ImageButton mImgBtnScissors, mImgBtnStone, mImgBtnPaper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mImgViewComPlay = (ImageView)findViewById(R.id.imgViewComPlay);
        mTxtResult = (TextView)findViewById(R.id.txtResult);
        mImgBtnScissors = (ImageButton)findViewById(R.id.imgBtnScissors);
        mImgBtnStone = (ImageButton)findViewById(R.id.imgBtnStone);
        mImgBtnPaper = (ImageButton)findViewById(R.id.imgBtnPaper);

        mImgBtnScissors.setOnClickListener(imgBtnScissorsOnClick);
        mImgBtnStone.setOnClickListener(imgBtnStoneOnClick);
        mImgBtnPaper.setOnClickListener(imgBtnPaperOnClick);

        mBtnShowResult = (Button)findViewById(R.id.btnShowResult);
        mBtnShowResult.setOnClickListener(btnShowResultOnClick);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
    	((NotificationManager) getSystemService(NOTIFICATION_SERVICE))
	   	    	.cancel(NOTI_ID);

	   	super.onDestroy();
	}

	private View.OnClickListener imgBtnScissorsOnClick = new View.OnClickListener() {
		public void onClick(View v) {
			// 決定電腦出拳.
			int iComPlay = (int)(Math.random()*3 + 1);

			miCountSet++;

			// 1 – 剪刀, 2 – 石頭, 3 – 布.
			if (iComPlay == 1) {
				mImgViewComPlay.setImageResource(R.drawable.scissors);
				mTxtResult.setText(getString(R.string.result) +
								  getString(R.string.player_draw));
				miCountDraw++;
				showNotification("已經平手" + Integer.toString(miCountDraw) + "局");
			} else if (iComPlay == 2) {
				mImgViewComPlay.setImageResource(R.drawable.stone);
				mTxtResult.setText(getString(R.string.result) +
						  getString(R.string.player_lose));
				miCountComWin++;
				showNotification("已經輸" + Integer.toString(miCountComWin) + "局");
			} else {
				mImgViewComPlay.setImageResource(R.drawable.paper);
				mTxtResult.setText(getString(R.string.result) +
						  getString(R.string.player_win));
				miCountPlayerWin++;
				showNotification("已經贏" + Integer.toString(miCountPlayerWin) + "局");
			}
		}
	};
		
    private View.OnClickListener imgBtnStoneOnClick = new View.OnClickListener() {
		public void onClick(View v) {
			// 決定電腦出拳.
			int iComPlay = (int)(Math.random()*3 + 1);

			miCountSet++;

			// 1 – 剪刀, 2 – 石頭, 3 – 布.
			if (iComPlay == 1) {
				mImgViewComPlay.setImageResource(R.drawable.scissors);
				mTxtResult.setText(getString(R.string.result) +
						  getString(R.string.player_win));
				miCountPlayerWin++;
				showNotification("已經贏" + Integer.toString(miCountPlayerWin) + "局");
			} else if (iComPlay == 2) {
				mImgViewComPlay.setImageResource(R.drawable.stone);
				mTxtResult.setText(getString(R.string.result) +
						  getString(R.string.player_draw));
				miCountDraw++;
				showNotification("已經平手" + Integer.toString(miCountDraw) + "局");
			}
			else {
				mImgViewComPlay.setImageResource(R.drawable.paper);
				mTxtResult.setText(getString(R.string.result) +
						  getString(R.string.player_lose));
				miCountComWin++;
				showNotification("已經輸" + Integer.toString(miCountComWin) + "局");
			}
		}
	};

    private View.OnClickListener imgBtnPaperOnClick = new View.OnClickListener() {
		public void onClick(View v) {
			// 決定電腦出拳.
			int iComPlay = (int)(Math.random()*3 + 1);

			miCountSet++;

			// 1 – 剪刀, 2 – 石頭, 3 – 布.
			if (iComPlay == 1) {
				mImgViewComPlay.setImageResource(R.drawable.scissors);
				mTxtResult.setText(getString(R.string.result) +
						  getString(R.string.player_lose));
				miCountComWin++;
				showNotification("已經輸" + Integer.toString(miCountComWin) + "局");
			} else if (iComPlay == 2) {
				mImgViewComPlay.setImageResource(R.drawable.stone);
				mTxtResult.setText(getString(R.string.result) +
						  getString(R.string.player_win));
				miCountPlayerWin++;
				showNotification("已經贏" + Integer.toString(miCountPlayerWin) + "局");
			} else {
				mImgViewComPlay.setImageResource(R.drawable.paper);
				mTxtResult.setText(getString(R.string.result) +
						  getString(R.string.player_draw));
				miCountDraw++;
				showNotification("已經平手" + Integer.toString(miCountDraw) + "局");
			}
		}
	};

	private View.OnClickListener btnShowResultOnClick = new View.OnClickListener() {
		public void onClick(View v) {
			Intent it = new Intent();
			it.setClass(MainActivity.this, GameResultActivity.class);
			
			Bundle bundle = new Bundle();
			bundle.putInt("KEY_COUNT_SET", miCountSet);
			bundle.putInt("KEY_COUNT_PLAYER_WIN", miCountPlayerWin);
			bundle.putInt("KEY_COUNT_COM_WIN", miCountComWin);
			bundle.putInt("KEY_COUNT_DRAW", miCountDraw);
			it.putExtras(bundle);
			
			startActivity(it);
		}
	 };

	 private void showNotification(String sMsg) {
		Intent it = new Intent(getApplicationContext(), GameResultActivity.class);
	    it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    Bundle bundle = new Bundle();
	    bundle.putInt("KEY_COUNT_SET", miCountSet);
	    bundle.putInt("KEY_COUNT_PLAYER_WIN", miCountPlayerWin);
	    bundle.putInt("KEY_COUNT_COM_WIN", miCountComWin);
	    bundle.putInt("KEY_COUNT_DRAW", miCountDraw);
	    it.putExtras(bundle);

    	PendingIntent penIt = PendingIntent.getActivity(getApplicationContext(), 
    			0, it, PendingIntent.FLAG_CANCEL_CURRENT);

    	// 建立Action的Intent。
    	Uri uri = Uri.parse("http://developer.android.com/");
		Intent itOpenWebsite = new Intent(Intent.ACTION_VIEW, uri);
		
		// 建立Action的PendingIntent。
		PendingIntent penItOpenWebsite = PendingIntent.getActivity(getApplicationContext(), 
    			0, itOpenWebsite, PendingIntent.FLAG_CANCEL_CURRENT);

		// Android Wear的Action按鈕使用的Intent和action。
		Intent itPhoneCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "123456789"));
		PendingIntent penItPhoneCall = PendingIntent.getActivity(getApplicationContext(), 
    			0, itPhoneCall, PendingIntent.FLAG_CANCEL_CURRENT);
		
		// Android Wear的Action按鈕使用的action。
		NotificationCompat.Action action = 
				new NotificationCompat.Action.Builder(
						android.R.drawable.ic_menu_call, "打電話", penItPhoneCall)
				.build();
		
		NotificationCompat.WearableExtender wearableExtender = 
				new NotificationCompat.WearableExtender();
		wearableExtender.addAction(action);
		wearableExtender.setBackground(BitmapFactory.decodeResource(getResources(), R.drawable.scissors));
		
		Notification secondNotiMsg = new NotificationCompat.Builder(this)
	        .setContentTitle("第二頁")
	        .setContentText("第二頁的說明。")
	        .build();
		wearableExtender.addPage(secondNotiMsg);

		// 利用BigTextStyle或是InboxStyle顯示更多說明。
		BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
		bigTextStyle.setBigContentTitle("BigTextStyle的訊息標題");
		bigTextStyle.bigText("BigTextStyle的訊息內容。");
		bigTextStyle.setSummaryText("BigTextStyle的訊息摘要");
		
		InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
		inboxStyle.setBigContentTitle("InboxStyle的訊息標題");
		inboxStyle.addLine("InboxStyle的訊息內容 - 第1行。");
		inboxStyle.addLine("InboxStyle的訊息內容 - 第2行。");
		inboxStyle.setSummaryText("InboxStyle的訊息摘要");
		
		// 必須設定PRIORITY_MAX才會顯示action button。
    	Notification noti = new NotificationCompat.Builder(this)
		        .setSmallIcon(android.R.drawable.btn_star_big_on)
		        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.paper))
		        .setTicker("訊息提示文字")
		        .setContentTitle("訊息標題")		// 如果套用BigTextStyle或是InboxStyle，可以省略。
		        .setContentText("訊息內容")		// 如果套用BigTextStyle或是InboxStyle，可以省略。
//		        .setContentIntent(penIt)
//		        .setPriority(NotificationCompat.PRIORITY_MAX)
//		        .setStyle(inboxStyle)
//		        .addAction(android.R.drawable.ic_menu_share, "開啟網頁", penItOpenWebsite)
		        .extend(wearableExtender)	// 設定Android Wear裝置專用的訊息內容。
		        .build();

	    NotificationManagerCompat notiMgr =
	         NotificationManagerCompat.from(getApplicationContext());

		notiMgr.notify(NOTI_ID, noti);	// NOTI_ID 是程式中定義的常數。
	}

}
