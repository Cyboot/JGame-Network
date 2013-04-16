package de.timweb.networkgame.client;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import de.timweb.networkgame.client.game.NetworkCanvas;
import de.timweb.networkgame.client.util.ImageLoader;

public class ClientAC {

	public static void main(String[] args) {
		NetworkCanvas evocanvas = new NetworkCanvas(1024, 600, 10);
		JFrame frame = new JFrame("Evolevel");
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(evocanvas);
		frame.setContentPane(panel);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);


		evocanvas.start();
		frame.setIconImage(ImageLoader.icon_client);
	}

}
