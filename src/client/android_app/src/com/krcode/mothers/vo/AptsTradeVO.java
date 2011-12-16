package com.krcode.mothers.vo;

public class AptsTradeVO {
	private AptsVO aptsVo;
	private int type;
	private String area;
	private String floor;
	private String year;
	private String month;
	private String day;
	private String price;
	private String deposit;
	private String monthlyFee;
	
	public AptsTradeVO() {
		this.aptsVo = new AptsVO();
		this.type = 0;
		this.area = "0";
		this.floor = "0";
		this.year = "1990";
		this.month = "1";
		this.day = "1";
		this.price = "0";
		this.deposit = "0";
		this.monthlyFee = "0";
	}

	public AptsVO getAptsVo() {
		return aptsVo;
	}

	public void setAptsVo(AptsVO aptsVo) {
		this.aptsVo = aptsVo;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getDeposit() {
		return deposit;
	}

	public void setDeposit(String deposit) {
		this.deposit = deposit;
	}

	public String getMonthlyFee() {
		return monthlyFee;
	}

	public void setMonthlyFee(String monthlyFee) {
		this.monthlyFee = monthlyFee;
	}

}
