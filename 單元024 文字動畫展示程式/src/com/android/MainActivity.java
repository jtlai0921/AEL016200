package com.android;

import android.os.Bundle;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	private LinearLayout mLinLayRoot;
	private TextView mTxtDemo;
	private Button mBtnDrop,
				   mBtnTransparent,
				   mBtnRotate;
	private float y, yEnd;
	private boolean mIsFallingFirst = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

    	mLinLayRoot = (LinearLayout)findViewById(R.id.linLayRoot);
    	mTxtDemo = (TextView)findViewById(R.id.txtDemo);
    	mBtnDrop = (Button)findViewById(R.id.btnDrop);
    	mBtnTransparent = (Button)findViewById(R.id.btnTransparent);
    	mBtnRotate = (Button)findViewById(R.id.btnRotate);

    	mBtnDrop.setOnClickListener(btnDropOnClick);
    	mBtnTransparent.setOnClickListener(btnTransparentOnClick);
    	mBtnRotate.setOnClickListener(btnRotateOnClick);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

    private View.OnClickListener btnRotateOnClick = new View.OnClickListener() {
		public void onClick(View v) {
	    	ObjectAnimator animTxtRotate = 
	    		ObjectAnimator.ofFloat(mTxtDemo, "rotation", 0, 360);
	    	animTxtRotate.setDuration(3000);
	    	animTxtRotate.setRepeatCount(1);
	    	animTxtRotate.setRepeatMode(ObjectAnimator.REVERSE);
	    	animTxtRotate.setInterpolator(new  AccelerateDecelerateInterpolator());
	    	animTxtRotate.start();
		}
	};

    private View.OnClickListener btnTransparentOnClick = new View.OnClickListener() {
		public void onClick(View v) {
	    	ObjectAnimator animTxtAlpha = 
	    		ObjectAnimator.ofFloat(mTxtDemo, "alpha", 1, 0);
	    	animTxtAlpha.setDuration(2000);
	    	animTxtAlpha.setRepeatCount(1);
	    	animTxtAlpha.setRepeatMode(ObjectAnimator.REVERSE);
	    	animTxtAlpha.setInterpolator(new  LinearInterpolator());
	    	animTxtAlpha.start();
		}
	};

    private View.OnClickListener btnDropOnClick = new View.OnClickListener() {
		public void onClick(View v) {
			if (mIsFallingFirst) {
				// ­pºâ±¼¸¨ªºy®y¼Ð
		    	y = mTxtDemo.getY();
		    	yEnd = mLinLayRoot.getHeight() - mTxtDemo.getHeight();

		    	mIsFallingFirst = false;
			}
			
			ObjectAnimator animTxtFalling = 
	    		ObjectAnimator.ofFloat(mTxtDemo, "y", y, yEnd);
	    	animTxtFalling.setDuration(2000);
	    	animTxtFalling.setRepeatCount(ObjectAnimator.INFINITE);
	    	animTxtFalling.setInterpolator(new  BounceInterpolator());
	    	animTxtFalling.start();
		}
	};

}
