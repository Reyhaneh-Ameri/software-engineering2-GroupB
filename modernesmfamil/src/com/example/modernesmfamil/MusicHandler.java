package com.example.esmfamil;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

/**
 * All the functions for playing music in the background of the application
 * is handled through this class.
 * @author Amin Fallahi, Mahsa Asadi
 *
 */
public class MusicHandler {
	MediaPlayer player;
	public static int appMusic=1;
	/**
	 * constructor gets a context which is an activity and sets the background
	 * music.
	 * @param con
	 * @author Amin Fallahi, Mahsa Asadi
	 */
	public MusicHandler(Context con) {
		player=MediaPlayer.create(con, R.raw.bground);
		player.setLooping(true);
		player.setVolume(100, 100);
	}
	/**
	 * this method sets the music on and starts playing it
	 * @author Mahsa Asadi, Amin Fallahi
	 */
	public void setMusicOn(){
		player.start();
		appMusic=1;

	}
	/**
	 * this method sets the music off and pauses it
	 * @author Mahsa Asadi, Amin Fallahi
	 */    
	public void setMusicOff(){
		player.pause();
		appMusic=0;
	}

}
