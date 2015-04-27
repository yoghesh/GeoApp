package com.example.flareon;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ChangePasswordFragment extends Fragment {
	
	private TextView txtUName;
	private TextView txtMailID;
	private EditText edtOldPass;
	private EditText edtNewPass;
	private EditText edtConfPass;
	private Button btnChangePass;
	private Button btnLogout;
	
	public String uname;
	public String mail;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.accountdetails, container, false);
		SharedPreferences m_loginPref= getActivity().getSharedPreferences("Login", Activity.MODE_PRIVATE);
		uname = m_loginPref.getString("name", "");
		mail = m_loginPref.getString("mail", "");
		txtUName = (TextView)v.findViewById(R.id.txtaccountUname);
		txtUName.setText(uname);
		txtMailID = (TextView)v.findViewById(R.id.txtaccountMailid);
		txtMailID.setText(mail);
		edtOldPass = (EditText)v.findViewById(R.id.edtchangepassOriginal);
		edtNewPass = (EditText)v.findViewById(R.id.edtChangepassNew);
		edtConfPass = (EditText)v.findViewById(R.id.edtChangepassconfirm);
		btnChangePass = (Button)v.findViewById(R.id.btnaccountChangePass);
		btnLogout = (Button)v.findViewById(R.id.btnaccountLogout);
		btnChangePass.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ChangePass changePassFrag = new ChangePass();
				changePassFrag.show(getFragmentManager(), "CP");
				
			}
		});
		return v;
	}

}
