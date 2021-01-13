package br.com.myhomefinances.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.myhomefinances.domain.Registro;

public class RegistroDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private Double valor;

	@JsonFormat(pattern="dd/MM/yyyy HH:mm:ss.SSS")
	private Date dataHora;
	private Long tipoRegistroId;
	private String nomeTipoRegistro;
	private Long itemId;
	private String nomeItem;
	private Long usuarioId;

	public RegistroDto(Registro registro) {
		id = registro.getId();
		valor = registro.getValor();
		dataHora = registro.getDataHora();
		tipoRegistroId = registro.getTipoRegistro().getId();
		nomeTipoRegistro = registro.getTipoRegistro().getNome();
		itemId = registro.getItem().getId();
		nomeItem = registro.getItem().getNome();
		usuarioId = registro.getUsuario().getId();
	}

	public Long getId() {
		return id;
	}

	public Double getValor() {
		return valor;
	}

	public Date getDataHora() {
		return dataHora;
	}

	public Long getTipoRegistroId() {
		return tipoRegistroId;
	}

	public String getNomeTipoRegistro() {
		return nomeTipoRegistro;
	}

	public Long getItemId() {
		return itemId;
	}

	public String getNomeItem() {
		return nomeItem;
	}

	public Long getUsuarioId() {
		return usuarioId;
	}

}
