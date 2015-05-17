package net.npg.abattle.common.configuration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import net.npg.abattle.common.configuration.PropertyStoredProcessor;
import org.eclipse.xtend.lib.macro.Active;

@Active(PropertyStoredProcessor.class)
@Target(ElementType.TYPE)
public @interface PropertyStored {
}
