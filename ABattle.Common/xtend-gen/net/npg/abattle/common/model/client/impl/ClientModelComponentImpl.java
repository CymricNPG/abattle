package net.npg.abattle.common.model.client.impl;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import net.npg.abattle.common.error.BaseException;
import net.npg.abattle.common.model.ModelExceptionCode;
import net.npg.abattle.common.model.client.ClientIdRegister;
import net.npg.abattle.common.model.client.ClientPlayer;
import net.npg.abattle.common.model.client.impl.ClientPlayerImpl;
import net.npg.abattle.common.model.impl.ColorImpl;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.StringExtensions;

/**
 * TODO merge with gameparameters?
 */
@SuppressWarnings("all")
public class ClientModelComponentImpl implements ClientIdRegister {
  private Semaphore waitForId;
  
  private int clientId;
  
  private String errorMessage;
  
  @Accessors
  private String name;
  
  public ClientModelComponentImpl() {
    this.resetClientPlayer();
  }
  
  @Override
  public ClientPlayer getLocalPlayer() throws BaseException {
    boolean _and = false;
    if (!(this.clientId >= 0)) {
      _and = false;
    } else {
      boolean _isNullOrEmpty = StringExtensions.isNullOrEmpty(this.errorMessage);
      _and = _isNullOrEmpty;
    }
    if (_and) {
      return new ClientPlayerImpl(this.name, this.clientId, ColorImpl.BLACK);
    } else {
      throw new BaseException(ModelExceptionCode.CLIENT_NOT_LOOGEDIN, this.errorMessage);
    }
  }
  
  @Override
  public void setClientId(final int id) {
    this.clientId = id;
    this.waitForId.release();
  }
  
  @Override
  public void setFailed(final String message) {
    this.errorMessage = message;
    this.waitForId.release();
  }
  
  @Override
  public void waitForClientId() {
    try {
      boolean _tryAcquire = this.waitForId.tryAcquire(3, TimeUnit.SECONDS);
      boolean _not = (!_tryAcquire);
      if (_not) {
        throw new BaseException(ModelExceptionCode.LOGIN_TIMEOUT);
      }
      boolean _isNullOrEmpty = StringExtensions.isNullOrEmpty(this.errorMessage);
      boolean _not_1 = (!_isNullOrEmpty);
      if (_not_1) {
        throw new BaseException(ModelExceptionCode.LOGIN_FAILED, this.errorMessage);
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Override
  public synchronized void resetClientPlayer() {
    this.clientId = (-1);
    this.errorMessage = null;
    Semaphore _semaphore = new Semaphore(0);
    this.waitForId = _semaphore;
  }
  
  @Pure
  public String getName() {
    return this.name;
  }
  
  public void setName(final String name) {
    this.name = name;
  }
}
