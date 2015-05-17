package net.npg.abattle.communication.service.common;

import net.npg.abattle.communication.service.common.BooleanResult;

@SuppressWarnings("all")
public class BooleanResultBuilder {
  private String errorMessage;
  
  public BooleanResultBuilder errorMessage(final String errorMessage) {
    this.errorMessage=errorMessage;
    return this;
  }
  
  private boolean success;
  
  public BooleanResultBuilder success(final boolean success) {
    this.success=success;
    return this;
  }
  
  public BooleanResult build() {
    return new BooleanResult(
    errorMessage,success
    );
  }
}
