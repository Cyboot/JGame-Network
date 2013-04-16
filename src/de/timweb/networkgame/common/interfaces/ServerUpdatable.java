package de.timweb.networkgame.common.interfaces;

import de.timweb.networkgame.common.entity.ServerValues;

public interface ServerUpdatable {
	public ServerValues getServerValues();

	public void updateServerValues(ServerValues serverValues);
}
