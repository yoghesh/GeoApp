package com.example.flareon;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements signupFrag.signupFragListener {

	private ProgressBar m_Bar;
	private Button m_btnSignup;
	private Button m_btnSignin;
	private SharedPreferences m_loginPref;
	public static android.support.v4.app.FragmentManager m_FragManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(com.example.flareon.R.layout.activity_main);
		
		Boolean isExit = getIntent().getBooleanExtra("EXIT", false);
		if(isExit)
		{finish();
		return;}
	
		m_btnSignin = (Button)findViewById(com.example.flareon.R.id.btnSignin);
		m_btnSignup = (Button)findViewById(com.example.flareon.R.id.btnSignup);
		m_FragManager = getSupportFragmentManager();
		m_btnSignin.setEnabled(false);
		m_btnSignup.setEnabled(false);
		m_btnSignup.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				signupFrag mDlgFrag = new signupFrag();
				mDlgFrag.show(getFragmentManager(), "SignUp");
			}
		});
		m_btnSignin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				signInFrag mDlgfrag = new signInFrag();
				mDlgfrag.show(getFragmentManager(), "Signin");
				
			}
		});
		
		m_Bar = (ProgressBar)findViewById(R.id.progressBar1);
		m_Bar.setVisibility(View.VISIBLE);
		m_Bar.setBackgroundResource(R.drawable.progressrotateblue);
		m_loginPref= getSharedPreferences("Login", MODE_PRIVATE);
		String username = m_loginPref.getString("name", "");
		String authKey = m_loginPref.getString("auth", "");
		String appKey = "sdjfba6+54KSHFSH&LKHFkl;jfsd6854584y5*^";
		if (username == "")
		{
			//Intent listIntent = new Intent(getApplicationContext(),mainList.class);
			//startActivity(listIntent);
			//user not logged in
			m_Bar.setVisibility(View.INVISIBLE);
			m_btnSignin.setEnabled(true);
			m_btnSignup.setEnabled(true);
		}
		else
		{
			//Intent listIntent = new Intent(getApplicationContext(),mainList.class);
			//startActivity(listIntent);
			
			Toast.makeText(this, "Please wait trying to login", Toast.LENGTH_LONG).show();
			JSONObject connectorInput[] = new JSONObject[1];
			JSONObject inputData = new JSONObject();
			try {
				inputData.put("URL", "http://geofinder.net84.net/Geofinder/auth.php");
				inputData.put("username", username);
				inputData.put("authkey", authKey);
				inputData.put("appkey", appKey);
				inputData.put("type", 2);
		
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Toast.makeText(getApplicationContext(), "Unable to process the request. Please login", Toast.LENGTH_LONG).show();
				m_btnSignin.callOnClick();
				return;
			}
		
			connectorInput[0] = inputData;
			httpConnector myHttpcon = new httpConnector();
			myHttpcon.setListen(new httpConnector.Datainterface() {
				
				@Override
				public void getUnsuccessData() {
					m_Bar.setVisibility(View.INVISIBLE);
					m_btnSignin.setEnabled(true);
					m_btnSignup.setEnabled(true);
					// TODO Auto-generated method stub
					Toast.makeText(getApplicationContext(), "Problem connecting to server.Please try after some time", Toast.LENGTH_LONG).show();
				}
				
				@Override
				public void getSuccessData(JSONArray data) {
					try {
						m_Bar.setVisibility(View.INVISIBLE);
						m_btnSignin.setEnabled(true);
						m_btnSignup.setEnabled(true);
						JSONObject a = data.getJSONObject(0);
						if (a.getInt("code") == 3)
						{Toast.makeText(getApplicationContext(), "Credentials not matching please login", Toast.LENGTH_LONG).show();
						m_btnSignin.callOnClick();
						return;}
						if (a.getInt("code") == 5)
						{Toast.makeText(getApplicationContext(), "Unable to find the username please login", Toast.LENGTH_LONG).show();
						m_btnSignin.callOnClick();
						return;}
						if (a.getInt("code") == 6)
						{Toast.makeText(getApplicationContext(), "Unauthorized access", Toast.LENGTH_LONG).show();
						return;}
						
						a = data.getJSONObject(1);
						String key = a.getString("authkey");
						
						Editor editor = m_loginPref.edit();
						editor.putString("auth", key);
						editor.commit();
						Intent listIntent = new Intent(getApplicationContext(),mainList.class);
						startActivity(listIntent);
						
						
						Toast.makeText(getApplicationContext(), "Succcessful Login", Toast.LENGTH_LONG).show();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						m_Bar.setVisibility(View.INVISIBLE);
						e.printStackTrace();
						Toast.makeText(getApplicationContext(), "Unable to authenticate please login", Toast.LENGTH_LONG).show();
						m_btnSignin.callOnClick();
					}
					
					
				}
			});
			myHttpcon.execute(connectorInput);
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(com.example.flareon.R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == com.example.flareon.R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void onDatarecieve(List<NameValuePair> data) {
		
		m_Bar.setVisibility(View.VISIBLE);
		String name = "";
		String mail = "";
		String pass = "";
		for (int i = 0; i < data.size(); i++)
		{
			NameValuePair singlePair = data.get(i);
			if(singlePair.getName() == "username")
			{name = singlePair.getValue();}
			else if(singlePair.getName() == "mail")
			{mail = singlePair.getValue();}
			else if(singlePair.getName() == "password")
			{pass = singlePair.getValue();}
		}
		
		int type = 0;
		JSONObject connectorInput[] = new JSONObject[1];
		JSONObject inputData = new JSONObject();
		try {
			inputData.put("URL", "http://geofinder.net84.net/Geofinder/auth.php");
			inputData.put("username", name);
			inputData.put("mailid", mail);
			inputData.put("password", pass);
			if (pass == "")
			{type = 0;
			m_Bar.setBackgroundResource(R.drawable.progressrotatered);
			inputData.put("type", 0);}
			else if (mail == "")
			{m_Bar.setBackgroundResource(R.drawable.progressrotateblue);
				inputData.put("type", 1);
			type  = 1; }
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), "Unable to process the request. Please try agin later", Toast.LENGTH_LONG).show();
			m_Bar.setVisibility(View.INVISIBLE);
			return;
		}
	
		connectorInput[0] = inputData;
		httpConnector myHttpcon = new httpConnector();
		myHttpcon.setListen(new httpConnector.Datainterface() {
			
			@Override
			public void getUnsuccessData() {
				// TODO Auto-generated method stub
				m_Bar.setVisibility(View.INVISIBLE);
				Toast.makeText(getApplicationContext(), "Problem connecting to server.Please try after some time", Toast.LENGTH_LONG).show();
			}
			
			@Override
			public void getSuccessData(JSONArray data) {
				try {
					m_Bar.setVisibility(View.INVISIBLE);
					JSONObject a = data.getJSONObject(0);
					int code = a.getInt("code");
					if (code == 0)
					{Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
					}
					else if (code == 1)
					{Toast.makeText(getApplicationContext(), "Error in connection please try again later", Toast.LENGTH_SHORT).show();
					return;}
					else if (code == 2)
					{Toast.makeText(getApplicationContext(), "Username already exist. Please enter another username", Toast.LENGTH_SHORT).show();
					return;}
					else if (code == 3)
					{Toast.makeText(getApplicationContext(), "Incorrect password. Please check the password entered", Toast.LENGTH_SHORT).show();
					return;}
					else if (code == 4)
					{Toast.makeText(getApplicationContext(), "Invalid email id please check the entered mail id", Toast.LENGTH_SHORT).show();
					return;}
					else if (code == 5)
					{Toast.makeText(getApplicationContext(), "Invalid username please check the entered username", Toast.LENGTH_SHORT).show();
					return;}
					else if (code == 7)
					{Toast.makeText(getApplicationContext(), "Email already exists.Please check the mailid", Toast.LENGTH_SHORT).show();
					return;}
					
					a = data.getJSONObject(1);
					String name = a.getString("username");
					a = data.getJSONObject(2);
					String key = a.getString("authkey");
					a = data.getJSONObject(3);
					String mail = a.getString("mail");
					
					Editor editor = m_loginPref.edit();
					editor.putString("name", name);
					editor.putString("mail", mail);
					editor.putString("auth", key);
					editor.commit();
					
					Intent listIntent = new Intent(getApplicationContext(),mainList.class);
					startActivity(listIntent);
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					m_Bar.setVisibility(View.INVISIBLE);
					e.printStackTrace();
				}
				
			}
		});
		myHttpcon.execute(connectorInput);
			}
	
	public void takeHome()
	{
		
	}
	
	public void waitLogin()
	{
		
	}
}
