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
import android.support.v4.app.RemoteInput;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static final int NOTI_ID = 100;
	public static final String KEY_OF_REPLY_FROM_ANDROID_WEAR = "key of reply from android wear";

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
    			0, it, PendingIntent.FLAG_CANCEL_CURRENT);

    	// ���o�w�]����r�^�вM��C
    	String[] sArrAndroidWearReplyChoices = getResources().getStringArray(R.array.android_wear_reply_choices);

    	// �إ�RemoteInput������Android Wear�˸m��Notitication�T���i�H�����y����J�C
    	RemoteInput remoteInput = new RemoteInput.Builder(KEY_OF_REPLY_FROM_ANDROID_WEAR)
    			.setLabel("�˵��C�����G�H")
//    			.setAllowFreeFormInput(false)				// �����y�����ѥ\��C
//    			.setChoices(sArrAndroidWearReplyChoices)	// �]�w���C
    			.build();

		// Android Wear��Action���s�ϥΪ�action�C
		NotificationCompat.Action action = 
				new NotificationCompat.Action.Builder(
						android.R.drawable.ic_dialog_info, "�^��", penIt)
					.addRemoteInput(remoteInput)
					.build();

		NotificationCompat.WearableExtender wearableExtender = 
				new NotificationCompat.WearableExtender();
		wearableExtender.addAction(action);

		// �����]�wPRIORITY_MAX�~�|���action button�C
    	Notification noti = new NotificationCompat.Builder(this)
		        .setSmallIcon(android.R.drawable.btn_star_big_on)
		        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.paper))
		        .setTicker(sMsg)
		        .setContentTitle(getString(R.string.app_name))
		        .setContentText(sMsg)
		        .extend(wearableExtender)	// �]�wAndroid Wear�˸m�M�Ϊ��T�����e�C
		        .build();

	    NotificationManagerCompat notiMgr =
	         NotificationManagerCompat.from(getApplicationContext());

		notiMgr.notify(NOTI_ID, noti);	// NOTI_ID �O�{�����w�q���`�ơC
	}

}
