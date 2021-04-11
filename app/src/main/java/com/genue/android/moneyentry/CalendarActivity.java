package com.genue.android.moneyentry;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class CalendarActivity extends AppCompatActivity
{
	GridView calendarView;
	CalendarAdapter calendarAdapter;

	//광고
	private AdView mAdView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar);
		initAd();

		TextView test = findViewById(R.id.test);
		Intent fromMain = getIntent();
		test.setText(fromMain.getIntExtra("calendar", -1) + " 내역");

		calendarView = findViewById(R.id.calendarView);
	}

	private void initAd()
	{
		MobileAds.initialize(this, new OnInitializationCompleteListener()
		{
			@Override
			public void onInitializationComplete(InitializationStatus initializationStatus)
			{
			}
		});

		mAdView = findViewById(R.id.adView);
		mAdView.setAdListener(new AdListener(){
			@Override
			public void onAdLoaded(){
				Log.d(">>>", "광고 로드 ");
			}
		});
		AdRequest adRequest = new AdRequest.Builder().build();
		mAdView.loadAd(adRequest);
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		Log.d(">>>>", "onResume 그리드 뷰 높이 " + calendarView.getHeight());
	}

	@Override public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		//ui 값 알기위해
		setCalendarView();
	}

	public void setCalendarView(){
		Log.d(">>>>", " onWindowFocusChanged 그리드 뷰 높이 " + calendarView.getHeight());
		Log.d(">>>>", " onWindowFocusChanged 그리드 뷰 가로 스페이스 " + calendarView.getVerticalSpacing());
		calendarAdapter = new CalendarAdapter(this, calendarView.getHeight(), calendarView.getVerticalSpacing());
		calendarView.setAdapter(calendarAdapter);
		calendarView.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				Toast.makeText(CalendarActivity.this, "눌림 " + position, Toast.LENGTH_SHORT).show();
			}
		});
	}
}
