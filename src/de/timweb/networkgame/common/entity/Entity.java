package de.timweb.networkgame.common.entity;

import java.awt.Graphics;
import java.io.Serializable;

import de.timweb.networkgame.common.interfaces.ServerUpdatable;
import de.timweb.networkgame.common.util.Vector2d;

public abstract class Entity implements Serializable, ServerUpdatable {
	private static final long	serialVersionUID	= 266921135181031818L;

	protected long				entityId;
	protected boolean			isAlive				= true;
	private boolean				isClientControlled	= false;
	protected final Vector2d	pos;
	protected final Vector2d	direction			= new Vector2d();
	protected ServerValues		serverValues;

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

	@Override
	public ServerValues getServerValues() {
		serverValues.pos = pos;
		serverValues.direction = direction;
		serverValues.entityId = entityId;
		serverValues.isAlive = isAlive;

		if (entityId < 0)
			serverValues.isNewlyCreated = true;
		else
			serverValues.isNewlyCreated = false;

		return serverValues;
	}

	@Override
	public void updateServerValues(ServerValues serverValues) {
		pos.set(serverValues.pos);
		direction.set(serverValues.direction);
		entityId = serverValues.entityId;

		if (serverValues.isAlive == false && isAlive == true) {
			kill();
		}
		isAlive = serverValues.isAlive;
	}

	public boolean isClientControlled() {
		return isClientControlled;
	}

	public void setClientControlled(boolean isClientControlled) {
		this.isClientControlled = isClientControlled;
	}

}
