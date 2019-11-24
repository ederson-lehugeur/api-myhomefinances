package br.com.myhomefinances.dto;

import java.io.Serializable;

import br.com.myhomefinances.domain.Categoria;

public class CategoriaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nome;
	private String complemento;

	public CategoriaDTO() {}

	public CategoriaDTO(Categoria categoria) {
		// Quando houver necessidade, fazer o filtro de atributos aqui.
		id = categoria.getId();
		nome = categoria.getNome();
		complemento = categoria.getComplemento();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

}
