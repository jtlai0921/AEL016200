package com.android;

import com.android.providers.FriendDbOpenHelper;
import com.android.providers.FriendsContentProvider;

import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends Activity {

	private static ContentResolver mContRes;

	private EditText mEdtName,
					 mEdtSex,
					 mEdtAddr,
					 mEdtList;
	
	private Button mBtnAdd,
				  mBtnQuery,
				  mBtnList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mContRes = getContentResolver();

		mEdtName = (EditText)findViewById(R.id.edtName);
		mEdtSex = (EditText)findViewById(R.id.edtSex);
		mEdtAddr = (EditText)findViewById(R.id.edtAddr);
		mEdtList = (EditText)findViewById(R.id.edtList);

		mBtnAdd = (Button)findViewById(R.id.btnAdd);
		mBtnQuery = (Button)findViewById(R.id.btnQuery);
		mBtnList = (Button)findViewById(R.id.btnList);

		mBtnAdd.setOnClickListener(btnAddOnClick);
		mBtnQuery.setOnClickListener(btnQueryOnClick);
		mBtnList.setOnClickListener(btnListOnClick);
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
	        
	        ContentValues newRow = new ContentValues();
	        newRow.put("name", mEdtName.getText().toString());
	        newRow.put("sex", mEdtSex.getText().toString());
	        newRow.put("address", mEdtAddr.getText().toString());
	        mContRes.insert(FriendsContentProvider.CONTENT_URI, newRow);
		}
    };

    private View.OnClickListener btnQueryOnClick = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			Cursor c = null;

			String[] projection = new String[]{"name", "sex", "address"};
			
			if (!mEdtName.getText().toString().equals("")) {
				c = mContRes.query(FriendsContentProvider.CONTENT_URI, projection,
						"name=" + "\"" + mEdtName.getText().toString() + "\"",
						null, null);
			} else if (!mEdtSex.getText().toString().equals("")) {
				c = mContRes.query(FriendsContentProvider.CONTENT_URI, projection,
						"sex=" + "\"" + mEdtSex.getText().toString() + "\"",
						null, null);
			} else if (!mEdtAddr.getText().toString().equals("")) {
				c = mContRes.query(FriendsContentProvider.CONTENT_URI, projection,
						"address=" + "\"" + mEdtAddr.getText().toString() + "\"",
						null, null);
			}
			
			if (c == null)
				return;

			if (c.getCount() == 0) {
				mEdtList.setText("");
				Toast.makeText(MainActivity.this, "沒有這筆資料", Toast.LENGTH_LONG)
					.show();
			} else {
				c.moveToFirst();
				mEdtList.setText(c.getString(0) + c.getString(1)  + c.getString(2));
				
				while (c.moveToNext())
					mEdtList.append("\n" + c.getString(0) + c.getString(1) + 
							c.getString(2));
			}
		}
    };

    private View.OnClickListener btnListOnClick = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String[] projection = new String[]{"name", "sex", "address"};
			
			Cursor c = mContRes.query(FriendsContentProvider.CONTENT_URI, projection,
					null, null, null);

			if (c == null)
				return;

			if (c.getCount() == 0) {
				mEdtList.setText("");
				Toast.makeText(MainActivity.this, "沒有資料", Toast.LENGTH_LONG)
					.show();
			}
			else {
				c.moveToFirst();
				mEdtList.setText(c.getString(0) + c.getString(1)  + c.getString(2));
				
				while (c.moveToNext())
					mEdtList.append("\n" + c.getString(0) + c.getString(1)  + 
							c.getString(2));
			}
		}
    };

}
