package com.android;

import com.android.MainFragment.GameResultType;

import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.Menu;

public class MainActivity extends Activity implements MainFragment.CallbackInterface {

	private final static String TAG = "Result";
	private int mTagCount = 0;
	public MainFragment.GameResultType mGameResultType;
	public Fragment fragResult;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
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
		if (findViewById(R.id.frameLay).isShown()) {
	        switch (mGameResultType) {
			case TYPE_1:
				((GameResultFragment) fragResult).updateGameResult(iCountSet, iCountPlayerWin,
						iCountComWin, iCountDraw);
				break;
			case TYPE_2:
				((GameResultFragment2) fragResult).updateGameResult(iCountSet, iCountPlayerWin,
						iCountComWin, iCountDraw);
				break;
			}
		}
	}

	@Override
	public void enableGameResult(GameResultType type) {
		// TODO Auto-generated method stub
		FragmentTransaction fragTran;
		String sFragTag;

		switch (type) {
		case TYPE_1:
			GameResultFragment frag = new GameResultFragment();
	        fragTran = getFragmentManager().beginTransaction();
	        mTagCount++;
	        sFragTag = TAG + new Integer(mTagCount).toString();
	        fragTran.replace(R.id.frameLay, frag, sFragTag);
	        fragTran.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
	        fragTran.addToBackStack(null);
	        fragTran.commit();
	          break;
		case TYPE_2:
			GameResultFragment2 frag2 = new GameResultFragment2();
	        fragTran = getFragmentManager().beginTransaction();
	        mTagCount++;
	        sFragTag = TAG + new Integer(mTagCount).toString();
	        fragTran.replace(R.id.frameLay, frag2, sFragTag);
	        fragTran.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
	        fragTran.addToBackStack(null);
	        fragTran.commit();
	        break;
		case TURN_OFF:
	        FragmentManager fragMgr = getFragmentManager();
	        sFragTag = TAG + new Integer(mTagCount).toString();
	    	Fragment fragGameResult = (Fragment)fragMgr.findFragmentByTag(sFragTag);
    		fragTran = fragMgr.beginTransaction();
	        fragTran.remove(fragGameResult);
	        fragTran.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
	        fragTran.addToBackStack(null);
	        fragTran.commit();
			break;
		}
	}

}
