package com.krcode.mothers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

public class InitActivity extends Activity implements Runnable {

	public static Activity ctx;
	Thread thread = null;

	private ProgressDialog progressDialog;

	int mJobId = 0;

	private SharedPreferences preferences;
	private static String strVersion;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.init);

		ctx = this;

//		Button goToMainActivityBtn = (Button) findViewById(R.id.goToMainActivityBtn);
//
//		goToMainActivityBtn.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent(InitActivity.this,
//						MainActivity.class);
//				startActivity(intent);
//			}
//		});

		preferences = getPreferences(MODE_WORLD_WRITEABLE);
		strVersion = preferences.getString("VERSION", "");

		// SharedPreferences에 저장된 버전과 app 버전이 다를 때 DB파일 외장메모리로 복사
		if (getString(R.string.appversion).compareTo(strVersion) != 0) {
			// 외장메모리가 사용가능할 때
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				Update();
			} else {
				// showDialog(IConstant.DIALOG_NOSDCARD);
			}
		}
		else {
			Intent intent = new Intent(InitActivity.this, MainActivity.class);
			startActivity(intent);
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			finish();
		}


	}

	public void doCopy() {
		File outDir = null;
		File outfile = null;

		// 외장메모리가 사용가능 상태인지 확인
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			outDir = new File(IConstant.SD_PATH + IConstant.DB_PATH);

			outDir.mkdirs(); // 디렉토리 생성
			outfile = new File(outDir, "mothers.db");

			InputStream is = null;
			OutputStream os = null;
			int size;

			try {
				is = getAssets().open("mothers.db");
				size = is.available();

				outfile.createNewFile(); // 파일 생성
				os = new FileOutputStream(outfile);

				byte[] buffer = new byte[size];

				is.read(buffer);
				os.write(buffer);

				is.close();
				os.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					is.close();
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		switch (mJobId) {
		case IConstant.JOB_DBCOPY:
			doCopy();
			handler.sendEmptyMessage(IConstant.JOB_DBCOPY);
			Intent intent = new Intent(InitActivity.this, MainActivity.class);
			startActivity(intent);
			
			finish();
			break;
		}
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (mJobId) {
			case IConstant.JOB_DBCOPY:
				if (progressDialog.isShowing()) {
					progressDialog.dismiss();
				}

				savePreferences();
				break;
			}
		}
	};

	public void Update() {
		progressDialog = ProgressDialog.show(ctx, "Loading",
				getString(R.string.loading), true, false);

		thread = new Thread(this);

		mJobId = IConstant.JOB_DBCOPY;

		try {
			thread.start();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void savePreferences() {
		preferences.edit().putString("VERSION", getString(R.string.appversion))
				.commit();
	}
}
