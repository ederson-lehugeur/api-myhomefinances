package br.com.myhomefinances.service.exception;

public class ResetTokenNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ResetTokenNotFoundException(String msg) {
		super(msg);
	}

	public ResetTokenNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
