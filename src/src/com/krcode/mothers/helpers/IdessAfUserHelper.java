package com.krcode.mothers.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.krcode.mothers.vo.IPointVO;
import com.krcode.mothers.vo.IdessAfUserVO;

public class IdessAfUserHelper {
	public static List<? extends IPointVO> findMarker(GeoPoint topleft,
			GeoPoint bottomright, int zoomlevel) {
		
		List<IdessAfUserVO> markers = new LinkedList<IdessAfUserVO>();
		
		if ( zoomlevel < 18 ) {
			return markers;
		}

		HttpClient cli = new DefaultHttpClient();

		HttpGet httpGet = new HttpGet(
				"http://idess.cafe24.com/af/user/api.php?action=academy_search&maxLongitude="
						+ (((float) bottomright.getLongitudeE6()) / 1E6)
						+ "&minLatitude="
						+ (((float) bottomright.getLatitudeE6()) / 1E6)
						+ "&maxLatitude="
						+ (((float) topleft.getLatitudeE6()) / 1E6)
						+ "&minLongitude="
						+ (((float) topleft.getLongitudeE6()) / 1E6));

		HttpResponse res = null;

		// String context = "<academyFee />";
		Document xmlDoc = null;

		try {
			res = cli.execute(httpGet);

			HttpEntity entity = res.getEntity();

			InputStream is = entity.getContent();

			InputStreamReader isr = new InputStreamReader(is, "UTF-8");
			xmlDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.parse(new InputSource(isr));
			isr.close();
			is.close();

			XPath xpath = XPathFactory.newInstance().newXPath();

			NodeList nodes = (NodeList) xpath.evaluate("/academyFee/a", xmlDoc,
					XPathConstants.NODESET);

			for (int i = 0; i < nodes.getLength(); i++) {
				Node a = nodes.item(i);
				IdessAfUserVO vo = new IdessAfUserVO();

				NodeList childNodes = a.getChildNodes();

				for (int j = 0; j < childNodes.getLength(); j++) {
					Node currNode = childNodes.item(j);
					
					if(currNode.getNodeType() != Node.ELEMENT_NODE){
						continue;
					}
					
					String nodeName = currNode.getNodeName();
					String nodeValue = currNode.getTextContent();

					if ("id".equals(nodeName)) {
						vo.setId(nodeValue);
					}
					else if ("nm".equals(nodeName)) {
						vo.setNm(nodeValue);
					}
					else if ("lt".equals(nodeName)) {
						vo.setLt(nodeValue);
					}
					else if ("ln".equals(nodeName)) {
						vo.setLn(nodeValue);
					}
				}
				
				markers.add(vo);
			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			Log.e("MOTHERS", e.getStackTrace().toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e("MOTHERS", e.getStackTrace().toString());
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			Log.e("MOTHERS", e.getStackTrace().toString());
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			Log.e("MOTHERS", e.getStackTrace().toString());
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			Log.e("MOTHERS", e.getStackTrace().toString());
		}

		return markers;

	}
}
