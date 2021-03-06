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
import javax.persistence.Transient;

@Entity
public class Registro implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private Double valor;
	private LocalDateTime dataHora;
	private LocalDateTime dataHoraCriacao = LocalDateTime.now();

	@Transient
    private Double valorPreAtualizacao;

	@ManyToOne
	@JoinColumn(name="tiporegistro_id", foreignKey=@ForeignKey(name="FK_REGISTRO_TIPOREGISTRO"))
	private TipoRegistro tipoRegistro;

	@ManyToOne
	@JoinColumn(name="usuario_id", foreignKey=@ForeignKey(name="FK_REGISTRO_USUARIO"))
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name="item_id", foreignKey=@ForeignKey(name="FK_REGISTRO_ITEM"))
	private Item item;

	public Registro() {}

	public Registro(Long id, Double valor, LocalDateTime dataHora, TipoRegistro tipoRegistro,
			Usuario usuario, Item item) {

		this.id = id;
		this.valor = valor;
		this.dataHora = dataHora;
		this.tipoRegistro = tipoRegistro;
		this.usuario = usuario;
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

	public TipoRegistro getTipoRegistro() {
		return tipoRegistro;
	}

	public void setTipoRegistro(TipoRegistro tipoRegistro) {
		this.tipoRegistro = tipoRegistro;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Double getValorPreAtualizacao() {
		return valorPreAtualizacao;
	}

	public void setValorPreAtualizacao(Double valorPreAtualizacao) {
		this.valorPreAtualizacao = valorPreAtualizacao;
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
		Registro other = (Registro) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
