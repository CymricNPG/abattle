/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.common.model.impl;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * @author spatzenegger
 * 
 */
public class IDElementTest {

	/**
	 * Test method for {@link net.npg.abattle.common.model.impl.IDElementImpl#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject() {
		final IDElementImpl e1 = new IDElementImpl();
		final IDElementImpl e2 = new IDElementImpl();
		assertThat(e1, not(e2));
	}

	/**
	 * Test method for {@link net.npg.abattle.common.model.impl.IDElementImpl#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObjectAuto() {
		final IDElementImpl e1 = new IDElementImpl(324);
		final IDElementImpl e2 = new IDElementImpl(324);
		assertEquals(e1, e2);
	}

	/**
	 * Test method for {@link net.npg.abattle.common.model.impl.IDElementImpl#getId()}.
	 */
	@Test
	public void testGetId() {
		final IDElementImpl e1 = new IDElementImpl(324);
		final IDElementImpl e2 = new IDElementImpl(325);
		assertThat(e1.getId(), not(e2.getId()));
		assertEquals(324, e1.getId());
		assertEquals(325, e2.getId());
	}

	/**
	 * Test method for {@link net.npg.abattle.common.model.impl.IDElementImpl#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		final IDElementImpl e1 = new IDElementImpl(324);
		final IDElementImpl e2 = new IDElementImpl(324);
		assertEquals(e1.hashCode(), e2.hashCode());
	}

	/**
	 * Test method for {@link net.npg.abattle.common.model.impl.IDElementImpl#hashCode()}.
	 */
	@Test
	public void testHashCodeAuto() {
		final IDElementImpl e1 = new IDElementImpl();
		final IDElementImpl e2 = new IDElementImpl();
		assertThat(e1.hashCode(), not(e2.hashCode()));
	}

	/**
	 * Test method for {@link net.npg.abattle.common.model.impl.IDElementImpl#IDElementImpl()}.
	 */
	@Test
	public void testIDElementImpl() {
		final IDElementImpl e1 = new IDElementImpl(0);
		final IDElementImpl e2 = new IDElementImpl(0);
		assertEquals(e1, e2);
		assertEquals(e1.getId(), e2.getId());
	}

	/**
	 * Test method for {@link net.npg.abattle.common.model.impl.IDElementImpl#IDElementImpl()}.
	 */
	@Test
	public void testIDElementImplAuto() {
		final IDElementImpl e1 = new IDElementImpl();
		final IDElementImpl e2 = new IDElementImpl();
		assertThat(e1, not(e2));
	}

}
