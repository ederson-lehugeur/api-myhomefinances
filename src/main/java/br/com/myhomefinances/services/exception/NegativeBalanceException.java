package br.com.myhomefinances.services.exception;

public class NegativeBalanceException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public NegativeBalanceException(String msg) {
		super(msg);
	}

	public NegativeBalanceException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
