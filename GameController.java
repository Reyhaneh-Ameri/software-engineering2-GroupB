package com.iust.fandogh.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

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
	
	public static int round = 0;
	char lastChar;
	char firstChar;
	int state = STATE_INIT;
	HashMap<String,ArrayList<String>> allFields=new HashMap<String, ArrayList<String>>();
	ArrayList<Player> players = new ArrayList<Player>();
	HashMap<Integer, Integer> modes = new HashMap<Integer, Integer>();
	HashMap<String, ArrayList<String>> playerFields = new HashMap<String, ArrayList<String>>();
	ArrayList<String> fields;

	
	
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
	
	public void setRound(int r) {
		this.round=r;
		
	}
	public void setMode(HashMap<Integer, Integer> modes) {
		this.modes=modes;
		
	}
	public void setFLChars (char lastChar,char firstChar) {
		this.firstChar=firstChar;
		this.lastChar=lastChar;
	}
	public void startGame() {
		
			//state = STATE_MAINGAME;
			

	    	ServerNetworkController.snc.stopGettingConnection();
	    	ServerNetworkController.snc.sendStartRoundMSG(modes, this.round);
	    
			
	
		
		
		
	}
	

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
	
	public ArrayList<String> wordsNotInsideOfDB(HashMap<String, ArrayList<String>> playerFields) {
		ArrayList<String> newWords=new ArrayList<String>();
		Set<String> Names=playerFields.keySet();
		String[] names=Names.toArray(new String[Names.size()]);
		for (int j=0; j<Names.size(); j++){
			String name=names[j];
			ArrayList<String> s=playerFields.get(name);
			
			
			//check if first letter is correct
			for (int i=0; i<s.size(); i++){
				String f=s.get(i);
				
				//check if shit is in db
				if (!DatabaseController.dbc.isItThere(i+1, f)){
					newWords.add(f);
				}
				}
		}
		return newWords;
		
		
	}
	HashMap <String, ArrayList<int[]>> calculateScores(HashMap<String, ArrayList<String>> playerFields,HashMap<String, ArrayList<String>> helperWords){
		
		HashMap<String, ArrayList<int[]>> scores=new HashMap<String, ArrayList<int[]>>();
		Set<String> Names=playerFields.keySet();
		String[] names=Names.toArray(new String[Names.size()]);
		for (int j=0; j<Names.size(); j++){
			String name=names[j];
			ArrayList<String> s=playerFields.get(name);
			int[] score;
			score=new int[s.size()];
			//check if first letter is correct
			for (int i=0; i<s.size(); i++){
				String f=s.get(i);
				if (!f.startsWith(firstChar+"")|| !f.endsWith(lastChar+""))
					score[i]=0;
				//check if shit is in db
				else if (DatabaseController.dbc.isItThere(i+1, f)){
					score[i]=20;
					//check if anyone has written the same shit
					int dupes=0;
					for (int k=0; k<Names.size(); k++){
						if (k==j)
							continue;
						String nameo=names[k];
						ArrayList<String> so=playerFields.get(name);
						String fo=s.get(i);
						if (f==fo)
							dupes++;
					}
					
					if (dupes!=0)
						score[i]=20/(dupes+1);
				}
				else if(helperWords.size()>0){
					ArrayList<String> n=helperWords.get(i);
					for(int o=0;o<n.size();o++){
						if(f.equals(n.get(o))){
							score[i]=20;
						}
					}
				}
			}
			ArrayList<int[]> t=new ArrayList<int[]>(Arrays.asList(score));
			scores.put(name, t);
		}
		return scores;
	}
		
	
}
