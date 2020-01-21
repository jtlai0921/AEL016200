package com.android;

import com.android.MainFragment.GameResultType;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class GameResultFragment2 extends Fragment {

	private EditText mEdtCountSet,
					   mEdtCountPlayerWin,
					   mEdtCountComWin,
					   mEdtCountDraw;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// 利用inflater物件的inflate()方法取得介面佈局檔，並將最後的結果傳回給系統
		return inflater.inflate(R.layout.fragment_game_result2, container, false);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		mEdtCountSet = (EditText)getActivity().findViewById(R.id.edtCountSet);
		mEdtCountPlayerWin = (EditText)getActivity().findViewById(R.id.edtCountPlayerWin);
    	mEdtCountComWin = (EditText)getActivity().findViewById(R.id.edtCountComWin);
    	mEdtCountDraw = (EditText)getActivity().findViewById(R.id.edtCountDraw);

    	((MainActivity) getActivity()).mGameResultType = GameResultType.TYPE_2;
    	((MainActivity) getActivity()).fragResult = this;

		getActivity().findViewById(R.id.frameLay).setVisibility(View.VISIBLE);
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

		getActivity().findViewById(R.id.frameLay).setVisibility(View.GONE);
	}

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
