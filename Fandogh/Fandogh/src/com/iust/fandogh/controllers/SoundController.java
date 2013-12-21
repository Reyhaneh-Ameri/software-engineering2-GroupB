package com.iust.fandogh.controllers;

import com.iust.fandogh.R;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * 
 * @author Mahsa Asadi, Amin Fallahi
 * This class handles sounds which are played during the game. different
 * functions are implemented for playing different kinds of sounds.
 *
 */
public class SoundController {
	public static SoundController sc = null;
	
	public static int appSound=1;
	MediaPlayer player;
	public int sound;

/*	public SoundHandler(Context con) {
		player=MediaPlayer.create(con, R.raw.win);
		player.setLooping(false);
		player.setVolume(100, 100);
	}*/
	/**
	 * @author Mahsa Asadi, Amin Fallahi
	 * This plays a sound needed for being played when a user has won an instance
	 * of the game.
	 * @param con
	 */
	public void playWin(Context con) {
		player=MediaPlayer.create(con, R.raw.win);
		player.setLooping(false);
		player.setVolume(100, 100);
		player.start();
	}
}
