package com.project.lightnote.fragment;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.renren.Renren;

import com.project.lightnote.R;
import com.project.lightnote.activity.MainActivity;
import com.project.lightnote.utils.KeyHelpers;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ShareFragment extends Fragment {


	public static Fragment newInstance(int sectionNumber) {
		ShareFragment fragment = new ShareFragment();
		Bundle args = new Bundle();
		args.putInt(KeyHelpers.ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ShareSDK.initSDK(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ListView rootView = (ListView) inflater.inflate(
				R.layout.fragment_share, container, false);
		rootView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				showShare(false, Renren.NAME);
			}
		});

		rootView.setAdapter(new ArrayAdapter<String>(getActivity(),
				R.layout.main_list_item, new String[] { "分享该应用到人人" }));

		return rootView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((MainActivity) activity).onSectionAttached(getArguments().getInt(
				KeyHelpers.ARG_SECTION_NUMBER));

	}

	private void showShare(boolean silent, String platform) {
		final OnekeyShare oks = new OnekeyShare();
		oks.setNotification(R.drawable.ic_launcher,
				this.getString(R.string.app_name));
		oks.setAddress("12345678901");
		oks.setText("轻巧记下生活点滴~");
		oks.setTitle(this.getString(R.string.app_name));
		oks.setTitleUrl("http://www.renren.com");
		oks.setUrl("http://www.renren.cn");
		oks.setComment(this.getString(R.string.share));
		oks.setVenueName("LightNote");
		oks.setVenueDescription("");
		oks.setLatitude(23.056081f);
		oks.setLongitude(113.385708f);
		oks.setSilent(silent);
		if (platform != null) {
			oks.setPlatform(platform);
		}
		// 去除注释，可令编辑页面显示为Dialog模式
		// oks.setDialogMode();

		// 去除注释，在自动授权时可以禁用SSO方式
		// oks.disableSSOWhenAuthorize();

		// 去除注释，则快捷分享的操作结果将通过OneKeyShareCallback回调
		// oks.setCallback(new OneKeyShareCallback());

		// 去除注释，演示在九宫格添加自定义的图标
		// Bitmap logo = BitmapFactory.decodeResource(getResources(),
		// R.drawable.ic_launcher);
		// String label = getString(R.string.app_name);
		// OnClickListener listener = new OnClickListener() {
		// public void onClick(View v) {
		// String text = "Customer Logo -- ShareSDK "
		// + ShareSDK.getSDKVersionName();
		// Toast.makeText(ShareActivity.this, text, Toast.LENGTH_SHORT)
		// .show();
		// oks.finish();
		// }
		// };
		// oks.setCustomerLogo(logo, label, listener);

		oks.show(getActivity());
	}

	class OneKeyShareCallback implements PlatformActionListener {

		@Override
		public void onComplete(Platform plat, int action,
				HashMap<String, Object> res) {
			System.out.println(res.toString());
			// 在这里添加分享成功的处理代码
		}

		@Override
		public void onError(Platform plat, int action, Throwable t) {
			t.printStackTrace();
			// 在这里添加分享失败的处理代码
		}

		@Override
		public void onCancel(Platform plat, int action) {
			// 在这里添加取消分享的处理代码
		}
	}
}
