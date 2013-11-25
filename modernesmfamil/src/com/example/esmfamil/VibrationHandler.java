package com.example.esmfamil;

import android.app.Activity;
import android.content.Context;
import android.os.Vibrator;
/**
 * @author Mahsa Asadi, Amin Fallahi
 * Different vibration effects are implemented to be used in the activities
 * in this class. All the methods are static and public.
 *
 */
public class VibrationHandler{
	private static int appVibra=1;
	/**
	 * @author Mahsa Asadi, Amin Fallahi
	 * Activates a short vibration effect
	 * @param context
	 */
	public static void miniVibra(Context context){
		Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		v.vibrate(500);
	}
	/**
	 * @author Mahsa Asadi, Amin Fallahi
	 * Activates a normal vibration effect
	 * @param context
	 */
	public static void mediumVibra(Context context){
		Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		v.vibrate(1000);
	}
	/**
	 * @author Mahsa Asadi, Amin Fallahi
	 * Activates a long vibration effect
	 * @param context
	 */
	public static void longVibra(Context context){
		Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		v.vibrate(2000);
	}
}
