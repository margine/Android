package com.project.lightnote.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;



import com.project.lightnote.elment.Note;
import com.project.lightnote.elment.NoteType;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

public class FileHelper {

	private ArrayList<Note> mNotes = new ArrayList<Note>();

	private String passwordFileName = "psw.txt";
	private final static int COLOR_INDEX = 0;
	private final static int COLOR_TAIL = 7;
	private final static int TYPE_INDEX = 7;
	private final static int TYPE_TAIL = 10;
	private final static int TIME_INDEX = 10;
	private final static int TIME_TAIL = 22;

	public ArrayList<Note> getNotes() {
		String sdCardPath = getSdCardPath();
		if (sdCardPath == null) {
			return mNotes;
		}
		File files = new File(sdCardPath);

		File[] fileList = files.listFiles();

		if (fileList == null) {
			return null;
		}
		int count = 0;
		for (int i = fileList.length - 1; i >= 0; i--) {
			String fileName = fileList[i].getName();
			if (fileName.contains("psw.txt")) {
				continue;
			}
			System.err.println(fileName);
			HashMap<String, Object> hashMap = parseFileName(fileName);
			
			Note note = new Note((String) hashMap.get(KeyHelpers.KEY_ID),
					fileName, (NoteType)hashMap.get(KeyHelpers.TYPE),
					hashMap.get(KeyHelpers.KEY_CONTENT),
					(String) hashMap.get(KeyHelpers.KEY_BACKGROUND));
			mNotes.add(count++, note);
		}
		return mNotes;
	}

	public boolean isSetPassword() {
		File file = new File(getSdCardPath(), passwordFileName);
		if (file.exists() && !getTXTContent(passwordFileName).equals("")) {
			return true;
		}
		return false;
	}

	public void savePassword(String password) {
		writeToSDCard(password, passwordFileName);

	}

	public String getPassword() {
		return getTXTContent(passwordFileName);
	}

	public static String getCurrentTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
		Date currentDate = new Date(System.currentTimeMillis());
		String time = formatter.format(currentDate);
		return time;
	}

	public static String getTimeString(String fileName) {
		String time = fileName.substring(TIME_INDEX, fileName.indexOf("."));
		return time.substring(0, 4) + "年" + time.substring(4, 6) + "月"
				+ time.substring(6, 8) + "日" + time.substring(8, 10) + "时";
	}

	public void writeToSDCard(String content, String fileName) {
		String sdCardPath = getSdCardPath();
		if (sdCardPath == null) {
			return;
		}
		File file = new File(sdCardPath, fileName);
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream outputStream = new FileOutputStream(sdCardPath
					+ fileName);
			outputStream.write(content.getBytes());
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void deleteNoteViaPath(String filePath) {
		 File file = new File(filePath);
		 file.delete();

//		File file2 = new File(getSdCardPath());
//		if (file2.exists() && file2.isDirectory()) {
//			File[] files = file2.listFiles();
//			for (int i = 0; i < files.length; i++) {
//				files[i].delete();
//			}
//		}
	}
	
	public void deleteNoteViaName(String fileName){
		if (getSdCardPath() == null) {
			return;
		}
		File file = new File(getSdCardPath() + fileName);
		file.delete();
	}

	public void addOneImage(Bitmap bitmap, String imgFileName) {
		File file = new File(getSdCardPath(), imgFileName);
		if (file.exists()) {
			file.delete();
		}
		try {
			FileOutputStream out = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addVoice(InputStream input, String voiceFileName){
		File file = new File(getSdCardPath(), voiceFileName);
		try {
			OutputStream os = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = input.read(buffer, 0, 8192)) != -1) {
			os.write(buffer, 0, bytesRead);
			}
			os.close();
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public void addOneAttach(int id, String backgroud) {

	}

	public void deleteOneNote(int id) {

	}

	public void modifyOneImage(int id, Bitmap bitmap, String backgroud) {

	}

	public String getSdCardPath() {
		String sdCardPath = null;
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			File sdCardDir = Environment.getExternalStorageDirectory();
			sdCardPath = sdCardDir.getAbsolutePath() + "/notes/";
			File file = new File(sdCardPath);
			if (!file.exists()) {
				file.mkdir();
			}
		}
		return sdCardPath;
	}

	private HashMap<String, Object> parseFileName(String fileName) {
		HashMap<String, Object> hashMap = new HashMap<>();
		String colorString = fileName.substring(COLOR_INDEX, COLOR_TAIL);
		hashMap.put(KeyHelpers.KEY_BACKGROUND, colorString);

		String id = fileName.substring(TIME_INDEX, TIME_TAIL);
		hashMap.put(KeyHelpers.KEY_ID, id);

		String typeString = fileName.substring(TYPE_INDEX,TYPE_TAIL);
		NoteType type = null;
		switch (typeString) {
		case "TXT":
			type = NoteType.TEXT;
			hashMap.put("content", getTXTContent(fileName));
			break;
		case "IMG":
			type = NoteType.IMAGE;
			hashMap.put("content", getImgContent(fileName));
			break;
		case "AVI":
			type = NoteType.VOICE;
			hashMap.put("content", getVoiceContent(fileName));
			break;
		case "ATTACH":
			type = NoteType.APPENDIX;
			break;
		default:
			break;
		}
		hashMap.put(KeyHelpers.TYPE, type);

		return hashMap;
	}

	private String getTXTContent(String fileName) {
		try {
			FileInputStream inputStream = new FileInputStream(getSdCardPath()
					+ fileName);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					inputStream));
			StringBuffer sb = new StringBuffer("");
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private String getImgContent(String fileName){
		return getSdCardPath() + fileName;
	}
	
	private String getVoiceContent(String fileName){
		return getSdCardPath() + fileName;
	}
	
	public Bitmap getImage(String filePath){
		FileInputStream inputStream;
		Bitmap bitmap = null;
		try {
			inputStream = new FileInputStream(filePath);
			bitmap = BitmapFactory.decodeStream(inputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return bitmap;
	}
}
