package com.krcode.mothers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.app.ListActivity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.krcode.mothers.helpers.AccountHelper;
import com.krcode.mothers.vo.AptsVO;
import com.krcode.mothers.vo.IPointVO;

public class AttentionListActivity extends ListActivity {
	
	private List<IPointVO> lst;

	private String currLat;
	private String currLng;
	
	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.attentionlist);
		
		Intent intent = getIntent();
		
		currLat = intent.getExtras().getString("LATITUDE");
		currLng = intent.getExtras().getString("LONGITUDE");
		
		Button addBtn = (Button) findViewById(R.id.attentionlistaddbtn);
		
		handler = new Handler() {
			public void handleMessage(Message msg)
			{
				if (msg.what == 1)
				{
					if (msg.obj != null ) {
						lst.add((IPointVO)msg.obj);
					}
				
					makeList();
				}
			}
		};
		
		addBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Locale.setDefault(Locale.KOREA); // = ko_KO, 디폴트로 되어 있으면 말고
				Geocoder geocoder = new Geocoder(getApplicationContext(),
						Locale.getDefault());

				List<Address> addresses = null;
				
				try {
					addresses = geocoder.getFromLocation(Double.parseDouble(AttentionListActivity.this.currLat) / 1E6, Double.parseDouble(AttentionListActivity.this.currLng) / 1E6, 1);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				StringBuffer geoAddress = new StringBuffer();
				
				if(addresses != null) {
//					for(int i = 0; i < addresses.get(0).getMaxAddressLineIndex(); i++ ) {
//						geoAddress.append(addresses.get(0).getAddressLine(i) + " ");
//					}
					
					geoAddress.append(addresses.get(0).getAddressLine(0));
				}
				
				AptsVO vo = new AptsVO();
				vo.setAddress(geoAddress.toString());
				vo.setLat(currLat);
				vo.setLng(currLng);
				
				AccountHelper hlp = new AccountHelper();
				if(hlp.putAttention(MainActivity.accountVO, vo)) {
					Message msg = new Message();
					
					msg.what = 1;
					msg.obj = vo;
					
					handler.sendMessage(msg);
				}
				else {
					Toast.makeText(getApplicationContext(), "관심목록 추가 중 에러가 발생하였습니다.",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		lst = new LinkedList<IPointVO>();

		if (MainActivity.accountVO != null
				&& MainActivity.accountVO.isAvalidable()) {
			AccountHelper hlp = new AccountHelper();
			lst.addAll(hlp.getAttentionApts(MainActivity.accountVO));
		}

		makeList();
	}
	
	private void makeList() {
		List<Map<String, String>> al = new ArrayList<Map<String, String>>();

		for (int i = 0; i < lst.size(); i++) {
			Map<String, String> dataMap = new HashMap<String, String>();

			dataMap.put("address", ((AptsVO) lst.get(i)).getAddress());

			al.add(dataMap);
		}

		SimpleAdapter sa = new SimpleAdapter(this, al,
				R.layout.attentionlistrow,
				new String[] { "address" },
				new int[] { R.id.attentionlistrowaddress });
		this.setListAdapter(sa);

	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		Intent intent = new Intent(this, MainActivity.class);

		intent.putExtra("ADDRESS", ((AptsVO) lst.get(position)).getAddress());
		intent.putExtra("LATITUDE", ((AptsVO) lst.get(position)).getLat());
		intent.putExtra("LONGITUDE", ((AptsVO) lst.get(position)).getLng());

		setResult(RESULT_OK, intent);

		this.finish();

		super.onListItemClick(l, v, position, id);
	}


}
