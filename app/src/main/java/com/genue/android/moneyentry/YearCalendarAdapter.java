package com.genue.android.moneyentry;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Calendar;

public class YearCalendarAdapter extends BaseAdapter
{
	Context mContext;
	LayoutInflater inflater;

	Calendar calendar;
	MonthData[] items;
	int year;
	int month;
	int day = 0;

	private final int MAX_ROW = 6;//행
	private final int MAX_COLUMN = 2;//열
	double row_height = 0;
	int vertiSpace = 0;

	public YearCalendarAdapter(Context context, int parentHeight, int vertiSpace, int year, int month)
	{
		mContext = context;
		inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Log.d(">>>>", "CalendarAdapter 그리드 뷰 높이 " + parentHeight);
		this.vertiSpace = vertiSpace * (MAX_ROW - 1);//세로 띄어쓰기 합
		row_height = (parentHeight - this.vertiSpace)/MAX_ROW;
		this.year = year;
		this.month = month;
		init(year, month);
	}

	//달력 칸 초기화
	public void init(int year, int month)
	{
		calendar = Calendar.getInstance();
		calendar.set(year, month, 1);
		//		day = calendar.get(Calendar.DAY_OF_MONTH);
		//		year = calendar.get(Calendar.YEAR);
		//		month = calendar.get(Calendar.MONTH);
		Log.d(">>>", "현재 날짜" + year + " " + month);
		items = new MonthData[MAX_COLUMN*MAX_ROW];
		for(int i = 0 ; i < items.length ; i++){
			items[i] = new MonthData(i+1);
		}
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
			convertView = inflater.inflate(R.layout.calendar_year_item, null);
//			convertView.setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,(int) row_height));
			convertView.setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
		}
		TextView tvMonth = convertView.findViewById(R.id.tvMonth);
		TextView tvSpend = convertView.findViewById(R.id.tvSpend);
		TextView tvSave = convertView.findViewById(R.id.tvSave);
		TextView tvEarn = convertView.findViewById(R.id.tvEarn);
		tvMonth.setText(items[position].getMonth()+"월");
		//이번달 구분 해야해
		if(items[position].getMonth().equals(month + "")){
			tvMonth.setTextColor(Color.MAGENTA);
		}else{
			tvMonth.setTextColor(Color.BLACK);
		}
		tvSpend.setText(items[position].getSpend());
		tvSave.setText(items[position].getSave());
		tvEarn.setText(items[position].getEarn());


		return convertView;
	}
}
