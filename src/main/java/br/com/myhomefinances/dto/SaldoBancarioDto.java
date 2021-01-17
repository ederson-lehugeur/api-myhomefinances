package br.com.myhomefinances.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.myhomefinances.domain.SaldoBancario;

public class SaldoBancarioDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private Double saldo;
	private Long contaId;

	@JsonFormat(pattern="dd/MM/yyyy HH:mm:ss.SSS")
	private Date dataHora;

	public SaldoBancarioDto(SaldoBancario saldoBancario) {
		this.id = saldoBancario.getId();
		this.saldo = saldoBancario.getSaldo();
		this.contaId = saldoBancario.getConta().getId();
		this.dataHora = saldoBancario.getDataHora();
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

	public Date getDataHora() {
		return dataHora;
	}

}
