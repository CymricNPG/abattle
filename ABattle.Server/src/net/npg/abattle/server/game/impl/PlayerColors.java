/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.server.game.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import net.npg.abattle.common.model.Color;
import net.npg.abattle.common.model.impl.ColorImpl;

/**
 * @author Cymric
 * 
 */
public class PlayerColors {

	public static Set<Color> colors = Collections.unmodifiableSet(new HashSet<Color>() {

		private static final long serialVersionUID = 1L;

		{
			add(ColorImpl.RED);
			add(ColorImpl.BLUE);
			add(ColorImpl.GRAY);
			add(ColorImpl.MAGENTA);
			add(ColorImpl.ORANGE);
			add(ColorImpl.CYAN);
			add(ColorImpl.YELLOW);
			add(ColorImpl.PINK);
			add(ColorImpl.WHITE);
		}
	});

}
