package br.com.myhomefinances.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import br.com.myhomefinances.domain.Conta;

public class ContaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	@NotNull(message="Preenchimento obrigatório")
	private Integer bancoId;

	@NotNull(message="Preenchimento obrigatório")
	private Integer tipoContaId;

	@NotNull(message="Preenchimento obrigatório")
	private Integer usuarioId;

	public ContaDTO() {}

	public ContaDTO(Conta conta) {
		id = conta.getId();
		bancoId = conta.getBanco().getId();
		tipoContaId = conta.getTipoConta().getId();
		usuarioId = conta.getUsuario().getId();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBancoId() {
		return bancoId;
	}

	public void setBancoId(Integer bancoId) {
		this.bancoId = bancoId;
	}

	public Integer getTipoContaId() {
		return tipoContaId;
	}

	public void setTipoContaId(Integer tipoContaId) {
		this.tipoContaId = tipoContaId;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

}
