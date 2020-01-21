package com.android;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class GameFragment extends Fragment {

	private TextView mTxtResult;
	private ImageView mImgViewComPlay;
    private ImageButton mImgBtnScissors, mImgBtnStone, mImgBtnPaper;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		mImgViewComPlay = (ImageView) getView().findViewById(R.id.imgViewComPlay);
        mTxtResult = (TextView) getView().findViewById(R.id.txtResult);
        mImgBtnScissors = (ImageButton) getView().findViewById(R.id.imgBtnScissors);
        mImgBtnStone = (ImageButton) getView().findViewById(R.id.imgBtnStone);
        mImgBtnPaper = (ImageButton) getView().findViewById(R.id.imgBtnPaper);

        mImgBtnScissors.setOnClickListener(imgBtnScissorsOnClick);
        mImgBtnStone.setOnClickListener(imgBtnStoneOnClick);
        mImgBtnPaper.setOnClickListener(imgBtnPaperOnClick);	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_game, container, false);
	}

	 private View.OnClickListener imgBtnScissorsOnClick = new View.OnClickListener() {
			public void onClick(View v) {
				// 決定電腦出拳.
				int iComPlay = (int)(Math.random()*3 + 1);
				
				// 1 – 剪刀, 2 – 石頭, 3 – 布.
				if (iComPlay == 1) {
					mImgViewComPlay.setImageResource(R.drawable.scissors);
					mTxtResult.setText(getString(R.string.result2) +
									  getString(R.string.player_draw));
				}
				else if (iComPlay == 2) {
					mImgViewComPlay.setImageResource(R.drawable.stone);
					mTxtResult.setText(getString(R.string.result2) +
							  getString(R.string.player_lose));
				}
				else {
					mImgViewComPlay.setImageResource(R.drawable.paper);
					mTxtResult.setText(getString(R.string.result2) +
							  getString(R.string.player_win));
				}
			}
		};
		
	    private View.OnClickListener imgBtnStoneOnClick = new View.OnClickListener() {
			public void onClick(View v) {
				// 決定電腦出拳.
				int iComPlay = (int)(Math.random()*3 + 1);
				
				// 1 – 剪刀, 2 – 石頭, 3 – 布.
				if (iComPlay == 1) {
					mImgViewComPlay.setImageResource(R.drawable.scissors);
					mTxtResult.setText(getString(R.string.result2) +
							  getString(R.string.player_win));
				}
				else if (iComPlay == 2) {
					mImgViewComPlay.setImageResource(R.drawable.stone);
					mTxtResult.setText(getString(R.string.result2) +
							  getString(R.string.player_draw));
				}
				else {
					mImgViewComPlay.setImageResource(R.drawable.paper);
					mTxtResult.setText(getString(R.string.result2) +
							  getString(R.string.player_lose));
				}
			}
		};

	    private View.OnClickListener imgBtnPaperOnClick = new View.OnClickListener() {
			public void onClick(View v) {
				// 決定電腦出拳.
				int iComPlay = (int)(Math.random()*3 + 1);
				
				// 1 – 剪刀, 2 – 石頭, 3 – 布.
				if (iComPlay == 1) {
					mImgViewComPlay.setImageResource(R.drawable.scissors);
					mTxtResult.setText(getString(R.string.result2) +
							  getString(R.string.player_lose));
				}
				else if (iComPlay == 2) {
					mImgViewComPlay.setImageResource(R.drawable.stone);
					mTxtResult.setText(getString(R.string.result2) +
							  getString(R.string.player_win));
				}
				else {
					mImgViewComPlay.setImageResource(R.drawable.paper);
					mTxtResult.setText(getString(R.string.result2) +
							  getString(R.string.player_draw));
				}
			}
		};

}
