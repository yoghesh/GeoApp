package com.example.flareon;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.flareon.SyncEvents.synEventLIsten;
import com.example.flareon.Syncdata.syncFriend;

public class eventDataSource {
	
	private SQLiteDatabase m_database;
	private EventDatabaseHelper m_dbHelper;
	private Context mContext;
	
	public eventDataSource(Context context)
	{
		m_dbHelper = new EventDatabaseHelper(context);
		mContext = context;
	}
	
	public void openWrite()
	{ m_database = m_dbHelper.getWritableDatabase();}
	
	public void openRead()
	{ m_database = m_dbHelper.getReadableDatabase();}
	
	public void close()
	{ m_database.close();}
	
	public Long addEvent(eventData data)
	{
		
		SharedPreferences m_loginPref= mContext.getSharedPreferences("Login", Activity.MODE_PRIVATE);
		String myName = m_loginPref.getString("name", "");
		try
		{
		openWrite();
		ContentValues val = new ContentValues();
		val.put(EventDatabaseHelper.E_COLOUMN_NAME, data.getName());
		val.put(EventDatabaseHelper.E_COLOUMN_LOCATION, data.getLocation());
		val.put(EventDatabaseHelper.E_COLOUMN_POSITION, data.getPosition());
		val.put(EventDatabaseHelper.E_COLOUMN_DATE, data.getDate().toString());
		val.put(EventDatabaseHelper.E_COLOUMN_TIME,data.getTime().toString());
		val.put(EventDatabaseHelper.E_COLOUMN_SERVERID, data.getServerId());
		long res = m_database.insertOrThrow(EventDatabaseHelper.EVENT_TABLE_NAME, null, val);
		close();
		
		List<String> friends = data.getFriends();
		String eventName = data.getName();
		for (int i =0 ; i < friends.size(); i++)
		{
			if(!(friends.get(i).equalsIgnoreCase(myName)))
			{addRelation(friends.get(i), eventName);}
		}
		
		return res;
		}
		catch(SQLException e)
		{
			Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();}
		return null;
	}
	
	public void updateEvent(Integer eid, eventData newData)
	{
		openWrite();
		ContentValues values = new ContentValues();
		values.put(EventDatabaseHelper.E_COLOUMN_NAME, newData.getName());
		values.put(EventDatabaseHelper.E_COLOUMN_LOCATION, newData.getLocation());
		values.put(EventDatabaseHelper.E_COLOUMN_POSITION, newData.getPosition());
		values.put(EventDatabaseHelper.E_COLOUMN_DATE, newData.getDate());
		values.put(EventDatabaseHelper.E_COLOUMN_TIME, newData.getTime());
		values.put(EventDatabaseHelper.E_COLOUMN_SERVERID, newData.getServerId());
		int val = m_database.update(EventDatabaseHelper.EVENT_TABLE_NAME, values, "EID = " + eid, null);
		Toast.makeText(mContext, val, Toast.LENGTH_LONG).show();
	}
	
