package com.krcode.mothers.helpers;

import java.util.LinkedList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.android.maps.GeoPoint;
import com.krcode.mothers.IConstant;
import com.krcode.mothers.vo.AptsVO;
import com.krcode.mothers.vo.IPointVO;

public class AptHelper {

	// public static final float 1E6 = 10000000.0;

	public static List<? extends IPointVO> findMarker(GeoPoint topleft,
			GeoPoint bottomright, int zoomlevel) {

		List<AptsVO> markers = new LinkedList<AptsVO>();

		if (zoomlevel < 16) {
			return markers;
		}

		// open databases;
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				getDatabaseName(), null);

		Cursor c = db.query(
				"apts",
				new String[] { "apt_name", "address", "latitude", "longitude" },
				"(latitude between ? and ?) and (longitude between ? and ?)",
				new String[] { String.valueOf(bottomright.getLatitudeE6()),
						String.valueOf(topleft.getLatitudeE6()),
						String.valueOf(topleft.getLongitudeE6()),
						String.valueOf(bottomright.getLongitudeE6()) }, null,
				null, null);

		while (c.moveToNext()) {
			AptsVO vo = new AptsVO();
			
			vo.setAptName(c.getString(0));
			vo.setAddress(c.getString(1));
			vo.setLat(c.getString(2));
			vo.setLng(c.getString(3));
			
			markers.add(vo);
		}

		c.close();

		db.close();

		return markers;
	}

	public static String getDatabaseName() {
		return IConstant.SD_PATH + IConstant.DB_PATH + "mothers.db";
	}
}
