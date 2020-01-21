package com.android;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class GameResultFragment extends Fragment {

	private EditText mEdtCountSet,
					   mEdtCountPlayerWin,
					   mEdtCountComWin,
					   mEdtCountDraw;

	private Button mBtnBackToGame;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// �Q��inflater����inflate()��k���o�����G���ɡA�ñN�̫᪺���G�Ǧ^���t��
		return inflater.inflate(R.layout.fragment_game_result, container, false);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		mEdtCountSet = (EditText)getActivity().findViewById(R.id.edtCountSet);
		mEdtCountPlayerWin = (EditText)getActivity().findViewById(R.id.edtCountPlayerWin);
    	mEdtCountComWin = (EditText)getActivity().findViewById(R.id.edtCountComWin);
    	mEdtCountDraw = (EditText)getActivity().findViewById(R.id.edtCountDraw);

    	mBtnBackToGame = (Button)getActivity().findViewById(R.id.btnBackToGame);
    	mBtnBackToGame.setOnClickListener(btnBackToGameOnClick);
    	
    	if (((MainActivity) getActivity()).UITypeFlag == MainActivity.UIType.TWO_FRAMES) {
    		mBtnBackToGame.setVisibility(View.GONE);
    	} else {
    		mBtnBackToGame.setVisibility(View.VISIBLE);
    	}
	}

	private View.OnClickListener btnBackToGameOnClick = new View.OnClickListener() {
		public void onClick(View v) {
			getActivity().findViewById(R.id.frameLay1).setVisibility(View.VISIBLE);
			getActivity().findViewById(R.id.frameLay2).setVisibility(View.GONE);
		}
	};

	public void updateGameResult(int iCountSet,
								 int iCountPlayerWin,
								 int iCountComWin,
								 int iCountDraw) {
		mEdtCountSet.setText(String.valueOf(iCountSet));
		mEdtCountDraw.setText(String.valueOf(iCountDraw));
		mEdtCountComWin.setText(String.valueOf(iCountComWin));
		mEdtCountPlayerWin.setText(String.valueOf(iCountPlayerWin));
	}

}
