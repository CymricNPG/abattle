package net.npg.abattle.client.view.selection.impl;

import net.npg.abattle.client.view.selection.SelectionComponent;
import net.npg.abattle.client.view.selection.SelectionModel;
import net.npg.abattle.client.view.selection.cone.ConeSelectionModelImpl;
import net.npg.abattle.client.view.selection.twocells.SelectionModelImpl;
import net.npg.abattle.common.component.Component;
import net.npg.abattle.common.component.ComponentLookup;
import net.npg.abattle.common.configuration.ConfigurationComponent;
import net.npg.abattle.common.configuration.GlobalOptionsData;
import net.npg.abattle.common.hex.HexBase;
import net.npg.abattle.common.model.client.ClientBoard;
import net.npg.abattle.common.model.client.ClientPlayer;
import net.npg.abattle.common.utils.impl.DisposeableImpl;

@SuppressWarnings("all")
public class SelectionComponentImpl extends DisposeableImpl implements Component, SelectionComponent {
  @Override
  public Class<? extends Component> getInterface() {
    return SelectionComponent.class;
  }
  
  @Override
  public SelectionModel createSelectionModel(final ClientBoard board, final HexBase hexBase, final ClientPlayer localPlayer) {
    SelectionModelImpl _xblockexpression = null;
    {
      ComponentLookup _instance = ComponentLookup.getInstance();
      ConfigurationComponent _component = _instance.<ConfigurationComponent>getComponent(ConfigurationComponent.class);
      GlobalOptionsData _globalOptions = _component.getGlobalOptions();
      final boolean config = _globalOptions.isConeSelection();
      SelectionModelImpl _xifexpression = null;
      if (config) {
        _xifexpression = new ConeSelectionModelImpl(board, hexBase, localPlayer);
      } else {
        _xifexpression = new SelectionModelImpl(board, hexBase, localPlayer);
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
}
