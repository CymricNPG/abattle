/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.common.model.impl;

import net.npg.abattle.common.model.Color;

/**
 * @author spatzenegger
 * 
 */
public class ColorImpl implements Color {

	/**
	 * The color white. In the default sRGB space.
	 */
	public final static Color white = new ColorImpl(255, 255, 255);

	/**
	 * The color white. In the default sRGB space.
	 * 
	 * @since 1.4
	 */
	public final static Color WHITE = white;

	/**
	 * The color light gray. In the default sRGB space.
	 */
	public final static Color lightGray = new ColorImpl(192, 192, 192);

	/**
	 * The color light gray. In the default sRGB space.
	 * 
	 * @since 1.4
	 */
	public final static Color LIGHT_GRAY = lightGray;

	/**
	 * The color gray. In the default sRGB space.
	 */
	public final static Color gray = new ColorImpl(128, 128, 128);

	/**
	 * The color gray. In the default sRGB space.
	 * 
	 * @since 1.4
	 */
	public final static Color GRAY = gray;

	/**
	 * The color dark gray. In the default sRGB space.
	 */
	public final static Color darkGray = new ColorImpl(64, 64, 64);

	/**
	 * The color dark gray. In the default sRGB space.
	 * 
	 * @since 1.4
	 */
	public final static Color DARK_GRAY = darkGray;

	/**
	 * The color black. In the default sRGB space.
	 */
	public final static Color black = new ColorImpl(0, 0, 0);

	/**
	 * The color black. In the default sRGB space.
	 * 
	 * @since 1.4
	 */
	public final static Color BLACK = black;

	/**
	 * The color red. In the default sRGB space.
	 */
	public final static Color red = new ColorImpl(255, 0, 0);

	/**
	 * The color red. In the default sRGB space.
	 * 
	 * @since 1.4
	 */
	public final static Color RED = red;

	/**
	 * The color pink. In the default sRGB space.
	 */
	public final static Color pink = new ColorImpl(255, 175, 175);

	/**
	 * The color pink. In the default sRGB space.
	 * 
	 * @since 1.4
	 */
	public final static Color PINK = pink;

	/**
	 * The color orange. In the default sRGB space.
	 */
	public final static Color orange = new ColorImpl(255, 200, 0);

	/**
	 * The color orange. In the default sRGB space.
	 * 
	 * @since 1.4
	 */
	public final static Color ORANGE = orange;

	/**
	 * The color yellow. In the default sRGB space.
	 */
	public final static Color yellow = new ColorImpl(255, 255, 0);

	/**
	 * The color yellow. In the default sRGB space.
	 * 
	 * @since 1.4
	 */
	public final static Color YELLOW = yellow;

	/**
	 * The color green. In the default sRGB space.
	 */
	public final static Color green = new ColorImpl(0, 255, 0);

	/**
	 * The color green. In the default sRGB space.
	 * 
	 * @since 1.4
	 */
	public final static Color GREEN = green;

	/**
	 * The color magenta. In the default sRGB space.
	 */
	public final static Color magenta = new ColorImpl(255, 0, 255);

	/**
	 * The color magenta. In the default sRGB space.
	 * 
	 * @since 1.4
	 */
	public final static Color MAGENTA = magenta;

	/**
	 * The color cyan. In the default sRGB space.
	 */
	public final static Color cyan = new ColorImpl(0, 255, 255);

	/**
	 * The color cyan. In the default sRGB space.
	 * 
	 * @since 1.4
	 */
	public final static Color CYAN = cyan;

	/**
	 * The color blue. In the default sRGB space.
	 */
	public final static Color blue = new ColorImpl(0, 0, 255);

	/**
	 * The color blue. In the default sRGB space.
	 * 
	 * @since 1.4
	 */
	public final static Color BLUE = blue;

	private final int b;

	private final int g;

	private final int r;

	/**
	 * 
	 * @param r
	 * @param g
	 * @param b
	 */
	public ColorImpl(final int r, final int g, final int b) {
		super();
		this.r = r;
		this.g = g;
		this.b = b;
	}

	@Override
	public int getB() {
		return b;
	}

	@Override
	public int getG() {
		return g;
	}

	@Override
	public int getR() {
		return r;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + b;
		result = prime * result + g;
		result = prime * result + r;
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ColorImpl)) {
			return false;
		}
		final ColorImpl other = (ColorImpl) obj;
		if (b != other.b) {
			return false;
		}
		if (g != other.g) {
			return false;
		}
		if (r != other.r) {
			return false;
		}
		return true;
	}

}
