package com.krcode.mothers.vo;

import java.io.Serializable;

public class AccountVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5389297416021954020L;
	
	private String userId;
	private String passwd;
	private float favor0;
	private float favor1;
	private float favor2;
	private float favor3;

	private boolean avalidable;

	public AccountVO() {
		this.userId = "";
		this.passwd = "";
		this.favor0 = 0;
		this.favor1 = 0;
		this.favor2 = 0;
		this.favor3 = 0;
		this.avalidable = false;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public float getFavor0() {
		return favor0;
	}

	public void setFavor0(float favor0) {
		this.favor0 = favor0;
	}

	public float getFavor1() {
		return favor1;
	}

	public void setFavor1(float favor1) {
		this.favor1 = favor1;
	}

	public float getFavor2() {
		return favor2;
	}

	public void setFavor2(float favor2) {
		this.favor2 = favor2;
	}

	public float getFavor3() {
		return favor3;
	}

	public void setFavor3(float favor3) {
		this.favor3 = favor3;
	}

	public boolean isAvalidable() {
		return avalidable;
	}

	public void setAvalidable(boolean avalidable) {
		this.avalidable = avalidable;
	}

}
