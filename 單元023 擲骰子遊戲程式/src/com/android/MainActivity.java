package com.android;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private ImageView mImgRollingDice;
	private TextView mTxtDiceResult;
	private Button mBtnRollDice;

	private Handler handler=new Handler() {

        public void handleMessage(Message msg) {
        	super.handleMessage(msg);

        	int iRand = (int)(Math.random()*6 + 1);

    		String s = getString(R.string.dice_result);
    		mTxtDiceResult.setText(s + iRand);
    		switch (iRand) {
    		case 1:
    			mImgRollingDice.setImageResource(R.drawable.dice01);
    			break;
    		case 2:
    			mImgRollingDice.setImageResource(R.drawable.dice02);
    			break;
    		case 3:
    			mImgRollingDice.setImageResource(R.drawable.dice03);
    			break;
    		case 4:
    			mImgRollingDice.setImageResource(R.drawable.dice04);
    			break;
    		case 5:
    			mImgRollingDice.setImageResource(R.drawable.dice05);
    			break;
    		case 6:
    			mImgRollingDice.setImageResource(R.drawable.dice06);
    			break;
    		}
        }
    };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

    	mImgRollingDice = (ImageView)findViewById(R.id.imgRollingDice);
    	mTxtDiceResult = (TextView)findViewById(R.id.txtDiceResult);
    	mBtnRollDice = (Button)findViewById(R.id.btnRollDice);

    	mBtnRollDice.setOnClickListener(btnRollDiceOnClick);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
    private View.OnClickListener btnRollDiceOnClick = new View.OnClickListener() {
		public void onClick(View v) {

			String s = getString(R.string.dice_result);
			mTxtDiceResult.setText(s);

			// 從程式資源中取得動畫檔，設定給ImageView物件，然後開始播放。
			Resources res = getResources();
			final AnimationDrawable animDraw =
					(AnimationDrawable) res.getDrawable(R.drawable.anim_roll_dice);
			mImgRollingDice.setImageDrawable(animDraw);
			animDraw.start();

			// 啟動background thread進行計時。
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						Thread.sleep(5000);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					animDraw.stop();
					handler.sendMessage(handler.obtainMessage());
				}
			}).start();
		}
	};

}
