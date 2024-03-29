package com.hjwordsgame.util;

import android.util.Log;

public class MyLogger {
	private static int VERBOSE = 5;
	private static int DEBUG = 4;
	private static int INFO = 3;
	private static int WARN = 2;
	private static int ERROR = 1;
	
	private static int LOG_LEVEL = 4;
	
	
	public static void v(String tag, String msg){
		if(LOG_LEVEL>=VERBOSE){
			Log.v(tag, msg);
		}
	}
	public static void d(String tag, String msg){
		if(LOG_LEVEL>=DEBUG){
			Log.d(tag, msg);
		}
	}
	public static void i(String tag, String msg){
		if(LOG_LEVEL>=INFO){
			Log.i(tag, msg);
		}
	}
	public static void w(String tag, String msg){
		if(LOG_LEVEL>=WARN){
			Log.w(tag, msg);
		}
	}
	public static void e(String tag, String msg){
		if(LOG_LEVEL>=ERROR){
			Log.e(tag, msg);
		}
	}
}
