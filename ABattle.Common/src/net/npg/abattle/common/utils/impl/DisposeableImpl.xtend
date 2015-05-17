package net.npg.abattle.common.utils.impl

import net.npg.abattle.common.utils.Disposeable

class DisposeableImpl implements Disposeable {

	private boolean disposed = false

	override dispose() {
		disposed = true
	}

	override isDisposed() {
		return disposed
	}

}
