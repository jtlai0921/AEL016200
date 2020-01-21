package com.android;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
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
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends Activity 
						implements ConnectionCallbacks, OnConnectionFailedListener,
									LocationListener {

	private static final int LOCATION_SERVICES_CONNECTION_FAILURE_REQUEST_CODE = 1000;

	private static String[][] mLocations = {
			{"台北101", "25.0336110,121.5650000"},
			{"中國長城", "40.0000350,119.7672800"},
			{"紐約自由女神像", "40.6892490,-74.0445000"},
			{"巴黎鐵塔", "48.8582220,2.2945000"}	};

	private GoogleMap mMap;
	private Spinner mSpnLocation;
	private MapFragment mMapFragment;
	private boolean mbIsZoomFirst = true;

	private LocationClient mLocationClient;		// 取得定位資料。
	private LocationRequest mLocationRequest;	// 設定定位參數。
	private LocationManager mLocationMgr;		// 決定使用GPS或是網路定位。

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

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

        // 建立一個LocationClient物件。
        mLocationClient = new LocationClient(this, this, this);

        // 準備一個LocationRequest物件，設定定位參數，在啟動定位時使用。
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(5000);	 // 二次定位之間的時間間隔，單位是千分之一秒。
        mLocationRequest.setSmallestDisplacement(5);	// 二次定位之間的最大距離，單位是公尺。

        mLocationMgr = (LocationManager) getSystemService(LOCATION_SERVICE);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		// 與Location Services連線。
		mLocationClient.connect();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

		if (mLocationClient.isConnected()) {
			// 停止定位。
			mLocationClient.removeLocationUpdates(this);

			// 斷開Location Services的連線。
			mLocationClient.disconnect();
			Toast.makeText(MainActivity.this, "Location Services已經斷線", Toast.LENGTH_LONG)
				.show();
		}
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
		case LOCATION_SERVICES_CONNECTION_FAILURE_REQUEST_CODE:
    	    if (resultCode == RESULT_OK)
    	    	// Google Play Services已經解決Location Services的連線問題，再嘗試連線一次。
    	    	mLocationClient.connect();

    	    break;
		}

		super.onActivityResult(requestCode, resultCode, data);
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
    		mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    	} else
	    	mMap.animateCamera(CameraUpdateFactory.newLatLng(
					new LatLng(dLat, dLon)));
   }

	private void setUpMap() {
		UiSettings mapUi = mMap.getUiSettings();
        mapUi.setZoomControlsEnabled(false);	// 隱藏縮放地圖的按鈕。
//        mMap.setMyLocationEnabled(true);
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

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		// TODO Auto-generated method stub
		Toast.makeText(MainActivity.this, "Location Services連線失敗", Toast.LENGTH_LONG)
				.show();

		// 嘗試利用Google Play services來解決問題。
		if (connectionResult.hasResolution()) {
			try {
				connectionResult.startResolutionForResult(this,
						LOCATION_SERVICES_CONNECTION_FAILURE_REQUEST_CODE);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else
			showDlgLocationServicesConnectionFailAndExitApp();
	}

	@Override
	public void onConnected(Bundle dataBundle) {
		// TODO Auto-generated method stub
		Toast.makeText(MainActivity.this, "Location Services已經連線", Toast.LENGTH_LONG)
				.show();

		// 啟動定位。
        if(mLocationMgr.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        	Toast.makeText(MainActivity.this, "使用GPS定位", Toast.LENGTH_LONG)
					.show();
        } else if(mLocationMgr.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            mLocationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);
        	Toast.makeText(MainActivity.this, "使用網路定位", Toast.LENGTH_LONG)
					.show();
        }
		mLocationClient.requestLocationUpdates(mLocationRequest, this);

        // 取得前一次的定位。
        Location location = mLocationClient.getLastLocation();

        if (location != null) {
        	Toast.makeText(MainActivity.this, "成功取得前一次的定位", Toast.LENGTH_LONG)
					.show();
        	onLocationChanged(location);	// 更新地圖的定位。
        } else
        	Toast.makeText(MainActivity.this, "前一次的定位傳回null", Toast.LENGTH_LONG)
					.show();
	}

	@Override
	public void onDisconnected() {
		// 當Location Services發生問題，無故斷線時，才會執行這個方法。
		// 程式呼叫LocationClient的disconnect()時不會執行這個方法。
		Toast.makeText(MainActivity.this, "Location Services發生問題，已經斷線", Toast.LENGTH_LONG)
				.show();
	}

	private void showDlgLocationServicesConnectionFailAndExitApp() {
		AlertDialog.Builder altDlg = new AlertDialog.Builder(this);
		altDlg.setTitle("錯誤");
		altDlg.setMessage("無法連線Location Services，程式無法執行。");
		altDlg.setIcon(android.R.drawable.ic_dialog_info);
		altDlg.setCancelable(false);
		altDlg.setPositiveButton("確定",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						finish();  // 結束App。
					}
				});
		altDlg.show();
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		// 移動地圖到新位置。
		LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

        // 更新Marker。
        mMap.clear();
        mMap.addMarker(new MarkerOptions()
			.position(latLng)
			.icon(BitmapDescriptorFactory.fromResource(android.R.drawable.btn_star_big_on))
			.anchor(0.5f, 0.5f));
	}

}