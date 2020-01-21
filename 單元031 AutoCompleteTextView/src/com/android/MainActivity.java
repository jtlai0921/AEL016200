package com.android;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

public class MainActivity extends Activity {

	private Button mBtnAddAutoCompleteText,
		   			mBtnClearAutoCompleteText;
	private AutoCompleteTextView mAutoCompTextView;

	private ArrayAdapter<String> mAdapAutoCompText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mBtnAddAutoCompleteText = (Button) findViewById(R.id.btnAddAutoCompleteText);
        mBtnClearAutoCompleteText = (Button) findViewById(R.id.btnClearAutoCompleteText);
        mAutoCompTextView = (AutoCompleteTextView) findViewById(R.id.autoCompTextView);
    	
        mAdapAutoCompText = new ArrayAdapter<String>(
        		this, android.R.layout.simple_dropdown_item_1line);
    	
        mAutoCompTextView.setAdapter(mAdapAutoCompText);
    	
        mBtnAddAutoCompleteText.setOnClickListener(btnAddAutoCompleteTextOnClick);
        mBtnClearAutoCompleteText.setOnClickListener(btnClearAutoCompleteTextOnClick);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

    private Button.OnClickListener btnAddAutoCompleteTextOnClick = new Button.OnClickListener() {
		public void onClick(View v) {
			String s = mAutoCompTextView.getText().toString();
			mAdapAutoCompText.add(s);
			mAutoCompTextView.setText("");
		}
	};

    private Button.OnClickListener btnClearAutoCompleteTextOnClick = new Button.OnClickListener() {
		public void onClick(View v) {
			mAdapAutoCompText.clear();
		}
	};

}
