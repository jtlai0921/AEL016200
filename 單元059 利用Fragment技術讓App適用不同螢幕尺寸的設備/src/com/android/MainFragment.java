package com.android;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

public class MainFragment extends Fragment {

	// ���ݪ� Activity ������@�H�U��������callback��k
	public interface CallbackInterface {
		public void updateGameResult(int iCountSet,
									 int iCountPlayerWin,
									 int iCountComWin,
									 int iCountDraw);
	};

	private CallbackInterface mCallback;

	private ImageButton mImgBtnScissors,
					   mImgBtnStone,
					   mImgBtnPaper;
	private ImageView mImgViewComPlay;
	private TextView mTxtResult;

	private Button mBtnShowResult;

	// �s�W�έp�C�����ƩM��Ĺ���ܼ�
	private int miCountSet = 0,
				miCountPlayerWin = 0,
				miCountComWin = 0,
				miCountDraw = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// �Q��inflater����inflate()��k���o�����G���ɡA�ñN�̫᪺���G�Ǧ^���t��
		return inflater.inflate(R.layout.fragment_main, container, false);
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);

		try {
			mCallback = (CallbackInterface) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + 
					"must implement GameFragment.CallbackInterface.");
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		// �������I�sgetView()���o�{���e������A�M��~��I�s����
		// findViewById()���o��������
        mTxtResult = (TextView) getView().findViewById(R.id.txtResult);
    	mImgBtnScissors = (ImageButton) getView().findViewById(R.id.imgBtnScissors);
    	mImgBtnStone = (ImageButton) getView().findViewById(R.id.imgBtnStone);
    	mImgBtnPaper = (ImageButton) getView().findViewById(R.id.imgBtnPaper);
		mImgViewComPlay = (ImageView) getView().findViewById(R.id.imgViewComPlay);

    	mImgBtnScissors.setOnClickListener(imgBtnScissorsOnClick);
    	mImgBtnStone.setOnClickListener(imgBtnStoneOnClick);
    	mImgBtnPaper.setOnClickListener(imgBtnPaperOnClick);

    	mBtnShowResult = (Button)getView().findViewById(R.id.btnShowResult);
    	mBtnShowResult.setOnClickListener(btnShowResultOnClick);

    	if (((MainActivity) getActivity()).UITypeFlag == MainActivity.UIType.TWO_FRAMES) {
    		mBtnShowResult.setVisibility(View.GONE);
    	} else {
    		mBtnShowResult.setVisibility(View.VISIBLE);
    	}
	}

	private View.OnClickListener imgBtnScissorsOnClick = new View.OnClickListener() {
		public void onClick(View v) {
			// Decide computer play.
			int iComPlay = (int)(Math.random()*3 + 1);
			
			miCountSet++;

			// 1 - scissors, 2 - stone, 3 - net.
			if (iComPlay == 1) {
				mImgViewComPlay.setImageResource(R.drawable.scissors);
				mTxtResult.setText(getString(R.string.result) +
								  getString(R.string.player_draw));
				miCountDraw++;
			}
			else if (iComPlay == 2) {
				mImgViewComPlay.setImageResource(R.drawable.stone);
				mTxtResult.setText(getString(R.string.result) +
						  getString(R.string.player_lose));
				miCountComWin++;
			}
			else {
				mImgViewComPlay.setImageResource(R.drawable.paper);
				mTxtResult.setText(getString(R.string.result) +
						  getString(R.string.player_win));
				miCountPlayerWin++;
			}

			mCallback.updateGameResult(miCountSet, miCountPlayerWin, 
					miCountComWin, miCountDraw);
		}
	};
	
    private View.OnClickListener imgBtnStoneOnClick = new View.OnClickListener() {
		public void onClick(View v) {
			int iComPlay = (int)(Math.random()*3 + 1);
			
			miCountSet++;

			// 1 - scissors, 2 - stone, 3 - net.
			if (iComPlay == 1) {
				mImgViewComPlay.setImageResource(R.drawable.scissors);
				mTxtResult.setText(getString(R.string.result) +
						  getString(R.string.player_win));
				miCountPlayerWin++;
			}
			else if (iComPlay == 2) {
				mImgViewComPlay.setImageResource(R.drawable.stone);
				mTxtResult.setText(getString(R.string.result) +
						  getString(R.string.player_draw));
				miCountDraw++;
			}
			else {
				mImgViewComPlay.setImageResource(R.drawable.paper);
				mTxtResult.setText(getString(R.string.result) +
						  getString(R.string.player_lose));
				miCountComWin++;
			}

			mCallback.updateGameResult(miCountSet, miCountPlayerWin, 
					miCountComWin, miCountDraw);
		}
	};
	
    private View.OnClickListener imgBtnPaperOnClick = new View.OnClickListener() {
		public void onClick(View v) {
			int iComPlay = (int)(Math.random()*3 + 1);
			
			miCountSet++;

			// 1 - scissors, 2 - stone, 3 - net.
			if (iComPlay == 1) {
				mImgViewComPlay.setImageResource(R.drawable.scissors);
				mTxtResult.setText(getString(R.string.result) +
						  getString(R.string.player_lose));
				miCountComWin++;
			}
			else if (iComPlay == 2) {
				mImgViewComPlay.setImageResource(R.drawable.stone);
				mTxtResult.setText(getString(R.string.result) +
						  getString(R.string.player_win));
				miCountPlayerWin++;
			}
			else {
				mImgViewComPlay.setImageResource(R.drawable.paper);
				mTxtResult.setText(getString(R.string.result) +
						  getString(R.string.player_draw));
				miCountDraw++;
			}

			mCallback.updateGameResult(miCountSet, miCountPlayerWin, 
					miCountComWin, miCountDraw);
		}
	};

	private View.OnClickListener btnShowResultOnClick = new View.OnClickListener() {
		public void onClick(View v) {
			getActivity().findViewById(R.id.frameLay1).setVisibility(View.GONE);
			getActivity().findViewById(R.id.frameLay2).setVisibility(View.VISIBLE);

			mCallback.updateGameResult(miCountSet, miCountPlayerWin, 
					miCountComWin, miCountDraw);
		}
	};

}
