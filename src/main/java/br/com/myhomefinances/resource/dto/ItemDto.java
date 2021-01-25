package br.com.myhomefinances.resource.dto;

import java.io.Serializable;

import br.com.myhomefinances.domain.Item;

public class ItemDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;
	private String complemento;
	private Long categoriaId;
	private String nomeCategoria;

	public ItemDto(Item item) {
		id = item.getId();
		nome = item.getNome();
		complemento = item.getComplemento();
		categoriaId = item.getCategoria().getId();
		nomeCategoria = item.getCategoria().getNome();
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getComplemento() {
		return complemento;
	}

	public Long getCategoriaId() {
		return categoriaId;
	}

	public String getNomeCategoria() {
		return nomeCategoria;
	}

}
