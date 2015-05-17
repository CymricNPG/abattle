package net.npg.abattle.server.model;

import java.util.Collection;
import java.util.List;

import net.npg.abattle.common.model.Links;

public interface ServerLinks extends Links<ServerCell> {

	Collection<ServerLink> getLinks(ServerPlayer player);

	List<ServerCell> getOutgoingLinks(ServerPlayer owner, ServerCell cell);

	boolean hasLink(ServerCell startCell, ServerCell endCell, ServerPlayer player);

	void toggleOutgoingLink(ServerCell startCell, ServerCell endCell, ServerPlayer player);

	Collection<ServerLink> getLinks();
}
