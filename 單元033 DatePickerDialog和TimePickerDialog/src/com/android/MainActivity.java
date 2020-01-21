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
			datePicDlg.setTitle("��ܤ��");
			datePicDlg.setMessage("�п�ܾA�X�z�����");
			datePicDlg.setIcon(android.R.drawable.ic_dialog_info);
			datePicDlg.setCancelable(false);
			datePicDlg.show();
		}
	};

	private DatePickerDialog.OnDateSetListener datePickerDlgOnDateSet = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet (DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			mTxtResult.setText("�A�]�w������O" +
							Integer.toString(year) + "�~" +
							Integer.toString(monthOfYear + 1) + "��" +
							Integer.toString(dayOfMonth) + "��");
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
			timePicDlg.setTitle("��ܮɶ�");
			timePicDlg.setMessage("�п�ܾA�X�z���ɶ�");
			timePicDlg.setIcon(android.R.drawable.ic_dialog_info);
			timePicDlg.setCancelable(false);
			timePicDlg.show();
		}
	};

	private TimePickerDialog.OnTimeSetListener timePickerDlgOnTimeSet = new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet (TimePicker  view, int hourOfDay, int minute) {
			mTxtResult.setText("�A�]�w���ɶ��O" +
							Integer.toString(hourOfDay) + "�I" +
							Integer.toString(minute) + "��");
		}
	};

}
