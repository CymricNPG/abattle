package net.npg.abattle.client.view.screens;

import net.npg.abattle.client.view.screens.Icons;
import net.npg.abattle.client.view.screens.Screens;
import net.npg.abattle.client.view.screens.WinLooseScreen;

@SuppressWarnings("all")
public class WinScreen extends WinLooseScreen {
  @Override
  public void create() {
    this.create(Icons.Win);
  }
  
  @Override
  public Screens getType() {
    return Screens.Win;
  }
}
