package tw.android;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

	// 新增統計遊戲局數和輸贏的變數
	private int miCountSet = 0,
				    miCountPlayerWin = 0,
				    miCountComWin = 0,
				    miCountDraw = 0;

	private Button mBtnShowResult;

	private TextView mTxtResult;
	private ImageView mImgViewComPlay;
    private ImageButton mImgBtnScissors, mImgBtnStone, mImgBtnPaper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mImgViewComPlay = (ImageView)findViewById(R.id.imgViewComPlay);
        mTxtResult = (TextView)findViewById(R.id.txtResult);
        mImgBtnScissors = (ImageButton)findViewById(R.id.imgBtnScissors);
        mImgBtnStone = (ImageButton)findViewById(R.id.imgBtnStone);
        mImgBtnPaper = (ImageButton)findViewById(R.id.imgBtnPaper);

        mImgBtnScissors.setOnClickListener(imgBtnScissorsOnClick);
        mImgBtnStone.setOnClickListener(imgBtnStoneOnClick);
        mImgBtnPaper.setOnClickListener(imgBtnPaperOnClick);

        mBtnShowResult = (Button)findViewById(R.id.btnShowResult);
        mBtnShowResult.setOnClickListener(btnShowResultOnClick);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	 private View.OnClickListener imgBtnScissorsOnClick = new View.OnClickListener() {
			public void onClick(View v) {
				// 決定電腦出拳.
				int iComPlay = (int)(Math.random()*3 + 1);

				miCountSet++;

				// 1 – 剪刀, 2 – 石頭, 3 – 布.
				if (iComPlay == 1) {
					mImgViewComPlay.setImageResource(R.drawable.scissors);
					mTxtResult.setText(getString(R.string.result) +
									  getString(R.string.player_draw));
					miCountDraw++;
				} else if (iComPlay == 2) {
					mImgViewComPlay.setImageResource(R.drawable.stone);
					mTxtResult.setText(getString(R.string.result) +
							  getString(R.string.player_lose));
					miCountComWin++;
				} else {
					mImgViewComPlay.setImageResource(R.drawable.paper);
					mTxtResult.setText(getString(R.string.result) +
							  getString(R.string.player_win));
					miCountPlayerWin++;
				}
			}
		};
		
	    private View.OnClickListener imgBtnStoneOnClick = new View.OnClickListener() {
			public void onClick(View v) {
				// 決定電腦出拳.
				int iComPlay = (int)(Math.random()*3 + 1);

				miCountSet++;

				// 1 – 剪刀, 2 – 石頭, 3 – 布.
				if (iComPlay == 1) {
					mImgViewComPlay.setImageResource(R.drawable.scissors);
					mTxtResult.setText(getString(R.string.result) +
							  getString(R.string.player_win));
					miCountPlayerWin++;
				} else if (iComPlay == 2) {
					mImgViewComPlay.setImageResource(R.drawable.stone);
					mTxtResult.setText(getString(R.string.result) +
							  getString(R.string.player_draw));
					miCountDraw++;
				} else {
					mImgViewComPlay.setImageResource(R.drawable.paper);
					mTxtResult.setText(getString(R.string.result) +
							  getString(R.string.player_lose));
					miCountComWin++;
				}
			}
		};

	    private View.OnClickListener imgBtnPaperOnClick = new View.OnClickListener() {
			public void onClick(View v) {
				// 決定電腦出拳.
				int iComPlay = (int)(Math.random()*3 + 1);

				miCountSet++;

				// 1 – 剪刀, 2 – 石頭, 3 – 布.
				if (iComPlay == 1) {
					mImgViewComPlay.setImageResource(R.drawable.scissors);
					mTxtResult.setText(getString(R.string.result) +
							  getString(R.string.player_lose));
					miCountComWin++;
				} else if (iComPlay == 2) {
					mImgViewComPlay.setImageResource(R.drawable.stone);
					mTxtResult.setText(getString(R.string.result) +
							  getString(R.string.player_win));
					miCountPlayerWin++;
				} else {
					mImgViewComPlay.setImageResource(R.drawable.paper);
					mTxtResult.setText(getString(R.string.result) +
							  getString(R.string.player_draw));
					miCountDraw++;
				}
			}
		};

		private View.OnClickListener btnShowResultOnClick = new View.OnClickListener() {
			public void onClick(View v) {
				Intent it = new Intent();
				it.setClass(MainActivity.this, GameResultActivity.class);
				
				Bundle bundle = new Bundle();
				bundle.putInt("KEY_COUNT_SET", miCountSet);
				bundle.putInt("KEY_COUNT_PLAYER_WIN", miCountPlayerWin);
				bundle.putInt("KEY_COUNT_COM_WIN", miCountComWin);
				bundle.putInt("KEY_COUNT_DRAW", miCountDraw);
				it.putExtras(bundle);
				
				startActivity(it);
			}
		 };

}
