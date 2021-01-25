package br.com.myhomefinances.resource.dto;

import java.io.Serializable;

import br.com.myhomefinances.domain.Perfil;

public class PerfilDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;

	public PerfilDto(Perfil perfil) {
		id = perfil.getId();
		nome = perfil.getNome();
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

}
