package com.genue.android.moneyentry;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Calendar;

public class CalendarAdapter extends BaseAdapter
{

	Context mContext;
	LayoutInflater inflater;


	Calendar calendar;
	DayData[] items;
	int year;
	int month;

	int todayDate = 0;

	private final int MAX_ROW = 6;//행
	private final int MAX_COLUMN = 7;//열
	double row_height = 0;
	int vertiSpace = 0;


	public CalendarAdapter(Context context, int parentHeight, int vertiSpace)
	{
		mContext = context;
		inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Log.d(">>>>", "CalendarAdapter 그리드 뷰 높이 " + parentHeight);
		this.vertiSpace = vertiSpace * (MAX_ROW - 1);//세로 띄어쓰기 합
		row_height = (parentHeight - this.vertiSpace)/MAX_ROW;
		init();
	}

	public void init()
	{
		calendar = Calendar.getInstance();
		todayDate = calendar.get(Calendar.DAY_OF_MONTH);
		items = new DayData[MAX_COLUMN*MAX_ROW];

		setMonth();
	}

	//보여줄 달력 값 세팅
	private void setMonth()
	{
		for(int i = 0 ; i < items.length ; i++) {
			items[i] = new DayData(0);
		}
		calendar.set(Calendar.DAY_OF_MONTH, 1);//1일 셋팅

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
			convertView = inflater.inflate(R.layout.calendar_day, null);
			convertView.setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,(int) row_height));
//			convertView.setBackgroundColor(mContext.getResources().getColor(R.color.c1));
		}
		TextView tvDay = convertView.findViewById(R.id.tvDay);
		TextView tvSpend = convertView.findViewById(R.id.tvSpend);
		TextView tvSave = convertView.findViewById(R.id.tvSave);
		TextView tvEarn = convertView.findViewById(R.id.tvEarn);
		tvDay.setText(items[position].getDay());
		tvSpend.setText(items[position].getSpend());
		tvSave.setText(items[position].getSave());
		tvEarn.setText(items[position].getEarn());
//		tvDay.setHeight((int) row_height);


		return convertView;
	}
}
