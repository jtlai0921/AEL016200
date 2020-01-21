package com.android;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class ShapeSurfaceView extends SurfaceView
						implements SurfaceHolder.Callback, Runnable {

	private Paint mPaintForeground,
				  mPaintBackground;
	private static final int INT_STROCK_THICK = 5;
	private int mIntXMaxLen, mIntYMaxLen,
				  mIntXCent, mIntYCent,
				  mIntXCurLen, mIntYCurLen,
				  mIntSign;
	
	private SurfaceHolder mSurfHold;

	public ShapeSurfaceView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub

		mSurfHold = getHolder();
		mSurfHold.addCallback(this);
		
		setFocusable(true);
		
		mPaintForeground = new Paint();
		mPaintForeground.setAntiAlias(true);
		mPaintForeground.setColor(Color.RED);
		mPaintForeground.setStyle(Style.STROKE);
		mPaintForeground.setStrokeWidth((float) INT_STROCK_THICK);

		mPaintBackground = new Paint();
		mPaintBackground.setAntiAlias(true);
		mPaintBackground.setColor(Color.WHITE);
		mPaintBackground.setStyle(Style.FILL);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, 
								int width, int height) {
		// TODO Auto-generated method stub
		mIntXMaxLen = width / 2 - 10;
		mIntYMaxLen = height / 2 - 10;
		mIntXCent = width / 2;
		mIntYCent = height / 2;
		
		mIntXCurLen = mIntXMaxLen;
		mIntYCurLen = mIntYMaxLen;
		mIntSign = -1;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		new Thread(this).start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		// 當程式畫面的Canvas被銷毀時執行
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		for (int i = 0; i < 500; i++) {
			Log.d("TEST_SURFACEVIEW", "run() " + i);

			Canvas c = null;
			try {
				c = mSurfHold.lockCanvas();
				synchronized(mSurfHold) {
					draw(c);
				}
			}
			finally {
				if (c != null)
					mSurfHold.unlockCanvasAndPost(c);
			}
		}
	}

	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		if (canvas == null)	// 如果動畫還沒完成就結束App，canvas會變成null
			return;

		super.draw(canvas);
		
		Log.d("TEST_SURFACEVIEW", "draw()");

		canvas.drawRect(0, 0, this.getWidth(), this.getHeight(), mPaintBackground);

		canvas.drawOval(new RectF(mIntXCent - mIntXCurLen,
								  mIntYCent - mIntYCurLen,
								  mIntXCent + mIntXCurLen,
								  mIntYCent + mIntYCurLen), 
							mPaintForeground);

		if (mIntXCurLen + mIntSign * INT_STROCK_THICK < 0 ||
			mIntYCurLen + mIntSign * INT_STROCK_THICK < 0) {
			mIntSign = 1;
		}
		else if (mIntXCurLen + mIntSign * INT_STROCK_THICK > mIntXMaxLen ||
				 mIntYCurLen + mIntSign * INT_STROCK_THICK > mIntYMaxLen) {
			mIntSign = -1;
		}

		mIntXCurLen += mIntSign * INT_STROCK_THICK;
		mIntYCurLen += mIntSign * INT_STROCK_THICK;
	}

}
