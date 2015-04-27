package com.example.flareon;

import com.example.flareon.newEvent.LocationGetter;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class MapLayout extends android.support.v4.app.DialogFragment{

	private FragmentManager mSupportFrag;
	private SupportMapFragment mMap;
	private GoogleMap myMap;
	private Marker mMarker;
	private String location;
	private LocationGetter mGetter;
	
	MapLayout(String loc, LocationGetter getter)
	{
		location = loc;
		mGetter = getter;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mMarker = null;
		View v = inflater.inflate(R.layout.maplayout, container, false);
		getDialog().getWindow().requestFeature(STYLE_NO_TITLE);
    	return v;
	}
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		mSupportFrag = ((FragmentActivity)activity).getSupportFragmentManager();
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		mMap = SupportMapFragment.newInstance();
		getChildFragmentManager().beginTransaction().replace(R.id.mapFrame,mMap).commit();
		
		Handler myHandle = new Handler();
		myHandle.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				myMap = mMap.getMap();
				myMap.setMyLocationEnabled(true);
				myMap.getUiSettings().setZoomControlsEnabled(true);
				myMap.setOnMapLongClickListener(new mapListener());
				if(location != "" && location != null)
				{
					String[] locationSplit = location.split(",");
					LatLng local = new LatLng(Double.parseDouble(locationSplit[0]), Double.parseDouble(locationSplit[1]));
					mMarker = myMap.addMarker(new MarkerOptions().position(local).title("Event"));
				}
			}
		}, 1000);
		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		getDialog().getWindow().getAttributes().windowAnimations = R.style.signupstyle;
	}

	public class mapListener implements OnMapLongClickListener
	{
		@Override
		public void onMapLongClick(LatLng position) {
			// TODO Auto-generated method stub
			if (mMarker != null)
			{mMarker.remove();
			mMarker = null;}
			mMarker = myMap.addMarker(new MarkerOptions().position(position).title("Event"));
						
			location = mMarker.getPosition().toString();
			String[] splLoc = location.split(" ");
			splLoc[1] = splLoc[1].replace("(", "");
			splLoc[1] = splLoc[1].replace(")", "");
			
			location = splLoc[1];
			Toast.makeText(getActivity(), location, Toast.LENGTH_LONG).show();
		}
	}
	
	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		mGetter.setLocation(location);
	}
	
	
}
