package net.npg.abattle.client.view.boardscene;

import org.junit.Assert;
import org.junit.Test;

import com.badlogic.gdx.graphics.Color;

public class RenderUtilTest {
	@Test
	public final void testUtils() throws Exception {
		for (int i = 0; i < 25; i++) {
			final int x = RendererUtils.getAnimationNumber(0.5f, 10);
			System.out.println(x);
			Thread.sleep(100);
		}
		Assert.assertEquals(1, RendererUtils.getAnimationNumber(0.5f, 10));
	}

	@Test
	public void testColor() {
		for (int animPos = 0; animPos < 10; animPos++) {
			System.out.println(">" + animPos);
			final float vx = 20;
			final float offx = vx * (animPos % 5) / 5.0f;
			System.out.println("->" + offx);
			int i = -1;
			int c = animPos >= 5 ? 0 : -1;
			System.out.println(colorArrow(Color.BLACK, Color.WHITE, c) + " - " + 0);
			c++;
			for (i++; i <= 4; i++) {
				System.out.println(colorArrow(Color.BLACK, Color.WHITE, c) + " - " + (vx * i + offx));
				c++;
			}
			System.out.println(colorArrow(Color.BLACK, Color.WHITE, c) + " - " + 100);
			System.out.println("----------------------------------------------");
		}
	}

	private Color colorArrow(final Color one, final Color two, final int i) {
		return i % 2 == 0 ? one : two;
	}
}
