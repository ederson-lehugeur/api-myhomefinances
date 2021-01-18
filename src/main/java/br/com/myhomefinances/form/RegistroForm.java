package br.com.myhomefinances.form;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

public class RegistroForm implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull
	private Double valor;

	@NotNull
	@JsonFormat(pattern="dd/MM/yyyy HH:mm:ss.SSS")
	private LocalDateTime dataHora;

	@NotNull
	private Long tipoRegistroId;

	@NotNull
	private Long itemId;

	private Long usuarioId;

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public LocalDateTime getDataHora() {
		return dataHora;
	}

	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}

	public Long getTipoRegistroId() {
		return tipoRegistroId;
	}

	public void setTipoRegistroId(Long tipoRegistroId) {
		this.tipoRegistroId = tipoRegistroId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

}
