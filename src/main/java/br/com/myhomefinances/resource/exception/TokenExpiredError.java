package br.com.myhomefinances.resource.exception;

import java.io.Serializable;

public class TokenExpiredError extends StandardError implements Serializable {
	private static final long serialVersionUID = 1L;

	public TokenExpiredError(Long timestamp, Integer status, String error,
			String message, String path) {

		super(timestamp, status, error, message, path);
	}

}
