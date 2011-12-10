package com.krcode.mothers.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.android.maps.GeoPoint;
import com.krcode.mothers.IConstant;
import com.krcode.mothers.vo.AccountVO;
import com.krcode.mothers.vo.AptsTradeVO;
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
				new String[] { "apt_name", "address", "latitude", "longitude",
						"dong_code", "danji_code" },
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
			vo.setDongCode(c.getString(4));
			vo.setDanjiCode(c.getString(5));

			markers.add(vo);
		}

		c.close();

		db.close();

		return markers;
	}

	public List<? extends AptsVO> getFavorApts(AccountVO vo) {
		List<AptsVO> markers = new LinkedList<AptsVO>();

		HttpClient cli = new DefaultHttpClient();

		HttpPost httpPost = new HttpPost();

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();

		nvps.add(new BasicNameValuePair("userId", vo.getUserId()));

		try {
			httpPost.setURI(new URI("http://mothers.krcode.com/apis/apts.php"));
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

			HttpResponse httpRes = cli.execute(httpPost);

			HttpEntity entity = httpRes.getEntity();
			InputStream is = entity.getContent();

			StringBuilder recvContent = new StringBuilder();
			BufferedReader buffReader = new BufferedReader(
					new InputStreamReader(is, "UTF-8"));

			String contentString = "";

			while ((contentString = buffReader.readLine()) != null) {
				recvContent.append(contentString);
			}

			JSONArray jary = new JSONArray(recvContent.toString());

			for (int i = 0; i < jary.length(); i++) {
				JSONObject jobj = jary.getJSONObject(i);

				AptsVO retVo = new AptsVO();

				retVo.setAptName(jobj.getString("name"));
				retVo.setAddress(jobj.getString("address"));

				markers.add(retVo);
			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return markers;
	}

	public List<AptsTradeVO> getAptsTraneInfos(AptsVO vo) {
		List<AptsTradeVO> retVal = new ArrayList<AptsTradeVO>();

		HttpClient cli = new DefaultHttpClient();
		HttpPost httpPost = null;
		List<NameValuePair> nvps = null;
		HttpResponse httpResponse = null;
		HttpEntity httpEntity = null;
		BufferedReader buffReader = null;
		String readLine = null;
		StringBuffer readStringBuffer = null;

		// 거래정보
		try {
			// 단자의 거래 정보에 있는 평수 가져오기
			httpPost = new HttpPost(
					"http://rtmobile.mltm.go.kr/mobile.do?cmd=getTradeDanji");
			nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("danjiCode", vo.getDanjiCode()));
			nvps.add(new BasicNameValuePair("dongCode", vo.getDongCode()));
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));

			httpResponse = cli.execute(httpPost);

			httpEntity = httpResponse.getEntity();
			readStringBuffer = new StringBuffer();
			buffReader = new BufferedReader(new InputStreamReader(
					httpEntity.getContent()));

			while ((readLine = buffReader.readLine()) != null) {
				readStringBuffer.append(readLine);
			}

			JSONObject tradeDanji = new JSONObject(readStringBuffer.toString());
			JSONArray tradeDanjiAreas = tradeDanji.getJSONArray("moctJsonArea");

			List<String> tradeAreas = new ArrayList<String>();
			for (int i = 0; i < tradeDanjiAreas.length(); i++) {
				JSONObject tradeDanjiAreaObj = tradeDanjiAreas.getJSONObject(i);
				tradeAreas.add(tradeDanjiAreaObj.getString("DANJI_AREA"));
			}

			Iterator<String> tradeAreasIter = tradeAreas.iterator();
			// 현재 저장되어 있는 거래 녀도 가져오기
			while (tradeAreasIter.hasNext()) {
				String currArea = tradeAreasIter.next();
				httpPost = new HttpPost(
						"http://rtmobile.mltm.go.kr/mobile.do?cmd=getTradeDanjiYear");
				nvps = new ArrayList<NameValuePair>();
				nvps.add(new BasicNameValuePair("danjiArea", currArea));
				nvps.add(new BasicNameValuePair("danjiCode", vo.getDanjiCode()));
				nvps.add(new BasicNameValuePair("dongCode", vo.getDongCode()));
				httpPost.setEntity(new UrlEncodedFormEntity(nvps));

				httpResponse = cli.execute(httpPost);

				httpEntity = httpResponse.getEntity();
				readStringBuffer = new StringBuffer();
				buffReader = new BufferedReader(new InputStreamReader(
						httpEntity.getContent()));

				while ((readLine = buffReader.readLine()) != null) {
					readStringBuffer.append(readLine);
				}

				JSONObject tradeDanjiYear = new JSONObject(
						readStringBuffer.toString());
				JSONArray tradeDanjiYears = tradeDanjiYear
						.getJSONArray("moctJsonYear");

				List<String> tradeYears = new ArrayList<String>();
				for (int i = 0; i < tradeDanjiYears.length(); i++) {
					JSONObject tradeDanjiYearObj = tradeDanjiYears
							.getJSONObject(i);
					tradeYears.add(tradeDanjiYearObj.getString("DANJI_YEAR"));
				}

				Iterator<String> tradeYearsIter = tradeYears.iterator();
				while (tradeYearsIter.hasNext()) {
					String currYear = tradeYearsIter.next();
					httpPost = new HttpPost(
							"http://rtmobile.mltm.go.kr/mobile.do?cmd=getTradeDanjiDetail");
					nvps = new ArrayList<NameValuePair>();
					nvps.add(new BasicNameValuePair("danjiArea", currArea));
					nvps.add(new BasicNameValuePair("danjiCode", vo
							.getDanjiCode()));
					nvps.add(new BasicNameValuePair("danjiYear", currYear));
					nvps.add(new BasicNameValuePair("dongCode", vo
							.getDongCode()));
					httpPost.setEntity(new UrlEncodedFormEntity(nvps));

					httpResponse = cli.execute(httpPost);

					httpEntity = httpResponse.getEntity();
					readStringBuffer = new StringBuffer();
					buffReader = new BufferedReader(new InputStreamReader(
							httpEntity.getContent()));

					while ((readLine = buffReader.readLine()) != null) {
						readStringBuffer.append(readLine);
					}

					JSONObject tradeDanjiDetail = new JSONObject(
							readStringBuffer.toString());
					JSONArray tradeDanjiDetails = tradeDanjiDetail
							.getJSONArray("moctJsonDetail");

					for (int i = 0; i < tradeDanjiDetails.length(); i++) {
						JSONObject tradeDanjiDetailObj = tradeDanjiDetails
								.getJSONObject(i);

						AptsTradeVO newVo = new AptsTradeVO();

						newVo.setAptsVo(vo);
						newVo.setType(0);  //trade
						newVo.setArea(currArea);
						newVo.setFloor(tradeDanjiDetailObj.getString("FLOOR"));
						newVo.setYear(currYear);
						newVo.setMonth(tradeDanjiDetailObj.getString("MONTH"));
						newVo.setDay(tradeDanjiDetailObj.getString("DAY"));
						newVo.setPrice(tradeDanjiDetailObj.getString("AMT")
								.trim());

						retVal.add(newVo);
					}
				}
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return retVal;
	}

	public static String getDatabaseName() {
		return IConstant.SD_PATH + IConstant.DB_PATH + "mothers.db";
	}
}
