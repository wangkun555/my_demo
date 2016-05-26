package com.lonely.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelper extends SQLiteOpenHelper {

	public static final String MyDB_name = "";

	public static final int VERSION = 1;

	public static String mY_TABLE_name = "student";

	public MyOpenHelper(Context context) {
		super(context, mY_TABLE_name, null, VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		// 创建数据库表
		String sql = "CREATE TABLE IF NOT EXISTS" + mY_TABLE_name
				+ "id INTEGER,name TEXT,score INTEGER";
		db.execSQL(sql);
		// db.rawQuery(sql, selectionArgs)

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

		if (newVersion > oldVersion) {

			// 增加一列表学生性别
			String sql = "ALTER" + mY_TABLE_name + "ADD COLUMN SEX TEXT";
			db.execSQL(sql);
		}
	}

}
