package com.iust.fandogh.controllers;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.provider.ContactsContract.CommonDataKinds.Nickname;
import android.util.Log;

import com.iust.fandogh.GameActivity;
import com.iust.fandogh.JoinActivity;
import com.iust.fandogh.MainActivity;
import com.iust.fandogh.ServerActivity;
import com.iust.fandogh.entity.Player;

public class GameController {
	public static final int MODE_KEYBOARD = 1;
	public static final int MODE_KEYBOARD_1 = 11;
	public static final int MODE_KEYBOARD_2 = 12;
	public static final int MODE_START_CHARACTER = 2;
	public static final int MODE_END_CHARACTER = 3;
	public static final int MODE_ALPHABETS = 4;
	public static final int MODE_ALPHABETS_1 = 41;
	public static final int MODE_ALPHABETS_2 = 42;
	
	public static final int STATE_INIT = -1;
	public static final int STATE_MAINGAME = 1;
	public static final int STATE_SCORE = 2;
	public static final int STATE_RANKING = 3;
	
	public static GameController gc = null;
	
	int round = 0;
	int state = STATE_INIT;
	ArrayList<Player> players = new ArrayList<Player>();
	HashMap<Integer, Integer> modes = new HashMap<Integer, Integer>();
	
	public GameController(String nickname) {
		int tmpID = this.addPlayer();
		players.get(0).setNickname(nickname);
		
		GameController.gc = this;
	}
	
	public Player getPlayer(int ID) {
		for (Player p : players)
			if(p.getID()==ID)
				return p;
		
		return null;
	}
	
	public String getPlayersList() {
		String ans = "";
		for (Player p : players)
			if(p.getNickname()!=null)
				ans+=p.getNickname()+",";
		return ans;
	}
	
	public int addPlayer() {
		Player tmpPlayer = new Player();
		players.add(tmpPlayer);
		return tmpPlayer.getID();
	}
	public void removePlayer(int ID) {
		for (Player p : players)
			if(p.getID()==ID)
				players.remove(p);
	}
	
	public void startGame(HashMap<Integer, Integer> modes, int rounds) {
		this.round = rounds;
		this.modes = modes;
		state = STATE_MAINGAME;
		
		
    	ServerNetworkController.snc.stopGettingConnection();
    	ServerNetworkController.snc.sendStartRoundMSG(modes, rounds);
	}
	
	HashMap<String, ArrayList<String>> allFields = new HashMap<String, ArrayList<String>>();
	public void endGame(String name, ArrayList<String> fields) {
		Log.d(MainActivity.tag, name);
		allFields.put(name, fields);
		if(allFields.keySet().size()==players.size()) {
			ServerNetworkController.snc.sendGoResultPage();
			((GameActivity)ServerNetworkController.snc.activity).endGame();
		}
	}
	
	public String getAllFields() {
		String ret = "";
		for (String key: allFields.keySet()) {
			ret+= key+",";
			for (String f : allFields.get(key)) {
				ret+= f+",";
			}
			ret+="#";
		}
		return ret;
	}
}
