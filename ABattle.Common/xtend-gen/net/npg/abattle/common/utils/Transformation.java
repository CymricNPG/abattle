package net.npg.abattle.common.utils;

import net.npg.abattle.common.utils.FloatPoint;

@SuppressWarnings("all")
public class Transformation {
  public static FloatPoint translate(final FloatPoint source, final float x, final float y) {
    return new FloatPoint((source.x + x), (source.y + y));
  }
}
