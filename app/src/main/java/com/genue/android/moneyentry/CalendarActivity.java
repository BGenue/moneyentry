package com.genue.android.moneyentry;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarActivity extends BaseActivity
{
	GridView calendarView;
	MonthCalendarAdapter monthCalendarAdapter;
	YearCalendarAdapter yearCalendarAdapter;

	int year;
	int month;
	int today;

	//광고
	private AdView mAdView;

	private TextView tvNum;

	private int calenderType = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		Log.d(">>>", "oncreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar);

		mAdView = findViewById(R.id.adView);
		initAd(mAdView);

		calendarView = findViewById(R.id.calendarView);

		Intent fromMain = getIntent();
		calenderType = fromMain.getIntExtra("calendar", 0);
		year = fromMain.getIntExtra("year", 0);
		month = fromMain.getIntExtra("month", 0) - 1;
		today = fromMain.getIntExtra("day", 0);
		Log.d(">>>>", "캘린더 타입 " + calenderType);

		tvNum = findViewById(R.id.tvNum);
		tvNum.setText(year + "." + (month + 1) + "." + today);
	}

	//원하는 날짜로 캘린더 셋팅
	private void initCalendar(int year, int month, int day){
	}

//	private void initAd()
//	{
//		MobileAds.initialize(this, new OnInitializationCompleteListener()
//		{
//			@Override
//			public void onInitializationComplete(InitializationStatus initializationStatus)
//			{
//			}
//		});
//
//		mAdView.setAdListener(new AdListener(){
//			@Override
//			public void onAdLoaded(){
//				Log.d(">>>", "광고 로드 ");
//			}
//		});
//		AdRequest adRequest = new AdRequest.Builder().build();
//		mAdView.loadAd(adRequest);
//	}

	@Override
	protected void onResume()
	{
		super.onResume();
		Log.d(">>>>", "onResume 그리드 뷰 높이 " + calendarView.getHeight());
	}

	@Override public void onWindowFocusChanged(boolean hasFocus) {
		Log.d(">>>", "onwindowfocuschanged");
		super.onWindowFocusChanged(hasFocus);

		//ui값 확인
		if(calenderType == Define.SHOW_MONTH || calenderType == Define.SHOW_YEAR){
			setYearCalendarView();
		}else {
			setMonthCalendarView();
		}
	}

	public void setMonthCalendarView(){
		Log.d(">>>>", " onWindowFocusChanged 그리드 뷰 높이 " + calendarView.getHeight());
		Log.d(">>>>", " onWindowFocusChanged 그리드 뷰 가로 스페이스 " + calendarView.getVerticalSpacing());
		monthCalendarAdapter = new MonthCalendarAdapter(this, calendarView.getHeight(), calendarView.getVerticalSpacing(), year, month, today);
		calendarView.setAdapter(monthCalendarAdapter);
		calendarView.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				Toast.makeText(CalendarActivity.this, "눌림 " + position, Toast.LENGTH_SHORT).show();
			}
		});
	}

	public void setYearCalendarView(){
		calendarView.setNumColumns(2);
		Log.d(">>>>", " onWindowFocusChanged 그리드 뷰 높이 " + calendarView.getHeight());
		Log.d(">>>>", " onWindowFocusChanged 그리드 뷰 가로 스페이스 " + calendarView.getVerticalSpacing());
		yearCalendarAdapter = new YearCalendarAdapter(this, calendarView.getHeight(), calendarView.getVerticalSpacing(), year, month);
		calendarView.setAdapter(yearCalendarAdapter);
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
