package de.timweb.networkgame.client.game;

import java.awt.Graphics;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.locks.Lock;

import de.timweb.networkgame.common.entity.Entity;
import de.timweb.networkgame.common.entity.PlayerEntity;
import de.timweb.networkgame.common.util.Vector2d;

public class Game {
	public static final Game	g		= new Game();

	public static boolean		VK_UP;
	public static boolean		VK_DOWN;
	public static boolean		VK_LEFT;
	public static boolean		VK_RIGHT;

	private PlayerEntity		player	= new PlayerEntity(new Vector2d(), true);
	private PlayerEntity		enemy	= new PlayerEntity(new Vector2d(), false);
	private int					lastServerUpdate;

	private Game() {

	}

	public void update(int delta) {

		// TODO: check if new ServerValues
		Lock lock = null;
		Map<Integer, Entity> entitymap = NetworkAC.n.getEntitymap(lock);
		for (Entry<Integer, Entity> entry : entitymap.entrySet()) {
			boolean isplayer = entry.getKey() == NetworkAC.n.getClientId();
			Entity e = entry.getValue();

			System.out.println("Entity " + e);
			if (isplayer) {
				player = (PlayerEntity) e;
			} else
				enemy = (PlayerEntity) e;
		}

		// TODO: send only Player controlled Entities
		if (lastServerUpdate > 250) {
			lastServerUpdate = 0;
			NetworkAC.n.sendObject(player);
		}
		lastServerUpdate += delta;

		player.update(delta);
		enemy.update(delta);

		World.w.update(delta);
	}

	public void render(Graphics g) {
		World.w.render(g);

		player.setIsPlayer(true);
		player.render(g);

		enemy.setIsPlayer(false);
		enemy.render(g);
	}

}
