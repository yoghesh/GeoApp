package com.example.flareon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Handler;

public class Syncdata extends AsyncTask<String, Void, JSONArray> {

	private syncFriend mListem;
	
	public void  setList(syncFriend val)
	{mListem = val;}
	@Override
	protected JSONArray doInBackground(String... params) {
		// TODO Auto-generated method stub
		HttpClient client = new DefaultHttpClient();
		URI myURI;
		List<NameValuePair> mList = new ArrayList<NameValuePair>();
	
			
			try {
				mList.add(new BasicNameValuePair("name",params[0]));
				myURI = new URI("http://geofinder.net84.net/Geofinder/friendsSync.php");
				HttpPost mPost = new HttpPost(myURI);
				mPost.setEntity(new UrlEncodedFormEntity(mList));
				HttpResponse mResp = client.execute(mPost);
				
				BufferedReader mRead = new BufferedReader(new InputStreamReader(mResp.getEntity().getContent()));
				StringBuffer sb = new StringBuffer();
				String line = "";
				while((line = mRead.readLine()) != null)
				{
					sb.append(line);
				}
				
				String ss = sb.toString();
				if(ss != null && ss != "")
				{
				JSONArray mObj = new JSONArray(sb.toString());
				//JSONObject m = mObj;
			return mObj;}
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
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
			
	}
	
	@Override
	protected void onPostExecute(final JSONArray result) {
		// TODO Auto-generated method stub
		new Handler().post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(result != null)
				{
				mListem.setdata(result);}
			}
		});
		
	}

	
public interface syncFriend
{
	public void setdata(JSONArray j);
	}


}
