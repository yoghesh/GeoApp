package com.example.flareon;

import com.example.flareon.FriendsList.oneFriendGetter;
import com.example.flareon.newEvent.FriendsGetter;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class AddFriendFragment extends DialogFragment {

	private Button btnOK;
	private EditText txtName;
	public oneFriendGetter mGetter;
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	View v = inflater.inflate(R.layout.addfriends, container);
	txtName = (EditText)v.findViewById(R.id.txtAddFriend);
	btnOK = (Button)v.findViewById(R.id.btnAddFriendOK);
	btnOK.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String name = txtName.getText().toString();
			mGetter.setFriend(name);
			dismiss();
		}
	});
	return v;
}	
}
