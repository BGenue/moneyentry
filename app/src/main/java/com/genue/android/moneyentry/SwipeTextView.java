package com.genue.android.moneyentry;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;

import java.util.Timer;

public class SwipeTextView extends AppCompatTextView implements View.OnTouchListener
{
	private long  touchStart = 0;
	private long  touchEnd = 0;
	private float startX = 0;
	private float endX = 0;

	private int isSwipe = Define.SWIPE_NONE;
	private boolean isRightSwipe = false;
	private float swipeV = 0;
	private boolean isViewPressed = true;

	public SwipeTextView(Context context)
	{
		super(context);
		init();
	}

	public SwipeTextView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init();
	}

	public SwipeTextView(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		init();
	}

	public void init(){
		super.setOnTouchListener(this);
	}

	@Override
	public boolean onTouch(View v, MotionEvent e){
		Log.d(">>>", "onTouch " + e.getAction());
		switch(e.getAction()){
			case MotionEvent.ACTION_MOVE:
				Log.d(">>>", "벗어났나 " + !v.isPressed());
				break;
			case MotionEvent.ACTION_DOWN:
				touchStart = System.currentTimeMillis();
				startX = e.getX();
				Log.d(">>>", "터치됨 " + startX);
				break;
			case MotionEvent.ACTION_CANCEL:
			case MotionEvent.ACTION_UP:
				touchEnd = System.currentTimeMillis();
				Long elapsedTime = touchEnd - touchStart;
				endX = e.getX();
				Log.d(">>>", "벗어났나 " + !v.isPressed());
				Log.d(">>>", "SwipeTextView 경과 시간 " + elapsedTime + " " + endX);
				swipeV = Math.abs(endX - startX)/elapsedTime;
				Log.d(">>>", "속도 " + swipeV);
				if(swipeV >= 1){
					if(endX <= startX){
						Log.d(">>>", "SWIPE_RIGHT_TO_LEFT ");
						isSwipe = Define.SWIPE_RIGHT_TO_LEFT;
					}else{
						Log.d(">>>", "SWIPE_LEFT_TO_RIGHT ");
						isSwipe = Define.SWIPE_LEFT_TO_RIGHT;
					}
				}
				else{
					Log.d(">>>", "SWIPE_NONE ");
					isSwipe = Define.SWIPE_NONE;
				}
				break;
		}
		return false;
	}

	public int getSwipe(){
		return isSwipe;
	}
}
