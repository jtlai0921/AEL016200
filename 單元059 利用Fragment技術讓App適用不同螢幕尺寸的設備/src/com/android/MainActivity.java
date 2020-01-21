package com.android;


import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity implements MainFragment.CallbackInterface {

	private MainFragment fragMain;
	private GameResultFragment fragGameResult;

	private boolean bUISettedOK = false;

	enum UIType {
		ONE_FRAME, TWO_FRAMES;
	}

	public UIType UITypeFlag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// 取得裝置螢幕大小的分類
		switch (getResources().getConfiguration().screenLayout & 
				Configuration.SCREENLAYOUT_SIZE_MASK) {
		case Configuration.SCREENLAYOUT_SIZE_SMALL:
		case Configuration.SCREENLAYOUT_SIZE_NORMAL:
		case Configuration.SCREENLAYOUT_SIZE_LARGE:
			UITypeFlag = UIType.ONE_FRAME;
			break;
		case Configuration.SCREENLAYOUT_SIZE_XLARGE:
			UITypeFlag = UIType.TWO_FRAMES;
			break;
		}

		fragMain = new MainFragment();
		fragGameResult = new GameResultFragment();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		FragmentTransaction fragTran = getFragmentManager().beginTransaction();
		fragTran.replace(R.id.frameLay1, fragMain, "Game");
		fragTran.replace(R.id.frameLay2, fragGameResult, "Game Result");
		fragTran.commit();

        if (bUISettedOK == false) {
			bUISettedOK = true;

			switch (UITypeFlag) {
			case ONE_FRAME:
				findViewById(R.id.frameLay1).setVisibility(View.VISIBLE);
				findViewById(R.id.frameLay2).setVisibility(View.GONE);
				break;
			case TWO_FRAMES:
				findViewById(R.id.frameLay1).setVisibility(View.VISIBLE);
				findViewById(R.id.frameLay2).setVisibility(View.VISIBLE);
				break;
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void updateGameResult(int iCountSet, int iCountPlayerWin,
			int iCountComWin, int iCountDraw) {
		// TODO Auto-generated method stub
		if (findViewById(R.id.frameLay2).isShown()) {
			fragGameResult.updateGameResult(iCountSet, iCountPlayerWin,
						iCountComWin, iCountDraw);
		}
	}

}
