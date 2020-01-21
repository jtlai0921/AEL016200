package com.android;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class GameResultFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// 利用inflater物件的inflate()方法取得介面佈局檔，並將最後的結果傳回給系統
		return inflater.inflate(R.layout.fragment_game_result, container, false);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		MainFragment frag = (MainFragment) getFragmentManager()
				.findFragmentById(R.id.fragMain);
		frag.mEdtCountSet = (EditText) getActivity().findViewById(R.id.edtCountSet);
		frag.mEdtCountPlayerWin = (EditText) getActivity()
				.findViewById(R.id.edtCountPlayerWin);
		frag.mEdtCountComWin = (EditText) getActivity()
				.findViewById(R.id.edtCountComWin);
		frag.mEdtCountDraw = (EditText)getActivity().findViewById(R.id.edtCountDraw);

		getActivity().findViewById(R.id.frameLay).setVisibility(View.VISIBLE);
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

		getActivity().findViewById(R.id.frameLay).setVisibility(View.GONE);
	}

}
