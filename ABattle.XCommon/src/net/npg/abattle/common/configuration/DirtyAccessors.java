/**
 *
 */
package net.npg.abattle.common.configuration;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import org.eclipse.xtend.lib.annotations.AccessorType;
import org.eclipse.xtend.lib.macro.Active;

/**
 * @author cymric
 *
 */

@Target({ ElementType.FIELD, ElementType.TYPE })
@Active(DirtyAccessorsProcessor.class)
@Documented
public  @interface DirtyAccessors {
	 public AccessorType[] value() default { AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER };
}