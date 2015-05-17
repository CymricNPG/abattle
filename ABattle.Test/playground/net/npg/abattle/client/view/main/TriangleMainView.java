/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.client.view.main;

import net.npg.abattle.client.ClientConstants;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

/**
 * @author cymric http://basic4gl.wikispaces.com/2D+Drawing+in+OpenGL
 */
public class TriangleMainView implements ApplicationListener {

	private Mesh mesh;
	private ShaderProgram meshShader;
	private ImmediateModeRenderer20 r;

	@Override
	public void create() {
		if (mesh == null) {
			final int count = 100000;
			mesh = new Mesh(true, count * 2, 0, new VertexAttribute(Usage.Position, 3, "a_position"));
			final float[] vertices = new float[count * 2 * 3];
			int idx = 0;
			for (int i = 0; i < count; i++) {
				final float xs = (float) Math.sin(Math.toRadians(i * 1.0));
				final float ys = (float) Math.cos(Math.toRadians(i * 1.0));
				final float xe = (float) Math.sin(Math.toRadians((i * 1.0) + 180));
				final float ye = (float) Math.cos(Math.toRadians((i * 1.0) + 180));
				vertices[idx++] = xs;
				vertices[idx++] = ys;
				vertices[idx++] = 0;
				vertices[idx++] = xe;
				vertices[idx++] = ye;
				vertices[idx++] = 0;
			}
			mesh.setVertices(vertices);
			// mesh.setIndices(new short[] { 0, 1, 2 });
			createShader();
			r = new ImmediateModeRenderer20(false, true, 0);
		}
	}

	@Override
	public void dispose() {
		ClientConstants.LOG.info("Window disposed");

	}

	@Override
	public void pause() {
		ClientConstants.LOG.info("App paused");
	}

	public static void main(final String[] args) {
		final TriangleMainView mainView = new TriangleMainView();
		new LwjglApplication(mainView, "TestView for ABattle", 16 * 32, 9 * 32);
	}

	private long lastTime;

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		final long current = System.currentTimeMillis();
		drawMesh();
		// drawImmediate();
		System.out.println(1000 / (current - lastTime) + " ! ");

		lastTime = current;
	}

	public void drawImmediate() {
		final Camera camera = new OrthographicCamera();
		for (int x = 0; x < 100; x++) {
			r.begin(camera.combined, GL20.GL_LINES);
			final int count = 1000;
			for (int i = 0; i < count; i++) {
				final float xs = (float) Math.sin(Math.toRadians(i * 1.0));
				final float ys = (float) Math.cos(Math.toRadians(i * 1.0));
				final float xe = (float) Math.sin(Math.toRadians((i * 1.0) + 180));
				final float ye = (float) Math.cos(Math.toRadians((i * 1.0) + 180));
				r.color(1.0f, 0.0f, 0.0f, 1.0f);
				r.vertex(xs, ys, 0);
				r.color(1.0f, 0.0f, 0.0f, 1.0f);
				r.vertex(xe, ye, 0);
			}

			// flush the renderer
			r.end();
		}
	}

	public void drawMesh() {
		// this should be called in render()
		if (mesh == null) {
			throw new IllegalStateException("drawMesh called before a mesh has been created.");
		}

		meshShader.begin();
		mesh.render(meshShader, GL20.GL_LINES);
		meshShader.end();
	}

	private void createShader() {
		// this shader tells opengl where to put things
		final String vertexShader = "attribute vec4 a_position;    \n" + //
				"void main()                   \n" + //
				"{                             \n" + //
				"   gl_Position = a_position;  \n" + //
				"}                             \n";

		// this one tells it what goes in between the points (i.e
		// colour/texture)
		final String fragmentShader = "#ifdef GL_ES                \n" + //
				"precision mediump float;    \n" + //
				"#endif                      \n" + //
				"void main()                 \n" + //
				"{                           \n" + //
				"  gl_FragColor = vec4(1.0,0.0,0.0,1.0);    \n" //
				+ "}";

		// make an actual shader from our strings
		meshShader = new ShaderProgram(vertexShader, fragmentShader);

		// check there's no shader compile errors
		if (!meshShader.isCompiled()) {
			throw new IllegalStateException(meshShader.getLog());
		}
	}

	@Override
	public void resize(final int width, final int height) {
		ClientConstants.LOG.info("App resized");
	}

	@Override
	public void resume() {
		ClientConstants.LOG.info("App resumed");
	}

}