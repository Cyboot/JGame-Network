package de.timweb.networkgame.client.game;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import de.timweb.networkgame.common.entity.Entity;
import de.timweb.networkgame.common.util.DTORegister;

public class NetworkAC {
	public static final NetworkAC	n			= new NetworkAC();

	final Lock						lock		= new ReentrantLock();
	private Client					client;
	private Map<Integer, Entity>	entitymap	= new HashMap<>();
	private int						clientId	= -1;


	private NetworkAC() {
	}


	private class NetworkListener extends Listener {
		@SuppressWarnings("unchecked")
		@Override
		public void received(Connection c, Object obj) {
			// lock.lock();
			if (obj instanceof HashMap<?, ?>) {
				entitymap = (Map<Integer, Entity>) obj;
			}
			System.out.println("Recieved: " + obj);
			clientId = c.getID();
			// lock.unlock();
		}
	}

	public void start() {
		client = new Client();
		client.start();

		DTORegister.register(client);

		client.addListener(new NetworkListener());

		try {
			client.connect(50000, "localhost", 12345);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void sendObject(Object obj) {
		client.sendTCP(obj);
	}

	public Map<Integer, Entity> getEntitymap(Lock lock) {
		lock = this.lock;
		lock.lock();
		return entitymap;
	}

	public int getClientId() {
		return clientId;
	}
}
