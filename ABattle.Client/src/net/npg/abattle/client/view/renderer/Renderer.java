/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.client.view.renderer;

import net.npg.abattle.common.utils.FloatPoint;

import com.badlogic.gdx.graphics.Color;

/**
 * takes a geometry and brings it to the screen.
 *
 * @author spatzenegger
 */
public interface Renderer {

	/**
	 * called at the Beginning of the rendering cycle.
	 */
	void begin();

	/**
	 * Draw arrow.
	 *
	 * @param start the start coordinate
	 * @param end the end coordinate
	 * @param color the color
	 */
	void drawArrow(FloatPoint start, FloatPoint end, Color color);

	/**
	 * Draw circle.
	 *
	 * @param center the center coordinate
	 * @param radius the radius
	 * @param color the color
	 */
	void drawCircle(FloatPoint center, float radius, Color color, boolean bold);

	void drawUnCircle(FloatPoint center, float radius, Color color);

	/**
	 * Draw filled circle.
	 *
	 * @param center the center coordinate
	 * @param radius the radius
	 * @param color the color
	 */
	void drawFilledCircle(FloatPoint center, float radius, Color color, int segments);

	/**
	 * Draw filled hex.
	 *
	 * @param center the center coordinate
	 * @param corners the corner coordinates
	 * @param color the color
	 */
	void drawFilledPoly(FloatPoint center, FloatPoint[] corners, Color color);

	/**
	 * Draw hex.
	 *
	 * @param corners the corner coordinates
	 * @param color the color
	 */
	void drawPoly(FloatPoint[] corners, Color color);

	void drawLines(FloatPoint[] corners, Color[] colors);

	void drawBox(FloatPoint start, FloatPoint end, Color color);

	void drawLine(FloatPoint start, FloatPoint end, Color color);

	void drawFilledPoly(FloatPoint center, FloatPoint[] corners, Color centerColor, Color[] colors);

	/**
	 * Draw text.
	 *
	 * @param text the text
	 * @param base the base coordinate
	 * @param color the color
	 */
	void drawText(String text, FloatPoint base, Color color);

	/**
	 * called at the end of the rendering cycle.
	 */
	void end();

}
