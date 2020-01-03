package br.com.myhomefinances.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

public class ResetPasswordDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotEmpty(message="Preenchimento obrigatório")
	private String senha;

	@NotEmpty(message="Preenchimento obrigatório")
	private String confirmacaoSenha;

	public ResetPasswordDTO() {}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getConfirmacaoSenha() {
		return confirmacaoSenha;
	}

	public void setConfirmacaoSenha(String confirmacaoSenha) {
		this.confirmacaoSenha = confirmacaoSenha;
	}

}
