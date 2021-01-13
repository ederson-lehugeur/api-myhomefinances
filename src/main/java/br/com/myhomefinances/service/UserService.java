package br.com.myhomefinances.service;

import org.springframework.security.core.context.SecurityContextHolder;

import br.com.myhomefinances.security.UserDetailsSpringSecurity;

public class UserService {

	public static UserDetailsSpringSecurity authenticated() {
		try {
			return (UserDetailsSpringSecurity) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}

}
