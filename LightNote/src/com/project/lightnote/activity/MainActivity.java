package com.project.lightnote.activity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;









import com.project.lightnote.R;
import com.project.lightnote.fragment.NoteFragment.OnImageClickListener;
import com.project.lightnote.fragment.NoteFragment.OnVoiceClickListener;
import com.project.lightnote.fragment.SettingFragment;
import com.project.lightnote.fragment.ExtraInfoFragment;
import com.project.lightnote.fragment.NavigationDrawerFragment;
import com.project.lightnote.fragment.NoteFragment;
import com.project.lightnote.fragment.NoteFragment.OnNoteTextClickListener;
import com.project.lightnote.fragment.ShareFragment;
import com.project.lightnote.utils.ColorSelector;
import com.project.lightnote.utils.FileHelper;
import com.project.lightnote.utils.KeyHelpers;
import com.project.lightnote.utils.ShareService;
import com.renn.rennsdk.RennClient;

import android.app.AlertDialog;
import android.support.v7.app.ActionBar.OnNavigationListener;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Images.ImageColumns;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.widget.DrawerLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks,
		OnNavigationListener, OnNoteTextClickListener, OnImageClickListener,OnVoiceClickListener {

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;
	private final int ALBUM_REQUEST_CODE = 0;
	private final int PHOTO_REQUEST_CODE = 1;
	private final int RECORD_REQUEST_CODE = 2;
	private Fragment mFragment;
	private FragmentManager fragmentManager;

	private RennClient rennClient;

	private static final String APP_ID = "267479";

	private static final String API_KEY = "f25c83a9b085485aab199f5c6b479ce5";

	private static final String SECRET_KEY = "19f72deb48cf4b5280677e77cff806fd";

	// ShareSDk
	// private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));

		rennClient = RennClient.getInstance(this);
		rennClient.init(APP_ID, API_KEY, SECRET_KEY);
		rennClient
				.setScope("read_user_blog read_user_photo read_user_status read_user_album "
						+ "read_user_comment read_user_share publish_blog publish_share "
						+ "send_notification photo_upload status_update create_album "
						+ "publish_comment publish_feed");
		rennClient.setTokenType("bearer");
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		fragmentManager = getSupportFragmentManager();
		switch (position) {
		case 0:
			mFragment = NoteFragment.newInstance(position + 1);
			break;
		case 1:
			mFragment = SettingFragment.newInstance(position + 1);
			break;
		case 2:
			mFragment = ExtraInfoFragment.newInstance(position + 1);
			break;
		case 3:
			mFragment = ShareFragment.newInstance(position + 1);
			break;
		default:
			break;
		}
		if (mFragment != null) {
			fragmentManager.beginTransaction()
					.replace(R.id.container, mFragment).commit();
		}
	}

	public void onSectionAttached(int number) {
		switch (number) {
		case 1:
			mTitle = getString(R.string.title_index);
			break;
		case 2:
			mTitle = getString(R.string.title_account);
			break;
		case 3:
			mTitle = getString(R.string.title_about);
			break;
		case 4:
			mTitle = getString(R.string.title_share);
			break;
		}
	}

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
//			getMenuInflater().inflate(R.menu.main, menu);
//			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_edit) {

			return true;
		}
		return super.onOptionsItemSelected(item);
	}


	public void addOneNote(View v) {
		Intent intent = new Intent();
		intent.putExtra(KeyHelpers.NEW_NOTE_KEY, true);
		intent.putExtra(KeyHelpers.TYPE, KeyHelpers.NEW_TYPE_TEXT);
		intent.setClass(MainActivity.this, EditNoteActivity.class);
		startActivity(intent);
	}

	private void viewNote(View v) {
		Intent intent = getStartIntent(v);
		intent.setClass(MainActivity.this, ViewNoteActivity.class);
		startActivity(intent);
	}

	private Intent getStartIntent(View v) {
		Intent intent = new Intent();
		TextView textView = (TextView) v;
		String noteContent = textView.getText().toString();
		String fileName = (String) textView.getTag();
		intent.putExtra(KeyHelpers.NOTE_CONTENT_KEY, noteContent);
		intent.putExtra(KeyHelpers.EXSIT_NOTE_KEY, true);
		intent.putExtra(KeyHelpers.KEY_FILENAME, fileName);
		return intent;
	}

	@Override
	public void onImageClick(View v) {
		Intent intent = new Intent(MainActivity.this, ViewImageActivity.class);
		intent.putExtra("bitmap", (String) v.getTag());
		startActivity(intent);
	}

	@Override
	public boolean onImageLongClick(View v) {
		final View mView = v;
		new AlertDialog.Builder(MainActivity.this).setItems(
				R.array.img_more_function, new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
							onImageClick(mView);
							break;
						case 1:
							shareImg(mView);
							break;
						case 2:
							deleteImg(mView);
							break;
						default:
							break;
						}
					}
				}).show();
		return true;
	}

	@Override
	public void onVoiceClick(View v){
		TextView textView = (TextView)v;
		String path = (String)textView.getTag();
		MediaPlayer   player  =   new MediaPlayer();

         try {
        	 player.setDataSource(path);
        	 player.setLooping(false);
			 player.prepare();
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}

         player.start();
	}
	
	@Override
	public boolean onVoiceLongClick(View v){
		final View mView = v;
		new AlertDialog.Builder(MainActivity.this).setItems(
				R.array.voice_more_function, new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
//							shareAction(mView);
							break;
						case 1:
//							deleteVoice(mView);;
							break;
						case 2:
							deleteVoice(mView);;
							break;
						default:
							break;
						}
					}
				}).show();
		return true;
	}
	@Override
	public void onNoteTextClick(View v) {
		viewNote(v);
	}

	@Override
	public boolean onNoteTextLongClick(View v) {
		final View mView = v;
		new AlertDialog.Builder(MainActivity.this).setItems(
				R.array.text_more_function, new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
							editNote(mView);
							break;
						case 1:
							shareAction(mView);
							break;
						case 2:
							deleteNote(mView);
							break;
						default:
							break;
						}
					}
				}).show();
		return true;
	}

	private void editNote(View v) {
		Intent intent = getStartIntent(v);
		intent.setClass(MainActivity.this, EditNoteActivity.class);
		startActivity(intent);
	}

	private void shareImg(View v) {
		ShareService service = new ShareService(this);
		service.shareImage(v);
	}

	private void shareAction(View v) {
		ShareService service = new ShareService(MainActivity.this);
		service.shareStatus(v);
	}

	private void deleteImg(View v){
		FileHelper helper = new FileHelper();
		String filePath = (String)v.getTag();
		helper.deleteNoteViaPath(filePath);
		recreate();
		Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
	}
	private void deleteVoice(View v){
		FileHelper helper = new FileHelper();
		String filePath = (String)v.getTag();
		helper.deleteNoteViaPath(filePath);
		recreate();
		Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
	}
	
	private void deleteNote(View v) {
		FileHelper helper = new FileHelper();
		String fileName = (String) v.getTag();
		helper.deleteNoteViaName(fileName);
		recreate();
		Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
	}

	public void addOnePhoto(View v) {
		new AlertDialog.Builder(MainActivity.this).setItems(
				R.array.photo_choice, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
							addPhotoViaCamera();
							break;
						case 1:
							addPhotoViaAlbum();
						default:
							break;
						}
					}
				}).show();

	}

	public void addOneRecord(View v) {
	  Intent intent = new Intent();
	  intent.setClass(MainActivity.this, RecordActivity.class);
	  startActivity(intent);

	}


	public void addAttachment(View v) {
		Intent shareInt = new Intent(Intent.ACTION_SEND);
		shareInt.setType("text/plain");
		shareInt.putExtra(Intent.EXTRA_SUBJECT, "选择分享方式");
		shareInt.putExtra(Intent.EXTRA_TEXT, "轻记下，记下生活点滴...");
		shareInt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(shareInt);
	}

	private void addPhotoViaCamera() {
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			Intent intent = new Intent(
					android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(ImageColumns.ORIENTATION, 0);
			startActivityForResult(intent, PHOTO_REQUEST_CODE);
		}

	}

	private void addPhotoViaAlbum() {
		Intent intent = new Intent(Intent.CATEGORY_OPENABLE);
		intent.setType("image/*");
		// 根据版本号不同使用不同的Action
		if (Build.VERSION.SDK_INT < 19) {
			intent.setAction(Intent.ACTION_GET_CONTENT);
		} else {
			intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
		}
		startActivityForResult(intent, ALBUM_REQUEST_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, requestCode, data);

		if (resultCode == RESULT_OK) {
			Bitmap bitmap = null;
			switch (requestCode) {
			case PHOTO_REQUEST_CODE:
				if (null != data) {
					bitmap = data.getParcelableExtra("data");
				}
				break;
			case ALBUM_REQUEST_CODE:
				if (null != data) {
					Uri uri = data.getData();
					InputStream inputStream;
					try {
						inputStream = getContentResolver().openInputStream(uri);
						bitmap = BitmapFactory.decodeStream(inputStream);

					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
				break;
			case RECORD_REQUEST_CODE:
//				if (null != data) {
//					Uri uri = data.getData();
//					InputStream inputStream;
//					try {
//						inputStream = getContentResolver().openInputStream(uri);
//						saveVoice(inputStream);
//					} catch (FileNotFoundException e) {
//						e.printStackTrace();
//					}
//				}
				break;
			}
			if (bitmap != null) {
				bitmap = resizeImage(bitmap);
				saveImage(bitmap);

			}
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, MainActivity.class);
			startActivity(intent);
		}
	}

	private Bitmap resizeImage(Bitmap bitmap) {
		Bitmap newBitmap = null;
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		int w_width = 400;
		int w_height = 400;

		float scaleWidth = ((float) w_width) / width;
		float scaleHeight = ((float) w_height) / height;
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		newBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix,
				true);
		return newBitmap;
	}

	private void saveImage(Bitmap bitmap) {
		String imgFileName = ColorSelector.getRandomColor() + "IMG"
				+ FileHelper.getCurrentTime() + ".png";
		FileHelper helper = new FileHelper();
		helper.addOneImage(bitmap, imgFileName);
	}

	@Override
	public boolean onNavigationItemSelected(int arg0, long arg1) {
		return false;
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
