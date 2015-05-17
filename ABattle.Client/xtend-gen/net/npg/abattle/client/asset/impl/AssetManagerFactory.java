package net.npg.abattle.client.asset.impl;

import net.npg.abattle.client.asset.AssetManager;

@SuppressWarnings("all")
public class AssetManagerFactory {
  public static AssetManager create() {
    net.npg.abattle.client.asset.AssetManager  instance = new net.npg.abattle.client.asset.impl.AssetManagerImpl();
    return net.npg.abattle.common.component.ComponentLookup.getInstance().registerComponent(instance);
  }
}
