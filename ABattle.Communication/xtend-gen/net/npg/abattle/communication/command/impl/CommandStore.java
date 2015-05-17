package net.npg.abattle.communication.command.impl;

import java.util.List;
import net.npg.abattle.communication.command.CommandType;
import net.npg.abattle.communication.command.GameCommand;
import org.eclipse.xtend.lib.Data;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringHelper;

@Data
@SuppressWarnings("all")
class CommandStore {
  private final CommandType _commandType;
  
  private final GameCommand _command;
  
  private final List<Integer> _destination;
  
  public CommandStore(final CommandType commandType, final GameCommand command, final List<Integer> destination) {
    super();
    this._commandType = commandType;
    this._command = command;
    this._destination = destination;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this._commandType== null) ? 0 : this._commandType.hashCode());
    result = prime * result + ((this._command== null) ? 0 : this._command.hashCode());
    result = prime * result + ((this._destination== null) ? 0 : this._destination.hashCode());
    return result;
  }
  
  @Override
  @Pure
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    CommandStore other = (CommandStore) obj;
    if (this._commandType == null) {
      if (other._commandType != null)
        return false;
    } else if (!this._commandType.equals(other._commandType))
      return false;
    if (this._command == null) {
      if (other._command != null)
        return false;
    } else if (!this._command.equals(other._command))
      return false;
    if (this._destination == null) {
      if (other._destination != null)
        return false;
    } else if (!this._destination.equals(other._destination))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public String toString() {
    String result = new ToStringHelper().toString(this);
    return result;
  }
  
  @Pure
  public CommandType getCommandType() {
    return this._commandType;
  }
  
  @Pure
  public GameCommand getCommand() {
    return this._command;
  }
  
  @Pure
  public List<Integer> getDestination() {
    return this._destination;
  }
}
