package com.krcode.mothers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.krcode.mothers.helpers.AccountHelper;
import com.krcode.mothers.vo.AccountVO;

public class CreateAccountActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.createaccount);

		final EditText userIdEdTxt = (EditText) findViewById(R.id.useridedtxt);
		final EditText passwdEdTxt = (EditText) findViewById(R.id.passwdedtxt);
		final EditText passwd2EdTxt = (EditText) findViewById(R.id.passwd2edtxt);
		final RatingBar favor0Rbar = (RatingBar) findViewById(R.id.ratingFavor0);
		final RatingBar favor1Rbar = (RatingBar) findViewById(R.id.ratingFavor1);
		final RatingBar favor2Rbar = (RatingBar) findViewById(R.id.ratingFavor2);
		final RatingBar favor3Rbar = (RatingBar) findViewById(R.id.ratingFavor3);
		
//		EditText emailEdTxt = (EditText) findViewById(R.id.emailedtxt);

		Button createAccountBtn = (Button) findViewById(R.id.createaccountbtn);
		createAccountBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ProgressDialog progressDialog = ProgressDialog.show(CreateAccountActivity.this, "", "Create Accounting...", true);
				
				AccountVO vo = new AccountVO();

				vo.setUserId(userIdEdTxt.getText().toString());
				vo.setPasswd(passwdEdTxt.getText().toString());

				if (!vo.getPasswd().equals(passwd2EdTxt.getText().toString())) {
					progressDialog.dismiss();
					Toast.makeText(getApplicationContext(), "비밀번호를 확인해 주세요.",
							Toast.LENGTH_SHORT).show();
					return;
				}
				
				vo.setFavor0(favor0Rbar.getRating());
				vo.setFavor1(favor1Rbar.getRating());
				vo.setFavor2(favor2Rbar.getRating());
				vo.setFavor3(favor3Rbar.getRating());

				AccountHelper hlp = new AccountHelper();
				AccountVO retVo = hlp.createAccount(vo);
				
				if (retVo.isAvalidable()) {
					Toast.makeText(getApplicationContext(), "가입되었습니다.",
							Toast.LENGTH_SHORT).show();
					
//					Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
//					intent.putExtra("ACCOUNTVO", retVo);
					
					MainActivity.accountVO = retVo;
					
					CreateAccountActivity.this.finish();
				}
				else {
					Toast.makeText(getApplicationContext(), "가입정보를 확인해 주세요.",
							Toast.LENGTH_SHORT).show();
				}

				progressDialog.dismiss();
			}
		});

		Button cancelBtn = (Button) findViewById(R.id.cancelbtn);
		cancelBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(CreateAccountActivity.this,
						MainActivity.class);
				startActivity(intent);
				
				CreateAccountActivity.this.finish();
			}
		});
	}
}
