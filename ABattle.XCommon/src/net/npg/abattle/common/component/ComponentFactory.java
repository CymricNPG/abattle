/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.common.component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import org.eclipse.xtend.lib.macro.Active;

/**
 * @author Cymric
 * 
 */
@Active(FactoryProcessor.class)
@Target(ElementType.TYPE)
public @interface ComponentFactory {

}
