package com.example.esmfamil;

import android.app.Activity;
import android.content.Context;
import android.os.Vibrator;

public class VibrationHandler{
	private static int appVibra=1;
	
	public static void miniVibra(Context context){
		Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		v.vibrate(500);
	}
	public static void mediumVibra(Context context){
		Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		v.vibrate(1000);
	}
	public static void longVibra(Context context){
		Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		v.vibrate(2000);
	}
}
