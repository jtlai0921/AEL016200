package com.android;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class GameResultFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// 利用inflater物件的inflate()方法取得介面佈局檔，並將最後的結果傳回給系統
		return inflater.inflate(R.layout.fragment_game_result, container, false);
	}

}
