package br.com.myhomefinances.resources.exception;

import java.io.Serializable;

public class ObjectNotFoundError extends StandardError implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String entidade;

	public ObjectNotFoundError(Integer status, String message, Long timestamp,
			Integer id, String entidade) {
		super(status, message, timestamp);
		this.id = id;
		this.entidade = entidade;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEntidade() {
		return entidade;
	}

	public void setEntidade(String entidade) {
		this.entidade = entidade;
	}

}
