package com.iust.fandogh.entity;

public class Player {
	public static int ID_COUNTER = 0;
	
	int ID;
	String nickname = null;
	int chars[];
	public Player() {
		this.ID = ID_COUNTER;
		ID_COUNTER++;
		chars=new int[32];
		for(int i=0;i<32;i++){
			chars[i]=10;
		}
	}
	
	//for initializing
	public void setCharsInitCount(int a) {
		for(int i=0;i<32;i++){
			chars[i]=a;
		}
		
	}
	
	
	//bara neshan dadane tedade har harf
	public int getCharCount(char c) {
		int index=0;
		for(int i=0;i<32;i++){
			if(AlphabetView.AlphabetChars[i]==c) index=i;
		}
		return chars[index];
	}
	
	//bara check kardane inke az ye harfi lazeme kharid konim ya na
	public boolean haveChar(char c) {
		int index=0;
		for(int i=0;i<32;i++){
			if(AlphabetView.AlphabetChars[i]==c) index=i;
		}
		if(chars[index]>0) return true;
		else return false;
	}
	
	// vaghti ro keyboard har harfi zade mishe in function call mishavad
	public void decreaseCharCount(char c) {
		int index=0;
		for(int i=0;i<32;i++){
			if(AlphabetView.AlphabetChars[i]==c) index=i;
		}
		chars[index]--;
		
		
	}
	
	//for buy function
	public void updateCharCount(char c,int n) {
		int index=0;
		for(int i=0;i<32;i++){
			if(AlphabetView.AlphabetChars[i]==c) index=i;
		}
		chars[index]+=n;
		
		
	}
	//kheili mohem niis
	public int[] getAllCharsCount() {
		return chars;
		
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getNickname() {
		return nickname;
	}
	
	public int getID() {
		return ID;
	}
}
