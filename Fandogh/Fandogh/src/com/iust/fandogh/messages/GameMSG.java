package com.iust.fandogh.messages;

import java.io.Serializable;
import java.util.HashMap;

public class GameMSG implements Serializable{
	public static final int START_GAME = 21;
	public static final int END_GAME = 22;
	public static final int END_GAME_REQUEST = 23;
	public static final int END_GAME_GET_FIELDS = 24;
	public static final int END_GAME_GIVE_FIELDS = 25;
	public static final int ALL_FIELDS_REQUEST = 26;
	public static final int ALL_FIELDS_RESPONSE = 27;
	
	int type = -1;
	int rounds = -1;
	HashMap<Integer, Integer> modes = null;
	
	public void setType(int type) {
		this.type = type;
	}
	public int getType() {
		return type;
	}
	
	public void setRounds(int rounds) {
		this.rounds = rounds;
	}
	public int getRounds() {
		return rounds;
	}
	
	public void setModes(HashMap<Integer, Integer> modes) {
		this.modes = modes;
	}
	public HashMap<Integer, Integer> getModes() {
		return modes;
	}
}
