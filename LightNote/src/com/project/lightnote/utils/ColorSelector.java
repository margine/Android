package com.project.lightnote.utils;

import java.util.Random;


public class ColorSelector {
	
	public static String getRandomColor(){
		String[] colors = new String[]{"#8ecad7","#FFB5C5","#FFF68F","#76cd98","#9F79EE"};
		int index = new Random().nextInt(5);
		System.err.println(index);
		return colors[index];
	}

}
