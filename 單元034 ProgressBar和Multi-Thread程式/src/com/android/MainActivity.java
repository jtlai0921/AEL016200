package com.android;

import java.util.Calendar;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.Menu;
import android.widget.ProgressBar;

public class MainActivity extends Activity {

	private Handler mHandler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);

        new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Calendar begin = Calendar.getInstance();
		    	do {
		    		Calendar now = Calendar.getInstance();
		    		final int iDiffSec = 60 * (now.get(Calendar.MINUTE) - begin.get(Calendar.MINUTE)) +
		    						now.get(Calendar.SECOND) - begin.get(Calendar.SECOND);
		    	
		    		if (iDiffSec * 2 > 100) {
		    			mHandler.post(new Runnable() {
			                public void run() {
			                	progressBar.setProgress(100);
			                }
			            });
		    			
		    			break;
		    		}
		    		
		    		mHandler.post(new Runnable() {
		                public void run() {
		                	progressBar.setProgress(iDiffSec * 2);
		                }
		            });
		    		
		    		if (iDiffSec * 4 < 100)
		    			mHandler.post(new Runnable() {
			                public void run() {
			                	progressBar.setSecondaryProgress(iDiffSec * 4);
			                }
			            });
		    		else
		    			mHandler.post(new Runnable() {
			                public void run() {
			                	progressBar.setSecondaryProgress(100);
			                }
			            });
		    	} while (true);
			}
        	
        }).start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
