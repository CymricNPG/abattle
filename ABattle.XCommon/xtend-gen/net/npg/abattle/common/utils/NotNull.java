package net.npg.abattle.common.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import net.npg.abattle.common.utils.NotNullProcessor;
import org.eclipse.xtend.lib.macro.Active;

@Active(NotNullProcessor.class)
@Target(ElementType.PARAMETER)
public @interface NotNull {
}
