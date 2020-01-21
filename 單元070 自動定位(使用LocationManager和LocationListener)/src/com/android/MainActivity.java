package com.android;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.LocationSource.OnLocationChangedListener;
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
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
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
							implements LocationListener, LocationSource {

	private static String[][] mLocations = {
			{"台北101", "25.0336110,121.5650000"},
			{"中國長城", "40.0000350,119.7672800"},
			{"紐約自由女神像", "40.6892490,-74.0445000"},
			{"巴黎鐵塔", "48.8582220,2.2945000"}	};

	private GoogleMap mMap;
	private Spinner mSpnLocation;
	private MapFragment mMapFragment;
	private boolean mbIsZoomFirst = true;

	private LocationManager mLocationMgr;
	private OnLocationChangedListener mLocationChangedListener;

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

        mLocationMgr = (LocationManager) getSystemService(LOCATION_SERVICE);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		// App從背景切換到前景執行，啟動Google Map內部的定位功能。
		if (mMap != null && !mMap.isMyLocationEnabled())
			mMap.setMyLocationEnabled(true);
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
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

		// 停止Google Map內部的定位功能。
		mMap.setMyLocationEnabled(false);
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
        mapUi.setMyLocationButtonEnabled(false);	// 隱藏定位按鈕。
        mMap.setLocationSource(this);
        mMap.setMyLocationEnabled(true);

        // 取得前一次的定位。
        Location location = mLocationMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location == null)
        	location = mLocationMgr.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        if (location != null) {
        	Toast.makeText(MainActivity.this, "成功取得前一次的定位", Toast.LENGTH_LONG)
					.show();
        	onLocationChanged(location);	// 更新地圖的定位。
        } else
        	Toast.makeText(MainActivity.this, "前一次的定位傳回null", Toast.LENGTH_LONG)
					.show();
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
	public void activate(OnLocationChangedListener listener) {
		// TODO Auto-generated method stub
		mLocationChangedListener = listener;
		enableLocationUpdate();
		Toast.makeText(MainActivity.this, "地圖的my-location layer已經啟用", Toast.LENGTH_LONG)
				.show();
	}

	@Override
	public void deactivate() {
		// TODO Auto-generated method stub
		mLocationChangedListener = null;
		disableLocationUpdate();
		Toast.makeText(MainActivity.this, "地圖的my-location layer已經關閉", Toast.LENGTH_LONG)
				.show();
	}

	@Override
	public void onLocationChanged(Location location) {
		// 把新位置傳給Google Map的my-location layer。
		if (mLocationChangedListener != null)
            mLocationChangedListener.onLocationChanged(location);

        // 移動地圖到新位置。
        mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
	}

	@Override
	public void onProviderDisabled(String provider) {
		Toast.makeText(MainActivity.this, provider + "定位功能已經被關閉", Toast.LENGTH_LONG)
		.show();
	}

	@Override
	public void onProviderEnabled(String provider) {
		Toast.makeText(MainActivity.this, provider + "定位功能開啟", Toast.LENGTH_LONG)
		.show();
		enableLocationUpdate();
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		String str = provider;
		switch (status) {
		case LocationProvider.OUT_OF_SERVICE:
			str += "定位功能無法使用";
			break;
		case LocationProvider.TEMPORARILY_UNAVAILABLE:
			str += "暫時無法定位";	// GPS正在定位中時會傳入這個值。
			break;
		}

		Toast.makeText(MainActivity.this, str, Toast.LENGTH_LONG)
				.show();
	}

	private void enableLocationUpdate() {
        // 如果GPS功能有開啟，優先使用GPS定位，否則使用網路定位。
        if(mLocationMgr.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            mLocationMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);
        	Toast.makeText(MainActivity.this, "使用GPS定位", Toast.LENGTH_LONG)
					.show();
        } else if(mLocationMgr.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
        	mLocationMgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);
        	Toast.makeText(MainActivity.this, "使用網路定位", Toast.LENGTH_LONG)
					.show();
        }
	}

	private void disableLocationUpdate() {
		mLocationMgr.removeUpdates(this);
		Toast.makeText(MainActivity.this, "定位功能已經停用", Toast.LENGTH_LONG)
				.show();
	}
}