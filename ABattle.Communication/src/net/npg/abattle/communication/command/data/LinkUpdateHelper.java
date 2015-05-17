/**
 * 
 */
package net.npg.abattle.communication.command.data;

import java.util.ArrayList;

/**
 * @author Cymric
 * 
 */
public class LinkUpdateHelper {

	/**
	 * 
	 */
	private LinkUpdateHelper() {
		//
	}

	public static LinkUpdateData[] toArray(final Iterable<LinkUpdateData> it) {
		final ArrayList<LinkUpdateData> list = new ArrayList<LinkUpdateData>();
		for (final LinkUpdateData element : it) {
			list.add(element);
		}
		return list.toArray(new LinkUpdateData[list.size()]);
	}
}
