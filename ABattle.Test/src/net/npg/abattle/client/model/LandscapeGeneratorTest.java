/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.client.model;

import net.npg.abattle.common.utils.Landscape;

import org.junit.Test;

/**
 * @author cymric
 * 
 */
public class LandscapeGeneratorTest {

	@Test
	public void generateLandscape() {
		final int maxHeight = 3;
		final int xsize = 10;
		final int ysize = 10;
		final int field[][] = new int[xsize][ysize];
		Landscape.initField(field, xsize, ysize);
		Landscape.fillField(field, xsize, ysize, maxHeight);
		Landscape.printField(field, xsize, ysize);
	}

}
