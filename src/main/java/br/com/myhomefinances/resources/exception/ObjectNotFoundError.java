package br.com.myhomefinances.resources.exception;

import java.io.Serializable;

public class ObjectNotFoundError extends StandardError implements Serializable {
	private static final long serialVersionUID = 1L;

	private String entity;

	public ObjectNotFoundError(Long timestamp, Integer status, String error,
			String message, String path, String entity) {

		super(timestamp, status, error, message, path);
		this.entity = entity;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

}
