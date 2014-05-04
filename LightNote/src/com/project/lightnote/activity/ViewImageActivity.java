package com.project.lightnote.activity;

import com.project.lightnote.R;
import com.project.lightnote.utils.FileHelper;
import com.project.lightnote.utils.ShareService;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

public class ViewImageActivity extends BaseActivity{
	 Bitmap bp=null;  
     ImageView imageview;  
     float scaleWidth;  
     float scaleHeight;  
       
     int h;  
     boolean num=false;  
     
     private String filePath;
     
     private ActionBar mActionBar;
 @Override  
 public void onCreate(Bundle savedInstanceState) {  
     super.onCreate(savedInstanceState);  
     setContentView(R.layout.activity_view_image);  
       
//     Display display=getWindowManager().getDefaultDisplay();  
     imageview=(ImageView)findViewById(R.id.viewImage);
     filePath = getIntent().getStringExtra("bitmap");
     imageview.setTag(filePath);
     FileHelper helper  = new FileHelper();
     bp= helper.getImage(filePath); 
     
//     int width=bp.getWidth();  
//     int height=bp.getHeight();  
//     int w=display.getWidth();  
//     int h=display.getHeight();  
////     scaleWidth=((float)w)/width;  
////     scaleHeight=((float)h)/height;  
     imageview.setImageBitmap(bp);  
     
     mActionBar = getActionBar();
	 mActionBar.setDisplayHomeAsUpEnabled(true);
	 
	 setTitle("Í¼Æ¬ä¯ÀÀ");
 }  
 
 	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.global, menu);
		return super.onCreateOptionsMenu(menu);
	}

 	@Override
 	public boolean onOptionsItemSelected(MenuItem item) {
 		super.onOptionsItemSelected(item);
 		Intent intent = new Intent();
 		switch (item.getItemId()) {

 		case android.R.id.home:
 			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
 			intent.setClass(ViewImageActivity.this, MainActivity.class);
 			startActivity(intent);
 			finish();
 			break;
 		case R.id.global_share:
 			ShareService service = new ShareService(this);
 			service.shareImage(imageview);
 			break;
 		case R.id.global_delete:
 			FileHelper helper = new FileHelper();
 			helper.deleteNoteViaPath(filePath);
 			Toast.makeText(this, "É¾³ý³É¹¦", Toast.LENGTH_SHORT).show();
 			intent.setClass(ViewImageActivity.this, MainActivity.class);
 			startActivity(intent);
 			finish();
 			return true;
 		default:
 			break;
 		}
 		return true;
 	}
}
