package br.com.myhomefinances.resource.form;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

public class PerfilForm implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull
	@NotEmpty
	@Length(min=6, max=255)
	@Pattern(regexp = "^ROLE_[A-Z_0-9]+$")
	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
