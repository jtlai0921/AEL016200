package com.android;

import java.util.Calendar;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

public class MainActivity extends Activity {

	private Button mBtnTimePickerDlg,
					mBtnDatePickerDlg;

	private TextView mTxtResult;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mBtnTimePickerDlg = (Button) findViewById(R.id.btnTimePickerDlg);
		mBtnDatePickerDlg = (Button) findViewById(R.id.btnDatePickerDlg);
		mTxtResult = (TextView) findViewById(R.id.txtResult);

		mBtnTimePickerDlg.setOnClickListener(btnTimePickerDlgOnClick);
		mBtnDatePickerDlg.setOnClickListener(btnDatePickerDlgOnClick);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

    private Button.OnClickListener btnDatePickerDlgOnClick = new Button.OnClickListener() {
		public void onClick(View v) {
			mTxtResult.setText("");

			Calendar now = Calendar.getInstance();

			DatePickerDialog datePicDlg = new DatePickerDialog(MainActivity.this,
							datePickerDlgOnDateSet,
							now.get(Calendar.YEAR),
							now.get(Calendar.MONTH),
							now.get(Calendar.DAY_OF_MONTH));
			datePicDlg.setTitle("選擇日期");
			datePicDlg.setMessage("請選擇適合您的日期");
			datePicDlg.setIcon(android.R.drawable.ic_dialog_info);
			datePicDlg.setCancelable(false);
			datePicDlg.show();
		}
	};

	private DatePickerDialog.OnDateSetListener datePickerDlgOnDateSet = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet (DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			mTxtResult.setText("你設定的日期是" +
							Integer.toString(year) + "年" +
							Integer.toString(monthOfYear + 1) + "月" +
							Integer.toString(dayOfMonth) + "日");
		}
	};

    private View.OnClickListener btnTimePickerDlgOnClick = new View.OnClickListener() {
		public void onClick(View v) {
			mTxtResult.setText("");

			Calendar now = Calendar.getInstance();

			TimePickerDialog timePicDlg = new TimePickerDialog(MainActivity.this,
							timePickerDlgOnTimeSet,
							now.get(Calendar.HOUR_OF_DAY),
							now.get(Calendar.MINUTE),
							true);
			timePicDlg.setTitle("選擇時間");
			timePicDlg.setMessage("請選擇適合您的時間");
			timePicDlg.setIcon(android.R.drawable.ic_dialog_info);
			timePicDlg.setCancelable(false);
			timePicDlg.show();
		}
	};

	private TimePickerDialog.OnTimeSetListener timePickerDlgOnTimeSet = new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet (TimePicker  view, int hourOfDay, int minute) {
			mTxtResult.setText("你設定的時間是" +
							Integer.toString(hourOfDay) + "點" +
							Integer.toString(minute) + "分");
		}
	};

}
