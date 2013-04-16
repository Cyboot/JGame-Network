package de.timweb.networkgame.common.entity;

import java.awt.Color;
import java.awt.Graphics;

import de.timweb.networkgame.client.game.Game;
import de.timweb.networkgame.client.game.NetworkCanvas;
import de.timweb.networkgame.common.util.Vector2d;

public class PlayerEntity extends Entity {
	private static final long	serialVersionUID	= -2913005858727007364L;
	private static final int	SIZE				= 20;
	private static final double	ACCL				= 0.003;
	private static final double	DEACCL				= ACCL / 4;

	private boolean				isPlayer;

	public PlayerEntity(Vector2d pos, boolean isPlayer) {
		super(pos);
		this.isPlayer = isPlayer;

		// if (isPlayer)
		// color = Color.blue;
		// else
		// color = Color.red;
	}

	public PlayerEntity() {
		this(new Vector2d(), false);
	}

	@Override
	public void update(int delta) {
		if (Game.VK_DOWN) {
			direction.y += ACCL * delta;
		}
		if (Game.VK_UP) {
			direction.y -= ACCL * delta;
		}
		if (Game.VK_RIGHT) {
			direction.x += ACCL * delta;
		}
		if (Game.VK_LEFT) {
			direction.x -= ACCL * delta;
		}

		if (Math.abs(direction.y) > DEACCL * delta) {
			if (direction.y > 0)
				direction.y -= DEACCL * delta;
			else
				direction.y += DEACCL * delta;
		} else {
			direction.y = 0;
		}

		if (Math.abs(direction.x) > DEACCL * delta) {
			if (direction.x > 0)
				direction.x -= DEACCL * delta;
			else
				direction.x += DEACCL * delta;
		} else {
			direction.x = 0;
		}

		move(direction.x, direction.y);

		if (pos.y < 0 || pos.y > NetworkCanvas.HEIGHT) {
			direction.y = -direction.y;
		}
		if (pos.x < 0 || pos.x > NetworkCanvas.WIDTH) {
			direction.x = -direction.x;
		}

	}

	@Override
	public void render(Graphics g) {
		int x = pos.x() - 10;
		int y = pos.y() - 10;

		if (isPlayer())
			g.setColor(Color.blue);
		else
			g.setColor(Color.red);

		g.fillOval(x, y, SIZE, SIZE);
	}

	public void setIsPlayer(boolean isplayer) {
		this.isPlayer = isplayer;
	}

	public boolean isPlayer() {
		return isPlayer;
	}

}
