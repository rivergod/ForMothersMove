package com.krcode.mothers.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
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

import com.krcode.mothers.vo.AccountVO;
import com.krcode.mothers.vo.AptsVO;
import com.krcode.mothers.vo.IPointVO;

public class AccountHelper {
	public AccountVO login(AccountVO vo) {
		AccountVO retVal = new AccountVO();

		HttpClient cli = new DefaultHttpClient();

		HttpPost httpPost = new HttpPost();

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();

		nvps.add(new BasicNameValuePair("userId", vo.getUserId()));
		nvps.add(new BasicNameValuePair("passwd", vo.getPasswd()));

		try {
			httpPost.setURI(new URI("http://mothers.krcode.com/apis/login.php"));
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

			JSONObject jobj = new JSONObject(recvContent.toString());

			if ("ok".equals(jobj.getString("result"))) {

				retVal.setUserId(jobj.getString("userid"));
				retVal.setFavor0(new Float(jobj.getDouble("favor0"))
						.floatValue());
				retVal.setFavor1(new Float(jobj.getDouble("favor1"))
						.floatValue());
				retVal.setFavor2(new Float(jobj.getDouble("favor2"))
						.floatValue());
				retVal.setFavor3(new Float(jobj.getDouble("favor3"))
						.floatValue());

				retVal.setAvalidable(true);
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

		return retVal;
	}

	public AccountVO logout(AccountVO vo) {
		return new AccountVO();
	}

	public AccountVO createAccount(AccountVO vo) {

		AccountVO retVal = new AccountVO();

		HttpClient cli = new DefaultHttpClient();

		HttpPost httpPost = new HttpPost();

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();

		nvps.add(new BasicNameValuePair("userId", vo.getUserId()));
		nvps.add(new BasicNameValuePair("passwd", vo.getPasswd()));
		nvps.add(new BasicNameValuePair("favor0",
				String.valueOf(vo.getFavor0())));
		nvps.add(new BasicNameValuePair("favor1",
				String.valueOf(vo.getFavor1())));
		nvps.add(new BasicNameValuePair("favor2",
				String.valueOf(vo.getFavor2())));
		nvps.add(new BasicNameValuePair("favor3",
				String.valueOf(vo.getFavor3())));

		try {
			httpPost.setURI(new URI(
					"http://mothers.krcode.com/apis/createaccount.php"));
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

			JSONObject jobj = new JSONObject(recvContent.toString());

			if ("ok".equals(jobj.getString("result"))) {

				retVal.setUserId(jobj.getString("userid"));
				retVal.setFavor0(new Float(jobj.getDouble("favor0"))
						.floatValue());
				retVal.setFavor1(new Float(jobj.getDouble("favor1"))
						.floatValue());
				retVal.setFavor2(new Float(jobj.getDouble("favor2"))
						.floatValue());
				retVal.setFavor3(new Float(jobj.getDouble("favor3"))
						.floatValue());

				retVal.setAvalidable(true);
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

		return retVal;
	}

	public boolean putAttention(AccountVO accountVO, AptsVO aptsVO) {
		boolean retVal = false;
		
		HttpClient cli = new DefaultHttpClient();

		HttpPost httpPost = new HttpPost();

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();

		nvps.add(new BasicNameValuePair("userId", accountVO.getUserId()));
		nvps.add(new BasicNameValuePair("address", aptsVO.getAddress()));
		nvps.add(new BasicNameValuePair("lat", aptsVO.getLat()));
		nvps.add(new BasicNameValuePair("lng", aptsVO.getLng()));

		try {
			httpPost.setURI(new URI(
					"http://mothers.krcode.com/apis/attentionappend.php"));
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

			JSONObject jobj = new JSONObject(recvContent.toString());

			if ("ok".equals(jobj.getString("result"))) {
				retVal = true;
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
		
		return retVal;
	}

	public List<? extends IPointVO> getAttentionApts(AccountVO vo) {
		List<AptsVO> markers = new LinkedList<AptsVO>();
		
		HttpClient cli = new DefaultHttpClient();

		HttpPost httpPost = new HttpPost();

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();

		nvps.add(new BasicNameValuePair("userId", vo.getUserId()));

		try {
			httpPost.setURI(new URI("http://mothers.krcode.com/apis/attention.php"));
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

				retVo.setAddress(jobj.getString("address"));
				retVo.setLat(jobj.getString("lat"));
				retVo.setLng(jobj.getString("lng"));

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
}
