package net.npg.abattle.common.utils.impl;

import net.npg.abattle.common.utils.Disposeable;

@SuppressWarnings("all")
public class DisposeableImpl implements Disposeable {
  private boolean disposed = false;
  
  @Override
  public void dispose() {
    this.disposed = true;
  }
  
  @Override
  public boolean isDisposed() {
    return this.disposed;
  }
}
