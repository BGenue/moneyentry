package com.genue.android.moneyentry;

public class DayData
{
	private int day = 0;//일
	private int spend = 0;//지출
	private int earn = 0;//수입
	private int save = 0;//저축

	DayData(int day){
		this.day = day;
	}

	public void setDay(int day){
		this.day = day;
	}

	public void setSpend(int spend){
		this.spend = spend;
	}

	public void setEarn(int earn){
		this.earn = earn;
	}

	public void setSave(int save){
		this.save = save;
	}

	public String getDay(){
		return day + "";
	}

	public String getSpend(){
		return spend + "";
	}

	public String getEarn(){
		return earn + "";
	}

	public String getSave(){
		return save + "";
	}

	public String toString(){
		return day + " " + spend + " " + earn + " " + save;
	}
}
