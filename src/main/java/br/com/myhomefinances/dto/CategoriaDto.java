package br.com.myhomefinances.dto;

import java.io.Serializable;

import br.com.myhomefinances.domain.Categoria;

public class CategoriaDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;
	private String complemento;
	private Long usuarioId;
	private String nomeUsuario;

	public CategoriaDto(Categoria categoria) {
		id = categoria.getId();
		nome = categoria.getNome();
		complemento = categoria.getComplemento();
		usuarioId = categoria.getUsuario().getId();
		nomeUsuario = categoria.getUsuario().getNome();
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

	public Long getUsuarioId() {
		return usuarioId;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

}
