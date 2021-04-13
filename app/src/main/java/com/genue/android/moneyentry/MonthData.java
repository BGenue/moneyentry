package com.genue.android.moneyentry;

public class MonthData
{
	private int month = 0;//월
	private int spend = 0;//지출
	private int earn = 0;//수입
	private int save = 0;//저축

	MonthData(int month){
		this.month = month;
	}

	public void setMonth(int month){
		this.month = month;
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

	public String getMonth(){
		return month + "";
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
		return month + " " + spend + " " + earn + " " + save;
	}
}
