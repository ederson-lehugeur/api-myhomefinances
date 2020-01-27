package br.com.myhomefinances.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.myhomefinances.domain.Item;

public class ItemDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	@NotEmpty(message="Preenchimento obrigatório")
	@Length(max=255, message="O tamanho máximo é de 255 caracteres")
	private String nome;

	@Length(max=255, message="O tamanho máxima é de 255 caracteres")
	private String complemento;

	@NotNull(message="Preenchimento obrigatório")
	private Integer categoriaId;

	private Integer usuarioId;

	public ItemDTO() {}

	public ItemDTO(Item item) {
		id = item.getId();
		nome = item.getNome();
		complemento = item.getComplemento();
		categoriaId = item.getCategoria().getId();
		usuarioId = item.getUsuario().getId();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public Integer getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(Integer categoriaId) {
		this.categoriaId = categoriaId;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

}
