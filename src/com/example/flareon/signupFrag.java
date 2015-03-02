package com.example.flareon;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.example.flareon.signupFrag.domainChecker.domainListener;

import android.R.bool;
import android.app.ActionBar;
import android.app.Activity;
import android.app.DialogFragment;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class signupFrag extends DialogFragment {

	public static interface signupFragListener
	{
		public abstract void onDatarecieve(List<NameValuePair> data);
	}

	private EditText m_txtName;
	private EditText m_txtemail;
	private EditText m_txtemail1;
	private Button m_btnOk;
	private signupFragListener m_listener;
	private domainListener m_DList;
	
	public signupFrag() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	super.onCreateView(inflater, container, savedInstanceState);
		View v = inflater.inflate(R.layout.signup_frag, container,false);
		getDialog().getWindow().requestFeature(STYLE_NO_TITLE);
		m_txtName = (EditText)v.findViewById(R.id.txtName);
		m_txtemail = (EditText)v.findViewById(R.id.txtMail);
		m_txtemail1 = (EditText)v.findViewById(R.id.txtMail1);
		m_btnOk = (Button)v.findViewById(R.id.btnOK);
		m_btnOk.setOnClickListener(new OnClickListener() {
		
			@Override
			public void onClick(View v) {
				if (!checkdata())
				{return;}
				//if (!isValidEmailAddress(m_txtemail.getText().toString()))
				//{Toast.makeText(getActivity(), "Please enter a valid email id", Toast.LENGTH_LONG).show();
					//return;}
				List<NameValuePair> mData = new ArrayList<NameValuePair>();
				mData.add(new BasicNameValuePair("username", m_txtName.getText().toString()));
				mData.add(new BasicNameValuePair("mail", m_txtemail.getText().toString()));
				mData.add(new BasicNameValuePair("mail1", m_txtemail1.getText().toString()));
				m_listener.onDatarecieve(mData);
				dismiss();
				
			}
		});
		
		return v;
	}
	
	public boolean checkdata()
	{
		String temp = "";
		temp = m_txtName.getText().toString();
		temp = temp.replaceAll(" ", "");
		if (temp == "")
		{
			Toast.makeText(getActivity(), "Please enter a username", Toast.LENGTH_LONG).show();
			return false;
			}
		temp = m_txtemail.getText().toString();
		temp = temp.replaceAll(" ", "");
		if (temp == "")
		{
			Toast.makeText(getActivity(), "Please enter a mailid", Toast.LENGTH_LONG).show();
			return false;
			}
		
		
		
		temp = m_txtemail1.getText().toString();
		temp = temp.replaceAll(" ", "");
		if (temp == "")
		{
			Toast.makeText(getActivity(), "Please confirm the mailid", Toast.LENGTH_LONG).show();
			return false;
			}
		
		String temp1 = "";
		Integer val;
		temp = m_txtemail.getText().toString();
		temp1 = m_txtemail1.getText().toString();
		
		if (temp.equals(temp1))
		{//The email matches
			}
		
		else
		{
			Toast.makeText(getActivity(), "The mailid doesnot match please try again", Toast.LENGTH_LONG).show();
			return false;
		}
		
		return true;
	}
	
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		m_listener = (signupFragListener)activity;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		getDialog().getWindow().getAttributes().windowAnimations = R.style.signupstyle;
	}
	
	public static class domainChecker extends AsyncTask<String, Void, Boolean>{

		public domainListener mListen;
		
		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				InetAddress domainV = InetAddress.getByName(params[params.length -1]);
				domainV.isReachable(2000);
			return true;
								
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			mListen.processData(result);
		}
		
		public static interface domainListener
		{
			void processData(Boolean val);
		}

}
	 public static boolean isValidEmailAddress(String aEmailAddress){
		    if (aEmailAddress == null) return false;
		    boolean result = true;
		    try {
		      InternetAddress emailAddr = new InternetAddress(aEmailAddress);
		      if (! hasNameAndDomain(aEmailAddress)) {
		        result = false;
		      }
		    }
		    catch (AddressException ex){
		      result = false;
		    }
		    return result;
		  }

		  private static boolean hasNameAndDomain(String aEmailAddress){
		    String[] tokens = aEmailAddress.split("@");
		    return  tokens.length == 2 && tokens[0] != null && tokens[0].trim().length()>0
		    		&& tokens[1] != null && tokens[1].trim().length()>0;
		  }
		  
	
		}
	
