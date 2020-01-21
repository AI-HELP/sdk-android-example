package com.aihelp.demo;

import android.util.Log;
import android.os.StrictMode;
import android.support.multidex.MultiDexApplication;

public class MyApp extends MultiDexApplication {

	@Override
	public void onCreate() {
		super.onCreate();
		try {
			StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
			StrictMode.setVmPolicy(builder.build());
			Log.d("debug", "Start Application");
		} catch (Exception e) {
		}
	}
	
}
