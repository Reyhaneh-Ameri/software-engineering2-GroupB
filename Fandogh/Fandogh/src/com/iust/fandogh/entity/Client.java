package com.iust.fandogh.entity;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * This class is for network processes
 * Each Client have a Player pair in GameController
 * @author FERi
 */
public class Client {
	ObjectOutputStream oos;
	ObjectInputStream ois;
	Socket socket;
	
	/**
	 * Get input and output streams of socket
	 * @param ois
	 * @param oos
	 * @param socket
	 */
	public Client(InputStream ois, OutputStream oos, Socket socket) {
		this.socket = socket;
		try {
			this.ois = new ObjectInputStream(ois);
			this.oos = new ObjectOutputStream(oos);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ObjectInputStream getInput() {
		return ois;
	}
	public ObjectOutputStream getOutput() {
		return oos;
	}
}
