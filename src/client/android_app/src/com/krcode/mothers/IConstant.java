package com.krcode.mothers;

import android.os.Environment;

public interface IConstant {
	final boolean DEBUG_MODE = true;

	/*
	 * 
	 */

	final int DIALOG_LINE_LIST = 0;
	final int DIALOG_DELETE_ALL = 1;
	final int DIALOG_EXIT = 2;
	final int DELETE_ID = 3;
	final int DIALOG_NOSDCARD = 4;

	final int JOB_DBCOPY = 10;
	final int JOB_EXIT_WAIT = 11;

	final int LIST_LINE = 20;
	final int LIST_FAVORITE = 21;

	final int NETWORK_ERR = 31;
	final int PAGE_ERR = 32;
	final int REALTIME = 33;
	final int NO_REALTIME = 34;

	// 외장메모리의 절대경로
	final String SD_PATH = Environment.getExternalStorageDirectory()
			.getAbsolutePath();
	final String DB_PATH = "/Android/data/com.krcode.mothers/databases/";
}
