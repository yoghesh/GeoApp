package com.example.flareon;

import android.R.integer;

public class UserData {

	private Integer m_UID;
	private Integer m_ServerID;
	private String m_Username;
	private String m_Location;
	
	public UserData(Integer UID, Integer ServerID, String Username, String Location) {
		// TODO Auto-generated constructor stub
		m_UID = UID;
		m_ServerID = ServerID;
		m_Username = Username;
		m_Location = Location;
	}
	
	public Integer getUID()
	{
		return m_UID;
	}
	
	public Integer getServerID()
	{
		return m_ServerID;
	}
	
	public String getUsername()
	{
		return m_Username;
	}
	
	public String getLocation()
	{
		return m_Location;
	}
	
	public void setUID(Integer UID)
	{
		m_UID = UID;
	}
	
	public void setServerID(Integer ServerID)
	{
		m_ServerID = ServerID;
	}
	
	public void setName(String Name)
	{
		m_Username = Name;
	}
	
	public void setLocation(String Location)
	{
		m_Location = Location;
	}
}
