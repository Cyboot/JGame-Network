package de.timweb.networkgame.server;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import de.timweb.networkgame.client.ClientAC;
import de.timweb.networkgame.common.entity.Entity;
import de.timweb.networkgame.common.log.Log;
import de.timweb.networkgame.common.log.ServerLog;
import de.timweb.networkgame.common.util.DTORegister;


public class ServerAC extends JFrame {
	private static final long			serialVersionUID	= 1L;
	public static final int				PORT				= 12345;

	private final Log					log;
	private final Server				server;
	private final JTextPane				textArea;
	private final JScrollPane			pane;
	private final Map<Integer, Entity>	entitymap			= new HashMap<>();

	public static void main(String[] args) {

		new ServerAC();
	}

	public ServerAC() {
		super("SERVER - JGame-Network");
		setSize(512, 512);
		textArea = new JTextPane();
		pane = new JScrollPane(textArea);
		getContentPane().add(pane);
		setVisible(true);
		setLocation(-1280, -200); // on second Monitor
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		log = new ServerLog(textArea);

		URL url;
		url = ClientAC.class.getResource("/server.png");
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.createImage(url);
		setIconImage(img);

		com.esotericsoftware.minlog.Log.set(com.esotericsoftware.minlog.Log.LEVEL_DEBUG);

		System.out.println("Starting Server...");
		server = new Server(100 * 1024, 100 * 1024);
		DTORegister.register(server);
		server.addListener(new ServerListener());

		try {
			server.bind(12345);
			server.start();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		pane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				e.getAdjustable().setValue(e.getAdjustable().getMaximum());
			}
		});

		new Thread() {
			@Override
			public void run() {
				while (true) {
					// TODO: real Game.update()
					// TODO: check how often Server can push Updates to Client

					// TODO: own Entities for Server, update with ServerValues

					server.sendToAllTCP(entitymap);

					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
		}.start();
	}

	private class ServerListener extends Listener {
		@Override
		public void received(Connection c, Object obj) {
			log.info(" >>> " + c.getID() + " send " + obj);

			if (obj instanceof Entity) {
				Entity e = (Entity) obj;

				entitymap.put(c.getID(), e);
			}

			// c.sendTCP("CHECK");
			server.sendToAllTCP(entitymap);
		}
	}
}
