package de.timweb.networkgame.common.util;

import java.awt.Color;
import java.util.HashMap;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

import de.timweb.networkgame.common.entity.Entity;
import de.timweb.networkgame.common.entity.PlayerEntity;

public class DTORegister {

	static public void register(EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		kryo.register(Entity.class);
		kryo.register(PlayerEntity.class);
		kryo.register(String.class);
		kryo.register(HashMap.class);
		kryo.register(Color.class);
		kryo.register(float[].class);
		kryo.register(Vector2d.class);
	}
}
