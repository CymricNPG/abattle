package net.npg.abattle.communication.command.commands;

import net.npg.abattle.common.utils.TransferData;
import net.npg.abattle.communication.command.DeadCommand;
import net.npg.abattle.communication.command.commands.CommandImpl;

@TransferData
@SuppressWarnings("all")
public class DeadCommandImpl extends CommandImpl implements DeadCommand {
  public String errorMessage;
  
  @Override
  public String getErrorMessage() {
    return this.errorMessage;
  }
  
  public DeadCommandImpl(final String errorMessage, final boolean dropable, final int game) {
    this.errorMessage = errorMessage;
    this.dropable = dropable;
    this.game = game;
  }
  
  public DeadCommandImpl() {
  }
  
  public String toString() {
    return com.google.common.base.Objects.toStringHelper(this)
    .add("errorMessage",errorMessage)
    .add("dropable",dropable)
    .add("game",game)
    .addValue(super.toString())
    .toString();
  }
}
