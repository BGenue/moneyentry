package com.genue.android.moneyentry;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;

public class UserInfo
{
	private String nickname;
	private String password;
	private HashMap monthlyEarn = null;
	private HashMap monthlySave = null;
	private HashMap monthlySpend = null;

	private static UserInfo USER;

	public static synchronized UserInfo createUser()
	{
		Log.i(">>>>", "UserInfo createUser");
		if(USER == null) {
			USER = new UserInfo();
		}
		return USER;
	}

	private UserInfo()
	{
		nickname = "";
		password = "";
	}

	public void setNickname(String nickname){
		this.nickname = nickname;
	}

	public String getNickname(){
		return nickname;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getPassword()
	{
		return password;
	}

	public void setMonthlyEarn(int key, int value)
	{
		this.monthlyEarn.put(key, value);
	}

	public HashMap getMonthlyEarn()
	{
		return monthlyEarn;
	}

	public void setMonthlySave(int key, int value)
	{
		this.monthlySave.put(key, value);
	}

	public HashMap getMonthlySave()
	{
		return monthlySave;
	}

	public void setMonthlySpend(int key, int value)
	{
		this.monthlySpend.put(key, value);
	}

	public HashMap getMonthlySpend()
	{
		return monthlySpend;
	}
}
