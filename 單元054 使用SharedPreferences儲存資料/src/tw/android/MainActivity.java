package tw.android;

import android.os.Bundle;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final int NOTI_ID = 100;

	private Button mBtnSaveResult,
					 mBtnLoadResult,
					 mBtnClearResult;

	// �s�W�έp�C�����ƩM��Ĺ���ܼ�
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

        mBtnSaveResult = (Button)findViewById(R.id.btnSaveResult);
    	mBtnLoadResult = (Button)findViewById(R.id.btnLoadResult);
    	mBtnClearResult = (Button)findViewById(R.id.btnClearResult);

    	mBtnSaveResult.setOnClickListener(btnSaveResultOnClick);
    	mBtnLoadResult.setOnClickListener(btnLoadResultOnClick);
    	mBtnClearResult.setOnClickListener(btnClearResultOnClick);

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
			// �M�w�q���X��.
			int iComPlay = (int)(Math.random()*3 + 1);

			miCountSet++;

			// 1 �V �ŤM, 2 �V ���Y, 3 �V ��.
			if (iComPlay == 1) {
				mImgViewComPlay.setImageResource(R.drawable.scissors);
				mTxtResult.setText(getString(R.string.result) +
								  getString(R.string.player_draw));
				miCountDraw++;
				showNotification("�w�g����" + Integer.toString(miCountDraw) + "��");
			} else if (iComPlay == 2) {
				mImgViewComPlay.setImageResource(R.drawable.stone);
				mTxtResult.setText(getString(R.string.result) +
						  getString(R.string.player_lose));
				miCountComWin++;
				showNotification("�w�g��" + Integer.toString(miCountComWin) + "��");
			} else {
				mImgViewComPlay.setImageResource(R.drawable.paper);
				mTxtResult.setText(getString(R.string.result) +
						  getString(R.string.player_win));
				miCountPlayerWin++;
				showNotification("�w�gĹ" + Integer.toString(miCountPlayerWin) + "��");
			}
		}
	};
		
    private View.OnClickListener imgBtnStoneOnClick = new View.OnClickListener() {
		public void onClick(View v) {
			// �M�w�q���X��.
			int iComPlay = (int)(Math.random()*3 + 1);

			miCountSet++;

			// 1 �V �ŤM, 2 �V ���Y, 3 �V ��.
			if (iComPlay == 1) {
				mImgViewComPlay.setImageResource(R.drawable.scissors);
				mTxtResult.setText(getString(R.string.result) +
						  getString(R.string.player_win));
				miCountPlayerWin++;
				showNotification("�w�gĹ" + Integer.toString(miCountPlayerWin) + "��");
			} else if (iComPlay == 2) {
				mImgViewComPlay.setImageResource(R.drawable.stone);
				mTxtResult.setText(getString(R.string.result) +
						  getString(R.string.player_draw));
				miCountDraw++;
				showNotification("�w�g����" + Integer.toString(miCountDraw) + "��");
			}
			else {
				mImgViewComPlay.setImageResource(R.drawable.paper);
				mTxtResult.setText(getString(R.string.result) +
						  getString(R.string.player_lose));
				miCountComWin++;
				showNotification("�w�g��" + Integer.toString(miCountComWin) + "��");
			}
		}
	};

    private View.OnClickListener imgBtnPaperOnClick = new View.OnClickListener() {
		public void onClick(View v) {
			// �M�w�q���X��.
			int iComPlay = (int)(Math.random()*3 + 1);

			miCountSet++;

			// 1 �V �ŤM, 2 �V ���Y, 3 �V ��.
			if (iComPlay == 1) {
				mImgViewComPlay.setImageResource(R.drawable.scissors);
				mTxtResult.setText(getString(R.string.result) +
						  getString(R.string.player_lose));
				miCountComWin++;
				showNotification("�w�g��" + Integer.toString(miCountComWin) + "��");
			} else if (iComPlay == 2) {
				mImgViewComPlay.setImageResource(R.drawable.stone);
				mTxtResult.setText(getString(R.string.result) +
						  getString(R.string.player_win));
				miCountPlayerWin++;
				showNotification("�w�gĹ" + Integer.toString(miCountPlayerWin) + "��");
			} else {
				mImgViewComPlay.setImageResource(R.drawable.paper);
				mTxtResult.setText(getString(R.string.result) +
						  getString(R.string.player_draw));
				miCountDraw++;
				showNotification("�w�g����" + Integer.toString(miCountDraw) + "��");
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
    			0, it, PendingIntent.FLAG_UPDATE_CURRENT);

    	Notification noti = new Notification.Builder(this)
		        .setSmallIcon(android.R.drawable.btn_star_big_on)
		        .setTicker(sMsg)
		        .setContentTitle(getString(R.string.app_name))
		        .setContentText(sMsg)
		        .setContentIntent(penIt)
		        .build();

	    NotificationManager notiMgr =
	         (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	    notiMgr.cancel(NOTI_ID);
		notiMgr.notify(NOTI_ID, noti);
	}

	 private View.OnClickListener btnSaveResultOnClick = new View.OnClickListener() {
			public void onClick(View v) {
				SharedPreferences gameResultData = 
						getSharedPreferences("GAME_RESULT", 0);
				
				gameResultData.edit()
					.putInt("COUNT_SET", miCountSet)
					.putInt("COUNT_PLAYER_WIN", miCountPlayerWin)
					.putInt("COUNT_COM_WIN", miCountComWin)
					.putInt("COUNT_DRAW", miCountDraw)
					.commit();
				
				Toast.makeText(MainActivity.this, "�x�s����", Toast.LENGTH_LONG)
				.show();
			}
		};
		
		private View.OnClickListener btnLoadResultOnClick = new View.OnClickListener() {
			public void onClick(View v) {
				SharedPreferences gameResultData = 
						getSharedPreferences("GAME_RESULT", 0);
				
				miCountSet = gameResultData.getInt("COUNT_SET", 0);
				miCountPlayerWin = gameResultData.getInt("COUNT_PLAYER_WIN", 0);
				miCountComWin = gameResultData.getInt("COUNT_COM_WIN", 0);
				miCountDraw = gameResultData.getInt("COUNT_DRAW", 0);
				
				Toast.makeText(MainActivity.this, "���J����", Toast.LENGTH_LONG)
				.show();
			}
		};
		
		private View.OnClickListener btnClearResultOnClick = new View.OnClickListener() {
			public void onClick(View v) {
				SharedPreferences gameResultData = 
						getSharedPreferences("GAME_RESULT", 0);
				
				gameResultData.edit()
					.clear()
					.commit();
				
				Toast.makeText(MainActivity.this, "�M������", Toast.LENGTH_LONG)
				.show();
			}
		};

}
