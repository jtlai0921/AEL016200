package com.android;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.support.v4.app.Fragment;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.os.Build;

public class MainActivity extends Activity {

	private static final String FILE_NAME = "file io.txt";
	
	private EditText mEdtIn,
	 			   mEdtFileContent;

	private Button mBtnAdd,
				   mBtnRead,
				   mBtnClear;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mEdtIn = (EditText)findViewById(R.id.edtIn);
		mEdtFileContent = (EditText)findViewById(R.id.edtFileContent);

		mBtnAdd = (Button)findViewById(R.id.btnAdd);
		mBtnRead = (Button)findViewById(R.id.btnRead);
		mBtnClear = (Button)findViewById(R.id.btnClear);

		mBtnAdd.setOnClickListener(btnAddOnClick);
		mBtnRead.setOnClickListener(btnReadOnClick);
		mBtnClear.setOnClickListener(btnClearOnClick);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private View.OnClickListener btnAddOnClick = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
	        FileOutputStream fileOut = null;
	        BufferedOutputStream bufFileOut = null;
	        
			try {
				fileOut = openFileOutput(FILE_NAME, MODE_APPEND);
				bufFileOut = new BufferedOutputStream(fileOut);
				bufFileOut.write(mEdtIn.getText().toString().getBytes());
				bufFileOut.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    };

    private View.OnClickListener btnReadOnClick = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
	        FileInputStream fileIn = null;
	        BufferedInputStream bufFileIn = null;

			try {
				fileIn = openFileInput("file io.txt");
				bufFileIn = new BufferedInputStream(fileIn);
				
				byte[] bufBytes = new byte[10];
				
				mEdtFileContent.setText("");
				
				do {
					int c = bufFileIn.read(bufBytes);
					
					if (c == -1)
						break;
					else
						mEdtFileContent.append(new String(bufBytes), 0, c);
				} while (true);
				
				bufFileIn.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    };

    private View.OnClickListener btnClearOnClick = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
	        FileOutputStream fileOut = null;
	        
			try {
				fileOut = openFileOutput(FILE_NAME, MODE_PRIVATE);
				fileOut.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    };

}
