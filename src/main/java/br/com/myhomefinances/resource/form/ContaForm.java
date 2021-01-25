package br.com.myhomefinances.resource.form;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class ContaForm implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull
	private Long bancoId;

	@NotNull
	private Long tipoContaId;

	@NotNull
	private Long usuarioId;

	public Long getBancoId() {
		return bancoId;
	}

	public void setBancoId(Long bancoId) {
		this.bancoId = bancoId;
	}

	public Long getTipoContaId() {
		return tipoContaId;
	}

	public void setTipoContaId(Long tipoContaId) {
		this.tipoContaId = tipoContaId;
	}

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

}
