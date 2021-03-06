package com.genue.android.moneyentry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class UserDBHelper extends SQLiteOpenHelper
{
	private Context mContext;

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "User.db";
	private static final String TABLE_USER_NAME = "USER";//유저 테이블
	private static String DATABASE_ID = "";//database 사용자 이름
	private static String DATABASE_PW = "";//database 사용자 비번
	private static final String SQL_CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_USER_NAME + " (" + "USER TEXT, " + "PW TEXT" + ");";

	/**
	 * 싱글톤 적용
	 */
	private static UserDBHelper dbInstance;

	public static synchronized UserDBHelper getInstance(Context context)
	{
		Log.i(">>>>", "UserDBHelper getInstance");
		if(dbInstance == null) {
			dbInstance = new UserDBHelper(context, DATABASE_NAME, null, 1);
		}
		return dbInstance;
	}

	private UserDBHelper(Context context, String user, SQLiteDatabase.CursorFactory factory, int version)
	{
		super(context, user, factory, version);
		Log.i(">>>>", "UserDBHelper construct");
		mContext = context;
	}

	//디비 생성
	//필요한 테이블 생성해
	public void createDB()
	{
		Log.i(">>>>", "createDB");
		onCreate(dbInstance.getWritableDatabase());
	}

	//초기화
	public void resetDB(){
		Log.i(">>>>", "resetDB");
		String drop = "DROP TABLE IF EXISTS " + TABLE_USER_NAME;
		SQLiteDatabase db = dbInstance.getWritableDatabase();
		db.execSQL(drop);
	}

	//데이터 추가
	public void insertDB(String tableName, String id, String pw)
	{
		Log.i(">>>>", "insertDB " + id);
		SQLiteDatabase db = dbInstance.getWritableDatabase();
		String insertQuery = "INSERT INTO " + tableName + "(USER, PW) VALUES (?, ?)";
		Object[] params = new Object[] {id, null};
		db.execSQL(insertQuery, params);
	}

	public String getID(){
		String id = "";
		SQLiteDatabase db = dbInstance.getWritableDatabase();
		String getQuery = "SELECT USER, PW FROM " + TABLE_USER_NAME;
		Cursor c = db.rawQuery(getQuery, null);
		if(c == null){
			Log.d(">>>", "DB 비어있어");
		}else{
			if(c.moveToNext()) {
				Log.i(">>>>", "checkID 내용 " + c.getString(0));
				Toast.makeText(mContext, "뭐가 있지?? " + c.getString(0) + " " + c.getString(1), Toast.LENGTH_SHORT).show();
				return c.getString(0);
			}
		}
		return "";
	}

	public void selectDB(String tableName, ContentValues row)
	{

	}

	public void updateDB(String tableName, ContentValues row)
	{

	}

	public void deleteDB(String tableName, ContentValues row)
	{

	}

	//user 테이블 id 받아와
	public String checkID()
		{
			Log.i(">>>>", "checkID");
			SQLiteDatabase db = dbInstance.getReadableDatabase();
			String SQL_GET_ID = "SELECT USER FROM " + TABLE_USER_NAME;
			Cursor c = db.rawQuery(SQL_GET_ID, null);
			if(c == null) {
				Toast.makeText(mContext, "아무것도 없어", Toast.LENGTH_SHORT).show();
			}
			else {
				if(c.moveToNext()) {
					Log.i(">>>>", "checkID 내용 " + c.getString(0));
					Toast.makeText(mContext, "뭐가 있지?? " + c.getString(0), Toast.LENGTH_SHORT).show();
					return c.getString(0);
				}
			}
		return null;
	}

	public void setID(String id)
	{
		insertDB(TABLE_USER_NAME, id, null);
	}

	public void setPW(String pw)
	{
		DATABASE_PW = pw;
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		Log.i(">>>>", "onCreate");
		db.execSQL(SQL_CREATE_USER_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
	}
}
