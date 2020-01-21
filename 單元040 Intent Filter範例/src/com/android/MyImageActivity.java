package com.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MyImageActivity extends Activity {

	private TextView mTxtResult;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_image);
        
        mTxtResult = (TextView)findViewById(R.id.txtResult);
		showResult();
    }

	private void showResult() {
        Intent it = getIntent();
        String sAct = it.getAction();
        String sScheme = it.getScheme();
        if (sScheme.equals("http")) {
        	String s = "�����쪺Intent����n�D\"�}�Һ���\"" + it.getData().toString();
        	mTxtResult.setText(s);
        } else if (sScheme.equals("tel")) {
        	String s = "�����쪺Intent����n�D\"�����q��\"" + it.getData().toString();
        	mTxtResult.setText(s);
        } else if (sScheme.equals("file")) {
        	if (sAct.equals("android.intent.action.VIEW")) {
	        	String s = "�����쪺Intent����n�D\"�˵�\"" + it.getData().toString();
	        	mTxtResult.setText(s);
	        } else if (sAct.equals("android.intent.action.EDIT")) {
	        	String s = "�����쪺Intent����n�D\"�s��\"" + it.getData().toString();
	        	mTxtResult.setText(s);
	        }
	    }
	}
}
