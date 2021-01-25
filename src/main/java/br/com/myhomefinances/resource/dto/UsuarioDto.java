package br.com.myhomefinances.resource.dto;

import java.io.Serializable;

import br.com.myhomefinances.domain.Usuario;

public class UsuarioDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nome;
	private String email;

	public UsuarioDto(Usuario usuario) {
		this.nome = usuario.getNome();
		this.email = usuario.getEmail();
	}

	public String getNome() {
		return nome;
	}

	public String getEmail() {
		return email;
	}

}
