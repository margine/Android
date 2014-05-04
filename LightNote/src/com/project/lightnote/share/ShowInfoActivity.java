package com.project.lightnote.share;



import com.project.lightnote.R;

import android.app.Activity;  
import android.os.Bundle;  
import android.widget.TextView;  
  
public class ShowInfoActivity extends Activity {  
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.show_info);  
        TextView show = (TextView) findViewById(R.id.show_info_tv);  
        show.setText(getIntent().getStringExtra("data"));  
    }  
}  
