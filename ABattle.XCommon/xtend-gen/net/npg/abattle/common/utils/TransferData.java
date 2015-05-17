package net.npg.abattle.common.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import net.npg.abattle.common.utils.TransferDataProcessor;
import org.eclipse.xtend.lib.macro.Active;

@Active(TransferDataProcessor.class)
@Target(ElementType.TYPE)
public @interface TransferData {
}
