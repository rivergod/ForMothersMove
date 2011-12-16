package com.krcode.mothers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.krcode.mothers.helpers.AptHelper;
import com.krcode.mothers.vo.AptsTradeVO;
import com.krcode.mothers.vo.AptsVO;

public class AptsActivity extends ListActivity {

	private AptsVO aptsVo = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.apts);

		TextView aptsTitleTextView = (TextView) findViewById(R.id.aptstitle);
		TextView aptsAddressTextView = (TextView) findViewById(R.id.aptsaddresstextview);

		if (getIntent().getExtras().containsKey("APTSVO")) {
			aptsVo = (AptsVO) getIntent().getExtras().getSerializable("APTSVO");
		}

		if (aptsVo != null) {
			aptsTitleTextView.setText(aptsVo.getAptName());
			aptsAddressTextView.setText(aptsVo.getAddress());

			ProgressDialog dlg = ProgressDialog.show(this, "loading",
					"loading APT information.");

			List<Map<String, String>> al = new ArrayList<Map<String, String>>();

			AptHelper hlp = new AptHelper();
			List<AptsTradeVO> tradeVos = hlp.getAptsTradeInfos(aptsVo);

			Iterator<AptsTradeVO> tradeVosIter = tradeVos.iterator();

			while (tradeVosIter.hasNext()) {
				AptsTradeVO vo = tradeVosIter.next();

				Map<String, String> dataMap = new HashMap<String, String>();

				dataMap.put("type", vo.getType() == 0 ? "매매" : "전세");
				dataMap.put("year", vo.getYear());
				dataMap.put("month", vo.getMonth());
				if ("1".equals(vo.getDay())) {
					dataMap.put("day", "초순");
				} else if ("2".equals(vo.getDay())) {
					dataMap.put("day", "중순");
				} else if ("3".equals(vo.getDay())) {
					dataMap.put("day", "하순");
				} else {
					dataMap.put("day", "");
				}
				dataMap.put("squaremeter", vo.getArea());
				dataMap.put("floor", vo.getFloor());
				
				if(vo.getType() == 0 ) {
					dataMap.put("price", vo.getPrice() + "만원");
				}
				else {
					String priceStr = vo.getDeposit() + "만원";
					if(vo.getMonthlyFee().length() > 0 ) {
						priceStr += " (" + vo.getMonthlyFee() + "만원)";
					}
					dataMap.put("price", priceStr);
				}
				al.add(dataMap);
			}

			SimpleAdapter sa = new SimpleAdapter(this, al, R.layout.aptsrow,
					new String[] { "year", "month", "day", "type", "squaremeter", "floor", "price" }, new int[] {
							R.id.aptsrowyeartextview,
							R.id.aptsrowmonthtextview, R.id.aptsrowdaytextview,
							R.id.aptsrowtype, R.id.aptsrowsquaremetertextview, R.id.aptsrowfloortextview, R.id.aptsrowpricetextview });
			this.setListAdapter(sa);

			dlg.dismiss();
		}

	}
}
