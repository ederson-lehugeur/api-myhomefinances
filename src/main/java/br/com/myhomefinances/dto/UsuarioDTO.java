package br.com.myhomefinances.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import br.com.myhomefinances.domain.Usuario;
import br.com.myhomefinances.services.validation.UsuarioInsert;

@UsuarioInsert
public class UsuarioDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	@NotEmpty(message="Preenchimento obrigatório")
	@Length(max=255, message="O tamanho máximo é de 255 caracteres")
	private String nome;

	@NotEmpty(message="Preenchimento obrigatório")
	@Length(max=255, message="O tamanho máximo é de 255 caracteres")
	private String sobrenome;

	@NotEmpty(message="Preenchimento obrigatório")
	@Email
	private String email;

	@NotEmpty(message="Preenchimento obrigatório")
	@Length(max=255, message="O tamanho máximo é de 255 caracteres")
	private String senha;

	public UsuarioDTO() {}

	public UsuarioDTO(Usuario usuario) {
		id = usuario.getId();
		nome = usuario.getNome();
		sobrenome = usuario.getSobrenome();
		email = usuario.getEmail();
		senha = usuario.getSenha();
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

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
