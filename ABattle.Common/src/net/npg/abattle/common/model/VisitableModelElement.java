/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.common.model;

/**
 * @author spatzenegger
 * 
 */
public interface VisitableModelElement {

	public void accept(ModelVisitor visitor);
}
