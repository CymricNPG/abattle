/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.common.model;

/**
 * @author Cymric
 * 
 */
public interface Link<T extends Cell> extends ModelElement, VisitableModelElement {

	T getDestinationCell();

	T getSourceCell();

}
