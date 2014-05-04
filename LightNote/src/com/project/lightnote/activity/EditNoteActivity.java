package com.project.lightnote.activity;

import com.project.lightnote.R;
import com.project.lightnote.elment.RichEditText;
import com.project.lightnote.utils.ColorSelector;
import com.project.lightnote.utils.FileHelper;
import com.project.lightnote.utils.KeyHelpers;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class EditNoteActivity extends BaseActivity{
	
	private ActionBar mActionBar;
	private boolean isNew = false;
	private CharSequence mNoteContent;
	private FileHelper mHelper;
	private String fileName;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		
		Intent intent = getIntent();
		//新建一个note
		if (intent.hasExtra(KeyHelpers.NEW_NOTE_KEY)) {
			isNew = true;
			setTitle(R.string.new_note);
			
			switch (intent.getStringExtra(KeyHelpers.TYPE)) {
			case KeyHelpers.NEW_TYPE_TEXT:
				createViaText();
				break;
			case KeyHelpers.NEW_TYPE_IMAGE:
				createViaImage();
				break;
			case KeyHelpers.NEW_TYPE_RECORD:
				createViaRecord();
				break;
			case KeyHelpers.NEW_TYPE_ATTACH:
				createViaAttach();
			default:
				break;
			}
		}
		//编辑原有的note
		else if (intent.hasExtra(KeyHelpers.EXSIT_NOTE_KEY)) {
			fileName = intent.getStringExtra(KeyHelpers.KEY_FILENAME);
			editOldNote();
		}
		
		mActionBar = getActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		
	}
	
	@Override
	public  boolean onCreateOptionsMenu(Menu menu){
		getMenuInflater().inflate(R.menu.edit_bar, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		
		case android.R.id.home:
			handleNoSaveAction();
			break;
//		case R.id.menu_add:
//			new AlertDialog.Builder(EditNoteActivity.this).setItems(R.array.more_function, new DialogInterface.OnClickListener(){
//				
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					switch (which) {
//					case 0:
//						
//						break;
//
//					default:
//						break;
//					}
//					
//				}
//			}).show();
//			break;
		case R.id.menu_done:
			EditText editText = (EditText)findViewById(R.id.editView);
			CharSequence content = editText.getText();
			if (isNew) {
				fileName = ColorSelector.getRandomColor() + "TXT" + FileHelper.getCurrentTime() + ".txt";
			}
			saveText(content, fileName);
			
			Intent intent = new Intent();
			intent.putExtra(KeyHelpers.NOTE_CONTENT_KEY, content);
			intent.setClass(EditNoteActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
		default:
			break;
		}
		return true;
	}
	//TODO
	private void editOldNote(){
		setTitle(R.string.edit_note);
		mNoteContent = getIntent().getCharSequenceExtra(KeyHelpers.NOTE_CONTENT_KEY);
		EditText editText = (EditText)findViewById(R.id.editView);
		editText.setText(mNoteContent);
		editText.setSelection(mNoteContent.length());
	}
	

	private void createViaText(){
		//DO NOTHING
	}
	
	//TODO
	private void createViaImage(){
		RichEditText editText = (RichEditText)findViewById(R.id.editView);
		editText.insertDrawable(getIntent().getStringExtra("pathName"));
//		editText.
	}
	
	//TODO
	private void createViaRecord(){
		
	}
	//TODO
	private void createViaAttach(){
		
	}
	private void handleNoSaveAction(){
		if (mNoteContent == null) {
			returnBack();
			return;
		}
		new  AlertDialog.Builder(this)     
		.setMessage("尚未保存编辑内容，确认退出么？o(>﹏<)o" )  
		.setPositiveButton("是" ,  new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				returnBack();
			}
		} )  
		.setNegativeButton("否" , null)  
		.show();
	}
	
	private void returnBack(){
		Intent intent = new Intent();
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		if (isNew) {
			intent.setClass(EditNoteActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
			return;
		}else {
			intent.putExtra(KeyHelpers.EXSIT_NOTE_KEY, true);
			intent.putExtra(KeyHelpers.NOTE_CONTENT_KEY, mNoteContent);
			intent.setClass(EditNoteActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
		}				
	}
	
	private void saveText(CharSequence content, String fileName){
		mHelper = new FileHelper();
		String writeContent = content.toString();
		if (!isNew && writeContent.trim().equals("")) {//修改笔记时，删除了全部内容，导致内容为空
			mHelper.deleteNoteViaName(fileName);
		}
		if (!writeContent.trim().equals("")) {//内容不为空
			mHelper.writeToSDCard(content.toString(), fileName);
		}
	}
	
}
