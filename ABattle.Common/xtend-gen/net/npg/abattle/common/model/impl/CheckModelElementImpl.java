package net.npg.abattle.common.model.impl;

import net.npg.abattle.common.CommonConstants;
import net.npg.abattle.common.configuration.GameConfigurationData;
import net.npg.abattle.common.model.CheckModelElement;
import net.npg.abattle.common.model.GameConfiguration;
import net.npg.abattle.common.utils.IntPoint;
import net.npg.abattle.common.utils.Validate;

@SuppressWarnings("all")
class CheckModelElementImpl implements CheckModelElement {
  private GameConfiguration configuration;
  
  protected CheckModelElementImpl(final GameConfiguration configuration) {
    Validate.notNull(configuration);
    this.configuration = configuration;
  }
  
  @Override
  public void checkCoordinate(final IntPoint coordinate) {
    Validate.notNull(coordinate);
    Validate.inclusiveBetween(0, (CommonConstants.MAX_BOARD_SIZE - 1), coordinate.x);
    Validate.inclusiveBetween(0, (CommonConstants.MAX_BOARD_SIZE - 1), coordinate.y);
  }
  
  @Override
  public void checkStrength(final int strength) {
    GameConfigurationData _configuration = this.configuration.getConfiguration();
    int _maxCellStrength = _configuration.getMaxCellStrength();
    Validate.inclusiveBetween(0, _maxCellStrength, strength);
  }
  
  @Override
  public void checkHeight(final int height) {
    GameConfigurationData _configuration = this.configuration.getConfiguration();
    int _maxCellHeight = _configuration.getMaxCellHeight();
    int _minus = (_maxCellHeight - 1);
    Validate.inclusiveBetween(0, _minus, height);
  }
}
