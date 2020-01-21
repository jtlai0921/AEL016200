package com.android;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

public class MainActivity extends Activity {

	private DatePicker mDatePicker;
	private TimePicker mTimePicker;
	private TextView mTxtResult;
	private Button mBtnOK;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        mDatePicker = (DatePicker) findViewById(R.id.datePicker);
        mTimePicker = (TimePicker) findViewById(R.id.timePicker);
        mTxtResult = (TextView) findViewById(R.id.txtResult);
        mBtnOK = (Button) findViewById(R.id.btnOK);

        mBtnOK.setOnClickListener(btnOKOnClick);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
    
    private Button.OnClickListener btnOKOnClick = new Button.OnClickListener() {
		public void onClick(View v) {
			String s = getString(R.string.result);
			mTxtResult.setText(s + mDatePicker.getYear() + "年" +
								  (mDatePicker.getMonth()+1) + "月 " +
								  mDatePicker.getDayOfMonth() + "日" +
								  mTimePicker.getCurrentHour() + "點" +
								  mTimePicker.getCurrentMinute() + "分");
		}
	};

}
