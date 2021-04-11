package com.genue.android.moneyentry;

import android.content.Context;
import android.util.Log;

public class UserInfo
{
	public String id;

	private static UserInfo USER;

	public static synchronized UserInfo createUser()
	{
		Log.i(">>>>", "UserInfo createUser");
		if(USER == null) {
			USER = new UserInfo();
		}
		return USER;
	}

	public UserInfo()
	{

	}

	public void setID(String id){
		this.id = id;
	}
}
