package com.android;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.os.Build;

public class MainActivity extends Activity {

	private static final int GOOGLE_PLAY_SERVICES_REQUEST_CODE = 1000;

	private static String[][] mLocations = {
			{"台北101", "25.0336110,121.5650000"},
			{"中國長城", "40.0000350,119.7672800"},
			{"紐約自由女神像", "40.6892490,-74.0445000"},
			{"巴黎鐵塔", "48.8582220,2.2945000"}	};

	private GoogleMap mMap;
	private Spinner mSpnLocation;
	private MapFragment mMapFragment;
	private boolean mbIsZoomFirst = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// 呼叫我們自己建立的方法，檢查Google Play Services
		checkGooglePlayServices();

        mSpnLocation = (Spinner) this.findViewById(R.id.spnLocation);

        ArrayAdapter<String> arrAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item);

        for (int i = 0; i < mLocations.length; i++)
        	arrAdapter.add(mLocations[i][0]);

        arrAdapter.setDropDownViewResource(
               android.R.layout.simple_spinner_dropdown_item);
        mSpnLocation.setAdapter(arrAdapter);
        mSpnLocation.setOnItemSelectedListener(spnLocationOnItemSelected);

        Spinner spnMapType = (Spinner) findViewById(R.id.spnMapType);
        spnMapType.setOnItemSelectedListener(spnMapTypeOnItemSelected);

        Button btn3DMap = (Button) findViewById(R.id.btn3DMap);
        btn3DMap.setOnClickListener(btn3DMapOnClick);

        mMapFragment = new MapFragment() {
            @Override
            public void onActivityCreated(Bundle savedInstanceState) {
                super.onActivityCreated(savedInstanceState);

                // 取得地圖物件，完成初始設定。
                mMap = mMapFragment.getMap();
                setUpMap();
            }
        };

        // 把地圖Fragment放到介面佈局檔裡頭的FrameLayout顯示。
        getFragmentManager().beginTransaction()
        		.add(R.id.frameLayMapContainer, mMapFragment)
        		.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch (requestCode) {
	    case GOOGLE_PLAY_SERVICES_REQUEST_CODE:
	    	if (resultCode == RESULT_CANCELED)
	    		// 使用者取消處理Google Play Services的問題，所以結束App。
	    		showDlgGooglePlayServicesFailAndExitApp();

	    	break;
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	private void showDlgGooglePlayServicesFailAndExitApp() {
		AlertDialog.Builder altDlg = new AlertDialog.Builder(this);
		altDlg.setTitle("錯誤");
		altDlg.setMessage("找不到Google Play Services，程式無法執行。");
		altDlg.setIcon(android.R.drawable.ic_dialog_info);
		altDlg.setCancelable(false);
		altDlg.setPositiveButton("確定",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						finish();	// 結束App。
					}
				});
		altDlg.show();
	}

	private void checkGooglePlayServices() {
		// 檢查是否已經安裝Google Play Services，並且是最新版。
		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		if (resultCode == ConnectionResult.SUCCESS)
			return;

		// 是否使用者可以自行排除錯誤，例如更新Google Play Services。
		if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
			// 顯示Google Play Services提供的對話盒，並且將執行權交給它。
	    	GooglePlayServicesUtil.getErrorDialog(resultCode, this, 
	    			GOOGLE_PLAY_SERVICES_REQUEST_CODE)
	    			.show();
	    	return;
		}

		// 使用者無法處裡的錯誤，顯示錯誤訊息並結束程式。
		showDlgGooglePlayServicesFailAndExitApp();
	}

	private Spinner.OnItemSelectedListener spnLocationOnItemSelected = 
			new Spinner.OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView parent, View v,
				int position, long id) {
			// TODO Auto-generated method stub
			updateMapLocation();
		}

		@Override
		public void onNothingSelected(AdapterView parent) {
			// TODO Auto-generated method stub
			
		}
		
	};

	private void updateMapLocation() {
		if (mMap == null)
			return;

    	int iSelected = mSpnLocation.getSelectedItemPosition();
    	String[] sLocation = mLocations[iSelected][1].split(",");
    	double dLat = Double.parseDouble(sLocation[0]);	// 南北緯
    	double dLon = Double.parseDouble(sLocation[1]);	// 東西經

    	// 如果是第一次執行，把地圖放大到設定的等級。
    	if (mbIsZoomFirst) {
    		mbIsZoomFirst = false;
    		mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
					new LatLng(dLat, dLon), 15));
    	} else
	    	mMap.animateCamera(CameraUpdateFactory.newLatLng(
					new LatLng(dLat, dLon)));
   }

	private void setUpMap() {
		UiSettings mapUi = mMap.getUiSettings();
        mapUi.setZoomControlsEnabled(false);	// 隱藏縮放地圖的按鈕。
	}

	private AdapterView.OnItemSelectedListener spnMapTypeOnItemSelected =
	    	new AdapterView.OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView parent,
								View v, int position, long id) {
					// TODO Auto-generated method stub
					// 依照使用者點選的項目位置，改變地圖模式。
					switch (position) {
					case 0:
						mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
						break;
					case 1:
						mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
						break;
					case 2:
						mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
						break;
					case 3:
						mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
						break;
					}
				}

				@Override
				public void onNothingSelected(AdapterView parent) {
					// TODO Auto-generated method stub
					
				}
			};

	private View.OnClickListener btn3DMapOnClick = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			// 設定地圖的俯視角度，並且放大到一定的等級，讓3D建築物出現。
			CameraUpdate camUpdate = CameraUpdateFactory.newCameraPosition(
					new CameraPosition.Builder()
					.target(mMap.getCameraPosition().target)
					.tilt(45)
					.zoom(17)
					.build());
			mMap.animateCamera(camUpdate);
		}
	};
}