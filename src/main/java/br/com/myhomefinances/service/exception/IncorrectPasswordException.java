package br.com.myhomefinances.service.exception;

public class IncorrectPasswordException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public IncorrectPasswordException(String msg) {
		super(msg);
	}

	public IncorrectPasswordException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
