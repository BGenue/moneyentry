package com.genue.android.moneyentry;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class InsertActivity extends AppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_insert);
		TextView test = findViewById(R.id.test);
		Intent fromMain = getIntent();
		test.setText(fromMain.getIntExtra("type", -1) + " 추가");
	}
}
