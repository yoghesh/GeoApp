package com.example.flareon;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.example.flareon.signupFrag.signupFragListener;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class signInFrag extends DialogFragment {

	public static interface signinFragListener
	{
		public abstract void onDatarecieve(List<NameValuePair> data);
	}
	
	private signupFragListener m_Listener;
	private EditText m_txtUsername;
	private EditText m_txtPass;
	private Button m_signin;
	
	public signInFrag()
	{}
	
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreateView(inflater, container, savedInstanceState);
	View v = inflater.inflate(com.example.flareon.R.layout.signin_frag, container);
	getDialog().getWindow().requestFeature(STYLE_NO_TITLE);
	m_txtUsername = (EditText)v.findViewById(com.example.flareon.R.id.txtname);
	m_txtPass = (EditText)v.findViewById(com.example.flareon.R.id.txtpass);
	m_signin = (Button)v.findViewById(com.example.flareon.R.id.btnsOK);
	m_signin.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			if(!checkdata())
			{return;}
			List<NameValuePair> mdata = new ArrayList<NameValuePair>();
			mdata.add(new BasicNameValuePair("username", m_txtUsername.getText().toString()));
			mdata.add(new BasicNameValuePair("password", m_txtPass.getText().toString()));
			m_Listener.onDatarecieve(mdata);
			dismiss();
			
		}
	});
	return v;
	
}

@Override
public void onAttach(Activity activity) {
	// TODO Auto-generated method stub
	super.onAttach(activity);
	m_Listener = (signupFragListener)activity;
}

@Override
public void onActivityCreated(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onActivityCreated(savedInstanceState);
	getDialog().getWindow().getAttributes().windowAnimations = R.style.signupstyle;
}
public boolean checkdata()
{
	String temp = "";
	temp = m_txtUsername.getText().toString();
	temp = temp.replaceAll(" ", "");
	if (temp == "")
	{
		Toast.makeText(getActivity(), "Please enter username", Toast.LENGTH_LONG).show();
		return false;
		}
	temp = m_txtPass.getText().toString();
	temp = temp.replaceAll(" ", "");
	if (temp == "")
	{
		Toast.makeText(getActivity(), "Please enter password", Toast.LENGTH_LONG).show();
		return false;
		}
	
	return true;
}

}
