package com.iust.fandogh.controllers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import com.iust.fandogh.GameActivity;
import com.iust.fandogh.JoinActivity;
import com.iust.fandogh.MainActivity;
import com.iust.fandogh.ResultActivity;
import com.iust.fandogh.entity.Player;
import com.iust.fandogh.messages.GameMSG;
import com.iust.fandogh.messages.PlayerInitializeMSG;

import android.R.integer;
import android.R.string;
import android.app.Activity;
import android.provider.ContactsContract.CommonDataKinds.Nickname;
import android.util.Log;

/**
 * class for join to server and creating sockets
 * @author Farhad hosseinkhani,reyhane ameri
 *
 */
public class ClientNetworkController {
	public static ClientNetworkController cnc = null;
	
	Activity activity;
	
	private Socket sock;
	ObjectInputStream ois;
	ObjectOutputStream oos;

	public String nickname;
		
	public ClientNetworkController(String IP, String nickname) {
		this.nickname = nickname;
		
		Thread join = new Thread(new JoinToNetwork(IP, ServerNetworkController.port));
		join.start();
		
		ClientNetworkController.cnc = this;
	}
	
	public void setActivity(Activity activity) {
		this.activity = activity;
	}
		
	/**
	 * runnable for join to server
	 * @author FERi
	 */
	class JoinToNetwork implements Runnable {
		String IP;
		int Port;
		
		public JoinToNetwork(String IP, int Port) {
			this.IP = IP;
			this.Port = Port;
		}
		
		@Override
		public void run() {
			try {
				Log.d(MainActivity.tag, "Joining to "+IP+":"+Integer.toString(Port));
				
				sock = new Socket(IP,Port);
				oos = new ObjectOutputStream(sock.getOutputStream());
				oos.flush();
				ois = new ObjectInputStream(sock.getInputStream());
				
				Log.d(MainActivity.tag ,"Networking established");
				
				//BIG problem boy!!! Its not thread safe! :X IDK!!!?
				Thread MSGparser = new Thread(new IncommingMessagesParser());
				MSGparser.start();
				
//				Initialize user in server
				Log.d(MainActivity.tag ,"Send Initialize message");
				oos.writeObject(Integer.valueOf(PlayerInitializeMSG.PLAYER_ENTER)+ 
						":"+ nickname);
				Log.d(MainActivity.tag ,"Complete initialization");
			} catch(Exception ex) {
				Log.d(MainActivity.tag , "Problem in connecting to server");
				ex.printStackTrace();
			}
		}
		
		/**
		 * thread for parsing incomming maessages
		 * @author Farhad hosseinkhani,reyhane ameri
		 */
		class IncommingMessagesParser implements Runnable {
			@Override
			public void run() {
				try {
					Object msg;
					while((msg = ois.readObject()) != null) {
						if(msg instanceof String) {
							final String[] message = ((String)msg).split(":");
							switch (Integer.valueOf(message[0])) {
							case PlayerInitializeMSG.PLAYER_CHANGE:
								((JoinActivity)activity).runOnUiThread(new Runnable() {
									@Override
									public void run() {
										((JoinActivity)activity).refreshPlayers(message[1].split(","));
									}
								});
								break;
							case GameMSG.END_GAME_GET_FIELDS:
								sendFields();
								break;
							case GameMSG.END_GAME:
								((GameActivity)activity).endGame();
								break;
							case GameMSG.ALL_FIELDS_RESPONSE:
								((ResultActivity)activity).runOnUiThread(new Runnable() {
									@Override
									public void run() {
										((ResultActivity)activity).importScores(message[1]);
									}
								});
								break;
							default:
								break;
							}
						} else if(msg instanceof GameMSG) {
							final GameMSG tmpGameMSG = (GameMSG)msg;
							switch (((GameMSG)msg).getType()) {
							case GameMSG.START_GAME:
								((JoinActivity)activity).runOnUiThread(new Runnable() {
									@Override
									public void run() {
										((JoinActivity)activity).startGame(tmpGameMSG.getModes(), tmpGameMSG.getRounds());
									}
								});
								break;
							default:
								Log.d(MainActivity.tag, "Problem in messages!!!");
								break;
							}
						}//else if(msg instanceof GameInitializeMSG) {
//							final GameInitializeMSG finalMSG = (GameInitializeMSG)msg;
//							switch (((GameInitializeMSG)msg).getType()) {
//							case GameInitializeMSG.CLIENT_UPDATE:
//								ja.runOnUiThread(new Runnable() {
//									@Override
//									public void run() {
//										((JoinActivity)ja).importClients((finalMSG).getClients());
//									}
//								});
//								break;
//							case GameInitializeMSG.GAME_STARTED:
//								ja.runOnUiThread(new Runnable() {
//									@Override
//									public void run() {
//										((JoinActivity)ja).startGame();
//									}
//								});
//								break;
//							default:
//								break;
//							}
//						} else if(msg instanceof GameMSG) {
//							switch (((GameMSG)msg).getType()) {
//							case GameMSG.FIELDS_REQUEST: 
//								Log.d("asdad", "field request amad");
//								HashMap<String, ArrayList<String>> fields = new HashMap<String, ArrayList<String>>();
//								fields.put(nickname, ((MainGameActivity)ja).getFields());
//								Log.d("asdasd", Integer.toString(((MainGameActivity)ja).getFields().size()));
//								
//								oos.writeObject(new GameMSG(GameMSG.FIELDS_RESPONSE, fields));
//								break;
//							case GameMSG.END_GAME:
//								Log.d("asdad", "ennnnnnnnnnnnd");
//								allFields = ((GameMSG)msg).getAllFields();
//								Log.d("asd", Integer.toString(allFields.size()));
//								((MainGameActivity)ja).endGame();
//								break;
//							default:
//								break;
//							}
//						}
					}
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}
	
	public void exitGame() {
		Log.d(MainActivity.tag, "cheraaaaa exit shod ind baz?");
		try {
			oos.writeObject(Integer.valueOf(PlayerInitializeMSG.PLAYER_EXIT)+ 
					":"+ nickname);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendEndGameRequest() {
		Log.d(MainActivity.tag, "send end game request");
		try {
			oos.writeObject(Integer.toString(GameMSG.END_GAME_REQUEST));
		} catch (IOException e) { 
			e.printStackTrace();
		}
	}
	
	public void sendFields() {
		Log.d(MainActivity.tag, "send fi elds");
		String ret = Integer.toString(GameMSG.END_GAME_GIVE_FIELDS)+":"+nickname+"#";
		for (String f: ((GameActivity)activity).getFields())
			ret += f + ",";

		try {
			oos.writeObject(ret);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void requestAllFields() {
		try {
			oos.writeObject(Integer.toString(GameMSG.ALL_FIELDS_REQUEST));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
