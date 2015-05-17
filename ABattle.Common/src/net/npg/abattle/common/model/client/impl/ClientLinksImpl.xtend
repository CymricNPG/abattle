package net.npg.abattle.common.model.client.impl

import java.util.Collection
import java.util.Set
import net.npg.abattle.common.CommonConstants
import net.npg.abattle.common.model.Link
import net.npg.abattle.common.model.client.ClientCell
import net.npg.abattle.common.model.client.ClientLinks
import net.npg.abattle.common.model.impl.IDElementImpl
import net.npg.abattle.common.utils.MyHashMap
import net.npg.abattle.common.utils.MyMap
import net.npg.abattle.common.utils.Validate

import static net.npg.abattle.common.utils.Asserts.*

import static extension net.npg.abattle.common.utils.IterableExtensions.*

/**
 * @author Cymric
 *
 */
class ClientLinksImpl extends IDElementImpl implements ClientLinks {

	private final MyMap<Integer, Link<ClientCell>> links

	private final MyMap<Integer, Set<Integer>> cellToCells

	new() {
		super()
		links = new MyHashMap
		cellToCells = new MyHashMap
	}

	new(int id) {
		super(id)
		links = new MyHashMap
		cellToCells = new MyHashMap
	}

	synchronized override void addLink(Link<ClientCell> link) {
		assertIt(links != null);
		assertIt(cellToCells != null);
		Validate.notNull(link);
		Validate.isTrue(!links.containsKey(link.id))

		links.put(link.id, link);

		var cellLinks = cellToCells.get(link.sourceCell.id)
		if (cellLinks == null) {
			cellLinks = newHashSet
			cellToCells.put(link.sourceCell.id, cellLinks)
		}
		cellLinks.add(link.destinationCell.id)
		CommonConstants.LOG.debug("Link:" + link.id + " added.")
	}

	synchronized	override boolean doesLinkExists(ClientCell startCell, ClientCell endCell) {
		var cellLinks = cellToCells.get(startCell.id)
		if (cellLinks == null) {
			return false
		}
		cellLinks.contains(endCell.id)
	}

	synchronized	override public Collection<Link<ClientCell>> getLinks() {
		assertIt(links != null);
		return newArrayList(links.values);
	}

	synchronized	override public boolean hasLink(int linkId) {
		return links.containsKey(linkId)
	}

	synchronized	override public void preserverLinks(Set<Integer> linkIds) {
		links.entrySet.filter[!linkIds.contains(it.key)].forEach[remove(it.value)]
		links.keySet.removeConditional[!linkIds.contains(it)]
	}

	private def remove(Link<ClientCell> link) {
		var cellLinks = cellToCells.get(link.sourceCell.id)
		if (cellLinks == null) {
			return
		}
		if (!cellLinks.contains(link.destinationCell.id)) {
			return
		}
		val success = cellLinks.remove(link.destinationCell.id)
		assertIt(success)
		CommonConstants.LOG.debug("Link:" + link.id + " removed.")
	}

}
