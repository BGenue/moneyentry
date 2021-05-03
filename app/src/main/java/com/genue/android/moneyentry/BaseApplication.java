package com.genue.android.moneyentry;

import android.app.Application;
import android.util.Log;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class BaseApplication extends Application
{

	//광고
	public AdView mAdView;
	public AdRequest adRequest;
	public AdListener myAdListener = new AdListener()
	{
		@Override
		public void onAdLoaded()
		{
			super.onAdLoaded();
			Log.d(">>>", "onAdLoaded");
		}

		@Override
		public void onAdImpression()
		{
			super.onAdImpression();
			Log.d(">>>", "onAdImpression");
		}
	};

	@Override
	public void onCreate(){
		super.onCreate();

		initAd();
	}

	protected void initAd()
	{
		MobileAds.initialize(this, new OnInitializationCompleteListener()
		{
			@Override
			public void onInitializationComplete(InitializationStatus initializationStatus)
			{
			}
		});
		adRequest = new AdRequest.Builder().build();
		//		mAdView.loadAd(adRequest);
	}
}
