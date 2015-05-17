package net.npg.abattle.client.local;

import com.google.common.base.Optional;
import net.npg.abattle.client.lan.ClientGameEnvironment;
import net.npg.abattle.common.error.ErrorMessage;
import net.npg.abattle.common.model.client.ClientGame;
import net.npg.abattle.common.utils.IntPoint;
import net.npg.abattle.common.utils.impl.DisposeableImpl;
import net.npg.abattle.communication.command.CommandQueueClient;
import net.npg.abattle.communication.command.GameCommand;
import net.npg.abattle.communication.command.commands.InitBoardCommand;

@SuppressWarnings("all")
public class ClientGameEnvironmentLocal extends DisposeableImpl implements ClientGameEnvironment {
  private ClientGame game;
  
  private CommandQueueClient commandQueue;
  
  private int gameStarted = 0;
  
  public ClientGameEnvironmentLocal(final ClientGame game, final CommandQueueClient commandQueue) {
    this.game = game;
    this.commandQueue = commandQueue;
  }
  
  @Override
  public ErrorMessage createGame(final IntPoint size, final int playerCount) {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }
  
  @Override
  public CommandQueueClient getCommandQueue() {
    return this.commandQueue;
  }
  
  @Override
  public ClientGame getGame() {
    return this.game;
  }
  
  @Override
  public ErrorMessage joinGame(final int gameId) {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }
  
  @Override
  public int login(final String name) {
    return 0;
  }
  
  @Override
  public void leave() {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }
  
  @Override
  public boolean checkGameStart() {
    return (this.gameStarted > 0);
  }
  
  @Override
  public void receivedCommand(final GameCommand command) {
    if ((command instanceof InitBoardCommand)) {
      this.gameStarted++;
    }
  }
  
  @Override
  public Optional<Integer> getRemainingCount() {
    return Optional.<Integer>absent();
  }
  
  @Override
  public void dispose() {
    super.dispose();
    this.commandQueue.dispose();
  }
  
  @Override
  public void ping() {
  }
}
