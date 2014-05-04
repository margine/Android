package com.project.lightnote.activity;

import com.project.lightnote.elment.LightNoteApplication;

import android.app.Activity;
import android.content.Intent;

public class BaseActivity extends Activity{
	LightNoteApplication application;
	
	@Override
	protected void onResume(){
		super.onResume();
		application = (LightNoteApplication)getApplication();
		if (application.isLocked()) {
			Intent intent = new Intent(this, PasswordActivity.class);
			startActivity(intent);
		}
	}
	

}
