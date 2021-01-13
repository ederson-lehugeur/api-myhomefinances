package br.com.myhomefinances.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.myhomefinances.domain.RegistroBancario;

public class RegistroBancarioDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private Double valor;

	@JsonFormat(pattern="dd/MM/yyyy HH:mm:ss.SSS")
	private Date dataHora;
	private Long contaId;
	private Long idBanco;
	private String nomeBanco;
	private Long idTipoConta;
	private String nomeTipoConta;
	private Long itemId;
	private String nomeItem;

	public RegistroBancarioDto(RegistroBancario registroBancario) {
		id = registroBancario.getId();
		valor = registroBancario.getValor();
		dataHora = registroBancario.getDataHora();
		contaId = registroBancario.getConta().getId();
		idBanco = registroBancario.getConta().getBanco().getId();
		nomeBanco = registroBancario.getConta().getBanco().getNome();
		idTipoConta = registroBancario.getConta().getTipoConta().getId();
		nomeTipoConta = registroBancario.getConta().getTipoConta().getNome();
		itemId = registroBancario.getItem().getId();
		nomeItem = registroBancario.getItem().getNome();
	}

	public Long getId() {
		return id;
	}

	public Double getValor() {
		return valor;
	}

	public Date getDataHora() {
		return dataHora;
	}

	public Long getContaId() {
		return contaId;
	}

	public Long getIdBanco() {
		return idBanco;
	}

	public String getNomeBanco() {
		return nomeBanco;
	}

	public Long getIdTipoConta() {
		return idTipoConta;
	}

	public String getNomeTipoConta() {
		return nomeTipoConta;
	}

	public Long getItemId() {
		return itemId;
	}

	public String getNomeItem() {
		return nomeItem;
	}

}
