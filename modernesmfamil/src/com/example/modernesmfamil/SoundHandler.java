package com.example.esmfamil;

import android.content.Context;
import android.media.MediaPlayer;

public class SoundHandler {
	public static int appSound=1;
    MediaPlayer player;
	public int sound;

/*	public SoundHandler(Context con) {
		player=MediaPlayer.create(con, R.raw.win);
		player.setLooping(false);
		player.setVolume(100, 100);
	}*/
	public void playWin(Context con) {
		player=MediaPlayer.create(con, R.raw.win);
		player.setLooping(false);
		player.setVolume(100, 100);
		player.start();
	}
}
