package net.npg.abattle.client.commands;

import com.google.common.base.Objects;
import java.util.Collection;
import net.npg.abattle.client.ClientConstants;
import net.npg.abattle.common.component.ComponentLookup;
import net.npg.abattle.common.error.ErrorComponent;
import net.npg.abattle.common.i18n.I18N;
import net.npg.abattle.common.model.client.ClientGame;
import net.npg.abattle.common.model.client.ClientPlayer;
import net.npg.abattle.common.model.client.impl.ClientPlayerImpl;
import net.npg.abattle.common.model.impl.ColorImpl;
import net.npg.abattle.common.utils.Validate;
import net.npg.abattle.communication.command.data.PlayerData;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

@SuppressWarnings("all")
public class UpdatePlayerList {
  protected static void update(final PlayerData[] players, final ClientGame game) {
    try {
      Validate.notNull(players);
      int _length = players.length;
      String _plus = ("Players joined:" + Integer.valueOf(_length));
      ClientConstants.LOG.info(_plus);
      for (final PlayerData player : players) {
        {
          ColorImpl color = new ColorImpl(player.r, player.g, player.b);
          Collection<ClientPlayer> _players = game.getPlayers();
          final Function1<ClientPlayer, Boolean> _function = new Function1<ClientPlayer, Boolean>() {
            @Override
            public Boolean apply(final ClientPlayer it) {
              int _id = it.getId();
              return Boolean.valueOf((_id == player.playerId));
            }
          };
          ClientPlayer exisitingPlayer = IterableExtensions.<ClientPlayer>findFirst(_players, _function);
          boolean _equals = Objects.equal(exisitingPlayer, null);
          if (_equals) {
            final ClientPlayerImpl newPlayer = new ClientPlayerImpl(player.playerName, player.playerId, color);
            game.addPlayer(newPlayer);
          } else {
            exisitingPlayer.setColor(color);
          }
        }
      }
      Collection<ClientPlayer> _players = game.getPlayers();
      final Function1<ClientPlayer, Boolean> _function = new Function1<ClientPlayer, Boolean>() {
        @Override
        public Boolean apply(final ClientPlayer existingPlayer) {
          final Function1<PlayerData, Boolean> _function = new Function1<PlayerData, Boolean>() {
            @Override
            public Boolean apply(final PlayerData it) {
              int _id = existingPlayer.getId();
              return Boolean.valueOf((it.playerId == _id));
            }
          };
          PlayerData _findFirst = IterableExtensions.<PlayerData>findFirst(((Iterable<PlayerData>)Conversions.doWrapArray(players)), _function);
          return Boolean.valueOf(Objects.equal(_findFirst, null));
        }
      };
      Iterable<ClientPlayer> _filter = IterableExtensions.<ClientPlayer>filter(_players, _function);
      final Procedure1<ClientPlayer> _function_1 = new Procedure1<ClientPlayer>() {
        @Override
        public void apply(final ClientPlayer it) {
          UpdatePlayerList.removePlayer(game, it);
        }
      };
      IterableExtensions.<ClientPlayer>forEach(_filter, _function_1);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public static void removePlayer(final ClientGame game, final ClientPlayer player) {
    game.removePlayer(player);
    ComponentLookup _instance = ComponentLookup.getInstance();
    ErrorComponent _component = _instance.<ErrorComponent>getComponent(ErrorComponent.class);
    String _name = player.getName();
    String _format = I18N.format("player_left", _name);
    _component.addError(_format, false);
  }
  
  public static void remove(final PlayerData data, final ClientGame game) {
    Collection<ClientPlayer> _players = game.getPlayers();
    final Function1<ClientPlayer, Boolean> _function = new Function1<ClientPlayer, Boolean>() {
      @Override
      public Boolean apply(final ClientPlayer it) {
        int _id = it.getId();
        return Boolean.valueOf((_id == data.playerId));
      }
    };
    final ClientPlayer player = IterableExtensions.<ClientPlayer>findFirst(_players, _function);
    ComponentLookup _instance = ComponentLookup.getInstance();
    ErrorComponent _component = _instance.<ErrorComponent>getComponent(ErrorComponent.class);
    String _name = player.getName();
    String _format = I18N.format("player_left", _name);
    _component.addError(_format, false);
  }
}
