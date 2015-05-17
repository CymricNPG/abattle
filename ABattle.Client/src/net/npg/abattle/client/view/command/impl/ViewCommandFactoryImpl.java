/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.client.view.command.impl;

import java.util.Collections;

import net.npg.abattle.client.ClientConstants;
import net.npg.abattle.client.view.command.ViewCommandFactory;
import net.npg.abattle.client.view.selection.SelectionModel;
import net.npg.abattle.common.model.client.ClientCell;
import net.npg.abattle.common.model.client.ClientGame;
import net.npg.abattle.common.utils.Validate;
import net.npg.abattle.communication.command.CommandQueueClient;
import net.npg.abattle.communication.command.CommandType;
import net.npg.abattle.communication.command.GameCommand;
import net.npg.abattle.communication.command.commands.ChangeLinkCommand;
import net.npg.abattle.communication.service.common.MutableIntPoint;

import com.google.common.base.Optional;

/**
 * @author spatzenegger
 *
 */
public class ViewCommandFactoryImpl implements ViewCommandFactory {

	private final CommandQueueClient commandQueue;
	private final ClientGame game;

	public ViewCommandFactoryImpl(final CommandQueueClient commandQueue, final ClientGame game) {
		Validate.notNulls(commandQueue, game);
		this.commandQueue = commandQueue;
		this.game = game;
	}

	@Override
	public void createLinkCommand(final SelectionModel selectionModel) {
		assert commandQueue != null;
		Validate.notNulls(selectionModel);

		final Optional<GameCommand> command = create(selectionModel);
		ClientConstants.LOG.debug("Send link toggle");
		if (command.isPresent()) {
			commandQueue.addCommand(command.get(), CommandType.TOSERVER, Collections.<Integer> emptyList());
		}
	}

	private Optional<GameCommand> create(final SelectionModel selectionModel) {
		Validate.notNulls(selectionModel);
		final ClientCell startCell = selectionModel.getStartCursor().getCellAtCursor();
		final ClientCell endCell = selectionModel.getEndCursor().getCellAtCursor();
		if (isInvalidLink(startCell, endCell)) {
			return Optional.absent();
		}
		final MutableIntPoint endCoordinate = MutableIntPoint.from(endCell.getBoardCoordinate());
		final MutableIntPoint startCoordinate = MutableIntPoint.from(startCell.getBoardCoordinate());

		final int gameId = game.getId();
		final int playerId = game.getLocalPlayer().getId();

		final boolean create = !game.getBoard().getLinks().doesLinkExists(startCell, endCell);

		return Optional.of((GameCommand) new ChangeLinkCommand(endCoordinate, startCoordinate, create, playerId, false, gameId));
	}

	private boolean isInvalidLink(final ClientCell startCell, final ClientCell endCell) {
		return startCell.equals(endCell) || !startCell.isAdjacentTo(endCell) || !startCell.isVisible() || !endCell.isVisible();
	}
}
