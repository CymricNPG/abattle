package net.npg.abattle.client.view.boardscene

import java.util.Properties
import net.npg.abattle.common.configuration.GraphicsConfigurationData
import net.npg.abattle.common.hex.Hex
import net.npg.abattle.common.hex.HexBase
import net.npg.abattle.common.model.Cell
import net.npg.abattle.common.model.CellTypes
import net.npg.abattle.common.model.ModelVisitor
import net.npg.abattle.common.model.Player
import net.npg.abattle.common.model.client.ClientCell
import net.npg.abattle.common.utils.IntPoint
import org.junit.Assert
import org.junit.Test

class FightShapeTest {
	@Test
	def void testDrawable() {
		val hexBase = new HexBase(1)
		val cell = new ClientCell() {

			public boolean battle

			override isVisible() {
				true
			}

			override setCellType(CellTypes type) {
				throw new UnsupportedOperationException("TODO: auto-generated method stub")
			}

			override setHeight(int height) {
				throw new UnsupportedOperationException("TODO: auto-generated method stub")
			}

			override setVisible(boolean visible) {
				throw new UnsupportedOperationException("TODO: auto-generated method stub")
			}

			override getBoardCoordinate() {
				IntPoint.from(0, 0)
			}

			override getCellType() {
				throw new UnsupportedOperationException("TODO: auto-generated method stub")
			}

			override getHeight() {
				throw new UnsupportedOperationException("TODO: auto-generated method stub")
			}

			override getOwner() {
				throw new UnsupportedOperationException("TODO: auto-generated method stub")
			}

			override getStrength() {
				throw new UnsupportedOperationException("TODO: auto-generated method stub")
			}

			override hasBattle() {
				return battle
			}

			override hasStructure() {
				throw new UnsupportedOperationException("TODO: auto-generated method stub")
			}

			override <CELLTYPE extends Cell> isAdjacentTo(CELLTYPE testCell) {
				throw new UnsupportedOperationException("TODO: auto-generated method stub")
			}

			override setBattle(boolean hasBattle) {
				throw new UnsupportedOperationException("TODO: auto-generated method stub")
			}

			override <PLAYERTYPE extends Player> setOwner(PLAYERTYPE player) {
				throw new UnsupportedOperationException("TODO: auto-generated method stub")
			}

			override setStrength(int newStrength) {
				throw new UnsupportedOperationException("TODO: auto-generated method stub")
			}

			override getCreationTime() {
				throw new UnsupportedOperationException("TODO: auto-generated method stub")
			}

			override getId() {
				throw new UnsupportedOperationException("TODO: auto-generated method stub")
			}

			override accept(ModelVisitor visitor) {
				throw new UnsupportedOperationException("TODO: auto-generated method stub")
			}

			override <PLAYERTYPE extends Player> isOwner(PLAYERTYPE player) {
				throw new UnsupportedOperationException("TODO: auto-generated method stub")
			}

		}
		val fight = new FightShape(cell, new Hex(cell.boardCoordinate,hexBase), new GraphicsConfigurationData(new Properties()))
		cell.battle = false
		Assert.assertFalse(cell.hasBattle)
		Assert.assertFalse(fight.drawable)

		cell.battle = true
		Assert.assertTrue(cell.hasBattle)
		Assert.assertTrue(fight.drawable)
		for (var i = 0; i < 100; i++) {
			Assert.assertTrue(fight.drawable)
		}
		cell.battle = false
		Assert.assertFalse(cell.hasBattle)
		Assert.assertTrue(fight.drawable)
		for (var i = 0; i < 19; i++) {
			Assert.assertTrue("Iteration:"+i,fight.drawable)
		}
		Assert.assertFalse(cell.hasBattle)
		Assert.assertFalse(fight.drawable)
		cell.battle = true
		Assert.assertTrue(cell.hasBattle)
		Assert.assertTrue(fight.drawable)
		cell.battle = false
		Assert.assertFalse(cell.hasBattle)
		Assert.assertTrue(fight.drawable)
		cell.battle = true
		Assert.assertTrue(cell.hasBattle)
		Assert.assertTrue(fight.drawable)

	}
}
