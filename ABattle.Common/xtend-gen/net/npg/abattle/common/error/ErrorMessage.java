package net.npg.abattle.common.error;

import net.npg.abattle.common.error.ExceptionCode;
import net.npg.abattle.common.utils.Validate;

@SuppressWarnings("all")
public class ErrorMessage {
  private final ExceptionCode code;
  
  private final boolean failed;
  
  private final String errorMessage;
  
  public ErrorMessage() {
    this.failed = false;
    ExceptionCode _exceptionCode = new ExceptionCode((-1), "no error");
    this.code = _exceptionCode;
    this.errorMessage = "";
  }
  
  public ErrorMessage(final boolean failed) {
    Validate.isFalse(failed);
    this.failed = failed;
    this.code = ExceptionCode.NO_EXCEPTION;
    this.errorMessage = "";
  }
  
  public ErrorMessage(final ExceptionCode code) {
    Validate.notNull(code);
    this.failed = true;
    this.code = code;
    this.errorMessage = "";
  }
  
  public ErrorMessage(final ExceptionCode code, final String errorMessage) {
    Validate.notNull(code);
    Validate.notNull(errorMessage);
    this.failed = true;
    this.code = code;
    this.errorMessage = errorMessage;
  }
  
  public boolean isFailed() {
    return this.failed;
  }
  
  public String getErrorMessage() {
    return this.errorMessage;
  }
  
  public ExceptionCode getErrorCode() {
    return this.code;
  }
}
