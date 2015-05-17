package net.npg.abattle.server.logic

import java.util.Collection
import net.npg.abattle.common.error.BaseException
import net.npg.abattle.common.model.Board
import net.npg.abattle.common.model.BoardCreator
import net.npg.abattle.common.model.CellTypes
import net.npg.abattle.common.model.impl.ColorImpl
import net.npg.abattle.common.model.impl.GameConfigurationImpl
import net.npg.abattle.common.utils.FieldLoop
import net.npg.abattle.common.utils.IntPoint
import net.npg.abattle.common.utils.LongHolder
import net.npg.abattle.server.model.ServerCell
import net.npg.abattle.server.model.ServerGame
import net.npg.abattle.server.model.ServerLinks
import net.npg.abattle.server.model.ServerPlayer
import net.npg.abattle.server.model.impl.ServerBoardImpl
import net.npg.abattle.server.model.impl.ServerCellImpl
import net.npg.abattle.server.model.impl.ServerGameImpl
import net.npg.abattle.server.model.impl.ServerPlayerImpl
import org.junit.Test

import static org.junit.Assert.*
import net.npg.abattle.server.model.BoardInitializedNotifier
import net.npg.abattle.common.configuration.GameConfigurationData

class SimpleGameLogicTest {

	static int XSIZE = 8
	static int YSIZE = 8
	int id = 0

	private def BoardInitializedNotifier createNotifier() {
		new BoardInitializedNotifier() {
			override boardCreated(Board<ServerPlayer, ServerCell, ServerLinks> board) {
			}

		}
	}

	@Test
	def void testFight() {
		val game = createGame(2)
		val player = game.players.get(0)
		val enemy = new ServerPlayerImpl("test2", 1, ColorImpl.BLACK, false)
		game.addPlayer(enemy)
		game.startGame(createNotifier)

		SimpleGameLogic.run(game)
		testTotalStrength(0, game)
		val cell1 = game.board.getCellAt(0, 0)
		val cell2 = game.board.getCellAt(0, 1)
		makeLink(0, 0, 0, 1, game, player)
		makeLink(0, 1, 0, 0, game, enemy)
		cell1.strength = 20
		cell2.strength = 20
		SimpleGameLogic.run(game)
		testTotalStrength(36, game)
		for (var i = 0; i < 8; i++) {
			SimpleGameLogic.run(game)
		}
		SimpleGameLogic.run(game)
		testTotalStrength(0, game)
		assertFalse(cell1.owner.isPresent)
		assertEquals(enemy, cell2.owner.get)
		assertEquals(1, game.board.links.links.size)
	}

	@Test
	def void testRemoveLink() {
		val game = createGame(2)
		val player = game.players.get(0)
		val enemy = new ServerPlayerImpl("test2", 1, ColorImpl.BLACK, false)
		game.addPlayer(enemy)
		game.startGame(createNotifier)

		SimpleGameLogic.run(game)
		testTotalStrength(0, game)
		val cell1 = game.board.getCellAt(0, 0)
		val cell2 = game.board.getCellAt(0, 1)
		makeLink(0, 0, 0, 1, game, player)
		cell1.strength = 20
		SimpleGameLogic.run(game)
		testTotalStrength(20, game)
		assertEquals(player, cell1.owner.get)
		assertEquals(player, cell2.owner.get)
		assertEquals(1, game.board.links.links.size)
	}

	@Test
	def void testCircle() {
		val game = createGame(1)
		game.startGame(createNotifier)
		val player = game.players.get(0)

		SimpleGameLogic.run(game)
		testTotalStrength(0, game)
		makeLinks(game, player)
		testTotalStrength(0, game)
		SimpleGameLogic.run(game)
		testTotalStrength(0, game)
		val cell1 = game.board.getCellAt(IntPoint.from(0, 0))
		val cell2 = game.board.getCellAt(IntPoint.from(0, 1))
		val cell3 = game.board.getCellAt(IntPoint.from(1, 1))
		val cell4 = game.board.getCellAt(IntPoint.from(1, 0))
		assertTrue(cell1.isAdjacentTo(cell2))
		assertTrue(cell2.isAdjacentTo(cell3))
		assertTrue(cell3.isAdjacentTo(cell4))
		assertTrue(cell4.isAdjacentTo(cell1))
		cell1.strength = 50
		testTotalStrength(50, game)
		SimpleGameLogic.run(game)
		SimpleGameLogic.run(game)
		SimpleGameLogic.run(game)
		SimpleGameLogic.run(game)
		SimpleGameLogic.run(game)
		SimpleGameLogic.run(game)
		SimpleGameLogic.run(game)
		testTotalStrength(50, game)
	}

	def createGame(int players) {
		val player = new ServerPlayerImpl("test", 0, ColorImpl.BLACK, false)

		val conf = new GameConfigurationImpl
		conf.configuration = new GameConfigurationData
		conf.configuration.baseGrowthPerTick = 10
		conf.configuration.maxCellStrength = 50
		conf.configuration.maxCellHeight = 1
		conf.configuration.maxMovement = 10
		conf.XSize = XSIZE
		conf.YSize = YSIZE
		conf.configuration.winCondition = 2

		val creator = new BoardCreator<Board<ServerPlayer, ServerCell, ServerLinks>>() {

			override getBoard() {
				val board = new ServerBoardImpl(XSIZE, YSIZE)
				FieldLoop.visitAllFields(XSIZE, YSIZE,
					[x, y|
						board.setCellAt(new ServerCellImpl(id++, IntPoint.from(x, y), 0, CellTypes.PLAIN, conf.checker))])
				return board
			}

			override run(Collection players) throws BaseException {
				//
			}

		}

		val game = new ServerGameImpl(players, creator, conf)
		game.addPlayer(player)
		game
	}

	def testTotalStrength(int expectedStrength, ServerGameImpl game) {
		val strength = new LongHolder
		FieldLoop.visitAllFields(XSIZE, YSIZE, [x, y|strength.add(game.board.getCellAt(x, y).strength)]);
		assertEquals(expectedStrength, strength.value as int)
	}

	def makeLinks(ServerGame game, ServerPlayer player) {
		makeLink(0, 0, 0, 1, game, player)
		makeLink(0, 1, 1, 1, game, player)
		makeLink(1, 1, 1, 0, game, player)
		makeLink(1, 0, 0, 0, game, player)
	}

	def makeLink(int xs, int ys, int xe, int ye, ServerGame game, ServerPlayer player) {
		val startCell = game.board.getCellAt(IntPoint.from(xs, ys))
		val endCell = game.board.getCellAt(IntPoint.from(xe, ye))
		game.board.links.toggleOutgoingLink(startCell, endCell, player)
		startCell.owner = player
	}

}
