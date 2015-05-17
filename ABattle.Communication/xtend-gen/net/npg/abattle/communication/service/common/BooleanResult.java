package net.npg.abattle.communication.service.common;

import net.npg.abattle.common.utils.TransferData;

@TransferData
@SuppressWarnings("all")
public class BooleanResult {
  /**
   * The error message.
   */
  public String errorMessage;
  
  /**
   * The success.
   */
  public boolean success;
  
  public BooleanResult(final String errorMessage, final boolean success) {
    this.errorMessage = errorMessage;
    this.success = success;
  }
  
  public BooleanResult() {
  }
  
  public String toString() {
    return com.google.common.base.Objects.toStringHelper(this)
    .add("errorMessage",errorMessage)
    .add("success",success)
    .addValue(super.toString())
    .toString();
  }
}
