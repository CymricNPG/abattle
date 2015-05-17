/**
 *
 */
package net.npg.abattle.server.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import net.npg.abattle.common.model.Cell;
import net.npg.abattle.common.model.CellTypes;
import net.npg.abattle.common.model.Color;
import net.npg.abattle.common.model.ModelVisitor;
import net.npg.abattle.common.model.Player;
import net.npg.abattle.common.utils.IntPoint;
import net.npg.abattle.server.model.impl.ServerLinksImpl;

import org.junit.Test;

import com.google.common.base.Optional;

/**
 * @author Cymric
 * 
 */
public class ServerLinksTest {

	protected int ID = 1;

	/**
	 * Test method for {@link net.npg.abattle.server.model.impl.ServerLinksImpl#ServerLinksImpl()} .
	 */
	@Test
	public void testServerLinksImpl() {
		final ServerLinksImpl sli = new ServerLinksImpl();
		assertNotNull(sli);
	}

	/**
	 * Test method for {@link net.npg.abattle.server.model.impl.ServerLinksImpl#getLinks()}.
	 */
	@Test
	public void testGetLinks() {
		final ServerLinksImpl sli = new ServerLinksImpl();
		final Collection<ServerLink> links = sli.getLinks();
		assertNotNull(links);
		assertTrue(links.isEmpty());
	}

	/**
	 * Test method for
	 * {@link net.npg.abattle.server.model.impl.ServerLinksImpl#getLinks(net.npg.abattle.server.model.ServerPlayer)} .
	 */
	@Test
	public void testGetLinksServerPlayer() {
		final ServerLinksImpl sli = new ServerLinksImpl();
		final Collection<ServerLink> links = sli.getLinks(newPlayer());
		assertNotNull(links);
		assertTrue(links.isEmpty());
	}

	private ServerPlayer newPlayer() {
		return new ServerPlayer() {

			@Override
			public void accept(final ModelVisitor visitor) {
				throw new RuntimeException();
			}

			@Override
			public int getId() {
				return ID++;
			}

			@Override
			public long getCreationTime() {
				throw new RuntimeException();
			}

			@Override
			public String getName() {
				throw new RuntimeException();
			}

			@Override
			public Color getColor() {
				throw new RuntimeException();
			}

			@Override
			public void setColor(final Color color) {
				throw new RuntimeException();
			}

			@Override
			public boolean isComputer() {
				throw new RuntimeException();
			}

			@Override
			public int getStrength() {
				return 0;
			}

			@Override
			public void setStrength(final int strength) {
			}
		};
	}

	/**
	 * Test method for
	 * {@link net.npg.abattle.server.model.impl.ServerLinksImpl#toggleOutgoingLink(net.npg.abattle.server.model.ServerCell, net.npg.abattle.server.model.ServerCell, net.npg.abattle.server.model.ServerPlayer)}
	 * .
	 */
	@Test
	public void testToggleOutgoingLink() {
		final ServerLinksImpl sli = new ServerLinksImpl();
		final ServerPlayer player = newPlayer();
		final ServerCell startCell = newCell();
		final ServerCell endCell = newCell();
		sli.toggleOutgoingLink(startCell, endCell, player);
		final Collection<ServerLink> links1 = sli.getLinks(player);
		assertNotNull(links1);
		assertEquals(1, links1.size());
		final Collection<ServerLink> links3 = sli.getLinks();
		assertNotNull(links3);
		assertEquals(1, links3.size());
		sli.toggleOutgoingLink(startCell, endCell, player);
		final Collection<ServerLink> links2 = sli.getLinks(player);
		assertNotNull(links2);
		assertEquals(0, links2.size());
		final Collection<ServerLink> links4 = sli.getLinks();
		assertNotNull(links4);
		assertEquals(0, links4.size());
	}

	private ServerCell newCell() {
		return new ServerCell() {

			@Override
			public IntPoint getBoardCoordinate() {
				throw new RuntimeException();
			}

			@Override
			public CellTypes getCellType() {
				throw new RuntimeException();
			}

			@Override
			public int getHeight() {
				throw new RuntimeException();
			}

			@Override
			public <PLAYERTYPE extends Player> Optional<PLAYERTYPE> getOwner() {
				throw new RuntimeException();
			}

			@Override
			public int getStrength() {
				throw new RuntimeException();
			}

			@Override
			public boolean hasBattle() {
				throw new RuntimeException();
			}

			@Override
			public boolean hasStructure() {
				throw new RuntimeException();
			}

			@Override
			public <CELLTYPE extends Cell> boolean isAdjacentTo(final CELLTYPE testCell) {
				throw new RuntimeException();
			}

			@Override
			public void setBattle(final boolean hasBattle) {
				throw new RuntimeException();
			}

			@Override
			public <PLAYERTYPE extends Player> void setOwner(final PLAYERTYPE player) {
				throw new RuntimeException();
			}

			@Override
			public void setStrength(final int newStrength) {
				throw new RuntimeException();
			}

			@Override
			public long getCreationTime() {
				throw new RuntimeException();
			}

			@Override
			public int getId() {
				return ID++;
			}

			@Override
			public void accept(final ModelVisitor visitor) {
				throw new RuntimeException();
			}

			@Override
			public void addStrength(final int addStrnegth) {
			}

			@Override
			public <PLAYERTYPE extends Player> boolean isOwner(final PLAYERTYPE player) {
				return false;
			}

		};
	}

}