	public Integer deleteEvent(Integer eid)
	{
		openWrite();
		Integer res = m_database.delete(EventDatabaseHelper.EVENT_TABLE_NAME, EventDatabaseHelper.E_COLOUMN_ID + "=?", new String[] {eid.toString()});
		close();
		return res;
	}
	
public eventData getSingleEventDataWithSID(Integer eid)
	{
		openRead();
		String command = "select * from " + EventDatabaseHelper.EVENT_TABLE_NAME + " where " + EventDatabaseHelper.E_COLOUMN_SERVERID + " = " + eid.toString();
		Cursor mCursor = m_database.rawQuery(command,null);

		String name = "";
		String location = "";
		String position = "";
		String date = "";
		String time = "";
		String serverId = "";
		
		if (mCursor.moveToFirst())
		{
			do
			{
				eid = mCursor.getInt(mCursor.getColumnIndex(EventDatabaseHelper.E_COLOUMN_ID));
				name = mCursor.getString(mCursor.getColumnIndex(EventDatabaseHelper.E_COLOUMN_NAME));
				location = mCursor.getString(mCursor.getColumnIndex(EventDatabaseHelper.E_COLOUMN_LOCATION));
				position = mCursor.getString(mCursor.getColumnIndex(EventDatabaseHelper.E_COLOUMN_POSITION));
				date = mCursor.getString(mCursor.getColumnIndex(EventDatabaseHelper.E_COLOUMN_DATE));
				time = mCursor.getString(mCursor.getColumnIndex(EventDatabaseHelper.E_COLOUMN_TIME));
				serverId = mCursor.getString(mCursor.getColumnIndex(EventDatabaseHelper.E_COLOUMN_SERVERID));
			}
			while(mCursor.moveToNext());
			close();
			List<String> friends = getAllFriendsFOrEvent(name);
			eventData mEventdata = new eventData(eid, name, location, position, date, time, Integer.parseInt(serverId), friends);
			close();
			return mEventdata;
		}
		close();
		return null;
	}

public eventData getSingleEventDataWithName(String eName)
{
	openRead();
	String command = "select * from " + EventDatabaseHelper.EVENT_TABLE_NAME + " where " + EventDatabaseHelper.E_COLOUMN_NAME+ " = \"" + eName.toString() + "\"";
	Cursor mCursor = m_database.rawQuery(command,null);

	String id = "";
	String name = "";
	String location = "";
	String position = "";
	String date = "";
	String time = "";
	String serverId = "";
	
	if (mCursor.moveToFirst())
	{
		do
		{
			id = mCursor.getString(mCursor.getColumnIndex(EventDatabaseHelper.E_COLOUMN_ID));
			name = mCursor.getString(mCursor.getColumnIndex(EventDatabaseHelper.E_COLOUMN_NAME));
			location = mCursor.getString(mCursor.getColumnIndex(EventDatabaseHelper.E_COLOUMN_LOCATION));
			position = mCursor.getString(mCursor.getColumnIndex(EventDatabaseHelper.E_COLOUMN_POSITION));
			date = mCursor.getString(mCursor.getColumnIndex(EventDatabaseHelper.E_COLOUMN_DATE));
			time = mCursor.getString(mCursor.getColumnIndex(EventDatabaseHelper.E_COLOUMN_TIME));
			serverId = mCursor.getString(mCursor.getColumnIndex(EventDatabaseHelper.E_COLOUMN_SERVERID));
		}
		while(mCursor.moveToNext());
		close();
		List<String> friends = getAllFriendsFOrEvent(name);
		eventData mEventdata = new eventData(Integer.parseInt(id), name, location, position, date, time, Integer.parseInt(serverId), friends);
		close();
		return mEventdata;
	}
	close();
	return null;
}
	
public List<eventData> getAlluUnSyncEvent()
	{
		openRead();
		String command = "select * from " + EventDatabaseHelper.EVENT_TABLE_NAME + " where " + EventDatabaseHelper.E_COLOUMN_SERVERID + " = " + 0;
		Cursor mCursor = m_database.rawQuery(command,null);

		List<eventData> allData = new ArrayList<eventData>();
		String eid = "";
		String name = "";
		String location = "";
		String position = "";
		String date = "";
		String time = "";
		String serverId = "";
		
		if (mCursor.moveToFirst())
		{
			do
			{
				eid = mCursor.getString(mCursor.getColumnIndex(EventDatabaseHelper.E_COLOUMN_ID));
				name = mCursor.getString(mCursor.getColumnIndex(EventDatabaseHelper.E_COLOUMN_NAME));
				location = mCursor.getString(mCursor.getColumnIndex(EventDatabaseHelper.E_COLOUMN_LOCATION));
				position = mCursor.getString(mCursor.getColumnIndex(EventDatabaseHelper.E_COLOUMN_POSITION));
				date = mCursor.getString(mCursor.getColumnIndex(EventDatabaseHelper.E_COLOUMN_DATE));
				time = mCursor.getString(mCursor.getColumnIndex(EventDatabaseHelper.E_COLOUMN_TIME));
				serverId = mCursor.getString(mCursor.getColumnIndex(EventDatabaseHelper.E_COLOUMN_SERVERID));
				List<String> friends = getAllFriendsFOrEvent(name);
				eventData mEventdata = new eventData(Integer.parseInt(eid), name, location, position, date, time, Integer.parseInt(serverId), friends);
				allData.add(mEventdata);
			}
			while(mCursor.moveToNext());
			close();
			return allData;
		}
		close();
		return null;
	}

