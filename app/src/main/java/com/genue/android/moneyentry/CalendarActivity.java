package com.genue.android.moneyentry;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity
{
	GridView calendarView;
	MonthCalendarAdapter monthCalendarAdapter;
	YearCalendarAdapter yearCalendarAdapter;

	int year;
	int month;
	int today;

	//광고
	private AdView mAdView;

	private TextView tvMonth;

	private int calenderType = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar);
		initAd();

		calendarView = findViewById(R.id.calendarView);

		Intent fromMain = getIntent();
		int type = fromMain.getIntExtra("calendar", 0);
		if(type == Define.SHOW_MONTH){
			//월별 달력 보여줘
			calenderType = 1;
		}else{
			//일별 달력 보여줘
			calenderType = 0;
		}
		initCalendar();
	}

	private void initCalendar(){
		tvMonth = findViewById(R.id.tvMonth);
		Calendar calendar = Calendar.getInstance();
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH) + 1;
		today = calendar.get(Calendar.DAY_OF_MONTH);
		tvMonth.setText(year + "." + month + "." + today);
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
		if(calenderType == 1){
			setYearCalendarView();
		}else {
			setMonthCalendarView();
		}
	}

	public void setMonthCalendarView(){
		Log.d(">>>>", " onWindowFocusChanged 그리드 뷰 높이 " + calendarView.getHeight());
		Log.d(">>>>", " onWindowFocusChanged 그리드 뷰 가로 스페이스 " + calendarView.getVerticalSpacing());
		monthCalendarAdapter = new MonthCalendarAdapter(this, calendarView.getHeight(), calendarView.getVerticalSpacing(), year, month - 1, today);
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
		Log.d(">>>>", " onWindowFocusChanged 그리드 뷰 높이 " + calendarView.getHeight());
		Log.d(">>>>", " onWindowFocusChanged 그리드 뷰 가로 스페이스 " + calendarView.getVerticalSpacing());
		yearCalendarAdapter = new YearCalendarAdapter(this, calendarView.getHeight(), calendarView.getVerticalSpacing(), year, month - 1);
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
