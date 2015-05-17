/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.client.view.renderer;

import net.npg.abattle.common.utils.FloatPoint;

import com.badlogic.gdx.math.Matrix4;

/**
 * The Interface Camera.
 * 
 * @author spatzenegger
 */
public interface Camera {

	public Matrix4 getCombined();

	/**
	 * Sets a new view size (size of the field which is displayed).
	 * 
	 * @param xsize the xsize
	 * @param ysize the ysize
	 */
	public void setNewViewSize(final float xsize, final float ysize);

	/**
	 * Unproject screen coordinates to world coordinates.
	 * 
	 * @param x the x
	 * @param y the y
	 * @return the float point
	 */
	public FloatPoint unproject(final int x, final int y);
}
