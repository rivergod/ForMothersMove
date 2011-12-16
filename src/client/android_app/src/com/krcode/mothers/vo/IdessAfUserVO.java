package com.krcode.mothers.vo;

public class IdessAfUserVO implements IPointVO{
	private String id;
	private String nm;
	private String ph;
	private String ad;
	private String lc;
	private String lt;
	private String ln;
	private String it;
	private String ut;
	
	public IdessAfUserVO() {
		id = "";
		nm = "";
		ph = "";
		ad = "";
		lc = "";
		lt = "";
		ln = "";
		it = "";
		ut = "";
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNm() {
		return nm;
	}
	public void setNm(String nm) {
		this.nm = nm;
	}
	public String getPh() {
		return ph;
	}
	public void setPh(String ph) {
		this.ph = ph;
	}
	public String getAd() {
		return ad;
	}
	public void setAd(String ad) {
		this.ad = ad;
	}
	public String getLc() {
		return lc;
	}
	public void setLc(String lc) {
		this.lc = lc;
	}
	public String getLt() {
		return lt;
	}
	public void setLt(String lt) {
		this.lt = lt;
	}
	public String getLn() {
		return ln;
	}
	public void setLn(String ln) {
		this.ln = ln;
	}
	public String getIt() {
		return it;
	}
	public void setIt(String it) {
		this.it = it;
	}
	public String getUt() {
		return ut;
	}
	public void setUt(String ut) {
		this.ut = ut;
	}

	@Override
	public int getLatitude1E6() {
		return (int) (Float.parseFloat(this.lt) * 1E6);
	}

	@Override
	public int getLongitude1E6() {
		return (int) (Float.parseFloat(this.ln) * 1E6);
	}
}
