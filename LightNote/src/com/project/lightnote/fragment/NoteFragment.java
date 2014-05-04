package com.project.lightnote.fragment;

import java.util.ArrayList;

import com.project.lightnote.R;
import com.project.lightnote.activity.MainActivity;
import com.project.lightnote.elment.Note;
import com.project.lightnote.elment.NoteType;
import com.project.lightnote.utils.ColorSelector;
import com.project.lightnote.utils.FileHelper;
import com.project.lightnote.utils.KeyHelpers;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils.TruncateAt;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NoteFragment extends Fragment {

	private OnNoteTextClickListener mClickListener;
	private OnImageClickListener mImageClickListener;
	private OnVoiceClickListener mVoiceClickListener;
	
	private ArrayList<Note> mNotes;
	private final int NOT_NOTE = 0;
	private final int IS_NOTE = 1;

	public static Fragment newInstance(int sectionNumber) {
		NoteFragment fragment = new NoteFragment();
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
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);

		FileHelper fileHelper = new FileHelper();
		LinearLayout firstLayout = (LinearLayout) rootView
				.findViewById(R.id.first_row);
		LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, (float) 1.27);
		mNotes = fileHelper.getNotes();

		if (mNotes.size() == 0) {
			Note tipNote = new Note("20140426093410",
					ColorSelector.getRandomColor() + "TXT20140426093410.txt",
					NoteType.TEXT, "点击左边的小图标，来创建你的第一个笔记吧",
					ColorSelector.getRandomColor());
			TextView firstView = (TextView) getView(rootView, tipNote, IS_NOTE);
			firstView.setLayoutParams(params);
			firstLayout.addView(firstView);
			return rootView;
		}
		
		int noteCount = mNotes.size();
		TextView textView = (TextView) getView(rootView, mNotes.get(0), IS_NOTE);
		textView.setLayoutParams(params);
		firstLayout.addView(textView);

		if (noteCount == 1) {
			return rootView;
		}
		for (int i = 1; i < noteCount; i += 2) {
			LinearLayout layout = (LinearLayout) rootView
					.findViewById(R.id.linear_layout);

			LinearLayout subLayout = new LinearLayout(rootView.getContext());

			subLayout.setOrientation(LinearLayout.HORIZONTAL);

			subLayout.addView(getView(rootView, mNotes.get(i), IS_NOTE));

			if (i + 1 < noteCount) {
				subLayout.addView(getView(rootView, mNotes.get(i+1), IS_NOTE));
			} else {
				Note lastNote = new Note("", "", NoteType.TEXT, "", "#ffffff");
				subLayout.addView(getView(rootView, lastNote, NOT_NOTE));
			}

			layout.addView(subLayout);
			container.invalidate();
		}
		return rootView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mClickListener = (OnNoteTextClickListener) activity;
		mImageClickListener = (OnImageClickListener)activity;
		mVoiceClickListener = (OnVoiceClickListener)activity;
		((MainActivity) activity).onSectionAttached(getArguments().getInt(
				KeyHelpers.ARG_SECTION_NUMBER));

	}
	
	private View getView(View rootView, Note note, int tag){
		if (note.getType() == NoteType.TEXT) {
			return getTextView(rootView, note, tag);
		}
		if (note.getType() == NoteType.IMAGE) {
			return getImgView(rootView, note, tag);
		}
		if (note.getType() == NoteType.VOICE) {
			return getVoiceView(rootView, note, tag);
		}
		return null;
	}

	private TextView getImgView(View rootView, Note note, int tag) {
		TextView tx = new TextView(rootView.getContext());
		
		tx.setBackgroundColor(Color.parseColor(note.getBackground()));
		tx.setClickable(true);
		tx.setEllipsize(TruncateAt.END);
		tx.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.LEFT);
		tx.setLineSpacing(4, (float) 1.20);
		tx.setLines(7);
		tx.setPadding(10, 10, 10, 10);
		tx.setSingleLine(false);
		tx.setTextSize(18);
		tx.setLongClickable(true);

		FileHelper helper = new FileHelper();
		
		Drawable drawable = new BitmapDrawable(helper.getImage((String)note.getContent()));
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
		tx.setBackground(drawable);
		tx.setTag(note.getContent());

		if (tag == IS_NOTE) {
			tx.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mImageClickListener.onImageClick(v);
				}
			});

			tx.setOnLongClickListener(new OnLongClickListener() {

				@Override
				public boolean onLongClick(View v) {
					return mImageClickListener.onImageLongClick(v);
				}
			});
		}
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, 1);
		lp.setMargins(0, 4, 5, 0);
		tx.setLayoutParams(lp);
		return tx;
	}
	
	private TextView getVoiceView(View rootView, Note note, int tag){
		TextView tx = new TextView(rootView.getContext());
		
		tx.setBackgroundColor(Color.parseColor(note.getBackground()));
		tx.setClickable(true);
		tx.setEllipsize(TruncateAt.END);
		tx.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.LEFT);
		tx.setLineSpacing(4, (float) 1.20);
		tx.setLines(7);
		tx.setPadding(10, 10, 10, 10);
		tx.setSingleLine(false);
		tx.setTextSize(18);
		tx.setLongClickable(true);

		
		Drawable drawable = getResources().getDrawable(R.drawable.voice);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
		tx.setBackground(drawable);
		tx.setTag(note.getContent());

		if (tag == IS_NOTE) {
			tx.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					 mVoiceClickListener.onVoiceClick(v);
				}
			});

			tx.setOnLongClickListener(new OnLongClickListener() {

				@Override
				public boolean onLongClick(View v) {
					return mVoiceClickListener.onVoiceLongClick(v);
				}
			});
		}
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, 1);
		lp.setMargins(0, 4, 5, 0);
		tx.setLayoutParams(lp);
		return tx;
	}

	private TextView getTextView(View rootView, Note note, int tag) {
		TextView tx = new TextView(rootView.getContext());
		
		tx.setBackgroundColor(Color.parseColor(note.getBackground()));
		tx.setClickable(true);
		tx.setEllipsize(TruncateAt.END);
		tx.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.LEFT);
		tx.setLineSpacing(4, (float) 1.20);
		tx.setLines(7);
		tx.setPadding(10, 10, 10, 10);
		tx.setSingleLine(false);
		tx.setTextSize(18);
		tx.setLongClickable(true);
		tx.setText((String) note.getContent());

		tx.setTag(note.getFileName());

		if (tag == IS_NOTE) {
			tx.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mClickListener.onNoteTextClick(v);
				}
			});

			tx.setOnLongClickListener(new OnLongClickListener() {

				@Override
				public boolean onLongClick(View v) {
					return mClickListener.onNoteTextLongClick(v);
				}
			});
		}
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, 1);
		lp.setMargins(0, 4, 5, 0);
		tx.setLayoutParams(lp);
		
		return tx;
	}

	public interface OnNoteTextClickListener {
		public void onNoteTextClick(View v);
		public boolean onNoteTextLongClick(View v);
	}
	
	public interface OnImageClickListener{
		public void onImageClick(View v);
		public boolean onImageLongClick(View v);
	}
	
	public interface OnVoiceClickListener{
		public void onVoiceClick(View v);
		public boolean onVoiceLongClick(View v);
	}
	
}
