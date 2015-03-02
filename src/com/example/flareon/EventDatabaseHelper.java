package com.example.flareon;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EventDatabaseHelper extends SQLiteOpenHelper {
	
	public static String DATABASE_NAME = "GeoFinder";
	public static int VERSION = 1;
	public static String TABLE_NAME = "Event_Data";
	public static String COLOUMN_ID = "EID";
	public static String COLOUMN_NAME = "EventName";
	public static String COLOUMN_LOCATION = "Location";
	public static String COLOUMN_POSITION = "Position";
	public static String COLOUMN_DATE = "Date";
	public static String COLOUMN_TIME = "Time";
	public static String COLOUMN_SERVERID = "ServerID";
	
	public String CREATE_TABLE = "create table " + TABLE_NAME + "(" + COLOUMN_ID + " integer primary key autoincrement, " +
			COLOUMN_NAME + " text not null, " + COLOUMN_LOCATION + " text not null, "+ COLOUMN_POSITION + " text not null, " +
			COLOUMN_DATE + "  date not null, " + COLOUMN_TIME + " time not null ," + COLOUMN_SERVERID + "integer not null);";
	
	public EventDatabaseHelper(Context context)
	{
		super(context,DATABASE_NAME,null,VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_TABLE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("drop table if exists " + TABLE_NAME);
		onCreate(db);
	}

}
