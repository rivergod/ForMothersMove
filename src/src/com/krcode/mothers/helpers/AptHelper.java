package com.krcode.mothers.helpers;

import java.util.LinkedList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.android.maps.GeoPoint;
import com.krcode.mothers.IConstant;

public class AptHelper {

	// public static final float 1E6 = 10000000.0;

	public static List<GeoPoint> findMarker(GeoPoint topleft,
			GeoPoint bottomright, int zoomlevel) {

		List<GeoPoint> marker = new LinkedList<GeoPoint>();

		if (zoomlevel < 16) {
			return marker;
		}

		// open databases;
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				getDatabaseName(), null);

		Cursor c = db.query(
				"apts",
				new String[] { "latitude", "longitude" },
				"(latitude between ? and ?) and (longitude between ? and ?)",
				new String[] { String.valueOf(bottomright.getLatitudeE6()),
						String.valueOf(topleft.getLatitudeE6()),
						String.valueOf(topleft.getLongitudeE6()),
						String.valueOf(bottomright.getLongitudeE6()) }, null,
				null, null);

		while (c.moveToNext()) {
			marker.add(new GeoPoint(c.getInt(0), c.getInt(1)));
		}

		c.close();

		db.close();

		return marker;
	}

	public static String getDatabaseName() {
		return IConstant.SD_PATH + IConstant.DB_PATH + "mothers.db";
	}
}
