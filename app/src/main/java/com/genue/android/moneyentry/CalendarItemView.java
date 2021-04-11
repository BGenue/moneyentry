package com.genue.android.moneyentry;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class CalendarItemView extends AppCompatTextView
{
	private DayData item;
	public CalendarItemView(Context context){
		super(context);
		init();
	}

	public CalendarItemView(Context context, AttributeSet attrs){
		super(context, attrs);
		init();
	}

	private void init(){

	}

	public DayData getItem(){
		return item;
	}

	public void setItem(DayData item){
		this.item = item;
	}
}
