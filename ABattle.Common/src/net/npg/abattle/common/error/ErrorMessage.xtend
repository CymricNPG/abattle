package net.npg.abattle.common.error

import net.npg.abattle.common.utils.Validate

class ErrorMessage {

	private final ExceptionCode code

	private final boolean failed

	private final String errorMessage

	new() {
		this.failed = false
		this.code = new ExceptionCode(-1, "no error")
		errorMessage = ""
	}

	new(boolean failed) {
		Validate.isFalse(failed)
		this.failed = failed
		this.code = ExceptionCode.NO_EXCEPTION
		errorMessage = ""
	}

	new(ExceptionCode code) {
		Validate.notNull(code)
		this.failed = true
		this.code = code
		errorMessage = ""
	}

	new(ExceptionCode code, String errorMessage) {
		Validate.notNull(code)
		Validate.notNull(errorMessage)
		this.failed = true
		this.code = code
		this.errorMessage = errorMessage
	}
	
	def isFailed() {
		failed
	}

	def getErrorMessage() {
		errorMessage
	}

	def getErrorCode() {
		code
	}


}
