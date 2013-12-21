package com.iust.fandogh.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.http.conn.util.InetAddressUtils;

import android.app.Activity;
import android.util.Log;

import com.iust.fandogh.GameActivity;
import com.iust.fandogh.MainActivity;
import com.iust.fandogh.ServerActivity;
import com.iust.fandogh.entity.Client;
import com.iust.fandogh.messages.*;

/**
 * Controls and contains all network connections of Server
 * @author FERi
 */
public class ServerNetworkController {
	public static ServerNetworkController snc;
	public final static int port = 5000;
	
	HashMap<Integer, Client> clients = new HashMap<Integer, Client>();
	boolean getNetworkConnection = true;
	Activity activity;
	
	/**
	 * Set static variable and start to getting connections
	 * @author FERi
	 */
	public ServerNetworkController() {
		Thread t = new Thread(new GetConnections());
		t.start();
		
		ServerNetworkController.snc = this;
	}
	
	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	
	/**
	 * Runnable for get and establish clients
	 * @author FERi
	 */
	class GetConnections implements Runnable {
		@Override
		public void run() {
			try {
				ServerSocket serverSock = new ServerSocket(port);
				
				Log.d(MainActivity.tag, "Start to listening to port "+Integer.toString(port));
				
				while (true) {
					Socket clientSocket = serverSock.accept();
					if(!getNetworkConnection)
						break;
					OutputStream clientOS = clientSocket.getOutputStream();
					clientOS.flush();
					InputStream clientIS = clientSocket.getInputStream();
					
					Client tmpClient = new Client(clientIS, clientOS, clientSocket);
					int tmpID = GameController.gc.addPlayer();
					clients.put(tmpID, tmpClient);
					Thread t = new Thread(new ClientHandler(tmpID, tmpClient));
					t.start();
					
					Log.d(MainActivity.tag, "New connection!!!");
				}
				
				serverSock.close();
			} catch (Exception ex) {
				Log.d(MainActivity.tag, "What the fucking problem in network listening!!!");
				ex.printStackTrace();
			}
		}
	}
	
	/**
	 * Runnable for handling one clients IOs
	 * @author FERi
	 */
	public class ClientHandler implements Runnable {
		Client client;
		Integer ID;
		
		public ClientHandler(Integer ID, Client client) {
			this.ID = ID;
			this.client = client;
		}
		
		public void run() {
			Object obj;
			try {
				while ((obj = client.getInput().readObject()) != null) {
					parsMSG(obj);
				}
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		
		public void parsMSG(Object msg) {
			if(msg instanceof String) {
				String[] message = ((String)msg).split(":");
				switch (Integer.valueOf(message[0])) {
				case PlayerInitializeMSG.PLAYER_ENTER:
					GameController.gc.getPlayer(this.ID).setNickname(message[1]);
					
					broadcastMSG(Integer.toString(PlayerInitializeMSG.PLAYER_CHANGE)+ 
							":"+ GameController.gc.getPlayersList());
					((ServerActivity)activity).runOnUiThread(new Runnable() {
						@Override
						public void run() {
							((ServerActivity)activity).refreshPlayers(GameController.gc.getPlayersList().split(","));
						}
					});
					break;
				case PlayerInitializeMSG.PLAYER_EXIT:
					Log.d(MainActivity.tag, "chera in umade!?");
					GameController.gc.removePlayer(ID);
					clients.remove(ID);
					 
					broadcastMSG(Integer.toString(PlayerInitializeMSG.PLAYER_CHANGE)+ 
							":"+ GameController.gc.getPlayersList());
					((ServerActivity)activity).runOnUiThread(new Runnable() {
						@Override
						public void run() {
							((ServerActivity)activity).refreshPlayers(GameController.gc.getPlayersList().split(","));
						}
					});
					break;
				case GameMSG.END_GAME_REQUEST:
					sendEndRoundMSG();
					GameController.gc.endGame(GameController.gc.getPlayer(0).getNickname(), 
							((GameActivity)activity).getFields());
					break;
				case GameMSG.END_GAME_GIVE_FIELDS :
					ArrayList<String> tmp = new ArrayList<String>();
					for (String s : message[1].split("#")[1].split(","))
						tmp.add(s);
					GameController.gc.endGame(message[1].split("#")[0], tmp);
					break;
				case GameMSG.ALL_FIELDS_REQUEST:
					try {
						client.getOutput().writeObject(Integer.toString(GameMSG.ALL_FIELDS_RESPONSE)+":"
					+GameController.gc.getAllFields());
					} catch (Exception e) {}
					break;
				default:
				}
			} else if(msg instanceof GameMSG) {
				switch (((GameMSG)msg).getType()) {
				case GameMSG.START_GAME:
					
					break;
				default:
					Log.d(MainActivity.tag, "Problem in messages!!!");
					break;
				}
			}
			//else if(msg instanceof ClientInitializeMSG) { 
//				client.initializeClient((ClientInitializeMSG)msg);
//				broadcastMSG(new GameInitializeMSG(GameInitializeMSG.CLIENT_UPDATE, 
//						getClientsNames(), null, null));
//				sa.runOnUiThread(new Runnable() {
//					@Override
//					public void run() {
//						((ServerActivity)sa).importClient(client);
//					}
//				});
//			} else if(msg instanceof GameInitializeMSG) {
//				System.out.println(((GameInitializeMSG)msg).getType());
//			} else if(msg instanceof GameMSG) {
//				switch (((GameMSG)msg).getType()) {
//				case GameMSG.END_GAME_REQUEST:
//					endGame();
//					break;
//				case GameMSG.FIELDS_RESPONSE:
//					Log.d("asd", "wtfffff");
//					clientsFields.put(client.getNickname(), ((GameMSG)msg).getFields(client.getNickname()));
//					Log.d("asdasd", Integer.toString(((GameMSG)msg).getFields(client.getNickname()
//							).size()));
//					if(clientsFields.size()== clients.size()+1) {
//						broadcastMSG(new GameMSG(GameMSG.END_GAME, clientsFields));
//						
//						((MainGameActivity)sa).endGame();
//					}
//					break;
//				default:
//					break;
//				}
//			}
		}
	}
	
	/**
	 * Broadcast message to all players
	 * @author FERi
	 * @param msg
	 */
	public void broadcastMSG(Object msg) {
		for (int id : clients.keySet()) {
			try {
				clients.get(id).getOutput().writeObject(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param modes
	 * @param rounds
	 */
	public void sendStartRoundMSG(HashMap<Integer, Integer> modes, int rounds) {
		GameMSG msg = new GameMSG();
		msg.setType(GameMSG.START_GAME);
		msg.setModes(modes);
		msg.setRounds(rounds);
		
		broadcastMSG(msg);
	}
	
	public void sendEndRoundMSG() {
		Log.d(MainActivity.tag, "send get field");
		broadcastMSG(Integer.toString(GameMSG.END_GAME_GET_FIELDS));
	}
	
	public void sendGoResultPage() {
		broadcastMSG(Integer.toString(GameMSG.END_GAME));
	}
	
	/**
	 * Function for cancel getting connections
	 * @author FERi
	 */
	public void stopGettingConnection() {
		getNetworkConnection = false;
	}
	
	
	/**
	 * Get IP address of device
	 * @return IP address as string, if device is not connected then return null
	 */
	public static String getIPv4Address() {
		try {
			List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
			for (NetworkInterface intf : interfaces) {
				List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
				for (InetAddress addr : addrs) {
					if (!addr.isLoopbackAddress()) {
						String sAddr = addr.getHostAddress().toUpperCase();
						boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr); 
						if (isIPv4) 
							return sAddr;
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
}
