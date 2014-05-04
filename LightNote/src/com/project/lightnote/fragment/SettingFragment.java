package com.project.lightnote.fragment;


import com.project.lightnote.R;
import com.project.lightnote.activity.MainActivity;
import com.project.lightnote.activity.SetPasswordActivity;
import com.project.lightnote.utils.FileHelper;
import com.project.lightnote.utils.KeyHelpers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SettingFragment extends Fragment {
	
	private FileHelper mHelper;

	public SettingFragment() {
	}

	public static SettingFragment newInstance(int sectionNumber) {
		SettingFragment fragment = new SettingFragment();
		Bundle args = new Bundle();
		args.putInt(KeyHelpers.ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ListView rootView = (ListView) inflater.inflate(
				R.layout.fragment_setting, container, false);
		
		rootView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), SetPasswordActivity.class);
				startActivity(intent);
			}
		});
		mHelper = new FileHelper();
		if (mHelper.isSetPassword()) {
			rootView.setAdapter(new ArrayAdapter<String>(getActivity(),
					R.layout.main_list_item, new String[] {  getString(R.string.editPsw)}));
		}else {
			
			rootView.setAdapter(new ArrayAdapter<String>(getActivity(),
					R.layout.main_list_item, new String[] { getString(R.string.setPsw)}));
		}
		return rootView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((MainActivity) activity).onSectionAttached(getArguments().getInt(
				KeyHelpers.ARG_SECTION_NUMBER));

	}

}
