package br.com.myhomefinances.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.myhomefinances.domain.Usuario;
import br.com.myhomefinances.repositories.UsuarioRepository;
import br.com.myhomefinances.security.UserDetailsSpringSecurity;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByEmail(email);

		if (usuario == null) {
			throw new UsernameNotFoundException(email);
		}

		return new UserDetailsSpringSecurity(usuario.getId(), usuario.getEmail(),
				usuario.getSenha(), usuario.getPerfis());
	}

}
