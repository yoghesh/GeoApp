package com.example.flareon;

import java.util.ArrayList;
import java.util.List;

import com.example.flareon.newEvent.FriendsGetter;

import android.R.string;
import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class addPpl extends android.support.v4.app.DialogFragment {
	
	//private Button addPpl;
	private ListView mList;
	//private EditText mName;
	private List<String> pplNames = new ArrayList<String>();
	//private List<String> bupNames = new ArrayList<String>();
	private FriendsGetter mGetter;
	//private Button btnOK;
	
	addPpl(FriendsGetter getter, List<String> plList)
	{
		mGetter = getter;
		pplNames = plList;
	}
	
	private ArrayAdapter<String> adapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.addppl, container,false);
		getDialog().getWindow().requestFeature(STYLE_NO_TITLE);
		mList = (ListView)v.findViewById(R.id.lstpplAddppl);
		adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,pplNames);
		//addPpl = (Button)v.findViewById(R.id.btnPpladd);
		//mName = (EditText)v.findViewById(R.id.txtPplname);
		//addPpl.setOnClickListener(new OnClickListener() {
		//	
		//	
		//	@Override
		//	public void onClick(View v) {
		//		// TODO Auto-generated method stub
		//		addInList();
		//	}
		//});
		mList.setAdapter(adapter);
		//btnOK = (Button)v.findViewById(R.id.btnPplOK);
		//btnOK.setOnClickListener(new OnClickListener() {
		//	
		//	@Override
		//	public void onClick(View v) {
		//		// TODO Auto-generated method stub
		//		mGetter.setFriends(pplNames);
		//		dismiss();
		//	}
		//});
		mList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				mGetter.setFriends(pplNames.get(arg2));
			}
		});
		return v;
	}
	
//	public void addInList()
//	{
//		
//		pplNames.add(mName.getText().toString());
//		adapter.notifyDataSetChanged();
///		
//	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		getDialog().getWindow().getAttributes().windowAnimations = R.style.signupstyle;
	}
}
