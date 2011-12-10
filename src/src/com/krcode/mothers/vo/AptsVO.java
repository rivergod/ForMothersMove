package com.krcode.mothers.vo;

public class AptsVO implements IPointVO {
	private String aptName;
	private String address;
	private String lat;
	private String lng;
	private String dongCode;
	private String danjiCode;

	public AptsVO() {
		this.aptName = "";
		this.address = "";
		this.lat = "";
		this.lng = "";
		this.dongCode = "";
		this.danjiCode = "";
	}

	public String getAptName() {
		return aptName;
	}

	public void setAptName(String aptName) {
		this.aptName = aptName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getDongCode() {
		return dongCode;
	}

	public void setDongCode(String dongCode) {
		this.dongCode = dongCode;
	}

	public String getDanjiCode() {
		return danjiCode;
	}

	public void setDanjiCode(String danjiCode) {
		this.danjiCode = danjiCode;
	}

	@Override
	public int getLatitude1E6() {
		return (int) (Float.parseFloat(this.lat));
	}

	@Override
	public int getLongitude1E6() {
		return (int) (Float.parseFloat(this.lng));
	}

}
