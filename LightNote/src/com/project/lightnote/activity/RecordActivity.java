package com.project.lightnote.activity;

import com.project.lightnote.R;
import com.project.lightnote.elment.RecordButton;
import com.project.lightnote.elment.RecordButton.OnFinishedRecordListener;
import com.project.lightnote.utils.ColorSelector;
import com.project.lightnote.utils.FileHelper;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class RecordActivity extends Activity{
	private ActionBar mActionBar;
	private RecordButton mRecordButton = null;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.activity_record);
		
		
		mActionBar = getActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		
		mRecordButton = (RecordButton) findViewById(R.id.record_button);

		FileHelper helper = new FileHelper();
		String sdCardPath = helper.getSdCardPath();
		String voiceFileName = ColorSelector.getRandomColor() + "AVI"
				+ FileHelper.getCurrentTime() + ".mp3";
		mRecordButton.setSavePath(sdCardPath + voiceFileName);
		mRecordButton
				.setOnFinishedRecordListener(new OnFinishedRecordListener() {

					@Override
					public void onFinishedRecord(String audioPath) {
//						Log.i("RECORD!!!", "finished!!!!!!!!!! save to "
//								+ audioPath);

					}
				});

	}
	
	@Override
	public  boolean onCreateOptionsMenu(Menu menu){
		getMenuInflater().inflate(R.menu.edit_bar, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		super.onOptionsItemSelected(item);
		Intent intent = new Intent();
		switch (item.getItemId()) {
		case android.R.id.home:
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.setClass(RecordActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
			break;
		case R.id.menu_done:
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.setClass(RecordActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
			break;
		}
		return true;
	}
}
