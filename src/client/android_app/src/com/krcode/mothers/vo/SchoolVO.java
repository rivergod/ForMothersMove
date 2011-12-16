package com.krcode.mothers.vo;

public class SchoolVO implements IPointVO {
	private String name;
	private String homepage;
	private String address;
	private String telephone;
	private String lat;
	private String lng;

	public SchoolVO() {
		this.name = "";
		this.homepage = "";
		this.address = "";
		this.telephone = "";
		this.lat = "";
		this.lng = "";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
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
		return (int)(Float.parseFloat(this.lat));
	}

	@Override
	public int getLongitude1E6() {
		return (int)(Float.parseFloat(this.lng));
	}

}
