package com.example.flareon;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class eventDataSource {
	
	private SQLiteDatabase m_database;
	private EventDatabaseHelper m_dbHelper;
	
	public eventDataSource(Context context)
	{
		m_dbHelper = new EventDatabaseHelper(context);
	}
	
	public void open()
	{ m_database = m_dbHelper.getWritableDatabase();}
	
	public void close()
	{ m_database.close();}
	
	public Long addEvent(eventData data)
	{
		ContentValues val = new ContentValues();
		val.put(EventDatabaseHelper.COLOUMN_NAME, data.getName());
		val.put(EventDatabaseHelper.COLOUMN_LOCATION, data.getLocation());
		val.put(EventDatabaseHelper.COLOUMN_POSITION, data.getPosition());
		val.put(EventDatabaseHelper.COLOUMN_DATE, data.getDate().toString());
		val.put(EventDatabaseHelper.COLOUMN_TIME,data.getTime().toString());
		val.put(EventDatabaseHelper.COLOUMN_SERVERID, data.getServerId());
		return m_database.insert(EventDatabaseHelper.TABLE_NAME, null, val);
	}
	
	public Integer deleteEvent(Integer eid)
	{
		return m_database.delete(EventDatabaseHelper.TABLE_NAME, EventDatabaseHelper.COLOUMN_ID + "=?", new String[] {eid.toString()});
	}

}
