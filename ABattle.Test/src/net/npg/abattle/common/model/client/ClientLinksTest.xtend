package net.npg.abattle.common.model.client

import net.npg.abattle.common.model.CellTypes
import net.npg.abattle.common.model.Link
import net.npg.abattle.common.model.client.impl.ClientCellImpl
import net.npg.abattle.common.model.client.impl.ClientLinksImpl
import net.npg.abattle.common.model.impl.LinkImpl
import net.npg.abattle.common.utils.IntPoint
import org.junit.Test

import static org.junit.Assert.*
import org.jmock.Mockery
import net.npg.abattle.common.model.CheckModelElement
import org.jmock.Expectations

class ClientLinksTest {
	
	@Test
	def testAddLink() {
		val cli = new ClientLinksImpl
		assertNotNull(cli.links)
		assertEquals(0, cli.links.size)
		val link = newLink
		cli.addLink(link)
		assertEquals(1, cli.links.size)
	}
	
	@Test
	def testPreserveLink() {
		val cli = new ClientLinksImpl
		assertNotNull(cli.links)
		assertEquals(0, cli.links.size)
		val link = newLink
		cli.addLink(link)
		assertEquals(1, cli.links.size)
		assertTrue(cli.doesLinkExists(link.sourceCell, link.destinationCell))
		val links = newHashSet(link.id)
		cli.preserverLinks(links)
		assertEquals(1, cli.links.size)
		assertTrue(cli.doesLinkExists(link.sourceCell, link.destinationCell))
	}
	
	@Test
	def testRemoveLink() {
		val cli = new ClientLinksImpl
		assertNotNull(cli.links)
		assertEquals(0, cli.links.size)
		val link = newLink
		cli.addLink(link)
		assertEquals(1, cli.links.size)
		assertTrue(cli.doesLinkExists(link.sourceCell, link.destinationCell))
		val links = newHashSet
		cli.preserverLinks(links)
		assertEquals(0, cli.links.size)
		assertTrue(!cli.doesLinkExists(link.sourceCell, link.destinationCell))
	}

	def Link<ClientCell> newLink() {
		val sourceCell = newCell
		val destinationCell = newCell
		new LinkImpl<ClientCell>(id, sourceCell, destinationCell
		)
	}

	static int ID = 0

	def newCell() {
			 val context = new Mockery
		val check = context.mock(CheckModelElement)
		val ex = new Expectations
		ex.allowing(check)
		context.checking(ex)
		new ClientCellImpl(id, IntPoint.from(id, id), 0, CellTypes.PLAIN, check)
	}

	def id() {
		ID = ID + 1
		ID
	}

}
