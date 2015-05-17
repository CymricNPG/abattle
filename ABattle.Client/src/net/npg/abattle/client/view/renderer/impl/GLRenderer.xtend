/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.client.view.renderer.impl

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20
import net.npg.abattle.client.view.boardscene.RendererUtils
import net.npg.abattle.client.view.renderer.Camera
import net.npg.abattle.client.view.renderer.Renderer
import net.npg.abattle.common.utils.FloatPoint
import net.npg.abattle.common.utils.MutableFloatPoint
import net.npg.abattle.common.utils.Validate
import net.npg.abattle.common.component.ComponentLookup
import net.npg.abattle.client.asset.AssetManager
import com.badlogic.gdx.math.Vector2
import static net.npg.abattle.common.utils.MyArrayLiterals.*

/**
 * @author spatzenegger
 *
 */
public class GLRenderer implements Renderer {

	private Camera camera

	private BitmapFont font

	public ImmediateModeRenderer20 immediateModeRenderer

	private MutableFloatPoint tmpPoint = new MutableFloatPoint(0f, 0f)

	private MutableFloatPoint tmpPoint2 = new MutableFloatPoint(0f, 0f)

	private SpriteBatch spriteBatch

	private final Vector2 tmp = new Vector2();

	public new(Camera camera) {
		Validate.notNull(camera)
		this.camera = camera
		immediateModeRenderer = new ImmediateModeRenderer20(50000, false, true, 0)
		spriteBatch = new SpriteBatch()
		font = ComponentLookup.getInstance.getComponent(AssetManager).smallFont
	}

	private def void addColoredVertex(Color color, float x, float y) {
		immediateModeRenderer.color(color)
		immediateModeRenderer.vertex(x, y, 0)
	}

	private def void addColoredVertex(Color color, FloatPoint coordinate) {
		addColoredVertex(color, coordinate.x, coordinate.y)
	}

	public override void begin() {
		spriteBatch.setProjectionMatrix(camera.combined)
		enableAlphaBlending()
		clearBackground()
	}

	private def void clearBackground() {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
	}

	public override void drawArrow(FloatPoint start, FloatPoint end, Color color) {
		drawColoredLine(start, end, color)
		drawArrowTriangle(end, start)
	}

	def drawArrowTriangle(FloatPoint end, FloatPoint start) {
		val xdiff = end.x - start.x
		val ydiff = end.y - start.y
		val xm = start.x + xdiff / 2
		val ym = start.y + ydiff / 2
		val xs = xm + xdiff * 0.1f
		val ys = ym + ydiff * 0.1f
		val xab1 = -ydiff * 0.1f + xm
		val yab1 = xdiff * 0.1f + ym
		val xab2 = ydiff * 0.1f + xm
		val yab2 = -xdiff * 0.1f + ym
		immediateModeRenderer.begin(camera.combined, GL20.GL_TRIANGLES)
		addColoredVertex(Color.BLACK, xab1, yab1)
		addColoredVertex(Color.BLACK, xs, ys)
		addColoredVertex(Color.BLACK, xab2, yab2)
		immediateModeRenderer.end()
		immediateModeRenderer.begin(camera.combined, GL20.GL_LINE_LOOP)
		addColoredVertex(Color.WHITE, xab1, yab1)
		addColoredVertex(Color.WHITE, xs, ys)
		addColoredVertex(Color.WHITE, xab2, yab2)
		immediateModeRenderer.end()
	}

	public def void drawColoredLine(FloatPoint start, FloatPoint end, Color color) {
		val Color[] colors = createArray(7)
		val x = floatArray(7)
		val y = floatArray(7)

		val vx = (end.x - start.x) / 5
		val vy = (end.y - start.y) / 5
		val animPos = RendererUtils.getAnimationNumber(6, 10)
		val offx = vx * (animPos % 5) / 5.0f
		val offy = vy * (animPos % 5) / 5.0f
		var i = -1
		val off = if(animPos >= 5) 1 else 0
		val white = Color.WHITE.cpy().sub(color)
		white.a = 0.5f
		colors.set(i + 1, colorArrow(color, white, i + off))
		x.set(i + 1, start.x)
		y.set(i + 1, start.y)
		for (i++; i <= 4; i++) {
			colors.set(i + 1, colorArrow(color, white, i + off))
			x.set(i + 1, start.x + vx * i + offx)
			y.set(i + 1, start.y + vy * i + offy)
		}
		colors.set(i + 1, colorArrow(color, white, i + off))
		x.set(i + 1, end.x)
		y.set(i + 1, end.y)

		for(var pos = 0; pos < 6; pos++) {
			tmpPoint.set(x.get(pos),y.get(pos))
			tmpPoint2.set(x.get(pos+1),y.get(pos+1))
			rectLine(tmpPoint,tmpPoint2, 0.1f,colors.get(pos))
		}

	}

	private def Color colorArrow(Color one, Color two, int i) {
		return if(i % 2 == 0) one else two
	}

	/** Draws a line using a rotated rectangle, where with one edge is centered at x1, y1 and the opposite edge centered at x2, y2. */
	private def rectLine(MutableFloatPoint s, MutableFloatPoint e, float width, Color color) {
		immediateModeRenderer.begin(camera.combined, GL20.GL_TRIANGLES)
		val t = tmp.set(e.y - s.y, s.x - e.x).nor();
		val tx = t.x * width * 0.5f;
		val ty = t.y * width * 0.5f;
		addColoredVertex(color, s.x + tx, s.y + ty)
		addColoredVertex(color, s.x - tx, s.y - ty)
		addColoredVertex(color, e.x + tx, e.y + ty)

		addColoredVertex(color, e.x - tx, e.y - ty)
		addColoredVertex(color, e.x + tx, e.y + ty)
		addColoredVertex(color, s.x - tx, s.y - ty)
		immediateModeRenderer.end
	}

