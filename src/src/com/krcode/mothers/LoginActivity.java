package com.krcode.mothers;

import com.krcode.mothers.helpers.AccountHelper;
import com.krcode.mothers.vo.AccountVO;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		final EditText userIdEdTxt = (EditText) findViewById(R.id.loginuseridedtxt);
		final EditText passwdEdTxt = (EditText) findViewById(R.id.loginpasswdedtxt);
		
		Button loginBtn = (Button) findViewById(R.id.loginloginbtn);
		loginBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ProgressDialog progressDialog = ProgressDialog.show(LoginActivity.this, "", "Create Accounting...", true);
				
				AccountVO vo = new AccountVO();

				vo.setUserId(userIdEdTxt.getText().toString());
				vo.setPasswd(passwdEdTxt.getText().toString());
				AccountHelper hlp = new AccountHelper();
				AccountVO retVo = hlp.login(vo);
				
				if (retVo.isAvalidable()) {
					Toast.makeText(getApplicationContext(), "로그인되었습니다.",
							Toast.LENGTH_SHORT).show();
					
					MainActivity.accountVO = retVo;
					
					LoginActivity.this.finish();
				}
				else {
					Toast.makeText(getApplicationContext(), "로그인정보를 확인해 주세요.",
							Toast.LENGTH_SHORT).show();
				}

				progressDialog.dismiss();
			}
		});
		
		Button createAccountBtn = (Button) findViewById(R.id.logincreateaccountbtn);
		createAccountBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(LoginActivity.this, CreateAccountActivity.class));
				
				LoginActivity.this.finish();
			}
		});
	}
}
