package com.genue.android.moneyentry;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{

	private UserDBHelper userDBHelper;
	SQLiteDatabase userDatabase;
	EditText tmp;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		tmp = findViewById(R.id.userName);

		initDB();
	}

	public void initDB(){
		//어플 시작 시 최초 인지 아닌지 구분해야해 -> user table 의 id 존재 여부 확인
		userDBHelper = UserDBHelper.getInstance(this);
		userDBHelper.onCreate(userDBHelper.getWritableDatabase());
		String user = userDBHelper.checkID();
		if(user != null){
			Toast.makeText(this, "사용자 있음 " + user, Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(this, "사용자 없음 " + user, Toast.LENGTH_SHORT).show();
		}
	}

	public void onClick(View v){
		switch(v.getId()){
			case R.id.btn:
				String t = tmp.getText().toString();
				if(t != null && t.length() > 0){
//					userDBHelper.checkID(userDatabase);
					userDBHelper.checkID();
//					userDBHelper.setID(userDatabase, "지누");
					userDBHelper.setID(tmp.getText().toString());
//					userDBHelper.checkID(userDatabase);
					userDBHelper.checkID();
//					Toast.makeText(this, "디비 생성됨", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(this, "디비 생성안됨", Toast.LENGTH_SHORT).show();
				}
				break;
		}
	}
}