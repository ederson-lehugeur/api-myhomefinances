package br.com.myhomefinances.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import br.com.myhomefinances.domain.Registro;

public class RegistroDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private Double valor;
	private LocalDateTime dataHora;
	private Long tipoRegistroId;
	private String nomeTipoRegistro;
	private Integer ehRegistroDeSaida;
	private Long itemId;
	private String nomeItem;
	private String complementoItem;
	private Long usuarioId;

	public RegistroDto(Registro registro) {
		id = registro.getId();
		valor = registro.getValor();
		dataHora = registro.getDataHora();
		tipoRegistroId = registro.getTipoRegistro().getId();
		nomeTipoRegistro = registro.getTipoRegistro().getNome();
		ehRegistroDeSaida = registro.getTipoRegistro().getEhRegistroDeSaida();
		itemId = registro.getItem().getId();
		nomeItem = registro.getItem().getNome();
		complementoItem = registro.getItem().getComplemento();
		usuarioId = registro.getUsuario().getId();
	}

	public Long getId() {
		return id;
	}

	public Double getValor() {
		return valor;
	}

	public LocalDateTime getDataHora() {
		return dataHora;
	}

	public Long getTipoRegistroId() {
		return tipoRegistroId;
	}

	public String getNomeTipoRegistro() {
		return nomeTipoRegistro;
	}

	public Integer getEhRegistroDeSaida() {
		return ehRegistroDeSaida;
	}

	public Long getItemId() {
		return itemId;
	}

	public String getNomeItem() {
		return nomeItem;
	}

	public String getComplementoItem() {
		return complementoItem;
	}

	public Long getUsuarioId() {
		return usuarioId;
	}

}
