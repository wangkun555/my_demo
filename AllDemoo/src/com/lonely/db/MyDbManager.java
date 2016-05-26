package com.lonely.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class MyDbManager {
	private static MyDbManager instance;

	private MyOpenHelper myOpenHelper;

	private SQLiteDatabase mDatabase;
	private Context mContext;

	private MyDbManager() {
	}

	public static MyDbManager getInstance() {
		if (instance == null) {
			synchronized (MyDbManager.class) {
				if (instance == null) {
					instance = new MyDbManager();
				}
			}
		}
		return instance;
	}

	// 初始化数据库
	public synchronized void init(Context context) {
		if (context == null) {
			return;
		}
		if (myOpenHelper == null) {
			myOpenHelper = new MyOpenHelper(context);
		}
		mContext = context;

		mDatabase = myOpenHelper.getReadableDatabase();
	}

	/**
	 * 退出时，释放
	 */
	public synchronized void release() {
		if (myOpenHelper != null) {
			myOpenHelper.close();
			myOpenHelper = null;
		}

		if (instance != null) {
			instance = null;
		}

		if (mContext != null) {
			mContext = null;
		}
	}

	public SQLiteDatabase getSQLiteDatabase() {
		if (mDatabase != null) {
			return mDatabase;
		} else {
			try {
				synchronized (this) {
					if (mDatabase == null) {
						init(mContext);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return mDatabase;
		}

	}

}
