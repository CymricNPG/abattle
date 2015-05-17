/**
 *
 */
package net.npg.abattle.common.configuration;

import java.util.Map;

/**
 * @author cymric
 *
 */
public interface SaveableData {

	void save(final Map<String, Object> map);

	void reset();

	boolean isDirty();

}
