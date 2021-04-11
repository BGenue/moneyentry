package com.genue.android.moneyentry;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class OnSwipeTextViewTouchListener implements View.OnTouchListener
{
	private long touchStart = 0;
	private long touchEnd = 0;
	boolean isSwipe = false;

	@Override
	public boolean onTouch(View v, MotionEvent event)
	{
		switch(event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				touchStart = System.currentTimeMillis();
				break;
			case MotionEvent.ACTION_UP:
				touchEnd = System.currentTimeMillis();
				long elapsedTime = touchEnd - touchStart;
				Log.d(">>>", "OnSwipeTextViewTouchListener 경과 시간 " + elapsedTime);
				break;
			case MotionEvent.ACTION_POINTER_DOWN:
				Log.d(">>>", "OnSwipeTextViewTouchListener 멀티터치");
				break;
		}
		return false;
	}
}
