/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.common.utils;

import net.npg.abattle.common.model.Color;

/**
 * @author spatzenegger
 * 
 */
public class ColorConvert {

	private static MyMap<Color, com.badlogic.gdx.graphics.Color> cache = new MyHashMap<Color, com.badlogic.gdx.graphics.Color>();

	public static com.badlogic.gdx.graphics.Color convert(final Color color) {
		final com.badlogic.gdx.graphics.Color gdxColor = cache.get(color);
		if (gdxColor != null) {
			return gdxColor;
		}
		final com.badlogic.gdx.graphics.Color newColor = new com.badlogic.gdx.graphics.Color(color.getR() / 255.0f, color.getG() / 255.0f,
				color.getB() / 255.0f, 1.0f);
		cache.put(color, newColor);
		return newColor;
	}

	public static Color convert(final com.badlogic.gdx.graphics.Color color) {
		return new Color() {

			@Override
			public int getB() {
				return (int) (color.b * 255);
			}

			@Override
			public int getG() {
				return (int) (color.g * 255);
			}

			@Override
			public int getR() {
				return (int) (color.r * 255);
			}

		};

	}
}
