package com.sjd.mynote2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLOpenHelper extends SQLiteOpenHelper {

	static String name = "MyDb.db";
	static int version = 1;
	
	public MySQLOpenHelper(Context context) {
		super(context, name, null, version);
		// TODO Auto-generated constructor stub
		
	}

	

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE Note(id integer primary key autoincrement,title varchar(20),content text)";
		db.execSQL(sql);
		

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
