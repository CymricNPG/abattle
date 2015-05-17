/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.client.view.renderer.impl;

import net.npg.abattle.client.view.renderer.Camera;
import net.npg.abattle.common.utils.FloatPoint;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

/**
 * @author spatzenegger
 * 
 */
public class CameraImpl implements Camera {

	private com.badlogic.gdx.graphics.Camera camera;

	public CameraImpl() {

	}

	public CameraImpl(final com.badlogic.gdx.graphics.Camera camera) {
		this.camera = camera;
	}

	private com.badlogic.gdx.graphics.Camera getNativeCamera() {
		if (camera == null) {
			try {
				camera = new OrthographicCamera(1.0f, 1.0f);
			} catch (final NullPointerException e) {
				throw new RuntimeException("GDX not initialized!", e);
			}
		}
		return camera;
	}

	@Override
	public void setNewViewSize(final float xsize, final float ysize) {
		final com.badlogic.gdx.graphics.Camera camera = getNativeCamera();
		camera.viewportWidth = xsize;
		camera.viewportHeight = ysize;
		camera.position.set(xsize / 2, ysize / 2, 0);
		camera.update();
	}

	@Override
	public FloatPoint unproject(final int x, final int y) {
		final com.badlogic.gdx.graphics.Camera camera = getNativeCamera();
		final Vector3 worldCoordinates = new Vector3(x, y, 0);
		camera.unproject(worldCoordinates);
		return new FloatPoint(worldCoordinates.x, worldCoordinates.y);
	}

	@Override
	public Matrix4 getCombined() {
		return getNativeCamera().combined;
	}
}
