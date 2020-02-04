package br.com.myhomefinances.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.myhomefinances.domain.Saldo;

public class SaldoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private Double saldo;

	@JsonFormat(pattern="dd/MM/yyyy HH:mm:ss.SSS")
	private Date dataHora;

	public SaldoDTO() {}

	public SaldoDTO(Saldo saldo) {
		this.id = saldo.getId();
		this.saldo = saldo.getSaldo();
		this.dataHora = saldo.getDataHora();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

	public Date getDataHora() {
		return dataHora;
	}

	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}

}
