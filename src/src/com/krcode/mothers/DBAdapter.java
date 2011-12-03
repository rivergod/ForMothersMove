package com.krcode.mothers;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBAdapter {
	public static final String KEY_STATIONNAME = "StationName";
	public static String DATABASE_NAME = IConstant.SD_PATH + IConstant.DB_PATH + "mothers.db";
	private static final String TABLE_NAME = "Station";

	public static SQLiteDatabase mDb;

	public static void close() {
		try {
			mDb.close();
		} catch (IllegalStateException e) {
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static public void open() {
		mDb = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null);
	}
	
	public static Cursor selectApts(String topLat, String bottomLat, String leftLng, String rightLng) {
		return mDb.query("apts", null, "(latitude between ? and ?) and (longitude between ? and ?)", new String[] {topLat, bottomLat, leftLng, rightLng}, null, null, null);
	}

	public static void setDBname(String strDBname) {
		DATABASE_NAME = strDBname;
	}
}
