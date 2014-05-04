package com.project.lightnote.activity;

import com.project.lightnote.R;
import com.project.lightnote.utils.FileHelper;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class SetPasswordActivity extends Activity{

	private ActionBar mActionBar;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_password);
		
		mActionBar = getActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		super.onOptionsItemSelected(item);
		if (item.getItemId() == android.R.id.home) {
			backToMain();
		}
		
		return true;
	}
	public void checkPassword(View v){
		String password = ((EditText)findViewById(R.id.input_password)).getText().toString();
		String queryPassword = ((EditText)findViewById(R.id.input_query_password)).getText().toString();
		
		Toast toast;
		if (password.equals("")) {
			toast = Toast.makeText(SetPasswordActivity.this, getString(R.string.toast_psw_empty), Toast.LENGTH_SHORT);
			toast.show();
		}
		else if (!password.equals(queryPassword)) {
			toast = Toast.makeText(SetPasswordActivity.this, getString(R.string.toast_pws_not_same), Toast.LENGTH_SHORT);
			toast.show();
		}else {
			toast = Toast.makeText(SetPasswordActivity.this, getString(R.string.toast_pws_sucess), Toast.LENGTH_SHORT);
			savePassword(password);
			toast.show();
			backToMain();
		}
	}
	
	private void backToMain(){
		Intent intent = new Intent();
		intent.setClass(SetPasswordActivity.this, MainActivity.class);
		startActivity(intent);
		finish();
	}
	

	
	private void savePassword(String psw){
		FileHelper helper = new FileHelper();
		helper.savePassword(psw);
	}
}
