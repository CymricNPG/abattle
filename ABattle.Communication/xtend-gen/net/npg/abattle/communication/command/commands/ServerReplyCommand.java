package net.npg.abattle.communication.command.commands;

import net.npg.abattle.common.utils.TransferData;
import net.npg.abattle.communication.command.ErrorCommand;
import net.npg.abattle.communication.command.commands.CommandImpl;
import net.npg.abattle.communication.service.common.BooleanResult;

@TransferData
@SuppressWarnings("all")
public class ServerReplyCommand extends CommandImpl implements ErrorCommand {
  public BooleanResult result;
  
  public int destinationId;
  
  @Override
  public int getDestinationId() {
    return this.destinationId;
  }
  
  public ServerReplyCommand(final BooleanResult result, final int destinationId, final boolean dropable, final int game) {
    this.result = result;
    this.destinationId = destinationId;
    this.dropable = dropable;
    this.game = game;
  }
  
  public ServerReplyCommand() {
  }
  
  public String toString() {
    return com.google.common.base.Objects.toStringHelper(this)
    .add("result",result)
    .add("destinationId",destinationId)
    .add("dropable",dropable)
    .add("game",game)
    .addValue(super.toString())
    .toString();
  }
}
