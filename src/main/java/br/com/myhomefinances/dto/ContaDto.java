package br.com.myhomefinances.dto;

import java.io.Serializable;

import br.com.myhomefinances.domain.Conta;

public class ContaDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private Long bancoId;
	private String nomeBanco;
	private Long tipoContaId;
	private String nomeTipoConta;
	private Long usuarioId;
	private String nomeUsuario;

	public ContaDto(Conta conta) {
		id = conta.getId();
		bancoId = conta.getBanco().getId();
		nomeBanco = conta.getBanco().getNome();
		tipoContaId = conta.getTipoConta().getId();
		nomeTipoConta = conta.getTipoConta().getNome();
		usuarioId = conta.getUsuario().getId();
		nomeUsuario = conta.getUsuario().getNome();
	}

	public Long getId() {
		return id;
	}

	public Long getBancoId() {
		return bancoId;
	}

	public String getNomeBanco() {
		return nomeBanco;
	}

	public Long getTipoContaId() {
		return tipoContaId;
	}

	public String getNomeTipoConta() {
		return nomeTipoConta;
	}

	public Long getUsuarioId() {
		return usuarioId;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

}
