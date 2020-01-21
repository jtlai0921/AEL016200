package com.android;

import java.util.Calendar;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	private Button mBtnShowProgressDlg;
    private Handler mHandler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mBtnShowProgressDlg = (Button) findViewById(R.id.btnShowProgressDlg);
		mBtnShowProgressDlg.setOnClickListener(btnShowProgressDlgOnClick);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

    private View.OnClickListener btnShowProgressDlgOnClick = new View.OnClickListener() {
		public void onClick(View v) {
			final ProgressDialog progressDlg = new ProgressDialog(MainActivity.this);
			progressDlg.setTitle("請稍等");
			progressDlg.setMessage("執行中...");
			progressDlg.setIcon(android.R.drawable.ic_dialog_info);
			progressDlg.setCancelable(false);
			progressDlg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			progressDlg.setMax(100);
			progressDlg.show();
			
			new Thread(new Runnable() {
				 public void run() {
				   	Calendar begin = Calendar.getInstance();
				    	do {
				    		Calendar now = Calendar.getInstance();
				    		final int iDiffSec = 60 * (now.get(Calendar.MINUTE) - begin.get(Calendar.MINUTE)) +
				    						now.get(Calendar.SECOND) - begin.get(Calendar.SECOND);
				
				    		if (iDiffSec * 2 > 100) {
				    			mHandler.post(new Runnable() {
					                public void run() {
					                	progressDlg.setProgress(100);
					                }
					            });
				    			
				    			break;
				    		}
				    		
				    		mHandler.post(new Runnable() {
				                public void run() {
				                	progressDlg.setProgress(iDiffSec * 2);
				                }
				            });
				    		
				    		if (iDiffSec * 4 < 100)
				    			mHandler.post(new Runnable() {
					                public void run() {
					                	progressDlg.setSecondaryProgress(iDiffSec * 4);
					                }
					            });
				    		else
				    			mHandler.post(new Runnable() {
					                public void run() {
					                	progressDlg.setSecondaryProgress(100);
					                }
					            });
				    	} while (true);
				    	
				    	progressDlg.dismiss();
				   }
			}).start();
		}
	};

}