	public List<eventData> getAllEventData()
	{
		openRead();
		String command = "select * from " + EventDatabaseHelper.EVENT_TABLE_NAME;
		Cursor mCursor = m_database.rawQuery(command,null);

		List<eventData> mAllEvents = new ArrayList<eventData>();
		String eid = "";
		String name = "";
		String location = "";
		String position = "";
		String date = "";
		String time = "";
		String serverId = "";
		
		if (mCursor.moveToFirst())
		{
			do
			{
				eid = mCursor.getString(mCursor.getColumnIndex(EventDatabaseHelper.E_COLOUMN_ID));
				name = mCursor.getString(mCursor.getColumnIndex(EventDatabaseHelper.E_COLOUMN_NAME));
				location = mCursor.getString(mCursor.getColumnIndex(EventDatabaseHelper.E_COLOUMN_LOCATION));
				position = mCursor.getString(mCursor.getColumnIndex(EventDatabaseHelper.E_COLOUMN_POSITION));
				date = mCursor.getString(mCursor.getColumnIndex(EventDatabaseHelper.E_COLOUMN_DATE));
				time = mCursor.getString(mCursor.getColumnIndex(EventDatabaseHelper.E_COLOUMN_TIME));
				serverId = mCursor.getString(mCursor.getColumnIndex(EventDatabaseHelper.E_COLOUMN_SERVERID));
				List<String> friends = new ArrayList<String>();
				friends = getAllFriendsFOrEvent(name);
				eventData mEventdata = new eventData(Integer.parseInt(eid), name, location, position, date, time, Integer.parseInt(serverId), friends);
				mAllEvents.add(mEventdata);
			}
			while(mCursor.moveToNext());
			
			
		}
		close();
		return mAllEvents;
	}
	
	public List<String> getAllFriendsFOrEvent(String eventName)
	{	
		
		String callQuery = "SELECT U.* FROM "+ EventDatabaseHelper.USER_TABLE_NAME +" AS U INNER JOIN "+
			EventDatabaseHelper.RELATION_TABLE_NAME+" AS R ON U.LUID = R.LUID INNER JOIN " + 
				EventDatabaseHelper.EVENT_TABLE_NAME + " AS E ON R.LEID = E.LEID WHERE E." + 
			EventDatabaseHelper.E_COLOUMN_NAME +" = '" + eventName + "'";
		
		
		openRead();
		List<String> names = new ArrayList<String>();
		
		//Cursor mCursor = m_database.rawQuery("SELECT U.* FROM User_data AS U INNER JOIN Relation_Data AS R ON U.LUID = R.LUID INNER JOIN Event_Data as E ON R.LEID = E.LEID", null);
		Cursor mCursor = m_database.rawQuery(callQuery, null);
		if(mCursor.moveToFirst())
		{
			do
			{
				String name = mCursor.getString(mCursor.getColumnIndex(EventDatabaseHelper.U_NAME));
				names.add(name);
			}
			while(mCursor.moveToNext());
		}
		
		close();
		return names;
	}
	
	public Long addUser(UserData data)
	{
		try
		{
			openWrite();
			ContentValues cv = new ContentValues();
			cv.put(EventDatabaseHelper.U_NAME, data.getUsername());
			cv.put(EventDatabaseHelper.U_LOCATION, data.getLocation());
			cv.put(EventDatabaseHelper.U_SERVERID, data.getServerID());
			Long res = m_database.insertOrThrow(EventDatabaseHelper.USER_TABLE_NAME, null, cv);
			close();
			return res;
		}
		catch(SQLException e1)
		{close();
			Toast.makeText(mContext, e1.getMessage(), Toast.LENGTH_LONG).show();}
		return null;
	}
	
	public void getEIDS(String name,List<String> vals)
	{
		String query = "SELECT " + EventDatabaseHelper.E_COLOUMN_ID + " , " + EventDatabaseHelper.E_COLOUMN_SERVERID +
				" FROM " + EventDatabaseHelper.EVENT_TABLE_NAME + " WHERE " + EventDatabaseHelper .E_COLOUMN_NAME + " = " +
				"'" + name + "'";
		openRead();
		Cursor mCursor = m_database.rawQuery(query, null);
		if(mCursor.moveToFirst())
		{
			do
			{
				String val1 = mCursor.getString(mCursor.getColumnIndex(EventDatabaseHelper.E_COLOUMN_ID));
				String val2 = mCursor.getString(mCursor.getColumnIndex(EventDatabaseHelper.E_COLOUMN_SERVERID));
				vals.clear();
				vals.add(val1);
				vals.add(val2);
			}
			while(mCursor.moveToNext());
		}
	}

