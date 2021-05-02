package com.genue.android.moneyentry;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import java.util.Calendar;

public class MonthCalendarAdapter extends BaseAdapter
{

	Context mContext;
	LayoutInflater inflater;


	Calendar calendar;
	DayData[] items;
	int year;
	int month;
	int today;
	int day = 0;

	private final int MAX_ROW = 6;//행
	private final int MAX_COLUMN = 7;//열
	double row_height = 0;
	int vertiSpace = 0;

	int presentMonthStart = 0;
	int presentMonthEnd = 0;

	int redGray = 0;
	int blackGray = 0;

	public MonthCalendarAdapter(Context context, int parentHeight, int vertiSpace, int year, int month, int today)
	{
		redGray = ContextCompat.getColor(context, R.color.redGray);
		blackGray = ContextCompat.getColor(context, R.color.blackGray);

		mContext = context;
		inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Log.d(">>>>", "CalendarAdapter 그리드 뷰 높이 " + parentHeight);
		this.vertiSpace = vertiSpace * (MAX_ROW - 1);//세로 띄어쓰기 합
		row_height = (parentHeight - this.vertiSpace)/MAX_ROW;
		this.year = year;
		this.month = month;
		this.today = today;
		init(year, month, today);
	}

	//달력 칸 초기화
	public void init(int year, int month, int today)
	{
		calendar = Calendar.getInstance();
		calendar.set(year, month, today);
//		day = calendar.get(Calendar.DAY_OF_MONTH);
//		year = calendar.get(Calendar.YEAR);
//		month = calendar.get(Calendar.MONTH);
		Log.d(">>>", "현재 날짜" + year + " " + month + " " + today);
		items = new DayData[MAX_COLUMN*MAX_ROW];

		setMonth(year, month);
	}

	//보여줄 셀의 내용 정리
	private void setMonth(int year, int month)
	{
		calendar.set(Calendar.DAY_OF_MONTH, 1);//1일 셋팅
		int prevMonthEnd = getPrevMonth(year, month);
		presentMonthStart = calendar.get(Calendar.DAY_OF_WEEK) - 1;// 0 - 일요일 ~ 6 - 토요일
		presentMonthEnd = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

		Log.d(">>>", prevMonthEnd + " " + presentMonthStart + " " + presentMonthEnd );

		int j = 1;
		for(int i = 0 ; i < items.length ; i++) {
			if(i < presentMonthStart){
				items[i] = new DayData(prevMonthEnd - (presentMonthStart - 1) + i);
			}
			else if(presentMonthStart <= i && i <= presentMonthEnd + presentMonthStart - 1){
				items[i] = new DayData(i - presentMonthStart + 1);
			} else {
				items[i] = new DayData(j++);
			}
		}
	}

	//이전달 마지막 일
	private int getPrevMonth(int year, int month){
		Calendar c = (Calendar) calendar.clone();
		c.set(year, (month - 1), 1);

		return c.getActualMaximum(Calendar.DAY_OF_MONTH);
	}



	@Override
	public int getCount()
	{
		return items.length;
	}

	@Override
	public Object getItem(int position)
	{
		return items[position];
	}

	@Override
	public long getItemId(int position)
	{
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		if(convertView == null) {
			convertView = inflater.inflate(R.layout.calendar_month_item, null);
//			convertView.setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,(int) row_height));
			convertView.setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
		}
		TextView tvDay = convertView.findViewById(R.id.tvDay);
		TextView tvSpend = convertView.findViewById(R.id.tvSpend);
		TextView tvSave = convertView.findViewById(R.id.tvSave);
		TextView tvEarn = convertView.findViewById(R.id.tvEarn);
		tvDay.setText(items[position].getDay());
		//주말 구분
		if(position % 7 == 0 || position % 7 == 6){
			if(position < presentMonthStart || position >= presentMonthStart + presentMonthEnd) {
				tvDay.setTextColor(redGray);
			} else {
				tvDay.setTextColor(Color.RED);
			}
		} else{
			if(position < presentMonthStart || position > presentMonthStart + presentMonthEnd){
				tvDay.setTextColor(blackGray);
			} else {
				tvDay.setTextColor(Color.BLACK);
			}
		}
		//오늘 구분
		if(items[position].getDay().equals(today + "")){
			tvDay.setTextColor(Color.MAGENTA);
		}
		tvSpend.setText(items[position].getSpend());
		tvSave.setText(items[position].getSave());
		tvEarn.setText(items[position].getEarn());


		return convertView;
	}
}
