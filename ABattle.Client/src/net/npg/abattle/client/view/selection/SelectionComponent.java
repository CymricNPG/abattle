package net.npg.abattle.client.view.selection;

import net.npg.abattle.common.component.Component;
import net.npg.abattle.common.component.Reusable;
import net.npg.abattle.common.hex.HexBase;
import net.npg.abattle.common.model.client.ClientBoard;
import net.npg.abattle.common.model.client.ClientPlayer;

public interface SelectionComponent extends Component, Reusable {

	SelectionModel createSelectionModel(ClientBoard board, HexBase hexBase, ClientPlayer localPlayer);
}