	public void getUIDS(String name,List<String> vals)
	{
		String query = "SELECT *" +	" FROM " + EventDatabaseHelper.USER_TABLE_NAME
		+ " WHERE " + EventDatabaseHelper .U_NAME + " = " +
		"'" + name + "'";
		openRead();
		Cursor mCursor = m_database.rawQuery(query, null);
		if(mCursor.moveToFirst())
		{
			do
			{
				String val1 = mCursor.getString(mCursor.getColumnIndex(EventDatabaseHelper.U_COLOUMN_ID));
				String val2 = mCursor.getString(mCursor.getColumnIndex(EventDatabaseHelper.U_SERVERID));
				String val3 = mCursor.getString(mCursor.getColumnIndex(EventDatabaseHelper.U_NAME));
			
				vals.clear();
				vals.add(val1);
				vals.add(val2);
			}
			while(mCursor.moveToNext());
		}
	}
	
	public Long addRelation(String Username, String Eventname)
	{
		try
		{
			openWrite();
			List<String> EIDs = new ArrayList<String>();
			List<String> UIDs = new ArrayList<String>();
			getUIDS(Username, UIDs);
			getEIDS(Eventname, EIDs);
			ContentValues cv = new ContentValues();
			cv.put(EventDatabaseHelper.R_EVENT_ID, EIDs.get(0));
			//cv.put(EventDatabaseHelper.R_S_EVENT_ID, EIDs.get(1));
			cv.put(EventDatabaseHelper.R_USER_ID, UIDs.get(0));
			//cv.put(EventDatabaseHelper.R_S_USER_ID, UIDs.get(1));
			Long res = m_database.insertOrThrow(EventDatabaseHelper.RELATION_TABLE_NAME, null, cv);
			close();
			return res;
		}
		catch(SQLException e1)
		{
			Toast.makeText(mContext, e1.getMessage(), Toast.LENGTH_LONG).show();
			close();
			return null;
		}
	}
	
	public List<String> getAllUsers()
	{
		List<String> list = new ArrayList<String>();
		String callQuery = "SELECT " + EventDatabaseHelper.F_Name + " FROM "+ EventDatabaseHelper.FRIENDS_DATA_TABLE_NAME;
		openRead();
		Cursor mCursor = m_database.rawQuery(callQuery, null);
		if(mCursor.moveToFirst())
		{
			do
			{
				String name = mCursor.getString(mCursor.getColumnIndex(EventDatabaseHelper.F_Name));
				list.add(name);
			}
			while(mCursor.moveToNext());
		}
		close();
		return list;
	}
	
