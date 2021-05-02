package com.genue.android.moneyentry;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class BaseActivity extends AppCompatActivity
{
	//광고
//	protected AdView mAdView;
	protected AdRequest adRequest;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
//
//		initAd();
	}

	protected AdSize getAdSize() {
		// Step 2 - Determine the screen width (less decorations) to use for the ad width.
		Display display = getWindowManager().getDefaultDisplay();
		DisplayMetrics outMetrics = new DisplayMetrics();
		display.getMetrics(outMetrics);

		float widthPixels = outMetrics.widthPixels;
		float density = outMetrics.density;

		int adWidth = (int) (widthPixels / density);

		// Step 3 - Get adaptive ad size and return for setting on the ad view.
		return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth);
	}

	protected void initAd(AdView mAdView)
	{
		Log.d(">>>", "initAd()");
		MobileAds.initialize(this, new OnInitializationCompleteListener()
		{
			@Override
			public void onInitializationComplete(InitializationStatus initializationStatus)
			{
			}
		});

		adRequest = new AdRequest.Builder().build();
		mAdView.loadAd(adRequest);
	}

	public static int getAdViewHeightInDP(Activity activity) {
		int adHeight = 0;

		int screenHeightInDP = getScreenHeightInDP(activity);
		if (screenHeightInDP < 400)
			adHeight = 32;
		else if (screenHeightInDP <= 720)
			adHeight = 50;
		else
			adHeight = 90;

		return adHeight;
	}

	public static int getScreenHeightInDP(Activity activity) {
		DisplayMetrics displayMetrics = ((Context) activity).getResources().getDisplayMetrics();

		float screenHeightInDP = displayMetrics.heightPixels / displayMetrics.density;

		return Math.round(screenHeightInDP);
	}

}
