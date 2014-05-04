package com.project.lightnote.activity;

import com.project.lightnote.R;
import com.project.lightnote.utils.FileHelper;
import com.project.lightnote.utils.KeyHelpers;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ViewNoteActivity extends BaseActivity {

	private ActionBar mActionBar;
	private CharSequence mNoteContent;
	private String fileName;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view);

		Intent intent = getIntent();
		fileName = intent.getStringExtra(KeyHelpers.KEY_FILENAME);
		setTitle(FileHelper.getTimeString(fileName));
		mNoteContent = intent.getCharSequenceExtra(KeyHelpers.NOTE_CONTENT_KEY);
		TextView textView = (TextView) findViewById(R.id.viewContent);
		textView.setText(mNoteContent);

		mActionBar = getActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.view_bar, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		Intent intent = new Intent();
		switch (item.getItemId()) {

		case android.R.id.home:
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.setClass(ViewNoteActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
			break;
		case R.id.menu_edit:
			intent.putExtra(KeyHelpers.NOTE_CONTENT_KEY, mNoteContent);
			intent.putExtra(KeyHelpers.EXSIT_NOTE_KEY, true);
			intent.putExtra(KeyHelpers.KEY_FILENAME, fileName);
			intent.setClass(ViewNoteActivity.this, EditNoteActivity.class);
			startActivity(intent);
			break;
		case R.id.action_edit:
			return true;
		default:
			break;
		}
		return true;
	}
}
