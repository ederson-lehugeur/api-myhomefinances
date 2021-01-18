package br.com.myhomefinances.dto;

import java.io.Serializable;

import br.com.myhomefinances.domain.Categoria;

public class CategoriaDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;
	private String complemento;

	public CategoriaDto(Categoria categoria) {
		id = categoria.getId();
		nome = categoria.getNome();
		complemento = categoria.getComplemento();
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getComplemento() {
		return complemento;
	}

}
