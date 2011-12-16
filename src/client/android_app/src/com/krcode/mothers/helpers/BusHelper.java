package com.krcode.mothers.helpers;

import java.util.LinkedList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.android.maps.GeoPoint;
import com.krcode.mothers.IConstant;
import com.krcode.mothers.vo.BusStationVO;
import com.krcode.mothers.vo.IPointVO;

public class BusHelper {
	public static List<? extends IPointVO> findMarker(GeoPoint topleft,
			GeoPoint bottomright, int zoomlevel) {

		List<BusStationVO> markers = new LinkedList<BusStationVO>();

		if (zoomlevel < 17) {
			return markers;
		}

		// open databases;
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				getDatabaseName(), null);

		Cursor c = db.query(
				"busstation",
				new String[] { "stationId", "stationNameKor", "lat", "lng" },
				"(lat between ? and ?) and (lng between ? and ?)",
				new String[] { String.valueOf(bottomright.getLatitudeE6()),
						String.valueOf(topleft.getLatitudeE6()),
						String.valueOf(topleft.getLongitudeE6()),
						String.valueOf(bottomright.getLongitudeE6()) }, null,
				null, null);

		while (c.moveToNext()) {
			BusStationVO vo = new BusStationVO();

			vo.setStationId(c.getString(0));
			vo.setStationNameKor(c.getString(1));
			vo.setLat(c.getString(2));
			vo.setLng(c.getString(3));

			markers.add(vo);
		}

		c.close();

		db.close();

		return markers;
	}

	/*
	 * getRouteByStationRequest
	 * 
	 * 
	 * http://ws.bus.go.kr/api/rest/stationinfo/getRouteByStation?arsId=02212
	 * 
	 * 
	 * http://ws.bus.go.kr/api/rest/stationinfo/getRouteByStation?arsId=02212
	 * 
	 * 
	 * <resource path="getRouteByStation/"> <method name="GET"> <request> <param
	 * name="arsid" style="query" type="xs:string" /> <param
	 * name="commsgheader.servicekey" style="query" type="xs:string" /> <param
	 * name="commsgheader.requestmsgid" style="query" type="xs:string" /> <param
	 * name="commsgheader.requesttime" style="query" type="xs:string" /> <param
	 * name="commsgheader.callbackuri" style="query" type="xs:string" /> <param
	 * name="msgheader.headermsg" style="query" type="xs:string" /> </request>
	 * <response> <representation mediaType="text/xml" /> </response> </method>
	 * </resource>
	 */

	public static String getDatabaseName() {
		return IConstant.SD_PATH + IConstant.DB_PATH + "mothers.db";
	}
}
