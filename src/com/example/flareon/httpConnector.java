package com.example.flareon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.ls.LSInput;

import android.R.string;
import android.os.AsyncTask;

public class httpConnector extends AsyncTask<JSONObject, Void, JSONArray > {

	Datainterface myData;
	public void setListen(Datainterface var)
	{
		this.myData = var;
	}
	

	protected JSONArray doInBackground(JSONObject... params) {
		// TODO Auto-generated method stub
		final HttpClient myClient =  new DefaultHttpClient();
        HttpParams params1 = myClient.getParams();
        HttpConnectionParams.setConnectionTimeout(params1, 30*1000);
        HttpConnectionParams.setSoTimeout(params1, 30*1000);
        ConnManagerParams.setTimeout(params1, 30*1000);
		
        JSONObject myIN = params[0];
        List<NameValuePair> mData = new ArrayList<NameValuePair>();
        try {
        	if(myIN.has("username"))
			{mData.add(new BasicNameValuePair("username", (String) myIN.get("username")));}
        	if(myIN.has("mailid"))
			{mData.add(new BasicNameValuePair("mailid", (String) myIN.get("mailid")));}
        	if(myIN.has("password"))
			{mData.add(new BasicNameValuePair("password", (String) myIN.get("password")));}
        	if(myIN.has("type"))
			{mData.add(new BasicNameValuePair("type", (String) myIN.get("type").toString()));}
        	if(myIN.has("appkey"))
			{mData.add(new BasicNameValuePair("appkey", (String) myIN.get("appkey").toString()));}
        	if(myIN.has("authkey"))
        	{mData.add(new BasicNameValuePair("authkey", (String) myIN.get("authkey").toString()));}
		} catch (JSONException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
        
        
        HttpPost myRequest;
		try {
			myRequest = new HttpPost(myIN.getString("URL"));
			myRequest.setEntity(new UrlEncodedFormEntity(mData));
			//myRequest.setEntity(new ByteArrayEntity(params[0].toString().getBytes("UTF8")));
			HttpResponse myResponse;
			myResponse = myClient.execute(myRequest);
			//String val = EntityUtils.toString(myResponse.getEntity());
			BufferedReader in = new BufferedReader(new InputStreamReader(myResponse.getEntity().getContent()));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			JSONArray jArr;
			while ((line = in.readLine()) != null) {
			sb.append(line);
			}
			in.close();

		String strs = sb.toString();
		jArr = new JSONArray(strs);
		//jArr.put(new JSONObject(strs));
		return(jArr);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
	protected void onPostExecute(JSONArray result) {
		// TODO Auto-generated method stub
		if (result != null)
		{
			myData.getSuccessData(result);
		}
		else
		{
			myData.getUnsuccessData();
		}
		}

	public static interface Datainterface
	{
		void getSuccessData(JSONArray data);
		void getUnsuccessData();
	}

}
