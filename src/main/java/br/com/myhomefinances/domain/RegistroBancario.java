package br.com.myhomefinances.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class RegistroBancario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private Double valor;
	private LocalDateTime dataHora;
	private LocalDateTime dataHoraCriacao = LocalDateTime.now();

	@ManyToOne
	@JoinColumn(name="conta_id", foreignKey=@ForeignKey(name="FK_REGISTROBANCARIO_CONTA"))
	private Conta conta;

	@ManyToOne
	@JoinColumn(name="item_id", foreignKey=@ForeignKey(name="FK_REGISTROBANCARIO_ITEM"))
	private Item item;

	public RegistroBancario() {}

	public RegistroBancario(Long id, Double valor, LocalDateTime dataHora, Conta conta, Item item) {
		this.id = id;
		this.valor = valor;
		this.dataHora = dataHora;
		this.conta = conta;
		this.item = item;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public LocalDateTime getDataHora() {
		return dataHora;
	}

	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}

	public LocalDateTime getDataHoraCriacao() {
		return dataHoraCriacao;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RegistroBancario other = (RegistroBancario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
