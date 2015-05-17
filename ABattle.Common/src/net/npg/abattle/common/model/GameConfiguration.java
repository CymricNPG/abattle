package net.npg.abattle.common.model;

import net.npg.abattle.common.configuration.GameConfigurationData;

public interface GameConfiguration extends ModelElement, VisitableModelElement {

	int getXSize();

	int getYSize();

	CheckModelElement getChecker();

	GameConfigurationData getConfiguration();
}
