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
			{"�x�_101", "25.0336110,121.5650000"},
			{"�������", "40.0000350,119.7672800"},
			{"�ì��ۥѤk����", "40.6892490,-74.0445000"},
			{"�ھ��K��", "48.8582220,2.2945000"}	};

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

                // ���o�a�Ϫ���A������l�]�w�C
                mMap = mMapFragment.getMap();
                setUpMap();
            }
        };

        // ��a��Fragment��줶���G���ɸ��Y��FrameLayout��ܡC
        getFragmentManager().beginTransaction()
        		.add(R.id.frameLayMapContainer, mMapFragment)
        		.commit();

        mLocationMgr = (LocationManager) getSystemService(LOCATION_SERVICE);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		// App�q�I��������e������A�Ұ�Google Map�������w��\��C
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

		// ����Google Map�������w��\��C
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
    	double dLat = Double.parseDouble(sLocation[0]);	// �n�_�n
    	double dLon = Double.parseDouble(sLocation[1]);	// �F��g

    	// �p�G�O�Ĥ@������A��a�ϩ�j��]�w�����šC
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
        mapUi.setZoomControlsEnabled(false);	// �����Y��a�Ϫ����s�C
        mapUi.setMyLocationButtonEnabled(false);	// ���éw����s�C
        mMap.setLocationSource(this);
        mMap.setMyLocationEnabled(true);

        // ���o�e�@�����w��C
        Location location = mLocationMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location == null)
        	location = mLocationMgr.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        if (location != null) {
        	Toast.makeText(MainActivity.this, "���\���o�e�@�����w��", Toast.LENGTH_LONG)
					.show();
        	onLocationChanged(location);	// ��s�a�Ϫ��w��C
        } else
        	Toast.makeText(MainActivity.this, "�e�@�����w��Ǧ^null", Toast.LENGTH_LONG)
					.show();
	}

	private AdapterView.OnItemSelectedListener spnMapTypeOnItemSelected =
	    	new AdapterView.OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView parent,
								View v, int position, long id) {
					// TODO Auto-generated method stub
					// �̷ӨϥΪ��I�諸���ئ�m�A���ܦa�ϼҦ��C
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
			// �]�w�a�Ϫ��������סA�åB��j��@�w�����šA��3D�ؿv���X�{�C
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
		Toast.makeText(MainActivity.this, "�a�Ϫ�my-location layer�w�g�ҥ�", Toast.LENGTH_LONG)
				.show();
	}

	@Override
	public void deactivate() {
		// TODO Auto-generated method stub
		mLocationChangedListener = null;
		disableLocationUpdate();
		Toast.makeText(MainActivity.this, "�a�Ϫ�my-location layer�w�g����", Toast.LENGTH_LONG)
				.show();
	}

	@Override
	public void onLocationChanged(Location location) {
		// ��s��m�ǵ�Google Map��my-location layer�C
		if (mLocationChangedListener != null)
            mLocationChangedListener.onLocationChanged(location);

        // ���ʦa�Ϩ�s��m�C
        mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
	}

	@Override
	public void onProviderDisabled(String provider) {
		Toast.makeText(MainActivity.this, provider + "�w��\��w�g�Q����", Toast.LENGTH_LONG)
		.show();
	}

	@Override
	public void onProviderEnabled(String provider) {
		Toast.makeText(MainActivity.this, provider + "�w��\��}��", Toast.LENGTH_LONG)
		.show();
		enableLocationUpdate();
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		String str = provider;
		switch (status) {
		case LocationProvider.OUT_OF_SERVICE:
			str += "�w��\��L�k�ϥ�";
			break;
		case LocationProvider.TEMPORARILY_UNAVAILABLE:
			str += "�ȮɵL�k�w��";	// GPS���b�w�줤�ɷ|�ǤJ�o�ӭȡC
			break;
		}

		Toast.makeText(MainActivity.this, str, Toast.LENGTH_LONG)
				.show();
	}

	private void enableLocationUpdate() {
        // �p�GGPS�\�঳�}�ҡA�u���ϥ�GPS�w��A�_�h�ϥκ����w��C
        if(mLocationMgr.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            mLocationMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);
        	Toast.makeText(MainActivity.this, "�ϥ�GPS�w��", Toast.LENGTH_LONG)
					.show();
        } else if(mLocationMgr.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
        	mLocationMgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);
        	Toast.makeText(MainActivity.this, "�ϥκ����w��", Toast.LENGTH_LONG)
					.show();
        }
	}

	private void disableLocationUpdate() {
		mLocationMgr.removeUpdates(this);
		Toast.makeText(MainActivity.this, "�w��\��w�g����", Toast.LENGTH_LONG)
				.show();
	}
}