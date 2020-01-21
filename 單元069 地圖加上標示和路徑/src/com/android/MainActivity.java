package com.android;

import java.util.ArrayList;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.graphics.Color;
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
import android.widget.TextView;
import android.os.Build;

public class MainActivity extends Activity {

	private static String[][] mLocations = {
			{"台北101", "25.0336110,121.5650000"},
			{"中國長城", "40.0000350,119.7672800"},
			{"紐約自由女神像", "40.6892490,-74.0445000"},
			{"巴黎鐵塔", "48.8582220,2.2945000"}	};

	private GoogleMap mMap;
	private Spinner mSpnLocation;
	private MapFragment mMapFragment;
	private boolean mbIsZoomFirst = true;

	private Marker mMarker1, mMarker2, mMarker3, mMarker4;
	private Polyline mPolylineRoute;

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

        // 設定4個新按鈕的OnClickListener。
        Button btnAddMarker = (Button) findViewById(R.id.btnAddMarker);
        btnAddMarker.setOnClickListener(btnAddMarkerOnClick);
        Button btnRemoveMarker = (Button) findViewById(R.id.btnRemoveMarker);
        btnRemoveMarker.setOnClickListener(btnRemoveMarkerOnClick);
        Button btnShowRoute = (Button) findViewById(R.id.btnShowRoute);
        btnShowRoute.setOnClickListener(btnShowRouteOnClick);
        Button btnHideRoute = (Button) findViewById(R.id.btnHideRoute);
        btnHideRoute.setOnClickListener(btnHideRouteOnClick);
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

        // 設定Google Map的Info Window。
        mMap.setInfoWindowAdapter(new InfoWindowAdapter() {

			@Override
			public View getInfoContents(Marker marker) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public View getInfoWindow(Marker marker) {
				// TODO Auto-generated method stub
				View v = getLayoutInflater()
							.inflate(R.layout.map_info_window, null);
				TextView txtTitle = (TextView) v.findViewById(R.id.txtTitle);
				txtTitle.setText(marker.getTitle());
				TextView txtSnippet = (TextView) v.findViewById(R.id.txtSnippet);
				txtSnippet.setText(marker.getSnippet());
				return v;
			}});

        // 設定Info Window的OnClickListener。
		mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
			
			@Override
			public void onInfoWindowClick(Marker marker) {
				// TODO Auto-generated method stub
				marker.hideInfoWindow();
			}
		});

		// 建立Polyline，並且先將它隱藏。
		PolylineOptions polylineOpt = new PolylineOptions()
						.width(15)
						.color(Color.BLUE);
		ArrayList<LatLng> listLatLng = new ArrayList<LatLng>();
		listLatLng.add(new LatLng(25.0336110, 121.5650000));
		listLatLng.add(new LatLng(25.037, 121.5650000));
		listLatLng.add(new LatLng(25.037, 121.5630000));
		polylineOpt.addAll(listLatLng);
		mPolylineRoute = mMap.addPolyline(polylineOpt);
		mPolylineRoute.setVisible(false);
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

	private View.OnClickListener btnAddMarkerOnClick = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (mMarker1 != null)
				return;

			// 在4個景點加上Marker。
			String[] sLocation = mLocations[0][1].split(",");
	    	double dLat = Double.parseDouble(sLocation[0]);
	    	double dLon = Double.parseDouble(sLocation[1]);
			mMarker1 = mMap.addMarker(new MarkerOptions()
					.title(mLocations[0][0])
					.snippet("2004年完工,高509公尺")
					.position(new LatLng(dLat, dLon))
					.icon(BitmapDescriptorFactory.fromResource(R.drawable.arrows))
					.anchor(0.5f, 0.5f));

			sLocation = mLocations[1][1].split(",");
	    	dLat = Double.parseDouble(sLocation[0]);
	    	dLon = Double.parseDouble(sLocation[1]);
			mMarker2 = mMap.addMarker(new MarkerOptions()
					.title(mLocations[1][0])
					.snippet("東起山海關,西至嘉峪關,全長6000多公里")
					.position(new LatLng(dLat, dLon))
					.icon(BitmapDescriptorFactory.fromResource(R.drawable.circle))
					.anchor(0.5f, 0.5f));

			sLocation = mLocations[2][1].split(",");
	    	dLat = Double.parseDouble(sLocation[0]);
	    	dLon = Double.parseDouble(sLocation[1]);
			mMarker3 = mMap.addMarker(new MarkerOptions()
					.title(mLocations[2][0])
					.snippet("1886年完工,高93公尺")
					.position(new LatLng(dLat, dLon))
					.icon(BitmapDescriptorFactory.fromResource(R.drawable.square))
					.anchor(0.5f, 0.5f));

			sLocation = mLocations[3][1].split(",");
	    	dLat = Double.parseDouble(sLocation[0]);
	    	dLon = Double.parseDouble(sLocation[1]);
			mMarker4 = mMap.addMarker(new MarkerOptions()
					.title(mLocations[3][0])
					.snippet("又稱為艾菲爾鐵塔,高324公尺")
					.position(new LatLng(dLat, dLon))
					.icon(BitmapDescriptorFactory.fromResource(R.drawable.star))
					.anchor(0.5f, 0.5f));
		}
	};

	private View.OnClickListener btnRemoveMarkerOnClick = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			mMarker1.remove();
			mMarker1 = null;
			mMarker2.remove();
			mMarker2 = null;
			mMarker3.remove();
			mMarker3 = null;
			mMarker4.remove();
			mMarker4 = null;
		}
	};

	private View.OnClickListener btnShowRouteOnClick = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			mPolylineRoute.setVisible(true);
		}
	};

	private View.OnClickListener btnHideRouteOnClick = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			mPolylineRoute.setVisible(false);
		}
	};
}