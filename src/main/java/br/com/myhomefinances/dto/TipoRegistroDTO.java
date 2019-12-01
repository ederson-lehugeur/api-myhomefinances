package br.com.myhomefinances.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import br.com.myhomefinances.domain.Item;

public class TipoRegistroDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	@NotEmpty(message="Preenchimento obrigatório")
	@Length(max=255, message="O tamanho máximo é de 255 caracteres")
	private String nome;

	public TipoRegistroDTO() {}

	public TipoRegistroDTO(Item item) {
		id = item.getId();
		nome = item.getNome();
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

}
