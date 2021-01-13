package br.com.myhomefinances.dto;

import java.io.Serializable;

import br.com.myhomefinances.domain.Banco;

public class BancoDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;

	public BancoDto(Banco banco) {
		id = banco.getId();
		nome = banco.getNome();
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

}
