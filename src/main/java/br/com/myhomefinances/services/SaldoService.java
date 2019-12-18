package br.com.myhomefinances.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.myhomefinances.domain.Saldo;
import br.com.myhomefinances.domain.Usuario;
import br.com.myhomefinances.repositories.SaldoRepository;
import br.com.myhomefinances.security.UserDetailsSpringSecurity;
import br.com.myhomefinances.services.exception.AuthorizationException;
import br.com.myhomefinances.services.exception.ObjectNotFoundException;

@Service
public class SaldoService {

	@Autowired
	SaldoRepository saldoRepository;

	@Autowired
	UsuarioService usuarioService;

	public List<Saldo> findByUsuario() {
		UserDetailsSpringSecurity user = UserService.authenticated();

		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}

		Usuario usuario = usuarioService.find(user.getId());

		List<Saldo> listaSaldos = saldoRepository.findByUsuario(usuario);

		return listaSaldos;
	}

	public Saldo findByIdAndUsuario(Integer id) {
		UserDetailsSpringSecurity user = UserService.authenticated();

		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}

		Usuario usuario = usuarioService.find(user.getId());

		Optional<Saldo> saldo = saldoRepository.findByIdAndUsuario(id, usuario);

		return saldo.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!",
				id, Saldo.class.getName()));
	}

	public Saldo findFirstByUsuarioOrderByDataHoraDesc(Usuario usuario) {
		return saldoRepository.findFirstByUsuarioOrderByDataHoraDesc(usuario);
	}

	public Saldo insert(Saldo saldo) {
		saldo.setId(null);

		saldo = saldoRepository.save(saldo);

		return saldo;
	}

}
