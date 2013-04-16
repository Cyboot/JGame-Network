package de.timweb.networkgame.common.entity;

import java.awt.Graphics;
import java.io.Serializable;

import de.timweb.networkgame.common.util.Vector2d;

public abstract class Entity implements Serializable {
	private static final long	serialVersionUID	= 266921135181031818L;

	protected boolean			isAlive				= true;

	protected Vector2d			pos;

	public Entity(Vector2d pos) {
		this.pos = pos.copy();
	}

	public void move(double x, double y) {
		pos.add(x, y);
	}

	public Vector2d getPos() {
		return pos;
	}

	public void kill() {
		onKilled();
		isAlive = false;
	}

	protected void onKilled() {
	}

	public boolean isAlive() {
		return isAlive;
	}

	public abstract void update(int delta);

	public abstract void render(Graphics g);
}
