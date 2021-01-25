package br.com.myhomefinances.resource.form;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class TipoContaForm implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull
	@NotEmpty
	@Length(min=3, max=255)
	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
