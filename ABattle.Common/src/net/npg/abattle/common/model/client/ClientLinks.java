/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.common.model.client;

import java.util.Collection;
import java.util.Set;

import net.npg.abattle.common.model.Link;
import net.npg.abattle.common.model.Links;

/**
 * @author Cymric
 * 
 */
public interface ClientLinks extends Links<ClientCell> {

	public void addLink(Link<ClientCell> link);

	public boolean doesLinkExists(ClientCell startCell, ClientCell endCell);

	public boolean hasLink(int linkId);

	public void preserverLinks(Set<Integer> linkIds);

	public Collection<Link<ClientCell>> getLinks();
}
