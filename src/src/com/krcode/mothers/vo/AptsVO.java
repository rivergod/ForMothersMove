package com.krcode.mothers.vo;

public class AptsVO implements IPointVO {
	private String aptName;
	private String address;
	private String lat;
	private String lng;

	public AptsVO() {
		this.aptName = "";
		this.address = "";
		this.lat = "";
		this.lng = "";
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

	@Override
	public int getLatitude1E6() {
		return (int) (Float.parseFloat(this.lat));
	}

	@Override
	public int getLongitude1E6() {
		return (int) (Float.parseFloat(this.lng));
	}

}