	public void syncAllData()
	{
		SharedPreferences m_loginPref= mContext.getSharedPreferences("Login", Activity.MODE_PRIVATE);
		final String name = m_loginPref.getString("name", "");
		String[] val = new String[1];
		val[0] = name;
		Syncdata mDat = new Syncdata();
		mDat.setList(new syncFriend() {
			
			@Override
			public void setdata(JSONArray j) {
				// TODO Auto-generated method stub
				if(j != null)
				{
					JSONArray friendList = j;
					try {
						
						for (int i = 0; i < friendList.length(); i++)
						{
							JSONObject singF = friendList.getJSONObject(i);
							
							String fname = singF.getString("Name");
						
							Integer uid = singF.getInt("UID");
					
							Integer status = singF.getInt("Status");
							
							String strQ = "SELECT * FROM " + EventDatabaseHelper.FRIENDS_DATA_TABLE_NAME+ " AS F WHERE F."+EventDatabaseHelper.F_UID + " = " + uid;
												
							openRead();
							Cursor mC = m_database.rawQuery(strQ,null);
							
							if(mC.moveToFirst())
							{
								do
								{
									Integer lStat = mC.getInt(mC.getColumnIndex(EventDatabaseHelper.F_STATUS));
									if(status == lStat)
									{}
									else
									{
										close();
										openWrite();
										ContentValues cv = new ContentValues();
										cv.put(EventDatabaseHelper.F_STATUS, status);
										int v = m_database.update(EventDatabaseHelper.FRIENDS_DATA_TABLE_NAME, cv, "UID = " + uid, null);
										close();
									}
								}
								while(mC.moveToNext());
							}
							else
							{
								close();
								openWrite();
								ContentValues cv = new ContentValues();
								cv.put(EventDatabaseHelper.F_Name, fname);
								cv.put(EventDatabaseHelper.F_UID, uid);
								cv.put(EventDatabaseHelper.F_STATUS, status);
								Long res = m_database.insertOrThrow(EventDatabaseHelper.FRIENDS_DATA_TABLE_NAME, null, cv);
								if(status == 1)
								{addUser(new UserData(0, uid, fname, "00.00"));}
								m_database.
								close();							
							}
						}
						
						/*openRead();
						String test = "SELECT * FROM " + EventDatabaseHelper.FRIENDS_DATA_TABLE_NAME;
						Cursor tc = m_database.rawQuery(test, null);
						if(tc.moveToFirst())
						{
						 do
						 {
							 String a = tc.getString(tc.getColumnIndex(EventDatabaseHelper.F_Name));
							 Integer b = tc.getInt(tc.getColumnIndex(EventDatabaseHelper.F_UID));
							
						 }
						 while(tc.moveToNext());
						}
						close();
					*/	
						SyncEvents mEve = new SyncEvents();
						mEve.mListen = new synEventLIsten() {
							
							@Override
							public void setEvents(JSONArray val) {
								// TODO Auto-generated method stub
								if(val != null)
								{
									try {
										//JSONArray mA = val.getJSONArray(0);
										JSONObject allObjs = val.getJSONObject(0);
										JSONArray allEvents = allObjs.getJSONArray("events");
										allObjs = val.getJSONObject(1);
										JSONArray allPeople = allObjs.getJSONArray("people");
										
										for (int i = 0; i < allEvents.length(); i++)
										{
											JSONObject singleEvent = allEvents.getJSONObject(i);
											String ename = singleEvent.getString("name");
											Integer serverID = singleEvent.getInt("eid");
											String loc = singleEvent.getString("location");
											String pos = singleEvent.getString("position");
											String date = singleEvent.getString("date");
											String time = singleEvent.getString("time");
											List<String> friends = new ArrayList<String>();
											JSONArray singleEventPpl = allPeople.getJSONArray(i);
											for(int j = 0; j < singleEventPpl.length(); j++)
											{
												JSONObject oneUser = singleEventPpl.getJSONObject(j);
												String name = oneUser.getString("Name");
												Integer id = oneUser.getInt("uid");
												SharedPreferences m_loginPref= mContext.getSharedPreferences("Login", Activity.MODE_PRIVATE);
												String user = m_loginPref.getString("name", "");
												if(user.compareToIgnoreCase(name) != 0)
												{
												friends.add(name);
												}
											}
											
											eventData mData = getSingleEventDataWithName(ename);
											if(mData == null)
											{
												addEvent(new eventData(0, ename, loc, pos, date, time, serverID, friends));
											}
											else
											{
												if(mData.getServerId() == 0)
												{
													mData.setServerId(serverID);
													updateEvent(mData.getEid(), mData);
												}
												
												Boolean needSync = false;
												//if(mData.getDate().compareToIgnoreCase(date) != 0)
												//{needSync = true;}
												if(mData.getLocation().compareToIgnoreCase(loc) != 0)
												{needSync = true;}
												if(mData.getPosition().compareToIgnoreCase(pos) != 0)
												{needSync = true;}
												//if(mData.getTime().compareToIgnoreCase(time) != 0)
												//{needSync = true;}
												if(needSync)
												{
													new AddEventOnline(name).execute(mData);
												}
												
												List<String> lFrnds = mData.getFriends();
												for(int j = 0; j < friends.size(); j++)
												{
													if(lFrnds.contains(friends.get(j)))
													{}
													else
													{
														List<String> ids = new ArrayList<String>();
														getUIDS(friends.get(j), ids);
														String[] dats = {mData.getServerId().toString(),ids.get(1),"1"};
														new AddRemoveRelation().execute(dats);
													}
													
												}
												
												for(int j = 0; j < lFrnds.size(); j++)
												{
													if(friends.contains(lFrnds.get(j)))
													{}
													else
													{
														List<String> ids = new ArrayList<String>();
														getUIDS(lFrnds.get(j), ids);
														String[] dats = {mData.getServerId().toString(),ids.get(1),"0"};
														new AddRemoveRelation().execute(dats);
													}
													
												}
											}
										}
										SharedPreferences m_loginPref= mContext.getSharedPreferences("Login", Activity.MODE_PRIVATE);
										String username = m_loginPref.getString("name", "");
										
										List<eventData> unSyncEvents = getAlluUnSyncEvent();
										if(unSyncEvents != null)
										{
											for(int i = 0; i < unSyncEvents.size(); i++)
											{
												eventData arr[] = new eventData[1];
												arr[0] = unSyncEvents.get(i);
												new AddEventOnline(username).execute(arr);
											}
										}
										
										
										
										
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}
						};
						
						SharedPreferences m_loginPref1= mContext.getSharedPreferences("Login", Activity.MODE_PRIVATE);
						String name1 = m_loginPref1.getString("name", "");
						String[] val1 = new String[1];
						val1[0] = name1;
						mEve.execute(val1);
	
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		
			mDat.execute(val);
			
			
						

	}
	
	
}
