package br.com.myhomefinances.resource.form;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

public class RegistroBancarioForm implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull
	private Double valor;

	@NotNull
	@JsonFormat(pattern="dd/MM/yyyy HH:mm:ss.SSS")
	private LocalDateTime dataHora;

	@NotNull
	private Long contaId;

	@NotNull
	private Long itemId;

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

	public Long getContaId() {
		return contaId;
	}

	public void setContaId(Long contaId) {
		this.contaId = contaId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

}
