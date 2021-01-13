package br.com.myhomefinances.form;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class CategoriaForm implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull
	@NotEmpty
	@Length(min=3, max=255)
	private String nome;

	@Length(max=255)
	private String complemento;

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
