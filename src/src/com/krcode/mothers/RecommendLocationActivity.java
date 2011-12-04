package com.krcode.mothers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.krcode.mothers.helpers.AptHelper;
import com.krcode.mothers.vo.AptsVO;
import com.krcode.mothers.vo.IPointVO;

public class RecommendLocationActivity extends ListActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recommendlist);

		List<IPointVO> lst = new LinkedList<IPointVO>();

		if (MainActivity.accountVO != null
				&& MainActivity.accountVO.isAvalidable()) {
			AptHelper hlp = new AptHelper();
			lst.addAll(hlp.getFavorApts(MainActivity.accountVO));
		}

		List<Map<String, String>> al = new ArrayList<Map<String, String>>();

		for (int i = 0; i < lst.size(); i++) {
			Map<String, String> dataMap = new HashMap<String, String>();
			
			dataMap.put("aptName", ((AptsVO)lst.get(i)).getAptName());
			dataMap.put("address", ((AptsVO)lst.get(i)).getAddress());
			
			al.add(dataMap);
		}

		SimpleAdapter sa = new SimpleAdapter(this, al,
				R.layout.recommendlistrow,
				new String[] { "aptName", "address" }, new int[] {
						R.id.recommendlistrowname, R.id.recommendlistrowaddress });
		this.setListAdapter(sa);
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		
		super.onListItemClick(l, v, position, id);
	}
}
