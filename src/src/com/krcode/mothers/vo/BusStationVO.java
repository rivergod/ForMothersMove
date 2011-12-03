package com.krcode.mothers.vo;

public class BusStationVO implements IPointVO {
	private String stationId;
	private String stationNameKor;
	private String lat;
	private String lng;

	public BusStationVO() {
		this.stationId = "";
		this.stationNameKor = "";
		this.lat = "";
		this.lng = "";
	}

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getStationNameKor() {
		return stationNameKor;
	}

	public void setStationNameKor(String stationNameKor) {
		this.stationNameKor = stationNameKor;
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
