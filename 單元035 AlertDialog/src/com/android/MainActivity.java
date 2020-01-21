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
			myAltDlg.setMessage("AlertDialog���ϥΤ覡�O�n�إߤ@���~�ӥ���class");
			myAltDlg.setIcon(android.R.drawable.ic_dialog_info);
			myAltDlg.setCancelable(false);
			myAltDlg.setButton(DialogInterface.BUTTON_POSITIVE, "�O", altDlgPositiveBtnOnClk);
			myAltDlg.setButton(DialogInterface.BUTTON_NEGATIVE, "�_", altDlgNegativeBtnOnClk);
			myAltDlg.setButton(DialogInterface.BUTTON_NEUTRAL, "����", altDlgNeutralBtnOnClk);

			myAltDlg.show();			
		}
	};
	
	private  DialogInterface.OnClickListener altDlgPositiveBtnOnClk = new  DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			mTxtResult.setText("�A�ҰʤFAlertDialog�ӥB���U�F\"�O\"���s");
		}
	};

	private  DialogInterface.OnClickListener altDlgNegativeBtnOnClk = new  DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			mTxtResult.setText("�A�ҰʤFAlertDialog�ӥB���U�F\"�_\"���s");
		}
	};

	private  DialogInterface.OnClickListener altDlgNeutralBtnOnClk = new  DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			mTxtResult.setText("�A�ҰʤFAlertDialog�ӥB���U�F\"����\"���s");
		}
	};

    private Button.OnClickListener btnAlertDlgBuilderOnClick = new Button.OnClickListener() {
		public void onClick(View v) {
			mTxtResult.setText("");
			AlertDialog.Builder altDlgBldr = new AlertDialog.Builder(MainActivity.this);
			altDlgBldr.setTitle("AlertDialog");
			altDlgBldr.setMessage("��AlertDialog.Builder����");
			altDlgBldr.setIcon(android.R.drawable.ic_dialog_info);
			altDlgBldr.setCancelable(false);
			altDlgBldr.setPositiveButton("�O",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						mTxtResult.setText("�A�ҰʤFAlertDialogBuilder�ӥB���U�F\"�O\"���s");
					}
				});
			altDlgBldr.setNegativeButton("�_",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						mTxtResult.setText("�A�ҰʤFAlertDialogBuilder�ӥB���U�F\"�_\"���s");
					}
				});
			altDlgBldr.setNeutralButton("����",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						mTxtResult.setText("�A�ҰʤFAlertDialogBuilder�ӥB���U�F\"����\"���s");
					}
				});
			altDlgBldr.show();			
		}
	};

}
