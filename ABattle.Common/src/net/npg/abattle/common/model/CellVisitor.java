/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.common.model;

/**
 * (not the design pattern)
 * 
 * @author cymric
 * 
 */
public interface CellVisitor<PLAYERTYPE extends Player, CELLTYPE extends Cell> {

	void visitCell(CELLTYPE cell);
}
