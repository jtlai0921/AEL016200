package com.android;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	private ImageView mImgView1,
				    mImgView2,
				    mImgView3;
	private Button mBtnStart;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mImgView1 = (ImageView)findViewById(R.id.imgView1);
        mImgView2 = (ImageView)findViewById(R.id.imgView2);
        mImgView3 = (ImageView)findViewById(R.id.imgView3);

        mBtnStart = (Button)findViewById(R.id.btnStart);
        mBtnStart.setOnClickListener(btnStartOnClick);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);

		return true;
	}

	private View.OnClickListener btnStartOnClick = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
		   // TODO Auto-generated method stub
		   Resources res = getResources();
		
		   Drawable drawImg = res.getDrawable(R.drawable.img01);
		   mImgView1.setBackground(drawImg);
		
		   TransitionDrawable drawTran = 
				   (TransitionDrawable)res.getDrawable(R.drawable.trans_drawable);
		   mImgView2.setImageDrawable(drawTran);
		   drawTran.startTransition(5000);
		
		   GradientDrawable gradShape = new GradientDrawable();
		   gradShape.setShape(GradientDrawable.OVAL);
		   gradShape.setColor(0xffff0000);
		   mImgView3.setBackground(gradShape);
		}
	};
}
