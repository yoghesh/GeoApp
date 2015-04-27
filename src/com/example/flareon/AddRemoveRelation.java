package com.example.flareon;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;

public class AddRemoveRelation extends AsyncTask<String, Void, Void> {

	@Override
	protected Void doInBackground(String... params) {
		// TODO Auto-generated method stub
	
	
		try {
			String eid = params[0];
			String uid = params[1];
			String stat = params[2];
			List<NameValuePair> vals = new ArrayList<NameValuePair>();
			vals.add(new BasicNameValuePair("EID", eid));
			vals.add(new BasicNameValuePair("UID", uid));
			vals.add(new BasicNameValuePair("Status", stat));
			URI mUri;
			mUri = new URI("");
			HttpClient mClient = new DefaultHttpClient();
			HttpPost mPost = new HttpPost(mUri);
			mPost.setEntity(new UrlEncodedFormEntity(vals));
			mClient.execute(mPost);
			
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
