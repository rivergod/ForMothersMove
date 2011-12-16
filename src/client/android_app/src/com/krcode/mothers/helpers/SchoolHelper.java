package com.krcode.mothers.helpers;

import java.util.LinkedList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.android.maps.GeoPoint;
import com.krcode.mothers.IConstant;
import com.krcode.mothers.vo.IPointVO;
import com.krcode.mothers.vo.SchoolVO;

public class SchoolHelper {
	public static List<? extends IPointVO> findMarker(GeoPoint topleft,
			GeoPoint bottomright, int zoomlevel) {

		List<SchoolVO> markers = new LinkedList<SchoolVO>();

		if (zoomlevel < 15) {
			return markers;
		}

		// open databases;
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				getDatabaseName(), null);

		Cursor c = db.query(
				"schools",
				new String[] { "name", "homepage", "address", "telephone",
						"lat", "lng" },
				"(lat between ? and ?) and (lng between ? and ?)",
				new String[] { String.valueOf(bottomright.getLatitudeE6()),
						String.valueOf(topleft.getLatitudeE6()),
						String.valueOf(topleft.getLongitudeE6()),
						String.valueOf(bottomright.getLongitudeE6()) }, null,
				null, null);

		while (c.moveToNext()) {
			SchoolVO vo = new SchoolVO();

			vo.setName(c.getString(0));
			vo.setHomepage(c.getString(1));
			vo.setAddress(c.getString(2));
			vo.setTelephone(c.getString(3));
			vo.setLat(c.getString(4));
			vo.setLng(c.getString(5));

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
