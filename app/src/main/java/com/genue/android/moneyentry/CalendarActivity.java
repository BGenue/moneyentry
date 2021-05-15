package com.genue.android.moneyentry;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;

import java.util.Calendar;

public class CalendarActivity extends BaseActivity
{
	GridView calendarView;
	MonthCalendarAdapter monthCalendarAdapter;
	YearCalendarAdapter yearCalendarAdapter;

	int year;
	int month;
	int today;

	Calendar calendar;
	int today_year;
	int today_month;
	int today_day;

	//광고
	private AdView mAdView;

	private TextView tvNum;

	private int calenderType = 0;
	int prevClickedPos = -1;
	int selectedPos = -1;

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
		if(year != 0)
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
		//pause 여부 확인 =>
		calendar = Calendar.getInstance();
		if(year == 0) {
//			today_year = calendar.get(Calendar.YEAR);
			year = calendar.get(Calendar.YEAR);
//			today_month = calendar.get(Calendar.MONTH) + 1;
			month = calendar.get(Calendar.MONTH) + 1;
//			today_day = calendar.get(Calendar.DAY_OF_MONTH);
			today = calendar.get(Calendar.DAY_OF_MONTH);
			tvNum.setText(year + "." + (month + 1));
			calenderType = 0; //shared 뭐시기로 해야할듯??
			showCalendar();
		}
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		//오늘 날짜, 캘린더 타입, pause 여부 저장
	}

	@Override public void onWindowFocusChanged(boolean hasFocus) {
		Log.d(">>>", "onwindowfocuschanged");
		super.onWindowFocusChanged(hasFocus);
		if(hasFocus)
			showCalendar();
	}

	private void showCalendar(){
		//ui값 확인
		tvNum.setText(year + "." + (month + 1));
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

//		calendarView.setOnItemClickListener(new AdapterView.OnItemClickListener()
//		{
//			@Override
//			public void onItemClick(AdapterView<?> parent, View clickedView, int position, long id)
//			{
//				Toast.makeText(CalendarActivity.this, "눌림 " + position, Toast.LENGTH_SHORT).show();
//				if(prevClickedPos == -1)
//				{
//					//처음 눌림
//					clickedView.setBackground(getDrawable(R.drawable.calendar_item_focused));
//					prevClickedPos = position;
//				}
//				else {
//					if(position != prevClickedPos) {
//						//이전이랑 다른 item 눌림
//						View prevClickedView = calendarView.getChildAt(prevClickedPos);//이전에 눌린 녀석
//						prevClickedView.setBackground(getDrawable(R.drawable.calendar_item_unclicked));
//						clickedView.setBackground(getDrawable(R.drawable.calendar_item_focused));
//						prevClickedPos = position;
//					}
//					else {
//						//이전이랑 같은 item 눌림
//						clickedView.setBackground(getDrawable(R.drawable.calendar_item_unclicked));
//						prevClickedPos = -1;
//					}
//				}
//			}
//		});
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

	public void onClick(View v){
		switch(v.getId()){
			case R.id.prevMonth:
				month -= 1;
				showCalendar();
				break;

			case R.id.nextMonth:
				month += 1;
				showCalendar();
				break;
		}
	}
}
