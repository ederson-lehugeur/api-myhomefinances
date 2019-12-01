package br.com.myhomefinances.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.myhomefinances.domain.Registro;

public class RegistroDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	@NotNull(message="Preenchimento obrigatório")
	private Double valor;

	@NotNull(message="Preenchimento obrigatório")
	@JsonFormat(pattern="dd/MM/yyyy HH:mm:ss.SSS")
	private Date dataHora;

	@NotNull(message="Preenchimento obrigatório")
	private Integer tipoRegistroId;

	@NotNull(message="Preenchimento obrigatório")
	private Integer itemId;

	@NotNull(message="Preenchimento obrigatório")
	private Integer usuarioId;

	public RegistroDTO() {}

	public RegistroDTO(Registro registro) {
		id = registro.getId();
		valor = registro.getValor();
		dataHora = registro.getDataHora();
		tipoRegistroId = registro.getTipoRegistro().getId();
		itemId = registro.getItem().getId();
		usuarioId = registro.getUsuario().getId();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Date getDataHora() {
		return dataHora;
	}

	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}

	public Integer getTipoRegistroId() {
		return tipoRegistroId;
	}

	public void setTipoRegistroId(Integer tipoRegistroId) {
		this.tipoRegistroId = tipoRegistroId;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

}
