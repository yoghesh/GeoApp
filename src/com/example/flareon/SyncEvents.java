package com.example.flareon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import com.google.android.gms.internal.mc;

import android.os.AsyncTask;

public class SyncEvents extends AsyncTask<String, Void, JSONArray> {

	public synEventLIsten mListen;
	
	@Override
	protected JSONArray doInBackground(String... params) {
		
		
		try {
			List<NameValuePair> mVal = new ArrayList<NameValuePair>();
			mVal.add(new BasicNameValuePair("name",params[0]));
			// TODO Auto-generated method stub
			HttpClient cli = new DefaultHttpClient();
			URI mUri;
			mUri = new URI("http://geofinder.net84.net/Geofinder/EventsSync.php");
			HttpPost req = new HttpPost(mUri);
			req.setEntity(new UrlEncodedFormEntity(mVal));
			HttpResponse resp = cli.execute(req);
			BufferedReader mRead = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));
			StringBuffer sr = new StringBuffer();
			String s;
			while ((s = mRead.readLine()) != null)
			{
				sr.append(s);
			}
			
			String ss = sr.toString();
			if(ss != null && ss != "")
			{JSONArray mAr = new JSONArray(sr.toString());
			return mAr;}
			
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
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	
	@Override
	protected void onPostExecute(JSONArray result) {
		// TODO Auto-generated method stub
		mListen.setEvents(result);
	}
	
	public interface synEventLIsten
	{
		public void setEvents(JSONArray val);
	}
}
