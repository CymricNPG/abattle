/**
 *
 */
package net.npg.abattle.common.i18n;

import net.npg.abattle.common.CommonConstants;
import net.npg.abattle.common.component.ComponentLookup;

import com.google.common.base.Strings;

/**
 * @author cymric
 * 
 */
public final class I18N {
	public static String get(final String key) {
		final String value = ComponentLookup.getInstance().getComponent(I18NComponent.class).get(key);
		return checkValue(key, value);
	}

	private static String checkValue(final String key, final String value) {
		if (Strings.isNullOrEmpty(value)) {
			CommonConstants.LOG.error("Unknown key:" + key);
			return key;
		}
		return value;
	}

	public static String format(final String key, final String... params) {
		final String value = ComponentLookup.getInstance().getComponent(I18NComponent.class).format(key, params);
		return checkValue(key, value);
	}
}
