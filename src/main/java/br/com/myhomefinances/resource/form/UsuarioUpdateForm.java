package br.com.myhomefinances.resource.form;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.myhomefinances.service.validation.UsuarioUpdate;

@UsuarioUpdate
public class UsuarioUpdateForm implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull
	@NotEmpty
	@Length(min=3, max=255)
	private String nome;

	@NotNull
	@NotEmpty
	@Length(min=1, max=255)
	private String sobrenome;

	@NotNull
	@NotEmpty
	@Email
	private String email;

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

}
