/**
 *
 */
package net.npg.abattle.common.i18n;

import net.npg.abattle.common.component.Component;
import net.npg.abattle.common.component.Reusable;

/**
 * @author cymric
 * 
 */
public interface I18NComponent extends Component, Reusable {

	String get(String key);

	String format(String key, String... params);

}
