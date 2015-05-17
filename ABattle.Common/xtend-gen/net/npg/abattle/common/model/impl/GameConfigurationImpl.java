package net.npg.abattle.common.model.impl;

import net.npg.abattle.common.configuration.GameConfigurationData;
import net.npg.abattle.common.model.CheckModelElement;
import net.npg.abattle.common.model.GameConfiguration;
import net.npg.abattle.common.model.ModelVisitor;
import net.npg.abattle.common.model.impl.CheckModelElementImpl;
import net.npg.abattle.common.model.impl.IDElementImpl;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * TODO builder pattern
 */
@SuppressWarnings("all")
public class GameConfigurationImpl extends IDElementImpl implements GameConfiguration {
  @Accessors
  private int xSize;
  
  @Accessors
  private int ySize;
  
  @Accessors
  private GameConfigurationData configuration;
  
  private CheckModelElement checker;
  
  public GameConfigurationImpl() {
    CheckModelElementImpl _checkModelElementImpl = new CheckModelElementImpl(this);
    this.checker = _checkModelElementImpl;
  }
  
  @Override
  public CheckModelElement getChecker() {
    return this.checker;
  }
  
  @Override
  public void accept(final ModelVisitor visitor) {
    visitor.visit(this);
  }
  
  @Pure
  public int getXSize() {
    return this.xSize;
  }
  
  public void setXSize(final int xSize) {
    this.xSize = xSize;
  }
  
  @Pure
  public int getYSize() {
    return this.ySize;
  }
  
  public void setYSize(final int ySize) {
    this.ySize = ySize;
  }
  
  @Pure
  public GameConfigurationData getConfiguration() {
    return this.configuration;
  }
  
  public void setConfiguration(final GameConfigurationData configuration) {
    this.configuration = configuration;
  }
}
