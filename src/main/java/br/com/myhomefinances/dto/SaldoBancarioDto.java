package br.com.myhomefinances.dto;

import java.io.Serializable;

import br.com.myhomefinances.domain.SaldoBancario;

public class SaldoBancarioDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private Double saldo;
	private Long contaId;

	public SaldoBancarioDto(SaldoBancario saldoBancario) {
		this.id = saldoBancario.getId();
		this.saldo = saldoBancario.getSaldo();
		this.contaId = saldoBancario.getConta().getId();
	}

	public Long getId() {
		return id;
	}

	public Double getSaldo() {
		return saldo;
	}

	public Long getContaId() {
		return contaId;
	}

}
