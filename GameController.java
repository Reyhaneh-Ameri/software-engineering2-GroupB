package com.iust.fandogh.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

import android.R.integer;
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
	String[] fields;
	ArrayList<Player> players = new ArrayList<Player>();
	HashMap<Integer, Integer> modes = new HashMap<Integer, Integer>();
	//HashMap<String, ArrayList<String>> playerFields = new HashMap<String, ArrayList<String>>();
	//ArrayList<String> fields;

	
	
	public GameController() {
		
		
		GameController.gc = this;
	}
	
	public void setFieldsName(String[] fieldsName) {
		fields=new String[fieldsName.length];
		this.fields=fieldsName;
		
	}
	public String[] getFields(){
		return fields;
	}
	/*public void playerFieldsUpdate(String playerName,ArrayList<String> fields) {
		this.playerFields.put(playerName, fields);
		
	}*/
	
	
	
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
	
	public void setFLChars (Character lastChar,Character firstChar) {
		this.firstChar=firstChar;
		this.lastChar=lastChar;
	}
	
	public void startGame() {
		
			//state = STATE_MAINGAME;
			
			if(this.round>0){
	    	ServerNetworkController.snc.stopGettingConnection();
	    	ServerNetworkController.snc.sendStartRoundMSG(modes, this.round);
			}
			
	
		
		
		
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
	
	public HashMap<String, ArrayList<String>> usersNewWords(HashMap<String, ArrayList<String>> playerFields) {
		HashMap<String,ArrayList<String>> newWords=new HashMap<String, ArrayList<String>>();
		Set<String> Names=playerFields.keySet();
		String[] names=Names.toArray(new String[Names.size()]);
		int n=playerFields.get(players.get(0).nickname).size();
		
		
		
		for(int k=0;k<n;k++){
			newWords.put(fields[k],new ArrayList<String>());
		}
		
		for (int j=0; j<Names.size(); j++){
			ArrayList<String> s=playerFields.get(names[j]);
			
			
			
			ArrayList<String> fieldNewWords=new ArrayList<String>();
			for (int i=0; i<s.size(); i++){
				String f=s.get(i);
				if (f.startsWith(firstChar+"") && f.endsWith(lastChar+"")){
				
				//check if shit is in db
				int DBNum=0;
				switch(fields[i]){
					case "fname": DBNum=1;
					case "lname" : DBNum=2;
					case "fruit": DBNum=3;
					case "flower": DBNum=4;
					case "color": DBNum=5;
					case "city": DBNum=6;
					case "country": DBNum=7;
				}
						
						
				
				
				 if(!DatabaseController.dbc.isItThere(DBNum, f)){
					
					newWords.get(i).add(f);
					
				}
				
				}
			
		}
		}
		return newWords;
		
		
	}
	
	public void calculateScores(HashMap<Integer, ArrayList<String>> helperWords,String finisher){
		
		//HashMap<Player,Integer> scores=new HashMap<Player,Integer>();
		Set<String> Names=allFields.keySet();
		String[] names=Names.toArray(new String[Names.size()]);
		
		for (int j=0; j<Names.size(); j++){
			String name=names[j];
			ArrayList<String> s=allFields.get(name);
			int[] score=new int[s.size()];
			Player player=players.get(j);
			
			//check if first letter is correct
			for (int i=0; i<s.size(); i++){
				String f=s.get(i);
				if (!f.startsWith(firstChar+"")|| !f.endsWith(lastChar+"")){
					score[i]=0;
					continue;
				}
				//check if shit is in db
				int DBNum=0;
				switch(fields[i]){
					case "fname": DBNum=1;
					case "lname" : DBNum=2;
					case "fruit": DBNum=3;
					case "flower": DBNum=4;
					case "color": DBNum=5;
					case "city": DBNum=6;
					case "country": DBNum=7;
				}
						
					
				if (DatabaseController.dbc.isItThere(DBNum, f)){
					score[i]=20;
					//check if anyone has written the same shit
					int dupes=0;
					for (int k=0; k<Names.size(); k++){
						if (k==j)
							continue;
						String nameo=names[k];
						ArrayList<String> so=allFields.get(nameo);
						String fo=so.get(i);
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
			int playerScore=player.getScore();
			
			for(int h=0;h<s.size();h++){
				playerScore+=score[h];
			}
			player.setScore(playerScore);
			players.set(j,player);
			
			
			/*ArrayList<int[]> t=new ArrayList<int[]>(Arrays.asList(score));
			
			scores.put(name, t);*/
		}
		
	}
		
	
}
