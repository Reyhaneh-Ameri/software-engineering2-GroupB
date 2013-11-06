package com.example.esmfamil;
import android.widget.ImageView;

import com.example.esmfamil.FileHandler;

/**
 * Control Class for settings
 * @author Amin Fallahi, Mahsa Asadi
 */
public class SettingsHandler {
	private static int appSound=1;
	private static int appVibra=1;
	private static int appMusic=1;
	private static ImageView avatarImg;
	
	/**
	 * Constructor reads settings from files using FileHandler class 
	 * and sets private variables.
	 * @author Amin Fallahi, Mahsa Asadi
	 */
	public SettingsHandler() {
		appSound=FileHandler.readSettingsFileSound();
		appVibra=FileHandler.readSettingsFileVibra();
		appMusic=FileHandler.readSettingsFileMusic();
	}
	/**
	 * Save settings data into files using the FileHandler class
	 * @author Amin Fallahi, Mahsa Asadi
	 */
	public static void writeFiles(){
		FileHandler.writeSettingsFile(appSound, appVibra, appMusic);
	}
	
	/**
	 * used for keeping the avatar image data in a static variable
	 * in order for it to be used in different activities.
	 * @param imgv which is the image passed to the method
	 * @author Amin Fallahi, Mahsa Asadi
	 */
	public static void setAvatarImg(ImageView imgv){
		avatarImg=imgv;
	}
	/**
	 * returns the status of the current sound status
	 * @return appSound
	 * @author Amin Fallahi, Mahsa Asadi
	 */
	public int getSoundStatus(){
		return appSound;
	}
	/**
	 * returns the status of the current music settings
	 * @return appSound
	 * @author Amin Fallahi, Mahsa Asadi
	 */
	public int getMusicStatus(){
		return appMusic;
	}
	/**
	 * returns the status of the current vibration settings
	 * @return appSound
	 * @author Amin Fallahi, Mahsa Asadi
	 */
	public int getVibraStatus(){
		return appVibra;
	}
	/**
	 * sets the status of the current vibration settings
	 * @param appSound
	 * @author Amin Fallahi, Mahsa Asadi
	 */
	public void setVibraStatus(int s){
		appVibra=s;		
	}
	/**
	 * sets the status of the current music settings
	 * @param appMusic
	 * @author Amin Fallahi, Mahsa Asadi
	 */
	public void setMusicStatus(int s){
		appMusic=s;		
	}
	/**
	 * sets the status of the current sound settings
	 * @param appSound
	 * @author Amin Fallahi, Mahsa Asadi
	 */
	public void setSoundStatus(int s){
		appSound=s;		
	}

}
