package com.android;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	private Button mBtnAlertDlg,
		   			mBtnAlertDlgBuilder;
	private TextView mTxtResult;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTxtResult = (TextView)findViewById(R.id.txtResult);

		mBtnAlertDlg = (Button)findViewById(R.id.btnAlertDlg);
		mBtnAlertDlg.setOnClickListener(btnAlertDlgOnClick);

		mBtnAlertDlgBuilder = (Button)findViewById(R.id.btnAlertDlgBuilder);
		mBtnAlertDlgBuilder.setOnClickListener(btnAlertDlgBuilderOnClick);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private View.OnClickListener btnAlertDlgOnClick = new View.OnClickListener() {
		public void onClick(View v) {
			mTxtResult.setText("");
			MyAlertDialog myAltDlg = new MyAlertDialog(MainActivity.this);
			myAltDlg.setTitle("AlertDialog");
			myAltDlg.setMessage("AlertDialog的使用方式是要建立一個繼承它的class");
			myAltDlg.setIcon(android.R.drawable.ic_dialog_info);
			myAltDlg.setCancelable(false);
			myAltDlg.setButton(DialogInterface.BUTTON_POSITIVE, "是", altDlgPositiveBtnOnClk);
			myAltDlg.setButton(DialogInterface.BUTTON_NEGATIVE, "否", altDlgNegativeBtnOnClk);
			myAltDlg.setButton(DialogInterface.BUTTON_NEUTRAL, "取消", altDlgNeutralBtnOnClk);

			myAltDlg.show();			
		}
	};
	
	private  DialogInterface.OnClickListener altDlgPositiveBtnOnClk = new  DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			mTxtResult.setText("你啟動了AlertDialog而且按下了\"是\"按鈕");
		}
	};

	private  DialogInterface.OnClickListener altDlgNegativeBtnOnClk = new  DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			mTxtResult.setText("你啟動了AlertDialog而且按下了\"否\"按鈕");
		}
	};

	private  DialogInterface.OnClickListener altDlgNeutralBtnOnClk = new  DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			mTxtResult.setText("你啟動了AlertDialog而且按下了\"取消\"按鈕");
		}
	};

    private Button.OnClickListener btnAlertDlgBuilderOnClick = new Button.OnClickListener() {
		public void onClick(View v) {
			mTxtResult.setText("");
			AlertDialog.Builder altDlgBldr = new AlertDialog.Builder(MainActivity.this);
			altDlgBldr.setTitle("AlertDialog");
			altDlgBldr.setMessage("由AlertDialog.Builder產生");
			altDlgBldr.setIcon(android.R.drawable.ic_dialog_info);
			altDlgBldr.setCancelable(false);
			altDlgBldr.setPositiveButton("是",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						mTxtResult.setText("你啟動了AlertDialogBuilder而且按下了\"是\"按鈕");
					}
				});
			altDlgBldr.setNegativeButton("否",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						mTxtResult.setText("你啟動了AlertDialogBuilder而且按下了\"否\"按鈕");
					}
				});
			altDlgBldr.setNeutralButton("取消",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						mTxtResult.setText("你啟動了AlertDialogBuilder而且按下了\"取消\"按鈕");
					}
				});
			altDlgBldr.show();			
		}
	};

}
