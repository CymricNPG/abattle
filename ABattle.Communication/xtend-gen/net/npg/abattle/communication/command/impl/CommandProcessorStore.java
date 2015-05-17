package net.npg.abattle.communication.command.impl;

import com.google.common.base.Optional;
import net.npg.abattle.communication.command.CommandProcessor;
import net.npg.abattle.communication.command.CommandType;
import org.eclipse.xtend.lib.Data;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringHelper;

@Data
@SuppressWarnings("all")
class CommandProcessorStore {
  private final CommandProcessor<?> _processor;
  
  private final Optional<Class<?>> _processedClass;
  
  private final CommandType _processedType;
  
  public CommandProcessorStore(final CommandProcessor<?> processor, final Optional<Class<?>> processedClass, final CommandType processedType) {
    super();
    this._processor = processor;
    this._processedClass = processedClass;
    this._processedType = processedType;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this._processor== null) ? 0 : this._processor.hashCode());
    result = prime * result + ((this._processedClass== null) ? 0 : this._processedClass.hashCode());
    result = prime * result + ((this._processedType== null) ? 0 : this._processedType.hashCode());
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
    CommandProcessorStore other = (CommandProcessorStore) obj;
    if (this._processor == null) {
      if (other._processor != null)
        return false;
    } else if (!this._processor.equals(other._processor))
      return false;
    if (this._processedClass == null) {
      if (other._processedClass != null)
        return false;
    } else if (!this._processedClass.equals(other._processedClass))
      return false;
    if (this._processedType == null) {
      if (other._processedType != null)
        return false;
    } else if (!this._processedType.equals(other._processedType))
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
  public CommandProcessor<?> getProcessor() {
    return this._processor;
  }
  
  @Pure
  public Optional<Class<?>> getProcessedClass() {
    return this._processedClass;
  }
  
  @Pure
  public CommandType getProcessedType() {
    return this._processedType;
  }
}
