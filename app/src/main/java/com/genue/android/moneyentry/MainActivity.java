package com.genue.android.moneyentry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends BaseActivity
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
	private ConstraintLayout baseLayout;
	private FrameLayout adViewLayout;
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

		user = UserInfo.createUser();
		initDB();
	}

	private void initUi()
	{
		mAdView = findViewById(R.id.adView);
		initAd(mAdView);
		//		mAdView.loadAd(adRequest);
		//		mAdView = findViewById(R.id.adView);

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

	protected void initAd()
	{
//		MobileAds.initialize(this, new OnInitializationCompleteListener()
//		{
//			@Override
//			public void onInitializationComplete(InitializationStatus initializationStatus)
//			{
//			}
//		});
//
//		mAdView = findViewById(R.id.adView);
//		AdRequest adRequest = new AdRequest.Builder().build();
//		mAdView.loadAd(adRequest);
	}

	private void initDB()
	{
		//어플 시작 시 최초 인지 아닌지 구분해야해 -> user table 의 id 존재 여부 확인
		userDBHelper = UserDBHelper.getInstance(this);
		userDBHelper.resetDB();
		userDBHelper.createDB();
		checkFirst();
	}

	//첫 사용인지 확인
	private void checkFirst()
	{
		user.setNickname(userDBHelper.checkID());
		user.setNickname("홍길동");
		if(user.hasNickname()) {
			Toast.makeText(this, "사용자 있음 " + user.getNickname(), Toast.LENGTH_SHORT).show();

			if(user.isMonthlyEarnEmpty()){
				Toast.makeText(this, "용돈/월급 입력해야해", Toast.LENGTH_SHORT).show();
			} else if(user.isMonthlySaveEmpty()){
				Toast.makeText(this, "정기 저축 입력해야해", Toast.LENGTH_SHORT).show();
			} else if(user.isMonthlySpendEmpty()){
				Toast.makeText(this, "정기 소비 입력해야해", Toast.LENGTH_SHORT).show();
			}
			startApp();
		}
		else {
			Toast.makeText(this, "사용자 없음 " + user.getNickname(), Toast.LENGTH_SHORT).show();
		}
	}

	private void startApp(){
		setContentView(R.layout.activity_main);

		getTodayDate();

		initUi();
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
			case R.id.tvMonthlyEarn:
				Toast.makeText(this, "월급/용돈 눌림", Toast.LENGTH_SHORT).show();
				goInsert(Define.PRESS_MONTHLY_EARN);
				break;
			case R.id.tvMonthlySave:
				Toast.makeText(this, "정기 저축 눌림", Toast.LENGTH_SHORT).show();
				goInsert(Define.PRESS_MONTHLY_SAVE);
				break;
			case R.id.tvMonthlySpend:
				Toast.makeText(this, "정기 소비 눌림", Toast.LENGTH_SHORT).show();
				goInsert(Define.PRESS_MONTHLY_SPEND);
				break;
			case R.id.tvTotalBalance:
				Toast.makeText(this, "잔액 눌림", Toast.LENGTH_SHORT).show();
				goInsert(Define.PRESS_TOTAL_BALANCE);
				break;
			case R.id.summaryTitle:
				Toast.makeText(this, "내역 눌림", Toast.LENGTH_SHORT).show();
				Intent goCalendar = new Intent(this, CalendarActivity.class);
				goCalendar.putExtra("calendar", posi);
				goCalendar.putExtra("year", year);
				goCalendar.putExtra("month", month);
				goCalendar.putExtra("day", day);
				startActivity(goCalendar);
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
			goInsert(pressType);
		}
	}

	public void goInsert(int pressType){
		Intent goInsert = new Intent(this, InsertActivity.class);
		goInsert.putExtra("type", pressType);
		startActivityForResult(goInsert, Define.REQUEST_CODE_INSERT);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);

		if(requestCode == Define.REQUEST_CODE_INSERT){
			if(resultCode == Define.RESULT_CODE_CONFIRM){

			} else if(resultCode == Define.RESULT_CODE_CANCEL){

			}
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