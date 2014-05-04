package com.project.lightnote.utils;

import java.io.File;

import android.app.Activity;
import android.app.ProgressDialog;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.renn.rennsdk.RennClient;
import com.renn.rennsdk.RennResponse;
import com.renn.rennsdk.RennExecutor.CallBack;
import com.renn.rennsdk.exception.RennException;
import com.renn.rennsdk.param.ShareStatusParam;
import com.renn.rennsdk.param.UploadPhotoParam;

public class ShareService {

	private RennClient rennClient;

	private static final String APP_ID = "267479";

	private static final String API_KEY = "f25c83a9b085485aab199f5c6b479ce5";

	private static final String SECRET_KEY = "19f72deb48cf4b5280677e77cff806fd";
	
	private Activity mActivity;
	
	private ProgressDialog mProgressDialog;
	
	public ShareService(Activity activity){
		mActivity = activity;
		rennClient = RennClient.getInstance(mActivity);
		rennClient.init(APP_ID, API_KEY, SECRET_KEY);
		rennClient
				.setScope("read_user_blog read_user_photo read_user_status read_user_album "
						+ "read_user_comment read_user_share publish_blog publish_share "
						+ "send_notification photo_upload status_update create_album "
						+ "publish_comment publish_feed");
		rennClient.setTokenType("bearer");
	}
	
	public void shareImage(View v){
		rennClient.login(mActivity);
		if (!rennClient.isLogin()) {
			return;
		}
		UploadPhotoParam param = new UploadPhotoParam();
//		FileHelper helper = new FileHelper();
		File picFile = new File((String)v.getTag());

		try {
			param.setFile(picFile);
			// param.setDescription(picInfo.getText().toString());
		} catch (Exception e) {
		}
		if (mProgressDialog == null) {
			mProgressDialog = new ProgressDialog(mActivity);
			mProgressDialog.setCancelable(true);
			mProgressDialog.setTitle("请等待");
			mProgressDialog.setIcon(android.R.drawable.ic_dialog_info);
			mProgressDialog.setMessage("正在获取信息");
			mProgressDialog.show();
		}
		try {
			rennClient.getRennService().sendAsynRequest(param, new CallBack() {

				@Override
				public void onSuccess(RennResponse response) {
					Toast.makeText(mActivity, "获取成功",
							Toast.LENGTH_SHORT).show();
					if (mProgressDialog != null) {
						mProgressDialog.dismiss();
						mProgressDialog = null;
					}
				}

				@Override
				public void onFailed(String errorCode, String errorMessage) {
					Toast.makeText(mActivity, "获取失败",
							Toast.LENGTH_SHORT).show();
					if (mProgressDialog != null) {
						mProgressDialog.dismiss();
						mProgressDialog = null;
					}
				}
			});
		} catch (RennException e1) {
			e1.printStackTrace();
		}
	}
	public void shareStatus(View v){
		rennClient.login(mActivity);
		if (!rennClient.isLogin()) {
			return;
		}
		String content = ((TextView) v).getText().toString();
		ShareStatusParam shareStatusParam = new ShareStatusParam();
		shareStatusParam.setContent(content);
		shareStatusParam.setOwnerId(391972133L);
		shareStatusParam.setStatusId(0L);

		if (mProgressDialog == null) {
			mProgressDialog = new ProgressDialog(mActivity);
			mProgressDialog.setCancelable(true);
			mProgressDialog.setTitle("请等待");
			mProgressDialog.setIcon(android.R.drawable.ic_dialog_info);
			mProgressDialog.setMessage("正在分享状态");
			mProgressDialog.show();
		}
		try {
			rennClient.getRennService().sendAsynRequest(shareStatusParam,
					new CallBack() {

						@Override
						public void onSuccess(RennResponse response) {
							Toast.makeText(mActivity, "状态分享成功",
									Toast.LENGTH_SHORT).show();
							if (mProgressDialog != null) {
								mProgressDialog.dismiss();
								mProgressDialog = null;
							}
						}

						@Override
						public void onFailed(String errorCode,
								String errorMessage) {
							Toast.makeText(mActivity, "状态分享失败",
									Toast.LENGTH_SHORT).show();
							if (mProgressDialog != null) {
								mProgressDialog.dismiss();
								mProgressDialog = null;
							}
						}
					});
		} catch (RennException e) {
			e.printStackTrace();
		}
	}
}
