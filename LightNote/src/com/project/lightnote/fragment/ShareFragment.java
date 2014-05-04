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
				R.layout.main_list_item, new String[] { "�����Ӧ�õ�����" }));

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
		oks.setText("���ɼ���������~");
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
		// ȥ��ע�ͣ�����༭ҳ����ʾΪDialogģʽ
		// oks.setDialogMode();

		// ȥ��ע�ͣ����Զ���Ȩʱ���Խ���SSO��ʽ
		// oks.disableSSOWhenAuthorize();

		// ȥ��ע�ͣ����ݷ���Ĳ��������ͨ��OneKeyShareCallback�ص�
		// oks.setCallback(new OneKeyShareCallback());

		// ȥ��ע�ͣ���ʾ�ھŹ�������Զ����ͼ��
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
			// ��������ӷ���ɹ��Ĵ������
		}

		@Override
		public void onError(Platform plat, int action, Throwable t) {
			t.printStackTrace();
			// ��������ӷ���ʧ�ܵĴ������
		}

		@Override
		public void onCancel(Platform plat, int action) {
			// ���������ȡ������Ĵ������
		}
	}
}
