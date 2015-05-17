package net.npg.abattle.server.model;

import net.npg.abattle.common.model.Link;

public interface ServerLink extends Link<ServerCell> {
	ServerPlayer getPlayer();
}
