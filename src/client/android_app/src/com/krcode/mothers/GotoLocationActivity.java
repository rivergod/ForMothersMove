package com.krcode.mothers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class GotoLocationActivity extends Activity {
	private Spinner gugunSpinner;
	private Spinner dongSpinner;
	private List<String> gugunLst;
	
	private String gugunStr;
	private String dongStr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gotolocation);

		Spinner siSpinner = (Spinner) findViewById(R.id.gotolocationsispinner);
		ArrayAdapter<String> siSpinnerAdaptor = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, new String[] { "서울시" });
		siSpinner.setAdapter(siSpinnerAdaptor);

		gugunSpinner = (Spinner) findViewById(R.id.gotolocationgugunspinner);

		gugunLst = new ArrayList<String>();

		gugunLst.addAll(gugunMap.values());

		ArrayAdapter<String> gugunSpinnerAdaptor = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, gugunLst);
		gugunSpinner.setAdapter(gugunSpinnerAdaptor);
		gugunSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				updateDongSpinner(gugunLst.get(arg2));
				gugunStr = gugunLst.get(arg2);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}

		});

		dongSpinner = (Spinner) findViewById(R.id.gotolocationdongspinner);
		dongSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				dongStr = (String)dongSpinner.getSelectedItem();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});

		Button gotoBtn = (Button) findViewById(R.id.gotoLocationgotobtn);
		gotoBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(GotoLocationActivity.this, MainActivity.class);
				intent.putExtra("ADDRESS", "서울시 " + GotoLocationActivity.this.gugunStr + " " + GotoLocationActivity.this.dongStr);

				setResult(RESULT_OK, intent);

				GotoLocationActivity.this.finish();
				
			}
		});
	}

	private void updateDongSpinner(String gugun) {
		Set<String> keySet = GotoLocationActivity.gugunMap.keySet();

		Object[] keys = (Object[]) keySet.toArray();
		String targetKey = "";

		for (int i = 0; i < keys.length; i++) {

			String guValue = GotoLocationActivity.gugunMap.get(keys[i]);

			if (gugun.equals(guValue)) {
				targetKey = (String)keys[i];
			}
		}
		
		List<String> dongLst = new ArrayList<String>();

		if (targetKey != null && !"".equals(targetKey.trim())) {
			Object[] dongKeys = (Object[]) (GotoLocationActivity.dongMap
					.keySet().toArray());

			for (int i = 0; i < dongKeys.length; i++) {
				if(((String)dongKeys[i]).startsWith(targetKey)) {
					dongLst.add(GotoLocationActivity.dongMap.get(dongKeys[i]));
				}
			}
		}
		
		ArrayAdapter<String> dongSpinnerAdaptor = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item,
				dongLst);
		dongSpinner.setAdapter(dongSpinnerAdaptor);
	}

	private static final Map<String, String> gugunMap = new HashMap<String, String>();
	private static final Map<String, String> dongMap = new HashMap<String, String>();

	static {
		gugunMap.put("11680", "강남구");
		gugunMap.put("11740", "강동구");
		gugunMap.put("11305", "강북구");
		gugunMap.put("11500", "강서구");
		gugunMap.put("11620", "관악구");
		gugunMap.put("11215", "광진구");
		gugunMap.put("11530", "구로구");
		gugunMap.put("11545", "금천구");
		gugunMap.put("11350", "노원구");
		gugunMap.put("11320", "도봉구");
		gugunMap.put("11230", "동대문구");
		gugunMap.put("11590", "동작구");
		gugunMap.put("11440", "마포구");
		gugunMap.put("11410", "서대문구");
		gugunMap.put("11650", "서초구");
		gugunMap.put("11200", "성동구");
		gugunMap.put("11290", "성북구");
		gugunMap.put("11710", "송파구");
		gugunMap.put("11470", "양천구");
		gugunMap.put("11560", "영등포구");
		gugunMap.put("11170", "용산구");
		gugunMap.put("11380", "은평구");
		gugunMap.put("11110", "종로구");
		gugunMap.put("11140", "중구");
		gugunMap.put("11260", "중랑구");
		dongMap.put("1130510100", "미아동");
		dongMap.put("1130510200", "번동");
		dongMap.put("1130510300", "수유동");
		dongMap.put("1130510400", "우이동");
		dongMap.put("1121510400", "광장동");
		dongMap.put("1121510300", "구의동");
		dongMap.put("1121510900", "군자동");
		dongMap.put("1121510200", "능동");
		dongMap.put("1121510500", "자양동");
		dongMap.put("1121510100", "중곡동");
		dongMap.put("1121510700", "화양동");
		dongMap.put("1168010300", "개포동");
		dongMap.put("1168010800", "논현동");
		dongMap.put("1168010600", "대치동");
		dongMap.put("1168011800", "도곡동");
		dongMap.put("1168010500", "삼성동");
		dongMap.put("1168011100", "세곡동");
		dongMap.put("1168011500", "수서동");
		dongMap.put("1168010700", "신사동");
		dongMap.put("1168011000", "압구정동");
		dongMap.put("1168010100", "역삼동");
		dongMap.put("1168011400", "일원동");
		dongMap.put("1168010400", "청담동");
		dongMap.put("1123010500", "답십리동");
		dongMap.put("1123010100", "신설동");
		dongMap.put("1123010200", "용두동");
		dongMap.put("1123011000", "이문동");
		dongMap.put("1123010600", "장안동");
		dongMap.put("1123010400", "전농동");
		dongMap.put("1123010300", "제기동");
		dongMap.put("1123010700", "청량리동");
		dongMap.put("1123010800", "회기동");
		dongMap.put("1123010900", "휘경동");
		dongMap.put("1153010300", "가리봉동");
		dongMap.put("1153010700", "개봉동");
		dongMap.put("1153010600", "고척동");
		dongMap.put("1153010200", "구로동");
		dongMap.put("1153010900", "궁동");
		dongMap.put("1153010100", "신도림동");
		dongMap.put("1153010800", "오류동");
		dongMap.put("1153011000", "온수동");
		dongMap.put("1153011100", "천왕동");
		dongMap.put("1153011200", "항동");
		dongMap.put("1147010200", "목동");
		dongMap.put("1147010300", "신월동");
		dongMap.put("1147010100", "신정동");
		dongMap.put("1117010400", "갈월동");
		dongMap.put("1117012000", "도원동");
		dongMap.put("1117012200", "문배동");
		dongMap.put("1117013600", "보광동");
		dongMap.put("1117011500", "산천동");
		dongMap.put("1117013300", "서빙고동");
		dongMap.put("1117012300", "신계동");
		dongMap.put("1117011400", "신창동");
		dongMap.put("1117012100", "용문동");
		dongMap.put("1117010200", "용산동2가");
		dongMap.put("1117012700", "용산동5가");
		dongMap.put("1117011200", "원효로1가");
		dongMap.put("1117011300", "원효로2가");
		dongMap.put("1117011700", "원효로3가");
		dongMap.put("1117011800", "원효로4가");
		dongMap.put("1117012900", "이촌동");
		dongMap.put("1117013000", "이태원동");
		dongMap.put("1117011600", "청암동");
		dongMap.put("1117011100", "청파동3가");
		dongMap.put("1117012400", "한강로1가");
		dongMap.put("1117012500", "한강로2가");
		dongMap.put("1117012800", "한강로3가");
		dongMap.put("1117013100", "한남동");
		dongMap.put("1117011900", "효창동");
		dongMap.put("1117010100", "후암동");
		dongMap.put("1120010900", "금호동1가");
		dongMap.put("1120011000", "금호동2가");
		dongMap.put("1120011100", "금호동3가");
		dongMap.put("1120011200", "금호동4가");
		dongMap.put("1120010400", "도선동");
		dongMap.put("1120010500", "마장동");
		dongMap.put("1120010600", "사근동");
		dongMap.put("1120011400", "성수동1가");
		dongMap.put("1120011500", "성수동2가");
		dongMap.put("1120011800", "송정동");
		dongMap.put("1120011300", "옥수동");
		dongMap.put("1120012200", "용답동");
		dongMap.put("1120010800", "응봉동");
		dongMap.put("1120010200", "하왕십리동");
		dongMap.put("1120010700", "행당동");
		dongMap.put("1120010300", "홍익동");
		dongMap.put("1156011700", "당산동");
		dongMap.put("1156011100", "당산동1가");
		dongMap.put("1156011200", "당산동2가");
		dongMap.put("1156011300", "당산동3가");
		dongMap.put("1156011400", "당산동4가");
		dongMap.put("1156011500", "당산동5가");
		dongMap.put("1156013300", "대림동");
		dongMap.put("1156011800", "도림동");
		dongMap.put("1156012000", "문래동2가");
		dongMap.put("1156012100", "문래동3가");
		dongMap.put("1156012200", "문래동4가");
		dongMap.put("1156012300", "문래동5가");
		dongMap.put("1156012400", "문래동6가");
		dongMap.put("1156013200", "신길동");
		dongMap.put("1156012500", "양평동1가");
		dongMap.put("1156012600", "양평동2가");
		dongMap.put("1156012700", "양평동3가");
		dongMap.put("1156012800", "양평동4가");
		dongMap.put("1156012900", "양평동5가");
		dongMap.put("1156013000", "양평동6가");
		dongMap.put("1156011000", "여의도동");
		dongMap.put("1156010100", "영등포동");
		dongMap.put("1156010200", "영등포동1가");
		dongMap.put("1156010600", "영등포동5가");
		dongMap.put("1156010800", "영등포동7가");
		dongMap.put("1156010900", "영등포동8가");
		dongMap.put("1132010800", "도봉동");
		dongMap.put("1132010600", "방학동");
		dongMap.put("1132010500", "쌍문동");
		dongMap.put("1132010700", "창동");
		dongMap.put("1165010700", "반포동");
		dongMap.put("1165010100", "방배동");
		dongMap.put("1165010800", "서초동");
		dongMap.put("1165010200", "양재동");
		dongMap.put("1165010300", "우면동");
		dongMap.put("1165010600", "잠원동");
		dongMap.put("1129013400", "길음동");
		dongMap.put("1129010300", "돈암동");
		dongMap.put("1129011900", "동선동4가");
		dongMap.put("1129010700", "동소문동4가");
		dongMap.put("1129010800", "동소문동5가");
		dongMap.put("1129010900", "동소문동6가");
		dongMap.put("1129011000", "동소문동7가");
		dongMap.put("1129013200", "보문동3가");
		dongMap.put("1129012800", "보문동6가");
		dongMap.put("1129011200", "삼선동2가");
		dongMap.put("1129011300", "삼선동3가");
		dongMap.put("1129011400", "삼선동4가");
		dongMap.put("1129013700", "상월곡동");
		dongMap.put("1129013900", "석관동");
		dongMap.put("1129010100", "성북동");
		dongMap.put("1129012100", "안암동1가");
		dongMap.put("1129012300", "안암동3가");
		dongMap.put("1129012400", "안암동4가");
		dongMap.put("1129013800", "장위동");
		dongMap.put("1129013300", "정릉동");
		dongMap.put("1129013500", "종암동");
		dongMap.put("1129013600", "하월곡동");
		dongMap.put("1171010700", "가락동");
		dongMap.put("1171011300", "거여동");
		dongMap.put("1171011400", "마천동");
		dongMap.put("1171010800", "문정동");
		dongMap.put("1171011100", "방이동");
		dongMap.put("1171010600", "삼전동");
		dongMap.put("1171010500", "석촌동");
		dongMap.put("1171010400", "송파동");
		dongMap.put("1171010200", "신천동");
		dongMap.put("1171011200", "오금동");
		dongMap.put("1171010100", "잠실동");
		dongMap.put("1171010900", "장지동");
		dongMap.put("1171010300", "풍납동");
		dongMap.put("1154510100", "가산동");
		dongMap.put("1154510200", "독산동");
		dongMap.put("1154510300", "시흥동");
		dongMap.put("1162010300", "남현동");
		dongMap.put("1162010100", "봉천동");
		dongMap.put("1162010200", "신림동");
		dongMap.put("1141012000", "남가좌동");
		dongMap.put("1141010500", "냉천동");
		dongMap.put("1141011200", "대현동");
		dongMap.put("1141010400", "미근동");
		dongMap.put("1141011900", "북가좌동");
		dongMap.put("1141011000", "북아현동");
		dongMap.put("1141011700", "연희동");
		dongMap.put("1141010800", "영천동");
		dongMap.put("1141011600", "창천동");
		dongMap.put("1141010600", "천연동");
		dongMap.put("1141010100", "충정로2가");
		dongMap.put("1141010200", "충정로3가");
		dongMap.put("1141010300", "합동");
		dongMap.put("1141010900", "현저동");
		dongMap.put("1141011800", "홍은동");
		dongMap.put("1141011100", "홍제동");
		dongMap.put("1174011000", "강일동");
		dongMap.put("1174010200", "고덕동");
		dongMap.put("1174010500", "길동");
		dongMap.put("1174010600", "둔촌동");
		dongMap.put("1174010100", "명일동");
		dongMap.put("1174010300", "상일동");
		dongMap.put("1174010800", "성내동");
		dongMap.put("1174010700", "암사동");
		dongMap.put("1174010900", "천호동");
		dongMap.put("1150010400", "가양동");
		dongMap.put("1150010800", "공항동");
		dongMap.put("1150010600", "내발산동");
		dongMap.put("1150010200", "등촌동");
		dongMap.put("1150010500", "마곡동");
		dongMap.put("1150010900", "방화동");
		dongMap.put("1150010100", "염창동");
		dongMap.put("1150010300", "화곡동");
		dongMap.put("1159010100", "노량진동");
		dongMap.put("1159010800", "대방동");
		dongMap.put("1159010600", "동작동");
		dongMap.put("1159010400", "본동");
		dongMap.put("1159010700", "사당동");
		dongMap.put("1159010300", "상도1동");
		dongMap.put("1159010200", "상도동");
		dongMap.put("1159010900", "신대방동");
		dongMap.put("1159010500", "흑석동");
		dongMap.put("1135010300", "공릉동");
		dongMap.put("1135010500", "상계동");
		dongMap.put("1135010200", "월계동");
		dongMap.put("1135010600", "중계동");
		dongMap.put("1135010400", "하계동");
		dongMap.put("1144010200", "공덕동");
		dongMap.put("1144011000", "노고산동");
		dongMap.put("1144010800", "대흥동");
		dongMap.put("1144010400", "도화동");
		dongMap.put("1144012100", "동교동");
		dongMap.put("1144010700", "마포동");
		dongMap.put("1144012300", "망원동");
		dongMap.put("1144011500", "상수동");
		dongMap.put("1144012700", "상암동");
		dongMap.put("1144012000", "서교동");
		dongMap.put("1144012500", "성산동");
		dongMap.put("1144010300", "신공덕동");
		dongMap.put("1144011100", "신수동");
		dongMap.put("1144011700", "신정동");
		dongMap.put("1144010100", "아현동");
		dongMap.put("1144012400", "연남동");
		dongMap.put("1144010900", "염리동");
		dongMap.put("1144010500", "용강동");
		dongMap.put("1144012600", "중동");
		dongMap.put("1144011400", "창전동");
		dongMap.put("1144010600", "토정동");
		dongMap.put("1144011600", "하중동");
		dongMap.put("1144012200", "합정동");
		dongMap.put("1144011200", "현석동");
		dongMap.put("1111012900", "견지동");
		dongMap.put("1111018000", "교북동");
		dongMap.put("1111018200", "구기동");
		dongMap.put("1111013700", "낙원동");
		dongMap.put("1111011800", "내수동");
		dongMap.put("1111011700", "당주동");
		dongMap.put("1111016800", "동숭동");
		dongMap.put("1111017000", "명륜1가");
		dongMap.put("1111017100", "명륜2가");
		dongMap.put("1111018700", "무악동");
		dongMap.put("1111018400", "부암동");
		dongMap.put("1111011500", "사직동");
		dongMap.put("1111017500", "숭인동");
		dongMap.put("1111018600", "신영동");
		dongMap.put("1111016000", "연지동");
		dongMap.put("1111016500", "이화동");
		dongMap.put("1111013300", "익선동");
		dongMap.put("1111015700", "인의동");
		dongMap.put("1111017400", "창신동");
		dongMap.put("1111018300", "평창동");
		dongMap.put("1111011300", "필운동");
		dongMap.put("1138010400", "갈현동");
		dongMap.put("1138010500", "구산동");
		dongMap.put("1138010200", "녹번동");
		dongMap.put("1138010600", "대조동");
		dongMap.put("1138010300", "불광동");
		dongMap.put("1138010100", "수색동");
		dongMap.put("1138010900", "신사동");
		dongMap.put("1138010800", "역촌동");
		dongMap.put("1138010700", "응암동");
		dongMap.put("1138011000", "증산동");
		dongMap.put("1138011400", "진관동");
		dongMap.put("1114011800", "남대문로5가");
		dongMap.put("1114011200", "남창동");
		dongMap.put("1114017300", "만리동1가");
		dongMap.put("1114017400", "만리동2가");
		dongMap.put("1114013600", "묵정동");
		dongMap.put("1114016800", "순화동");
		dongMap.put("1114016200", "신당동");
		dongMap.put("1114015100", "을지로5가");
		dongMap.put("1114013400", "인현동2가");
		dongMap.put("1114014400", "장충동2가");
		dongMap.put("1114017100", "중림동");
		dongMap.put("1114013200", "충무로4가");
		dongMap.put("1114016500", "황학동");
		dongMap.put("1114012100", "회현동1가");
		dongMap.put("1114012200", "회현동2가");
		dongMap.put("1114016300", "흥인동");
		dongMap.put("1126010500", "망우동");
		dongMap.put("1126010100", "면목동");
		dongMap.put("1126010400", "묵동");
		dongMap.put("1126010200", "상봉동");
		dongMap.put("1126010600", "신내동");
		dongMap.put("1126010300", "중화동");

	}

}
