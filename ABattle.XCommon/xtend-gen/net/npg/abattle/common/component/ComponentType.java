package net.npg.abattle.common.component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import net.npg.abattle.common.component.ComponentTypeProcessor;
import org.eclipse.xtend.lib.macro.Active;

/**
 * annotated classes get the getInterface-Method
 */
@Active(ComponentTypeProcessor.class)
@Target(ElementType.TYPE)
public @interface ComponentType {
}
