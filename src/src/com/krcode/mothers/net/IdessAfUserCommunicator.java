package com.krcode.mothers.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class IdessAfUserCommunicator {
	public void getDatas(Map<String, Object> param) {
		HttpClient cli = new DefaultHttpClient();

		HttpGet httpGet = new HttpGet(
				"http://idess.cafe24.com/af/user/api.php?action=academy_search&maxLongitude=127.08616638183594&minLatitude=37.497493743896484&maxLatitude=37.538204193115234&minLongitude=127.04496765136719");

		HttpResponse res = null;

		try {
			res = cli.execute(httpGet);

			HttpEntity entity = res.getEntity();

			InputStream is = entity.getContent();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "EUC-KR"), 8);

			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line).append("\n");
			}
			is.close();

			Log.i("MOTHERS", "Ins Datas Comming......" + res.getStatusLine()
					+ " -- " + sb.toString());

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			Log.e("MOTHERS", e.getStackTrace().toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e("MOTHERS", e.getStackTrace().toString());
		}

	}
}
