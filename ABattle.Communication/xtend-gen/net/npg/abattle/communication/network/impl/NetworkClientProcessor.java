package net.npg.abattle.communication.network.impl;

import com.google.common.base.Optional;
import net.npg.abattle.common.error.BaseException;
import net.npg.abattle.common.utils.Validate;
import net.npg.abattle.communication.CommunicationConstants;
import net.npg.abattle.communication.command.CommandProcessor;
import net.npg.abattle.communication.command.ErrorCommand;
import net.npg.abattle.communication.command.GameCommand;
import net.npg.abattle.communication.network.NetworkClient;
import org.eclipse.xtext.xbase.lib.Exceptions;

/**
 * sends all messages to the server, only one client can exists in a application
 * if any error happens, an exception is thrown!
 */
@SuppressWarnings("all")
public class NetworkClientProcessor implements CommandProcessor<GameCommand> {
  private NetworkClient client;
  
  public NetworkClientProcessor(final NetworkClient client) {
    Validate.notNull(client);
    this.client = client;
  }
  
  @Override
  public Optional<ErrorCommand> execute(final GameCommand command, final int destination) {
    try {
      Optional<ErrorCommand> _xblockexpression = null;
      {
        boolean _isDead = this.client.isDead();
        if (_isDead) {
          throw new BaseException(CommunicationConstants.NETWORK_SERVER_UNREACHABLE, "Server communication is dead.");
        }
        Optional<ErrorCommand> _xtrycatchfinallyexpression = null;
        try {
          Optional<ErrorCommand> _xblockexpression_1 = null;
          {
            this.client.send(command);
            _xblockexpression_1 = Optional.<ErrorCommand>absent();
          }
          _xtrycatchfinallyexpression = _xblockexpression_1;
        } catch (final Throwable _t) {
          if (_t instanceof Exception) {
            final Exception e = (Exception)_t;
            if ((e instanceof BaseException)) {
              throw e;
            } else {
              throw new BaseException(CommunicationConstants.NETWORK_SERVER_UNREACHABLE, "Server communication failed.", e);
            }
          } else {
            throw Exceptions.sneakyThrow(_t);
          }
        }
        _xblockexpression = _xtrycatchfinallyexpression;
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
