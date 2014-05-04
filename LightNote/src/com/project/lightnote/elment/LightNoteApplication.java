package com.project.lightnote.elment;


import com.project.lightnote.utils.FileHelper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.app.Application;

public class LightNoteApplication extends Application{

	private boolean isSetPassword = false;
	private String mPassword;
	private LockScreenReceiver receiver;
	private IntentFilter filter ;
	
	public String getPassword(){
		return mPassword;
	}
	
	public boolean isLocked(){
		return isSetPassword;
	}
	
	public void setLock(boolean isLocked){
		isSetPassword = isLocked;
	}
	
	@Override
	public void onCreate(){
		super.onCreate();
		
		FileHelper helper = new FileHelper();
		isSetPassword = helper.isSetPassword();
		
		if (isSetPassword) {
			mPassword = helper.getPassword();
		}
		
		receiver = new LockScreenReceiver();
        filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        this.registerReceiver(receiver, filter);
	}
	
	@Override
	public void onTerminate() {
		super.onTerminate();
		this.unregisterReceiver(receiver);
	}
	
	class LockScreenReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			/* 在这里处理广播 */
			if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())) {
				setLock(isSetPassword);
			}
		}
	}
}
