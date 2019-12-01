package br.com.myhomefinances.resources.exception;

import java.io.Serializable;

public class NegativeBalanceError extends StandardError implements Serializable {
	private static final long serialVersionUID = 1L;

	public NegativeBalanceError(Integer status, String message, Long timestamp) {
		super(status, message, timestamp);
	}

}
