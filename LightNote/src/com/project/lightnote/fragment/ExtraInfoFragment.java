package com.project.lightnote.fragment;

import com.project.lightnote.R;
import com.project.lightnote.activity.MainActivity;
import com.project.lightnote.utils.KeyHelpers;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ExtraInfoFragment extends Fragment{
	
	public ExtraInfoFragment(){
	}
	
	public static Fragment newInstance(int sectionNumber) {
		ExtraInfoFragment fragment = new ExtraInfoFragment();
		Bundle args = new Bundle();
		args.putInt(KeyHelpers.ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View textView = inflater.inflate(R.layout.fragment_extra_info, container, false);
		return textView;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((MainActivity) activity).onSectionAttached(getArguments().getInt(
				KeyHelpers.ARG_SECTION_NUMBER));
		
	}
}
