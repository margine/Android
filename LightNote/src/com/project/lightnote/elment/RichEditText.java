package com.project.lightnote.elment;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.widget.EditText;

public class RichEditText extends EditText{

	public RichEditText(Context context){
		super(context);
	}
	
	public RichEditText(Context context, AttributeSet attrs){
		super(context, attrs);
	}
	
	public void insertDrawable(String pathName){
		String tempString = "one";
		final SpannableString ss = new SpannableString(tempString);
		Drawable drawable = Drawable.createFromPath(pathName);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
		ImageSpan span = new ImageSpan(drawable, DynamicDrawableSpan.ALIGN_BASELINE);
		ss.setSpan(span, 0, 3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		append(ss);
	}
	
	
	public void insertIntoEditText(SpannableString ss){
		
	}

}
