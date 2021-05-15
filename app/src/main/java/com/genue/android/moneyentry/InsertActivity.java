package com.genue.android.moneyentry;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import java.text.DecimalFormat;

public class InsertActivity extends AppCompatActivity
{
	Context mContext;

	EditText etTitle;
	EditText etAmount;
	EditText etExplain;
	EditText startYear;
	EditText startMonth;
//	EditText startDay;
	EditText endYear;
	EditText endMonth;
//	EditText endDay;
	final String DEFAULT_TEXT = "₩   ";
	final int DEFAULT_TEXT_LENGTH = 4;
	boolean isChanging = false;
	int type = -1;

	ConstraintLayout rgCategory;
	RadioButton rbEarn;
	RadioButton rbSave;
	RadioButton rbSpend;
	RadioButton rbBalance;
	int rbClicked = -1;

	private int blank = 10;//원화기호로부터 떨어진 정도
	private String result = "";
	private DecimalFormat decimalFormat = new DecimalFormat("#,###");
	private TextWatcher watcher = new TextWatcher()
	{
		@Override
		public void beforeTextChanged(CharSequence charSequence, int start, int count, int after)
		{
			//변경 전 문자열
		}

		@Override
		public void onTextChanged(CharSequence charSequence, int start, int before, int count)
		{
			Log.d(">>>>", "onTextChanged");
			//DEFAULT_TEXT_LENGTH 이하로는 안대
			if(isChanging && charSequence.toString().length() < DEFAULT_TEXT_LENGTH) {
				etAmount.setText(DEFAULT_TEXT);
				etAmount.setSelection(DEFAULT_TEXT_LENGTH);
			}

			if(!TextUtils.isEmpty(charSequence.toString()) && !charSequence.toString().equals("₩   ") && !charSequence.toString().equals("₩   " + result)) {
				Log.d(">>>", charSequence.toString());
				charSequence = charSequence.toString().replace("₩   ", "");
				Log.d(">>>", charSequence.toString());
				result = decimalFormat.format(Double.parseDouble(charSequence.toString().replaceAll(",", "")));
				etAmount.setText("₩   " + result);
				etAmount.setSelection(result.length() + 4);
			}
		}

		@Override
		public void afterTextChanged(Editable editable)
		{

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_insert);

		mContext = this;

		etTitle = findViewById(R.id.etTitle);
		etAmount = findViewById(R.id.etAmount);
		etAmount.setOnFocusChangeListener(new View.OnFocusChangeListener()
		{
			@Override
			public void onFocusChange(View view, boolean hasFocus)
			{
				EditText focusEdit = (EditText) view;
				Log.d(">>", "onFocusChange " + focusEdit.getText().length() + " " + hasFocus);
				if(hasFocus) {
					isChanging = true;
					if(focusEdit.getText().length() < DEFAULT_TEXT_LENGTH) {
						focusEdit.setText("₩   ");
						focusEdit.setSelection(DEFAULT_TEXT_LENGTH);
					}
				}
				else {
					isChanging = false;
					if(focusEdit.getText().length() <= DEFAULT_TEXT_LENGTH) {
						focusEdit.setText("");
						focusEdit.setHint("금액");
					}
				}
			}
		});
		etAmount.addTextChangedListener(watcher);

		etExplain = findViewById(R.id.etExplain);
		startYear = findViewById(R.id.startYear);
		startMonth = findViewById(R.id.startMonth);
//		startDay = findViewById(R.id.startDay);
		endYear = findViewById(R.id.endYear);
		endMonth = findViewById(R.id.endMonth);
//		endDay = findViewById(R.id.endDay);

		rgCategory = findViewById(R.id.rgCategory);
		rbEarn = findViewById(R.id.rbEarn);
		rbEarn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				RadioButton v = (RadioButton) view;
				if(rbClicked != 0){
					v.setChecked(true);
					rbSave.setChecked(false);
					rbSpend.setChecked(false);
					rbBalance.setChecked(false);
					rbClicked = 0;
					SetHint(rbClicked);
				}else {
					v.setChecked(false);
					rbClicked = -1;
				}
			}
		});
		rbSave = findViewById(R.id.rbSave);
		rbSave.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				RadioButton v = (RadioButton) view;
				if(rbClicked != 1){
					v.setChecked(true);
					rbEarn.setChecked(false);
					rbSpend.setChecked(false);
					rbBalance.setChecked(false);
					rbClicked = 1;
					SetHint(rbClicked);
				}else {
					v.setChecked(false);
					rbClicked = -1;
				}
			}
		});
		rbSpend = findViewById(R.id.rbSpend);
		rbSpend.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				RadioButton v = (RadioButton) view;
				if(rbClicked != 2){
					v.setChecked(true);
					rbEarn.setChecked(false);
					rbSave.setChecked(false);
					rbBalance.setChecked(false);
					rbClicked = 2;
					SetHint(rbClicked);
				}else {
					v.setChecked(false);
					rbClicked = -1;
				}
			}
		});
		rbBalance = findViewById(R.id.rbBalance);
		rbBalance.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				RadioButton v = (RadioButton) view;
				if(rbClicked != 3){
					v.setChecked(true);
					rbEarn.setChecked(false);
					rbSave.setChecked(false);
					rbSpend.setChecked(false);
					rbClicked = 3;
					SetHint(rbClicked);
				}else {
					v.setChecked(false);
					rbClicked = -1;
				}
			}
		});

		Intent fromMain = getIntent();
		type = fromMain.getIntExtra("type", -1);
		switch(type) {
			case Define.PRESS_MONTHLY_EARN:
				monthlyClicked();
				rbEarn.setChecked(true);
				rbClicked = 0;
				break;
			case Define.PRESS_MONTHLY_SAVE:
				monthlyClicked();
				rbSave.setChecked(true);
				rbClicked = 1;
				break;
			case Define.PRESS_MONTHLY_SPEND:
				monthlyClicked();
				rbSpend.setChecked(true);
				rbClicked = 2;
				break;
			case Define.PRESS_TOTAL_BALANCE:
				monthlyClicked();
				rbBalance.setChecked(true);
				rbClicked = 3;
				break;
			case Define.PRESS_EARN:
				rbEarn.setChecked(true);
				rbBalance.setVisibility(View.GONE);
				rbClicked = 0;
				break;
			case Define.PRESS_SAVE:
				rbSave.setChecked(true);
				rbBalance.setVisibility(View.GONE);
				rbClicked = 1;
				break;
			case Define.PRESS_SPEND:
				rbSpend.setChecked(true);
				rbBalance.setVisibility(View.GONE);
				rbClicked = 2;
				break;
		}
	}

	private void monthlyClicked(){
		rbEarn.setText("월급/용돈");
		rbSave.setText("정기 저축");
		rbSpend.setText("정기 소비");
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		SetHint(type);
	}

	private void SetHint(int type)
	{
		switch(type) {
			case Define.PRESS_MONTHLY_EARN:
			case Define.RB_TYPE_EARN:
				etTitle.setHint("월급/용돈/정기 수입");
				break;
			case Define.PRESS_MONTHLY_SAVE:
			case Define.RB_TYPE_SAVE:
				etTitle.setHint("정기 저축");
				break;
			case Define.PRESS_MONTHLY_SPEND:
			case Define.RB_TYPE_SPEND:
				etTitle.setHint("정기 소비");
				break;
			case Define.PRESS_TOTAL_BALANCE:
			case Define.RB_TYPE_BALANCE:
				etTitle.setHint("잔액");
				break;
		}
	}

	public void onClick(View v)
	{
		Intent result = new Intent();
		if(v.getId() == R.id.tvConfirm) {
			if(etTitle.getText() == null || etTitle.getText().length() < 1) {
				Toast.makeText(this, "이름을 정해주세요", Toast.LENGTH_SHORT).show();
			}
			else if(etAmount.getText() == null || etAmount.getText().length() < 1) {
				Toast.makeText(this, "금액을 정해주세요", Toast.LENGTH_SHORT).show();
			}
			else {
				result.putExtra("name", etTitle.getText().toString());
				result.putExtra("amount", etAmount.getText().toString());
				result.putExtra("startYear", startYear.getText().toString());
				result.putExtra("startMonth", startMonth.getText().toString());
//				result.putExtra("startDay", startDay.getText().toString());
				result.putExtra("endYear", endYear.getText().toString());
				result.putExtra("endMonth", endMonth.getText().toString());
//				result.putExtra("endDay", endDay.getText().toString());

				setResult(Define.RESULT_CODE_CONFIRM, result);
				finish();
			}
		}
		else if(v.getId() == R.id.tvCancel) {
			setResult(Define.RESULT_CODE_CANCEL);
			finish();
		}
	}
}
