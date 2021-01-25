package br.com.myhomefinances.resource.form;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ForgotPasswordForm implements Serializable {
	private static final long serialVersionUID = 1L;

	@Email
	@NotNull
	@NotEmpty
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
