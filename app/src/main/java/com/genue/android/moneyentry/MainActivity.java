package com.genue.android.moneyentry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity
{
//	private final String TAG = getClass().getSimpleName();
	private final String TAG = ">>>";

	//db
	private UserDBHelper userDBHelper;
	SQLiteDatabase userDatabase;

	//광고
	private AdView mAdView;

	//유저
	private UserInfo user;

	//ui
	private ConstraintLayout clBaseLayout;
	private TextView tvSummaryTitle;
	private TextView[] mSummaryText = new TextView[4];
	private int touched = -1;

	private long  touchStart = 0;
	private long  touchEnd = 0;
	private float startX = 0;
	private float endX = 0;
	private float swipeV = 0;
	private int isSwipe = Define.SWIPE_NONE;

	private int year = 0;
	private int month = 0;
	private int week = 0;
	private int day = 0;


	//보여주는 위치
	private String[] sumTitle = new String[] {"일 내역", "주차 내역", "월 내역", "년 내역"};
	private int posi = Define.SHOW_DAY;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		user = UserInfo.createUser();

		getTodayDate();

		initDB();
		initAd();
		initUi();
	}

	private void initUi()
	{
		tvSummaryTitle = findViewById(R.id.summaryTitle);
		setDateText(posi);
		mSummaryText[0] = findViewById(R.id.summarySpend);
		mSummaryText[1] = findViewById(R.id.summaryEarn);
		mSummaryText[2] = findViewById(R.id.summarySave);
		mSummaryText[3] = findViewById(R.id.summaryBalance);

		//스와이프 체크
		for(int i = 0 ; i < mSummaryText.length ; i++){
			mSummaryText[i].setOnTouchListener(new View.OnTouchListener()
			{
				@Override
				public boolean onTouch(View v, MotionEvent e)
				{
					Log.d(TAG, "onTouch " + e.getAction());
					switch(e.getAction()){
						case MotionEvent.ACTION_MOVE:
							Log.d(TAG, "벗어났나 " + !v.isPressed());
							break;
						case MotionEvent.ACTION_DOWN:
							touchStart = System.currentTimeMillis();
							startX = e.getX();
							Log.d(TAG, "터치됨 " + startX);
							break;
						case MotionEvent.ACTION_CANCEL:
						case MotionEvent.ACTION_UP:
							touchEnd = System.currentTimeMillis();
							Long elapsedTime = touchEnd - touchStart;
							endX = e.getX();
							Log.d(TAG, "벗어났나 " + !v.isPressed());
							Log.d(TAG, "SwipeTextView 경과 시간 " + elapsedTime + " " + endX);
							swipeV = Math.abs(endX - startX)/elapsedTime;
							Log.d(TAG, "속도 " + swipeV);
							if(swipeV >= 1){
								if(endX <= startX){
									Log.d(TAG, "SWIPE_RIGHT_TO_LEFT ");
									isSwipe = Define.SWIPE_RIGHT_TO_LEFT;
								}else{
									Log.d(TAG, "SWIPE_LEFT_TO_RIGHT ");
									isSwipe = Define.SWIPE_LEFT_TO_RIGHT;
								}
							}
							else{
								Log.d(TAG, "SWIPE_NONE ");
								isSwipe = Define.SWIPE_NONE;
							}
							break;
					}
					return false;
				}
			});
		}
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
		AdRequest adRequest = new AdRequest.Builder().build();
		mAdView.loadAd(adRequest);
	}

	private void initDB()
	{
		//어플 시작 시 최초 인지 아닌지 구분해야해 -> user table 의 id 존재 여부 확인
		userDBHelper = UserDBHelper.getInstance(this);
		userDBHelper.resetDB();
		userDBHelper.createDB();
		changeBtn();
	}

	public void changeBtn()
	{
		user.id = userDBHelper.checkID();
		if(user.id != null) {
			Toast.makeText(this, "사용자 있음 " + user.id, Toast.LENGTH_SHORT).show();
		}
		else {
			Toast.makeText(this, "사용자 없음 " + user.id, Toast.LENGTH_SHORT).show();
		}
	}

	public void getTodayDate()
	{
		Calendar calendar = Calendar.getInstance();
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH) + 1;
		week = calendar.get(Calendar.WEEK_OF_MONTH);
		day = calendar.get(Calendar.DAY_OF_MONTH);
	}

	public void setDateText(int p)
	{
		if(p == Define.SHOW_DAY) {
			//일
			tvSummaryTitle.setText(month + "월 " + day + sumTitle[p]);
		}
		else if(p == Define.SHOW_WEEK) {
			//주
			tvSummaryTitle.setText(month + "월 " + week + " " + sumTitle[p]);
		}
		else if(p == Define.SHOW_MONTH) {
			//월
			tvSummaryTitle.setText(month + sumTitle[p]);
		}
		else if(p == Define.SHOW_YEAR) {
			//년
			tvSummaryTitle.setText(year + sumTitle[p]);
		}
	}

	public void onClick(View v)
	{
		//		String id = userDBHelper.getID();
		//		Log.d(TAG, "id " + id);
		//		String t = "";
		//		String t2 = "";
		//		if(t != null && t.length() > 0){
		//			userDBHelper.checkID();
		//			userDBHelper.setID(t);
		//			if(t2 != null && t2.length() > 0){
		//				userDBHelper.setPW(t2);
		//			}
		//			userDBHelper.checkID();
		//			changeBtn();
		//		}else{
		//			Toast.makeText(this, "디비 생성안됨", Toast.LENGTH_SHORT).show();
		//		}
		switch(v.getId()) {
			case R.id.leftBtn:
				changeLeft();
				break;
			case R.id.rightBtn:
				changeRight();
				break;
			case R.id.summarySpend:
				Toast.makeText(this, "지출 눌림", Toast.LENGTH_SHORT).show();
				checkSwipe(isSwipe, Define.PRESS_SPEND);
				break;
			case R.id.summaryEarn:
				Toast.makeText(this, "수입 눌림", Toast.LENGTH_SHORT).show();
				checkSwipe(isSwipe, Define.PRESS_EARN);
				break;
			case R.id.summarySave:
				Toast.makeText(this, "저축 눌림", Toast.LENGTH_SHORT).show();
				checkSwipe(isSwipe, Define.PRESS_SAVE);
				break;
			case R.id.summaryBalance:
				Toast.makeText(this, "잔고 눌림", Toast.LENGTH_SHORT).show();
				checkSwipe(isSwipe, Define.PRESS_BALANCE);
				break;
			case R.id.monthlyEarn:
				Toast.makeText(this, "월급/용돈 눌림", Toast.LENGTH_SHORT).show();
				break;
			case R.id.monthlySave:
				Toast.makeText(this, "정기 저축 눌림", Toast.LENGTH_SHORT).show();
				break;
			case R.id.totalBalance:
				Toast.makeText(this, "잔액 눌림", Toast.LENGTH_SHORT).show();
				break;
			case R.id.summaryTitle:
				Toast.makeText(this, "내역 눌림", Toast.LENGTH_SHORT).show();
				Intent goInsert = new Intent(this, CalendarActivity.class);
				goInsert.putExtra("calendar", posi);
				startActivity(goInsert);
				break;
		}
	}

	public void checkSwipe(int getSwipe, int pressType)
	{
		if(getSwipe == Define.SWIPE_LEFT_TO_RIGHT){
			changeLeft();
		}else if(getSwipe == Define.SWIPE_RIGHT_TO_LEFT){
			changeRight();
		}else if(getSwipe == Define.SWIPE_NONE){
			Intent goCalendar = new Intent(this, InsertActivity.class);
			goCalendar.putExtra("type",pressType);
			startActivity(goCalendar);
		}
	}

	public void changeLeft()
	{
		if(--this.posi < 0) {
			this.posi = sumTitle.length - 1;
		}
		setDateText(this.posi);
	}

	public void changeRight()
	{
		if(++this.posi == sumTitle.length) {
			this.posi = 0;
		}
		setDateText(this.posi);
	}
}