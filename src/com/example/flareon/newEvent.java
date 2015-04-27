package com.example.flareon;

import java.util.ArrayList;
import java.util.List;

import android.R.string;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flareon.mainList.addEventData;
import com.example.flareon.mainList.eventDataAdder;

public class newEvent extends android.support.v4.app.Fragment   {
	
	private eventData mEventdata;
	private String eNmae;
	private String eLocation;
	private String ePosition;
	private String eDate;
	private String eTime;
	private List<String> efriendsList;
	private ListView mListV;
	private Button mAddPpl;
	private Button mAddLocation;
	private mainList.addEventData mEventadder;
	private Button mOK;
	private ArrayAdapter<String> mAdapter;
	private Button mDateTIme;
	private FragmentActivity  mActive;
	private EditText txtName;
	private TextView txtLocation;
	private List<String> allFriendsList;
	
	newEvent(addEventData adder, List<String>all)
	{
		allFriendsList = new ArrayList<String>();
		mEventadder = adder;
		allFriendsList = all;
	}
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		mActive = (FragmentActivity)activity;
	}
	
	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		onDestroy();
	}
	
	@Override
	public void setMenuVisibility(boolean menuVisible) {
		// TODO Auto-generated method stub
			
		super.setMenuVisibility(menuVisible);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		efriendsList = new ArrayList<String>();
		
		mEventdata = ((eventDataAdder)mEventadder).mData;
		if(mEventdata != null)
		{
			eNmae = mEventdata.getName();
			eLocation = mEventdata.getLocation();
			ePosition = mEventdata.getPosition();
			eDate = mEventdata.getDate();
			eTime = mEventdata.getTime();
			efriendsList = mEventdata.getFriends();
		}
		View v = inflater.inflate(R.layout.newevent,container,false);
		txtName = (EditText)v.findViewById(R.id.txtEventNmae);
		if (eNmae != null)
		{txtName.setText(eNmae);}
		txtLocation = (EditText)v.findViewById(R.id.txtLocationName);
		if(eLocation != null)
		{txtLocation.setText(eLocation);}
		mListV = (ListView)v.findViewById(R.id.LinPplList);
		mListV.setOnItemClickListener(new mListen());
		mAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, efriendsList);
		mListV.setAdapter(mAdapter);
		mDateTIme = (Button)v.findViewById(R.id.btnDateTime);
		mDateTIme.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dateTimeFrag mDateFrag = new dateTimeFrag(new dateTimeGetter());
				mDateFrag.show(mActive.getSupportFragmentManager(), "dateFrag");
			}
		});
		mAddLocation = (Button)v.findViewById(R.id.btnLocation);
		mAddLocation.setOnClickListener(new OnClickListener() {
			
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				MapLayout mMapL = new MapLayout(ePosition, new LocationGetter());
				
				mMapL.show(mActive.getSupportFragmentManager(), "Frag");
			}
		});
		mAddPpl = (Button)v.findViewById(R.id.btnAddppl);
		mAddPpl.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				addPplClick();
			}
		});
		
		
		mOK = (Button)v.findViewById(R.id.btnEventOK);
		mOK.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				eNmae = txtName.getText().toString();
				eLocation = txtLocation.getText().toString();
				
				if(eNmae == null || eNmae.replace(" ", "") == "" || eNmae.length() == 0)
				{Toast.makeText(getActivity(), "Enter an Event Name", Toast.LENGTH_LONG).show();
					return;}
				if(eLocation == null || eLocation.replace(" ", "") == "" || eLocation.length() == 0)
				{Toast.makeText(getActivity(), "Enter an Location Name", Toast.LENGTH_LONG).show();
					return;}
				if(efriendsList.size() <=0)
				{Toast.makeText(getActivity(), "Enter Friends who are attending the event", Toast.LENGTH_LONG).show();
				return;}
				if(eDate == null || eDate.replace(" ", "") == "" || eDate.length() == 0)
				{Toast.makeText(getActivity(), "Enter the event date", Toast.LENGTH_LONG).show();
					return;}
				if(eTime == null || eTime.replace(" ", "") == "" || eTime.length() == 0)
				{Toast.makeText(getActivity(), "Enter the event time", Toast.LENGTH_LONG).show();
					return;}
				if(ePosition == null || ePosition.replace(" ", "") == "" || ePosition.length() == 0)
				{Toast.makeText(getActivity(), "Select the location of the event", Toast.LENGTH_LONG).show();
					return;}
				Boolean isNew = false;
				if(mEventdata == null)
				{isNew = true;}
				if(isNew)
				{mEventdata = new eventData(0, eNmae, eLocation, ePosition, eDate, eTime, 0, efriendsList);}
				else
				{mEventdata = new eventData(mEventdata.getEid(), eNmae, eLocation, ePosition, eDate, eTime, mEventdata.getServerId(), efriendsList);}
				mEventadder.addDatatoInternalDB(mEventdata);
				if(isNew)
				{mEventadder.addDatatoExternalDB(mEventdata);}
			}
		});
		return v;
		
	}
	
	public class mListen implements AdapterView.OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private void addPplClick()
	{
		addPpl pplFrag = new addPpl(new FriendsGetter(),allFriendsList);
		pplFrag.show(mActive.getSupportFragmentManager(), "addPpl");
		}

	public class LocationGetter
	{
		public void setLocation(String location)
		{
			ePosition = location;
		}
	}
	
	public class FriendsGetter
	{
		public void setFriends(String name)
		{
			if(efriendsList.contains(name))
			{
				Toast.makeText(mActive, "The user is already in event", Toast.LENGTH_LONG).show();
			}
			else
			{
			efriendsList.add(name);
			mAdapter.notifyDataSetChanged();
		}}
	}
	
	public class dateTimeGetter
	{
		public void setDateTime(String date, String time)
		{
			eDate = date;
			eTime = time;
		}
		
		public String getdate()
		{
			return eDate;
		}
		
		public String getTime()
		{
			return eTime;
		}
	}
}
