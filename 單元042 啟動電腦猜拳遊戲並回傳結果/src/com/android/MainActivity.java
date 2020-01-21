package com.android;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	final private int LAUNCH_GAME = 0;
	private TextView mTxtResult;
	private Button mBtnLaunchGame;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mBtnLaunchGame = (Button) findViewById(R.id.btnLaunchGame);
		mBtnLaunchGame.setOnClickListener(btnLaunchGameOnClick);
		
		mTxtResult = (TextView)findViewById(R.id.txtResult);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private View.OnClickListener btnLaunchGameOnClick = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent it = new Intent();
			it.setClass(MainActivity.this, GameActivity.class);
			startActivityForResult(it, LAUNCH_GAME);
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode != LAUNCH_GAME)
    		return;
    	
    	switch (resultCode) {
    	case RESULT_OK:
    		Bundle bundle = data.getExtras();
    		
    		int iCountSet = bundle.getInt("KEY_COUNT_SET");
    		int iCountPlayerWin = bundle.getInt("KEY_COUNT_PLAYER_WIN");
    		int iCountComWin = bundle.getInt("KEY_COUNT_COM_WIN");
    		int iCountDraw = bundle.getInt("KEY_COUNT_DRAW");
    		
    		String s = "遊戲結果：你總共玩了" + iCountSet +
    				   "局, 贏了" + iCountPlayerWin +
    				   "局, 輸了" + iCountComWin +
    				   "局, 平手" + iCountDraw + "局";
    		mTxtResult.setText(s);
    		
    		break;
    	case RESULT_CANCELED:
    		mTxtResult.setText("你選擇取消遊戲。");
    	}
	}

}
