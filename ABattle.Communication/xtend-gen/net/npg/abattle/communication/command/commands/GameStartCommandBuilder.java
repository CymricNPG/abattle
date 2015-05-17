package net.npg.abattle.communication.command.commands;

import net.npg.abattle.communication.command.commands.GameStartCommand;
import net.npg.abattle.communication.command.data.CellUpdateData;
import net.npg.abattle.communication.command.data.PlayerData;

@SuppressWarnings("all")
public class GameStartCommandBuilder {
  private PlayerData[] players;
  
  public GameStartCommandBuilder players(final PlayerData[] players) {
    this.players=players;
    return this;
  }
  
  private CellUpdateData[][] cellUpdates;
  
  public GameStartCommandBuilder cellUpdates(final CellUpdateData[][] cellUpdates) {
    this.cellUpdates=cellUpdates;
    return this;
  }
  
  private boolean dropable;
  
  public GameStartCommandBuilder dropable(final boolean dropable) {
    this.dropable=dropable;
    return this;
  }
  
  private int game;
  
  public GameStartCommandBuilder game(final int game) {
    this.game=game;
    return this;
  }
  
  public GameStartCommand build() {
    return new GameStartCommand(
    players,cellUpdates,dropable,game
    );
  }
}
