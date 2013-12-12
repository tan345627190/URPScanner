package com.gs.main;

import java.text.DecimalFormat;

public class IDBuilder {
	private static int i = 1;
	public static String get(){
		return new DecimalFormat("00").format(i++);
	}
	public static void clear(){
		i = 1;
	}
}
