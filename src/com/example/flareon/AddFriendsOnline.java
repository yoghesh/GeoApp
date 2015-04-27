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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.net.Uri;
import android.os.AsyncTask;

public class AddFriendsOnline extends AsyncTask<String, Void, Void>
{

	@Override
	protected Void doInBackground(String... params) {
		// TODO Auto-generated method stub
		
		
			try {
				String name = params[0];
				String name2 = params[1];
				String st = params[2];
				HttpClient mClient = new DefaultHttpClient();
				URI mUri;
				mUri = new URI("http://geofinder.net84.net/Geofinder/AddFriend.php");
				HttpPost mPost = new HttpPost(mUri);
				List<NameValuePair> mInp = new ArrayList<NameValuePair>();
				mInp.add(new BasicNameValuePair("name1",name));
				mInp.add(new BasicNameValuePair("name2",name2));
				mInp.add(new BasicNameValuePair("Status",st));
				mPost.setEntity(new UrlEncodedFormEntity(mInp));
				
				HttpResponse mResp = mClient.execute(mPost);
				BufferedReader mRead = new BufferedReader(new InputStreamReader(mResp.getEntity().getContent()));
				String line;
				StringBuffer mBuffer = new StringBuffer();
				
				while((line = mRead.readLine()) != null)
				{
					mBuffer.append(line);
				}
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

}
