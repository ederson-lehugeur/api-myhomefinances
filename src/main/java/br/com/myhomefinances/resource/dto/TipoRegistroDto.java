package br.com.myhomefinances.resource.dto;

import java.io.Serializable;

import br.com.myhomefinances.domain.TipoRegistro;

public class TipoRegistroDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;
	private Integer ehRegistroDeSaida;

	public TipoRegistroDto(TipoRegistro tipoRegistro) {
		id = tipoRegistro.getId();
		nome = tipoRegistro.getNome();
		ehRegistroDeSaida = tipoRegistro.getEhRegistroDeSaida();
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public Integer getEhRegistroDeSaida() {
		return ehRegistroDeSaida;
	}

}
