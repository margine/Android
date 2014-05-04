package com.project.lightnote.elment;


public class Note {
	
	private Object mContent;
	private String mNoteFileName;
	private String mTime;
	private String mBackground;
	private NoteType mType;
	
	public Note(String pTime, String pNoteFileName,NoteType pType, Object pContent, String pBackground){
		mTime = pTime;
		mNoteFileName = pNoteFileName;
		mType = pType;
		mContent = pContent;
		mBackground = pBackground;
	}
	
	public void setContent(String newContent){
		mContent = newContent;
	}
	
	public String getTime(){
		return mTime;
	}
	
	public NoteType getType(){
		return mType;
	}
	
	public Object getContent(){
		return mContent;
	}
	
	public String getBackground(){
		return mBackground;
	}
	
	public String getFileName(){
		return mNoteFileName;
	}
	
}
