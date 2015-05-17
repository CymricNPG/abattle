/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.common.model.client.impl;

import java.util.Collection;

import net.npg.abattle.common.component.ComponentLookup;
import net.npg.abattle.common.configuration.ConfigurationComponent;
import net.npg.abattle.common.error.BaseException;
import net.npg.abattle.common.model.Board;
import net.npg.abattle.common.model.Cell;
import net.npg.abattle.common.model.Game;
import net.npg.abattle.common.model.GameConfiguration;
import net.npg.abattle.common.model.Link;
import net.npg.abattle.common.model.ModelVisitor;
import net.npg.abattle.common.model.Player;
import net.npg.abattle.common.model.client.ClientBoard;
import net.npg.abattle.common.model.client.ClientCell;
import net.npg.abattle.common.model.client.ClientGame;
import net.npg.abattle.common.model.client.ClientPlayer;
import net.npg.abattle.common.model.impl.GameConfigurationImpl;
import net.npg.abattle.common.model.impl.LinkImpl;
import net.npg.abattle.common.utils.Validate;

import com.google.common.base.Optional;

/**
 * only used for single player games
 *
 * @author Cymric
 *
 */
@SuppressWarnings("rawtypes")
public class ClientStructureCopy implements ModelVisitor {

	public static ClientGame copy(final ClientGame sourceGame) {
		Validate.notNull(sourceGame);
		final ClientStructureCopy copy = new ClientStructureCopy();
		((Game) sourceGame).accept(copy);
		Validate.notNull(copy.game);
		return copy.game;
	}

	ClientGameImpl game;

	private ClientStructureCopy() {
		// ok
	}

	private ClientPlayerImpl convertPlayer(final Player sourcePlayer) {
		Validate.notNull(sourcePlayer);
		return new ClientPlayerImpl(sourcePlayer.getName(), sourcePlayer.getId(), sourcePlayer.getColor());
	}

	private Player findPlayer(final Optional<Player> owner) {
		if (!owner.isPresent()) {
			return null;
		}
		for (final Player player : game.getPlayers()) {
			if (player.getId() == owner.get().getId()) {
				return player;
			}
		}
		throw new IllegalArgumentException("Unknown Player:" + owner.get().getId());
	}

	@Override
	public void visit(final Board sourceBoard) {
		Validate.notNull(sourceBoard);
		Validate.notNull(game);
		game.setBoard(new ClientBoardImpl(sourceBoard.getId(), sourceBoard.getXSize(), sourceBoard.getYSize()));
		for (final Link link : ((ClientBoard) sourceBoard).getLinks().getLinks()) {
			link.accept(this);
		}
	}

	@Override
	public void visit(final Cell sourceCell) {
		Validate.notNull(sourceCell);
		Validate.notNull(game);
		Validate.notNull(game.getBoard());
		final ClientBoardImpl board = (ClientBoardImpl) game.getBoard();
		final ClientCellImpl cell = new ClientCellImpl(sourceCell.getId(), sourceCell.getBoardCoordinate(), sourceCell.getHeight(), sourceCell.getCellType(),
				game.getGameConfiguration().getChecker());
		cell.setBattle(sourceCell.hasBattle());
		final Player player = findPlayer(sourceCell.getOwner());
		cell.setOwner(player);
		cell.setStrength(sourceCell.getStrength());
		cell.setVisible(((ClientCell) sourceCell).isVisible());
		board.setCellAt(cell);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void visit(final Game sourceGame) {
		Validate.isTrue(this.game == null);
		Validate.notNull(sourceGame);
		this.game = new ClientGameImpl(sourceGame.getId());
		sourceGame.getGameConfiguration().accept(this);

		for (final ClientPlayer player : (Collection<ClientPlayer>) sourceGame.getPlayers()) {
			player.accept(this);
		}
		this.game.setLocalPlayer(convertPlayer(((ClientGame) sourceGame).getLocalPlayer()));

		sourceGame.getBoard().accept(this);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void visit(final Link link) {
		final Link<ClientCell> clink = link;
		final Link<ClientCell> clientLinkImpl = new LinkImpl(clink.getId(), clink.getSourceCell(), clink.getDestinationCell());
		((ClientBoard) game.getBoard()).getLinks().addLink(clientLinkImpl);
	}

	@Override
	public void visit(final Player sourcePlayer) {
		Validate.notNull(sourcePlayer);
		Validate.notNull(game);
		final ClientPlayerImpl clientPlayer = convertPlayer(sourcePlayer);
		try {
			game.addPlayer(clientPlayer);
		} catch (final BaseException e) {
			throw new IllegalArgumentException("Converting failed:", e);
		}
	}

	@Override
	public void visit(final GameConfiguration configuration) {
		final GameConfigurationImpl newConfiguration = new GameConfigurationImpl();
		newConfiguration.setConfiguration(ComponentLookup.getInstance().getComponent(ConfigurationComponent.class).getGameConfiguration());
		newConfiguration.setXSize(configuration.getXSize());
		newConfiguration.setYSize(configuration.getYSize());
		game.setGameConfiguration(newConfiguration);
	}
}
