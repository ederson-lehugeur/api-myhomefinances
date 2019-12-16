package br.com.myhomefinances.services.exception;

public class InvalidPerfilException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvalidPerfilException(String msg) {
		super(msg);
	}

	public InvalidPerfilException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
