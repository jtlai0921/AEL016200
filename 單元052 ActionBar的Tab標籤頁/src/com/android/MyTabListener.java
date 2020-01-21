package com.android;

import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;

public class MyTabListener<T extends Fragment> implements TabListener {

	private Fragment mFragment = null;	// �O���o��tab page������fragment
	private final Activity mActivity;	// �����o��fragment���ݪ�activity
	private final String mTag;			// �����o��tab page��tag
	private final Class<T> mFragmentClass;	// �O���Ψӫإ߳o��fragment�����O

	public MyTabListener(Activity activity, String tag, Class<T> fragmentClass) {
		mActivity = activity;
		mTag = tag;
		mFragmentClass = fragmentClass;
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		// �ˬd�O�_�w�g�إߦnfragment�Atab page�Ĥ@����ܮɭn���إ�fragment
		if (mFragment == null) {
			mFragment = Fragment.instantiate(mActivity, mFragmentClass.getName());
			ft.add(R.id.frameLayout, mFragment, mTag);
		} else
			ft.attach(mFragment);
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		if (mFragment != null)
			ft.detach(mFragment);
	}

}
