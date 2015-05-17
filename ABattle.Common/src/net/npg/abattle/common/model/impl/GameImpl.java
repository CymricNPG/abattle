/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.common.model.impl;

import java.util.Set;

import net.npg.abattle.common.error.BaseException;
import net.npg.abattle.common.model.Board;
import net.npg.abattle.common.model.BoardCreator;
import net.npg.abattle.common.model.Cell;
import net.npg.abattle.common.model.Game;
import net.npg.abattle.common.model.GameConfiguration;
import net.npg.abattle.common.model.GameStatus;
import net.npg.abattle.common.model.Links;
import net.npg.abattle.common.model.ModelExceptionCode;
import net.npg.abattle.common.model.Player;
import net.npg.abattle.common.utils.ConcurrentHashSet;
import net.npg.abattle.common.utils.Validate;

/**
 * @author cymric
 *
 */
@SuppressWarnings("rawtypes")
public abstract class GameImpl<PLAYERTYPE extends Player, CELLTYPE extends Cell, LINKTYPE extends Links<CELLTYPE>> extends IDElementImpl implements
Game<PLAYERTYPE, CELLTYPE, LINKTYPE> {

	private Board<PLAYERTYPE, CELLTYPE, LINKTYPE> board;

	private BoardCreator<? extends Board> boardCreator;
	private Set<PLAYERTYPE> players;
	private volatile GameStatus status;
	private GameConfiguration configuration;

	public GameImpl(final int id, final BoardCreator<? extends Board> boardCreator, final GameConfiguration configuration) {
		super(id);
		init(boardCreator, configuration);
	}

	public GameImpl(final BoardCreator<? extends Board> boardCreator, final GameConfiguration configuration) {
		super();
		init(boardCreator, configuration);
	}

	private void init(final BoardCreator<? extends Board> boardCreator, final GameConfiguration configuration) {
		Validate.notNull(boardCreator);
		Validate.notNull(configuration);
		this.players = new ConcurrentHashSet<PLAYERTYPE>();
		this.status = GameStatus.PENDING;
		this.boardCreator = boardCreator;
		this.configuration = configuration;
	}

	public GameImpl(final int id) {
		super(id);
		this.players = new ConcurrentHashSet<PLAYERTYPE>();
		this.status = GameStatus.PENDING;
		this.boardCreator = null;
	}

	@Override
	public synchronized void addPlayer(final PLAYERTYPE player) throws BaseException {
		assert players != null;
		assert status != null;
		if (!GameStatus.isPending(status)) {
			throw new BaseException(ModelExceptionCode.GAME_ALREADY_RUNNING);
		}
		if (players.contains(player)) {
			throw new BaseException(ModelExceptionCode.PLAYER_ALREADY_ADDED);
		}
		if (players.contains(player)) {
			return;
		}
		players.add(player);
	}

	@Override
	public Board<PLAYERTYPE, CELLTYPE, LINKTYPE> getBoard() {
		assert board != null;
		return board;
	}

	/**
	 * safe to return, because of ConcurrentSet!
	 */
	@Override
	public Set<PLAYERTYPE> getPlayers() {
		assert players != null;
		return players;
	}

	@Override
	public GameStatus getStatus() {
		assert status != null;
		return status;
	}

	public void setBoard(final Board<PLAYERTYPE, CELLTYPE, LINKTYPE> board) {
		assert board != null;
		this.board = board;
	}

	@Override
	public GameConfiguration getGameConfiguration() {
		assert configuration != null;
		return configuration;
	}

	public void setBoardCreator(final BoardCreator<Board<PLAYERTYPE, CELLTYPE, LINKTYPE>> boardCreator) {
		this.boardCreator = boardCreator;
	}

	public void setPlayers(final Set<PLAYERTYPE> players) {
		this.players = players;
	}

	public void setStatus(final GameStatus status) {
		this.status = status;
	}

	public void setGameConfiguration(final GameConfiguration newConfiguration) {
		Validate.notNull(newConfiguration);
		this.configuration = newConfiguration;
	}

	@Override
	public void stopGame() {
		status = GameStatus.FINISHED;
	}

	/**
	 * @return the boardCreator
	 */
	protected BoardCreator<? extends Board> getBoardCreator() {
		return boardCreator;
	}

	@Override
	public boolean removePlayer(final PLAYERTYPE player) {
		assert players != null;
		assert status != null;
		if (status.isRunning()) {
			return false;
		}
		players.remove(player);
		return true;
	}
}