	public override void drawUnCircle(FloatPoint center, float radius, Color color) {
		var maxRadius = true;
		val step = 360 / 18
		for (var angle = 0; angle < 360; angle += step) {
			calcCirclePoint(tmpPoint, angle, center, radius * (if(maxRadius) 1.0f else 0.8f))
			calcCirclePoint(tmpPoint2, angle + step, center, radius * (if(!maxRadius) 1.0f else 0.8f))
			rectLine(tmpPoint, tmpPoint2, 0.1f, color)
			maxRadius = ! maxRadius
		}

	}

	public override void drawCircle(FloatPoint center, float radius, Color color, boolean bold) {
		if(bold) {
			drawBoldCircle(center, radius, color)
		} else {
			drawFineCircle(center, radius, color)
		}

	}

	def drawBoldCircle(FloatPoint center, float radius, Color color) {
		val step = 360 / 18
		for (var angle = 0; angle < 360; angle += step) {
			calcCirclePoint(tmpPoint, angle, center, radius)
			calcCirclePoint(tmpPoint2, angle + step, center, radius)
			rectLine(tmpPoint, tmpPoint2, 0.1f, color)
		}
	}

	private def  void drawFineCircle(FloatPoint center, float radius, Color color) {
		immediateModeRenderer.begin(camera.combined, GL20.GL_LINE_LOOP)
		addCircleVertices(center, radius, color, false)
		immediateModeRenderer.end()
	}

	private def addCircleVertices(FloatPoint center, float radius, Color color, boolean addFirstTwice) {
		val step = 360 / 18
		for (var angle = 0; angle < (360 + if(addFirstTwice) 1 else 0); angle += step) {
			calcCirclePoint(tmpPoint, angle, center, radius)
			addColoredVertex(color, tmpPoint.x, tmpPoint.y)
		}
	}

	private def void calcCirclePoint(MutableFloatPoint returnPoint, float angle, FloatPoint center, float radius) {
		val radiansAngle = Math.toRadians(angle)
		returnPoint.x = (radius * Math.cos(radiansAngle) + center.x) as float
		returnPoint.y = (radius * Math.sin(radiansAngle) + center.y) as float
	}

	public override void drawFilledCircle(FloatPoint center, float radius, Color color, int segments) {
		immediateModeRenderer.begin(camera.combined, GL20.GL_TRIANGLE_FAN)
		addColoredVertex(color, center)
		addCircleVertices(center, radius, color, true)
		immediateModeRenderer.end()
	}

	public override void drawFilledPoly(FloatPoint center, FloatPoint[] corners, Color color) {
		immediateModeRenderer.begin(camera.combined, GL20.GL_TRIANGLE_FAN)
		addColoredVertex(color, center)
		for (FloatPoint corner : corners) {
			addColoredVertex(color, corner)
		}
		addColoredVertex(color, corners.get(0))
		immediateModeRenderer.end()
	}

	public override void drawFilledPoly(FloatPoint center, FloatPoint[] corners, Color centerColor, Color[] colors) {
		immediateModeRenderer.begin(camera.combined, GL20.GL_TRIANGLE_FAN)
		addColoredVertex(centerColor, center)
		for (var i = 0; i < corners.length; i++) {
			addColoredVertex(colors.get(i), corners.get(i))
		}
		addColoredVertex(colors.get(0), corners.get(0))
		immediateModeRenderer.end()
	}

	public override void drawPoly(FloatPoint[] corners, Color color) {
		immediateModeRenderer.begin(camera.combined, GL20.GL_LINE_LOOP)
		for (FloatPoint corner : corners) {
			addColoredVertex(color, corner)
		}
		immediateModeRenderer.end()
	}

	public override void drawLines(FloatPoint[] corners, Color[] colors) {
		immediateModeRenderer.begin(camera.combined, GL20.GL_LINE_STRIP)
		for (var i = 0; i < corners.length; i++) {
			addColoredVertex(colors.get(i), corners.get(i))
		}
		immediateModeRenderer.end()
	}

	public override void drawLine(FloatPoint start, FloatPoint end, Color color) {
		immediateModeRenderer.begin(camera.combined, GL20.GL_LINE_STRIP)
		addColoredVertex(color, start)
		addColoredVertex(color, end)
		immediateModeRenderer.end()
	}

	public override void drawText(String text, FloatPoint base, Color color) {
		spriteBatch.begin()
		font.setColor(color)
		font.data.setScale(0.02f)
		font.setUseIntegerPositions(false)
		font.draw(spriteBatch, text, base.x, base.y)
		spriteBatch.end()

	}

	private def void enableAlphaBlending() {
		Gdx.gl.glEnable(GL20.GL_BLEND) // Enable blending.
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)
	}

	public override void end() {
	}

	override drawBox(FloatPoint start, FloatPoint end, Color color) {
		immediateModeRenderer.begin(camera.combined, GL20.GL_TRIANGLE_STRIP)
		addColoredVertex(color, start)
		addColoredVertex(color, start.x, end.y)
		addColoredVertex(color, end.x, start.y)
		addColoredVertex(color, end)

		immediateModeRenderer.end()
	}

	def dispose() {
		immediateModeRenderer.dispose
		immediateModeRenderer = null
	}

}
