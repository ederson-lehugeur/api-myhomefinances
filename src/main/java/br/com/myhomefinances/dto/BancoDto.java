package br.com.myhomefinances.dto;

import java.io.Serializable;

import br.com.myhomefinances.domain.Banco;

public class BancoDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer codigo;
	private String nome;

	public BancoDto(Banco banco) {
		codigo = banco.getCodigo();
		nome = banco.getNome();
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getNome() {
		return nome;
	}

}
