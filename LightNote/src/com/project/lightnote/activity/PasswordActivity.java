package com.project.lightnote.activity;

import com.project.lightnote.R;
import com.project.lightnote.elment.LightNoteApplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class PasswordActivity extends Activity{
	
	private LightNoteApplication application;
	@Override  
    protected void onCreate(Bundle savedInstanceState) {  
          
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_password);  
        application = (LightNoteApplication) getApplication();  
        
    }
	
	public void vertifyPassword(View v){
			EditText editTextPassword = (EditText) findViewById(R.id.password_field);  
		  String password = editTextPassword.getText().toString();  
          if (password != null && password.equals(application.getPassword())){  
              //Toast.makeText(PasswordActivity.this, "√‹¬Î’˝»∑£°", Toast.LENGTH_SHORT).show();  
              application.setLock(false);
              PasswordActivity.this.finish();  
          } else {  
              Toast.makeText(PasswordActivity.this, "√‹¬Î¥ÌŒÛ£°", Toast.LENGTH_SHORT).show();  
              editTextPassword.setText("");  
          }  
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		 
        if (keyCode == KeyEvent.KEYCODE_BACK
                 && event.getRepeatCount() == 0) {
        	Intent intent = new Intent(Intent.ACTION_MAIN);
        	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        	intent.addCategory(Intent.CATEGORY_HOME);
        	startActivity(intent);
             return true;
         }
         return super.onKeyDown(keyCode, event);
     }
}  
