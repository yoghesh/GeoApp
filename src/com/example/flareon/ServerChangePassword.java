package com.example.flareon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.stream.StreamResult;

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
import org.json.JSONObject;

import android.os.AsyncTask;

public class ServerChangePassword extends AsyncTask<String, Void, JSONObject>{

	public ChangePasswordCommunicator mCommunicator;
	@Override
	protected JSONObject doInBackground(String... params) {
		// TODO Auto-generated method stub
		
		
	
		try {
			URI Link;
			Link = new URI("http://geofinder.net84.net/Geofinder/ChangePass.php");
			HttpClient Client = new DefaultHttpClient();
			HttpPost Req = new HttpPost(Link);
			List<NameValuePair> AllData = new ArrayList<NameValuePair>();
			AllData.add(new BasicNameValuePair("Username", params[0]));
			AllData.add(new BasicNameValuePair("OldPassword", params[1]));
			AllData.add(new BasicNameValuePair("NewPassword", params[2]));
			
			Req.setEntity(new UrlEncodedFormEntity(AllData));
			HttpResponse Resp = Client.execute(Req);
			BufferedReader Result = new BufferedReader(new InputStreamReader(Resp.getEntity().getContent()));
			StringBuffer StringBuf = new StringBuffer("");
			String line = "";
			
			while((line = Result.readLine()) != null)
			{
				StringBuf.append(line);
			}
			
			String StrResult = StringBuf.toString();
			JSONArray Arr = new JSONArray(StrResult);
			JSONObject JResult = Arr.getJSONObject(0);
			return JResult;
			
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
	protected void onPostExecute(JSONObject result) {
		// TODO Auto-generated method stub
		if(result != null)
		{
			mCommunicator.OnSuccess(result);
		}
		else
		{
			mCommunicator.OnFailure();
		}
	}
	
	
	public interface ChangePasswordCommunicator
	{
		public void OnSuccess(JSONObject Inp);
		public void OnFailure();
	}
}


