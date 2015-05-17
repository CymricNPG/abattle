package net.npg.abattle.common.model.client.impl

import java.util.concurrent.Semaphore
import java.util.concurrent.TimeUnit
import net.npg.abattle.common.error.BaseException
import net.npg.abattle.common.model.ModelExceptionCode
import net.npg.abattle.common.model.client.ClientIdRegister
import net.npg.abattle.common.model.impl.ColorImpl
import org.eclipse.xtend.lib.annotations.Accessors

/**
 * TODO merge with gameparameters?
 */
class ClientModelComponentImpl implements ClientIdRegister {

	Semaphore waitForId
	int clientId
	String errorMessage
	@Accessors String name

	new() {
		resetClientPlayer
	}

	override getLocalPlayer() throws BaseException {
		if (clientId >= 0 && errorMessage.nullOrEmpty) {
			return new ClientPlayerImpl(name, clientId, ColorImpl.BLACK)
		} else {
			throw new BaseException(ModelExceptionCode.CLIENT_NOT_LOOGEDIN, errorMessage)
		}
	}

	override setClientId(int id) {
		clientId = id
		waitForId.release
	}

	override setFailed(String message) {
		errorMessage = message
		waitForId.release
	}

	override waitForClientId() {
		if (!waitForId.tryAcquire(3, TimeUnit.SECONDS)) {
			throw new BaseException(ModelExceptionCode.LOGIN_TIMEOUT)
		}
		if (!errorMessage.nullOrEmpty) {
			throw new BaseException(ModelExceptionCode.LOGIN_FAILED, errorMessage)
		}
	}

	synchronized override void resetClientPlayer() {
		clientId = -1
		errorMessage = null
		waitForId = new Semaphore(0)
	}

}
