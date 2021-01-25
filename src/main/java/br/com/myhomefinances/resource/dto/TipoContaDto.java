package br.com.myhomefinances.resource.dto;

import java.io.Serializable;

import br.com.myhomefinances.domain.TipoConta;

public class TipoContaDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;

	public TipoContaDto(TipoConta tipoConta) {
		id = tipoConta.getId();
		nome = tipoConta.getNome();
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

}
