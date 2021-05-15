package com.genue.android.moneyentry;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class UserInfoActivity extends AppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userinfo);
	}

	protected void onClick(View v){
		if(v.getId() == R.id.tvConfirm){

		} else if(v.getId() == R.id.tvCancel){

		}
	}
}
