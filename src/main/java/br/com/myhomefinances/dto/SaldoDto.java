package br.com.myhomefinances.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.myhomefinances.domain.Saldo;

public class SaldoDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private Double saldo;

	@JsonFormat(pattern="dd/MM/yyyy HH:mm:ss.SSS")
	private Date dataHora;

	public SaldoDto(Saldo saldo) {
		this.id = saldo.getId();
		this.saldo = saldo.getSaldo();
		this.dataHora = saldo.getDataHora();
	}

	public Long getId() {
		return id;
	}

	public Double getSaldo() {
		return saldo;
	}

	public Date getDataHora() {
		return dataHora;
	}

}
