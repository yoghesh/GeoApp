package com.example.flareon;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.flareon.ServerChangePassword.ChangePasswordCommunicator;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePass extends DialogFragment{
	
	private EditText edtOldPass;
	private EditText edtNewPass;
	private EditText EdtConfirmPass;
	private Button BtnOK;
	private ServerChangePassword ServerPass;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	View v = inflater.inflate(R.layout.changepassword, container);
	
	edtOldPass = (EditText)v.findViewById(R.id.edtchangepassOriginal);
	edtNewPass = (EditText)v.findViewById(R.id.edtChangepassNew);
	EdtConfirmPass = (EditText)v.findViewById(R.id.edtChangepassconfirm);
	BtnOK = (Button)v.findViewById(R.id.btnChangepassword);
	BtnOK.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			ServerPass = new ServerChangePassword();
			edtOldPass.getText().toString();
			String NewPass = edtNewPass.getText().toString();
			String ConfirmPass = EdtConfirmPass.getText().toString();
			String OldPass = edtOldPass.getText().toString();
			if(NewPass.compareTo(ConfirmPass) == 0)
			{
				String[] Inputs = new String[3];
				SharedPreferences m_loginPref= getActivity().getSharedPreferences("Login", Activity.MODE_PRIVATE);
				String username = m_loginPref.getString("name", "");
				Inputs[0] = username;
				Inputs[1] = OldPass;
				Inputs[2] = NewPass;
				ServerPass.mCommunicator = new ChangePasswordOperator();
				ServerPass.execute(Inputs);
			}
			else
			{
				Toast.makeText(getActivity(), "The new password doesn't match please try again", Toast.LENGTH_SHORT).show();
				ClearTexts();
				return;
			}
		}
	});
	return v;
	}

	public void ClearTexts()
	{
		edtNewPass.setText("");
		edtOldPass.setText("");
		EdtConfirmPass.setText("");
	}
	
	public class ChangePasswordOperator implements ChangePasswordCommunicator
	{

		@Override
		public void OnSuccess(JSONObject Inp) {
			// TODO Auto-generated method stub
			try {
				Integer code = Inp.getInt("code");
				switch (code) {
				case 0:
					Toast.makeText(getActivity(), "Password successfully changed", Toast.LENGTH_SHORT).show();
					dismiss();
					break;
				case 1:
					Toast.makeText(getActivity(), "The Old password doesn't match please try again later", Toast.LENGTH_SHORT).show();
					ClearTexts();
					break;
				default:
					Toast.makeText(getActivity(), Inp.getString("message"), Toast.LENGTH_SHORT).show();
					ClearTexts();
					break;
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		@Override
		public void OnFailure() {
			// TODO Auto-generated method stub
			Toast.makeText(getActivity(), "ERROR Please try again later!!", Toast.LENGTH_SHORT).show();
			ClearTexts();
		}
		
	}
}
