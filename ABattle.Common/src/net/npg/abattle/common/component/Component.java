package net.npg.abattle.common.component;

import net.npg.abattle.common.utils.Disposeable;

/**
 * a component is a modular part which realizes an interface (only one in abattle)
 * 
 * @author Cymric
 * 
 */

public interface Component extends Disposeable {

	Class<? extends Component> getInterface();

}
