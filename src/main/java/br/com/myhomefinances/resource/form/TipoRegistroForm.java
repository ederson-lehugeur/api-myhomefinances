package br.com.myhomefinances.resource.form;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

public class TipoRegistroForm implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull
	@NotEmpty
	@Length(min=3, max=255)
	private String nome;

	@NotNull
	@Pattern(regexp = "^[01]{1}$")
	private Integer ehRegistroDeSaida;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getEhRegistroDeSaida() {
		return ehRegistroDeSaida;
	}

	public void setEhRegistroDeSaida(Integer ehRegistroDeSaida) {
		this.ehRegistroDeSaida = ehRegistroDeSaida;
	}

}
