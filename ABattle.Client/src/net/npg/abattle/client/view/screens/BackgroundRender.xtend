package net.npg.abattle.client.view.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.MathUtils
import net.npg.abattle.client.view.boardscene.RendererUtils
import net.npg.abattle.client.view.renderer.impl.CameraImpl
import net.npg.abattle.client.view.renderer.impl.GLRenderer
import net.npg.abattle.common.hex.Hex
import net.npg.abattle.common.hex.HexBase
import net.npg.abattle.common.utils.FieldLoop
import net.npg.abattle.common.utils.FloatPoint
import net.npg.abattle.common.utils.IntPoint
import net.npg.abattle.common.utils.impl.DisposeableImpl

import static extension net.npg.abattle.common.utils.Transformation.*

class BackgroundRender extends DisposeableImpl {
	private static final int HEX_SIZE = 32

	private HexBase hexBase

	private GLRenderer renderer

	new(Camera camera) {
		hexBase = new HexBase(HEX_SIZE)
		val x = 17.0f * hexBase.side;
		val y = 17.0f * hexBase.height;
		val cam = new CameraImpl()
		cam.setNewViewSize(x,y)
		this.renderer = new GLRenderer(cam)
		
	}

	def void drawHexField() {
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		FieldLoop.visitAllFields(16,16,
			[ x, y |
				renderHex(x, y)
			])
	}

	private def void renderHex(int x, int y) {
		val hex = new Hex(IntPoint.from(x, y), hexBase)
		val corners = hex.computeCorners.map[c|c.translate(0,0 )].subList(0, 4)
		renderer.drawLines(corners, computeColors(corners))
	}

	private def Color[] computeColors(FloatPoint[] corners) {
		corners.map[corner|computeColor(corner.x, corner.y)]
	}

	private def Color computeColor(float x, float y) {
		val off = RendererUtils.getAnimationNumber(50, 360)
		val v = (MathUtils.sinDeg(x + off) * MathUtils.cosDeg(y + off)) * 0.8
		new Color(0, 0, v as float, 0)
	}

	override dispose() {
		super.dispose
		renderer.dispose
	}
}
