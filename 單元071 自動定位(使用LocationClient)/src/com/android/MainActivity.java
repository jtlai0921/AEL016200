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
			{"�x�_101", "25.0336110,121.5650000"},
			{"�������", "40.0000350,119.7672800"},
			{"�ì��ۥѤk����", "40.6892490,-74.0445000"},
			{"�ھ��K��", "48.8582220,2.2945000"}	};

	private GoogleMap mMap;
	private Spinner mSpnLocation;
	private MapFragment mMapFragment;
	private boolean mbIsZoomFirst = true;

	private LocationClient mLocationClient;		// ���o�w���ơC
	private LocationRequest mLocationRequest;	// �]�w�w��ѼơC
	private LocationManager mLocationMgr;		// �M�w�ϥ�GPS�άO�����w��C

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

        // �إߤ@��LocationClient����C
        mLocationClient = new LocationClient(this, this, this);

        // �ǳƤ@��LocationRequest����A�]�w�w��ѼơA�b�Ұʩw��ɨϥΡC
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(5000);	 // �G���w�줧�����ɶ����j�A���O�d�����@��C
        mLocationRequest.setSmallestDisplacement(5);	// �G���w�줧�����̤j�Z���A���O���ءC

        mLocationMgr = (LocationManager) getSystemService(LOCATION_SERVICE);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		// �PLocation Services�s�u�C
		mLocationClient.connect();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

		if (mLocationClient.isConnected()) {
			// ����w��C
			mLocationClient.removeLocationUpdates(this);

			// �_�}Location Services���s�u�C
			mLocationClient.disconnect();
			Toast.makeText(MainActivity.this, "Location Services�w�g�_�u", Toast.LENGTH_LONG)
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
    	    	// Google Play Services�w�g�ѨMLocation Services���s�u���D�A�A���ճs�u�@���C
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
    	double dLat = Double.parseDouble(sLocation[0]);	// �n�_�n
    	double dLon = Double.parseDouble(sLocation[1]);	// �F��g

    	// �p�G�O�Ĥ@������A��a�ϩ�j��]�w�����šC
    	if (mbIsZoomFirst) {
    		mbIsZoomFirst = false;
    		mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    	} else
	    	mMap.animateCamera(CameraUpdateFactory.newLatLng(
					new LatLng(dLat, dLon)));
   }

	private void setUpMap() {
		UiSettings mapUi = mMap.getUiSettings();
        mapUi.setZoomControlsEnabled(false);	// �����Y��a�Ϫ����s�C
//        mMap.setMyLocationEnabled(true);
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
	public void onConnectionFailed(ConnectionResult connectionResult) {
		// TODO Auto-generated method stub
		Toast.makeText(MainActivity.this, "Location Services�s�u����", Toast.LENGTH_LONG)
				.show();

		// ���էQ��Google Play services�ӸѨM���D�C
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
		Toast.makeText(MainActivity.this, "Location Services�w�g�s�u", Toast.LENGTH_LONG)
				.show();

		// �Ұʩw��C
        if(mLocationMgr.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        	Toast.makeText(MainActivity.this, "�ϥ�GPS�w��", Toast.LENGTH_LONG)
					.show();
        } else if(mLocationMgr.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            mLocationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);
        	Toast.makeText(MainActivity.this, "�ϥκ����w��", Toast.LENGTH_LONG)
					.show();
        }
		mLocationClient.requestLocationUpdates(mLocationRequest, this);

        // ���o�e�@�����w��C
        Location location = mLocationClient.getLastLocation();

        if (location != null) {
        	Toast.makeText(MainActivity.this, "���\���o�e�@�����w��", Toast.LENGTH_LONG)
					.show();
        	onLocationChanged(location);	// ��s�a�Ϫ��w��C
        } else
        	Toast.makeText(MainActivity.this, "�e�@�����w��Ǧ^null", Toast.LENGTH_LONG)
					.show();
	}

	@Override
	public void onDisconnected() {
		// ��Location Services�o�Ͱ��D�A�L�G�_�u�ɡA�~�|����o�Ӥ�k�C
		// �{���I�sLocationClient��disconnect()�ɤ��|����o�Ӥ�k�C
		Toast.makeText(MainActivity.this, "Location Services�o�Ͱ��D�A�w�g�_�u", Toast.LENGTH_LONG)
				.show();
	}

	private void showDlgLocationServicesConnectionFailAndExitApp() {
		AlertDialog.Builder altDlg = new AlertDialog.Builder(this);
		altDlg.setTitle("���~");
		altDlg.setMessage("�L�k�s�uLocation Services�A�{���L�k����C");
		altDlg.setIcon(android.R.drawable.ic_dialog_info);
		altDlg.setCancelable(false);
		altDlg.setPositiveButton("�T�w",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						finish();  // ����App�C
					}
				});
		altDlg.show();
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		// ���ʦa�Ϩ�s��m�C
		LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

        // ��sMarker�C
        mMap.clear();
        mMap.addMarker(new MarkerOptions()
			.position(latLng)
			.icon(BitmapDescriptorFactory.fromResource(android.R.drawable.btn_star_big_on))
			.anchor(0.5f, 0.5f));
	}

}