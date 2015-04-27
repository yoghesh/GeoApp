package com.example.flareon;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import android.R.integer;

public class eventData {

	private Integer m_eid;
	private String m_name;
	private String m_location;
	private String m_position;
	private String m_date;
	private String m_time;
	private Integer m_ServerID;
	private List<String> mFriends;
	
	public eventData(Integer eid, String name, String location, String position, String date, String time, Integer serverId, List<String>Friends)
	{
		m_eid = eid;
		m_name = name;
		m_location = location;
		m_position = position;
		m_date = date;
		m_time = time;
		m_ServerID = serverId;
		mFriends = Friends;
	}

	public Integer getEid()
	{return m_eid;}
	
	public String getName()
	{return m_name;}
	
	public String getLocation()
	{return m_location;}
	
	public String getPosition()
	{return m_position;}
	
	public String getDate()
	{return m_date;}
	
	public String getTime()
	{return m_time;}
	
	public Integer getServerId()
	{return m_ServerID;}
	
	public List<String> getFriends()
	{return mFriends;}
	
	public void setEid(Integer val)
	{ m_eid = val;}
	
	public void setName(String val)
	{m_name = val;}
	
	public void setLocation(String val)
	{m_location = val;}
	
	public void setPosition(String val)
	{ m_position = val;}
	
	public void setDate(String val)
	{ m_date = val;}
	
	public void setTime(String val)
	{m_time = val;}
	
	public void setServerId(Integer val)
	{ m_ServerID = val;}
	
	public void setFriends(List<String> Friends)
	{mFriends = Friends;}
}
