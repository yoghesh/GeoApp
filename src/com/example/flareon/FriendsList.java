package com.example.flareon;

import java.util.ArrayList;
import java.util.List;

import com.example.flareon.mainList.addFriendToDB;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class FriendsList extends Fragment {

	private ListView lstFriends;
	private List<String> strList;
	private Button btnAdd;
	public addFriendToDB mAddtoDB;
	private FragmentActivity mAct;
	private ListView mList;
	private ArrayAdapter<String> mAdapter;
	
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	View v = inflater.inflate(R.layout.friendslist, container, false);
	//strList = new ArrayList<String>();
	mAdapter = new ArrayAdapter<String>(mAct, android.R.layout.simple_list_item_1, strList);
	mList = (ListView)v.findViewById(R.id.lstFriends);
	mList.setAdapter(mAdapter);
	btnAdd = (Button)v.findViewById(R.id.btnAddFriends);
	btnAdd.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			AddFriendFragment mAdd = new AddFriendFragment();
			mAdd.mGetter = new oneFriendGetter();
			mAdd.show(mAct.getSupportFragmentManager(), "Add");
		}
	});
	return v;
}	

@Override
public void onAttach(Activity activity) {
	// TODO Auto-generated method stub
	mAct = (FragmentActivity)activity;
	super.onAttach(activity);
}

public class oneFriendGetter
{
	private String mName;
	 public void setFriend(String name)
	 {
		 mName = name;
		 if (strList.contains(name))
		 {
			 Toast.makeText(mAct, "The friend already exist", Toast.LENGTH_LONG).show();
		 }
		 else
		 {
		 strList.add(name);
		 mAddtoDB.addTodb(name);}
	 }
	}

public void allFriends(List<String> val)
{
	strList = val;
	}
	
}
