package br.com.myhomefinances.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.myhomefinances.domain.RegistroBancario;

public class RegistroBancarioDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	@NotNull(message="Preenchimento obrigatório")
	private Double valor;

	@NotNull(message="Preenchimento obrigatório")
	@JsonFormat(pattern="dd/MM/yyyy HH:mm:ss.SSS")
	private Date dataHora;

	@NotNull(message="Preenchimento obrigatório")
	private Integer contaId;

	@NotNull(message="Preenchimento obrigatório")
	private Integer itemId;

	public RegistroBancarioDTO() {}

	public RegistroBancarioDTO(RegistroBancario registroBancario) {
		id = registroBancario.getId();
		valor = registroBancario.getValor();
		dataHora = registroBancario.getDataHora();
		contaId = registroBancario.getConta().getId();
		itemId = registroBancario.getItem().getId();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Date getDataHora() {
		return dataHora;
	}

	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}

	public Integer getContaId() {
		return contaId;
	}

	public void setContaId(Integer contaId) {
		this.contaId = contaId;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

}
