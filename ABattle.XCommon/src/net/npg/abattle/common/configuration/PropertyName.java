/**
 *
 */
package net.npg.abattle.common.configuration;

/**
 * @author Cymric
 *
 */
public @interface PropertyName {

	String name();

	String defaultValue();

	boolean noReset() default false;
}
