package com.example.flareon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class AddEventOnline extends AsyncTask<eventData, Void, String>{

	private String mName;
	public AddEventOnline(String name) {
		// TODO Auto-generated constructor stub
		mName = name;
	}
	@Override
	protected String doInBackground(eventData... params) {
		// TODO Auto-generated method stub
		
		try {
			
			
			HttpClient mClient = new DefaultHttpClient();
			URI mUri;
			mUri = new URI("http://geofinder.net84.net/Geofinder/addEve.php");
			HttpPost mPost = new HttpPost(mUri);
			List<NameValuePair> mC = new ArrayList<NameValuePair>();
			mC.add(new BasicNameValuePair("EName", params[0].getName()));
			mC.add(new BasicNameValuePair("Location", params[0].getLocation()));
			mC.add(new BasicNameValuePair("Position", params[0].getPosition()));
			mC.add(new BasicNameValuePair("Date", params[0].getDate()));
			mC.add(new BasicNameValuePair("Time", params[0].getTime()));
			mC.add(new BasicNameValuePair("ServerID", params[0].getServerId().toString()));
			mC.add(new BasicNameValuePair("Friends[]", mName));
			for(int i =0; i < params[0].getFriends().size(); i++)
			{
				mC.add(new BasicNameValuePair("Friends[]", params[0].getFriends().get(i)));
			}
			
			mPost.setEntity(new UrlEncodedFormEntity(mC));
			
			HttpResponse mResp = mClient.execute(mPost);
			BufferedReader mRead = new BufferedReader(new InputStreamReader(mResp.getEntity().getContent()));
			
			StringBuffer mStr = new StringBuffer();
			String line = "";
			
			while((line = mRead.readLine()) != null)
			{
				mStr.append(line);
			}
			
			String allD = mStr.toString();
			String b = allD;
			
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return null;
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		
		
		super.onPreExecute();
		
	}

}
