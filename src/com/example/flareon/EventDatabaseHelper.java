package com.example.flareon;

import android.R.string;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EventDatabaseHelper extends SQLiteOpenHelper {
	
	public static String DATABASE_NAME = "GeoFinder";
	public static int VERSION = 1;
	public static String EVENT_TABLE_NAME = "Event_Data";
	public static String E_COLOUMN_ID = "LEID";
	public static String E_COLOUMN_NAME = "EventName";
	public static String E_COLOUMN_LOCATION = "Location";
	public static String E_COLOUMN_POSITION = "Position";
	public static String E_COLOUMN_DATE = "Date";
	public static String E_COLOUMN_TIME = "Time";
	public static String E_COLOUMN_SERVERID = "EID";
	
	public static String USER_TABLE_NAME = "User_data";
	public static String U_COLOUMN_ID = "LUID";
	public static String U_NAME = "UserName";
	public static String U_LOCATION = "Location";
	public static String U_SERVERID = "UID";
	
	public static String RELATION_TABLE_NAME = "Relation_Data";
	public static String R_EVENT_ID = "LEID";
	public static String R_USER_ID = "LUID";
	public static String R_S_EVENT_ID = "EID";
	public static String R_S_USER_ID = "UID";
	public static String RID = "RID";
	
	public static String FRIENDS_DATA_TABLE_NAME = "Friends_Data";
	public static String F_SNO = "SNo";
	public static String F_UID = "UID";
	public static String F_Name = "Name";
	public static String F_STATUS = "Status";
	
/*	public static String FRIEND_RELATION_TABLE_NAME = "Friend_Relation";
	public static String FF_SNO = "SNo";
	public static String FF_UID1 = "UID1";
	public static String FF_UID2 = "UID2";
	public static String FF_STATUS = "Status";*/
	
	
	public String CREATE_EVENT_TABLE = "create table " + EVENT_TABLE_NAME + "(" + E_COLOUMN_ID + " integer primary key autoincrement, " +
			E_COLOUMN_NAME + " text not null, " + E_COLOUMN_LOCATION + " text not null, "+ E_COLOUMN_POSITION + " text not null, " +
			E_COLOUMN_DATE + "  date not null, " + E_COLOUMN_TIME + " time not null ," + E_COLOUMN_SERVERID + " integer not null);";
	
	public static String CREATE_USER_TABLE = "create table "+ USER_TABLE_NAME + "(" + U_COLOUMN_ID + " integer primary key autoincrement, " +
			 U_NAME + " text not null, " + U_LOCATION + " text not null, " + U_SERVERID + " integer not null);"; 
	
	public static String CREATE_RELATION_TABLE = "create table " + RELATION_TABLE_NAME + "(" + RID + " integer primary key autoincrement , "+ R_EVENT_ID + " integer, " +
			 R_USER_ID + " integer " + ",foreign key (" + R_EVENT_ID + ") references " + EVENT_TABLE_NAME + "(" + R_S_EVENT_ID + ")" +
			" ,foreign key (" + R_USER_ID + ") references " + USER_TABLE_NAME + "(" + R_S_USER_ID +  "));";
	
	public static String CREATE_FRIENDS_DATA = "create table "+ FRIENDS_DATA_TABLE_NAME+ "(" + F_SNO + " integer primary key autoincrement, "+ F_Name + " text not null, "
			+ F_UID + " integer, "+ F_STATUS + " integer, " + "foreign key (" + F_UID + ") references "+ USER_TABLE_NAME + " ("+ U_SERVERID + "))";
	
	/*public static String CREATE_FRIERND_RELATION = "create table "+ FRIEND_RELATION_TABLE_NAME + " (" + FF_SNO +" integer primary key autoincrement, " + 
			FF_UID1+ " integer, "+ FF_UID1 + "integer, foreign key ("+ FF_UID1 +") reference "+ USER_TABLE_NAME + "(" + U_SERVERID + "), foreign key ("
			+ FF_UID2 + " reference "+USER_TABLE_NAME + "(" + U_SERVERID + "))";*/
	
	public EventDatabaseHelper(Context context)
	{
		super(context,DATABASE_NAME,null,VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_EVENT_TABLE);
		db.execSQL(CREATE_USER_TABLE);
		db.execSQL(CREATE_RELATION_TABLE);
		db.execSQL(CREATE_FRIENDS_DATA);
		//db.execSQL(CREATE_FRIERND_RELATION);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("drop table if exists " + EVENT_TABLE_NAME);
		db.execSQL("drop table if exists " + USER_TABLE_NAME);
		db.execSQL("drop table if exists " + RELATION_TABLE_NAME);
		onCreate(db);
	}

}
