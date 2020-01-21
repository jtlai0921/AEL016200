package com.android;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	private Button mBtnLoginDlg;
	private TextView mTxtResult;
	private Dialog mDlgLogin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mBtnLoginDlg = (Button) findViewById(R.id.btnLoginDlg);
		mTxtResult = (TextView) findViewById(R.id.txtResult);

		mBtnLoginDlg.setOnClickListener(btnLoginDlgOnClick);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private View.OnClickListener btnLoginDlgOnClick = new View.OnClickListener() {
		public void onClick(View v) {
			mTxtResult.setText("");
			
			mDlgLogin = new Dialog(MainActivity.this);
			mDlgLogin.setTitle("�n�J�t��");
			mDlgLogin.setCancelable(false);
			mDlgLogin.setContentView(R.layout.dlg_login);
			Button loginBtnOK = (Button) mDlgLogin.findViewById(R.id.btnOK);
			Button loginBtnCancel = (Button) mDlgLogin.findViewById(R.id.btnCancel);
			loginBtnOK.setOnClickListener(loginDlgBtnOKOnClick);
			loginBtnCancel.setOnClickListener(loginDlgBtnCancelOnClick);
			mDlgLogin.show();			
		}
	};
	
    private View.OnClickListener loginDlgBtnOKOnClick = new View.OnClickListener() {
		public void onClick(View v) {
			EditText edtUserName = (EditText) mDlgLogin.findViewById(R.id.edtUserName);
			EditText edtPassword = (EditText) mDlgLogin.findViewById(R.id.edtPassword);

			mTxtResult.setText("�A��J���ϥΪ̦W�١G" + edtUserName.getText().toString() +
								"�A�K�X�G" + edtPassword.getText().toString());
			mDlgLogin.cancel();
		}
	};
	
    private View.OnClickListener loginDlgBtnCancelOnClick = new View.OnClickListener() {
		public void onClick(View v) {
			mTxtResult.setText("�A���U\"����\"���s�C");
			mDlgLogin.cancel();
		}
	};

}
