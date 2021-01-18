package br.com.myhomefinances.dto;

import java.io.Serializable;

import br.com.myhomefinances.domain.Saldo;

public class SaldoDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private Double saldo;

	public SaldoDto(Saldo saldo) {
		this.id = saldo.getId();
		this.saldo = saldo.getSaldo();
	}

	public Long getId() {
		return id;
	}

	public Double getSaldo() {
		return saldo;
	}

}
