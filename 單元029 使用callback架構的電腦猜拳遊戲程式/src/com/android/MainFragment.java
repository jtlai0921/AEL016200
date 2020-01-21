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
		public void enableGameResult(GameResultType type);
	};

	enum GameResultType {
		TYPE_1, TYPE_2, TURN_OFF;
	}

	private CallbackInterface mCallback;

	private ImageButton mImgBtnScissors,
					   mImgBtnStone,
					   mImgBtnPaper;
	private ImageView mImgViewComPlay;
	private TextView mTxtResult;

/*  ������GameResultFragment�MGameResultFragment2�ۦ汱��
	public EditText mEdtCountSet,
					 mEdtCountPlayerWin,
					 mEdtCountComWin,
					 mEdtCountDraw;
*/
	// �s�W�έp�C�����ƩM��Ĺ���ܼ�
	private int miCountSet = 0,
				miCountPlayerWin = 0,
				miCountComWin = 0,
				miCountDraw = 0;

	private Button mBtnShowResult1,
				   mBtnShowResult2,
				   mBtnHiddenResult;

	private boolean mbShowResult = false;
    
//	private final static String TAG = "Result";
//  private int mTagCount = 0;

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

		// �H�U��������O�b�t�@��Fragment���A�]�������I�s���ݪ�Activity
		// �~����o�o�Ǥ�������
/*    	mEdtCountSet = (EditText) getActivity().findViewById(R.id.edtCountSet);
    	mEdtCountPlayerWin = (EditText) getActivity().findViewById(R.id.edtCountPlayerWin);
    	mEdtCountComWin = (EditText) getActivity().findViewById(R.id.edtCountComWin);
    	mEdtCountDraw = (EditText) getActivity().findViewById(R.id.edtCountDraw);
*/
    	mImgBtnScissors.setOnClickListener(imgBtnScissorsOnClick);
    	mImgBtnStone.setOnClickListener(imgBtnStoneOnClick);
    	mImgBtnPaper.setOnClickListener(imgBtnPaperOnClick);

    	mBtnShowResult1 = (Button) getView().findViewById(R.id.btnShowResult1);
    	mBtnShowResult2 = (Button) getView().findViewById(R.id.btnShowResult2);
    	mBtnHiddenResult = (Button) getView().findViewById(R.id.btnHiddenResult);

    	mBtnShowResult1.setOnClickListener(btnShowResult1OnClick);
    	mBtnShowResult2.setOnClickListener(btnShowResult2OnClick);
    	mBtnHiddenResult.setOnClickListener(btnHiddenResultOnClick);
	}

	private View.OnClickListener imgBtnScissorsOnClick = new View.OnClickListener() {
		public void onClick(View v) {
			// Decide computer play.
			int iComPlay = (int)(Math.random()*3 + 1);
			
			miCountSet++;
//			mEdtCountSet.setText(String.valueOf(miCountSet));

			// 1 - scissors, 2 - stone, 3 - net.
			if (iComPlay == 1) {
				mImgViewComPlay.setImageResource(R.drawable.scissors);
				mTxtResult.setText(getString(R.string.result) +
								  getString(R.string.player_draw));
				miCountDraw++;
//				mEdtCountDraw.setText(String.valueOf(miCountDraw));
			}
			else if (iComPlay == 2) {
				mImgViewComPlay.setImageResource(R.drawable.stone);
				mTxtResult.setText(getString(R.string.result) +
						  getString(R.string.player_lose));
				miCountComWin++;
//				mEdtCountComWin.setText(String.valueOf(miCountComWin));
			}
			else {
				mImgViewComPlay.setImageResource(R.drawable.paper);
				mTxtResult.setText(getString(R.string.result) +
						  getString(R.string.player_win));
				miCountPlayerWin++;
//				mEdtCountPlayerWin.setText(String.valueOf(miCountPlayerWin));
			}

			mCallback.updateGameResult(miCountSet, miCountPlayerWin, 
					miCountComWin, miCountDraw);
		}
	};
	
    private View.OnClickListener imgBtnStoneOnClick = new View.OnClickListener() {
		public void onClick(View v) {
			int iComPlay = (int)(Math.random()*3 + 1);
			
			miCountSet++;
//			mEdtCountSet.setText(String.valueOf(miCountSet));

			// 1 - scissors, 2 - stone, 3 - net.
			if (iComPlay == 1) {
				mImgViewComPlay.setImageResource(R.drawable.scissors);
				mTxtResult.setText(getString(R.string.result) +
						  getString(R.string.player_win));
				miCountPlayerWin++;
//				mEdtCountPlayerWin.setText(String.valueOf(miCountPlayerWin));
			}
			else if (iComPlay == 2) {
				mImgViewComPlay.setImageResource(R.drawable.stone);
				mTxtResult.setText(getString(R.string.result) +
						  getString(R.string.player_draw));
				miCountDraw++;
//				mEdtCountDraw.setText(String.valueOf(miCountDraw));
			}
			else {
				mImgViewComPlay.setImageResource(R.drawable.paper);
				mTxtResult.setText(getString(R.string.result) +
						  getString(R.string.player_lose));
				miCountComWin++;
//				mEdtCountComWin.setText(String.valueOf(miCountComWin));
			}

			mCallback.updateGameResult(miCountSet, miCountPlayerWin, 
					miCountComWin, miCountDraw);
		}
	};
	
    private View.OnClickListener imgBtnPaperOnClick = new View.OnClickListener() {
		public void onClick(View v) {
			int iComPlay = (int)(Math.random()*3 + 1);
			
			miCountSet++;
//			mEdtCountSet.setText(String.valueOf(miCountSet));

			// 1 - scissors, 2 - stone, 3 - net.
			if (iComPlay == 1) {
				mImgViewComPlay.setImageResource(R.drawable.scissors);
				mTxtResult.setText(getString(R.string.result) +
						  getString(R.string.player_lose));
				miCountComWin++;
//				mEdtCountComWin.setText(String.valueOf(miCountComWin));
			}
			else if (iComPlay == 2) {
				mImgViewComPlay.setImageResource(R.drawable.stone);
				mTxtResult.setText(getString(R.string.result) +
						  getString(R.string.player_win));
				miCountPlayerWin++;
//				mEdtCountPlayerWin.setText(String.valueOf(miCountPlayerWin));
			}
			else {
				mImgViewComPlay.setImageResource(R.drawable.paper);
				mTxtResult.setText(getString(R.string.result) +
						  getString(R.string.player_draw));
				miCountDraw++;
//				mEdtCountDraw.setText(String.valueOf(miCountDraw));
			}

			mCallback.updateGameResult(miCountSet, miCountPlayerWin, 
					miCountComWin, miCountDraw);
		}
	};

	private View.OnClickListener btnShowResult1OnClick = new View.OnClickListener() {
		public void onClick(View v) {
			mCallback.enableGameResult(GameResultType.TYPE_1);
		}
	};

	private View.OnClickListener btnShowResult2OnClick = new View.OnClickListener() {
		public void onClick(View v) {
			mCallback.enableGameResult(GameResultType.TYPE_2);
		}
	};

	private View.OnClickListener btnHiddenResultOnClick = new View.OnClickListener() {
		public void onClick(View v) {
			mCallback.enableGameResult(GameResultType.TURN_OFF);
		}
	};

}
