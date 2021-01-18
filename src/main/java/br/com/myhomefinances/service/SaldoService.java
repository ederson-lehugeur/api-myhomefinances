package br.com.myhomefinances.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.myhomefinances.domain.Saldo;
import br.com.myhomefinances.domain.Usuario;
import br.com.myhomefinances.dto.SaldoDto;
import br.com.myhomefinances.repository.SaldoRepository;
import br.com.myhomefinances.service.exception.AuthorizationException;
import br.com.myhomefinances.service.exception.ObjectNotFoundException;

@Service
public class SaldoService {

	@Autowired
	SaldoRepository saldoRepository;

	@Autowired
	UsuarioService usuarioService;

	public List<Saldo> findAll() {
		Usuario usuario = UsuarioService.authenticated();

		if (usuario == null) {
			throw new AuthorizationException("Acesso negado");
		}

		List<Saldo> saldos = saldoRepository.findByUsuario(usuario);

		return saldos;
	}

	public Saldo findById(Long id) {
		Usuario usuario = UsuarioService.authenticated();

		if (usuario == null) {
			throw new AuthorizationException("Acesso negado");
		}

		Optional<Saldo> saldo = saldoRepository.findByIdAndUsuario(id, usuario);

		return saldo.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!",
				Saldo.class.getName()));
	}

	public Saldo findFirstOrderByDataHoraDesc() {
		Usuario usuario = UsuarioService.authenticated();

		if (usuario == null) {
			throw new AuthorizationException("Acesso negado");
		}

		return saldoRepository.findFirstByUsuarioOrderByDataHoraCriacaoDesc(usuario);
	}

	public Saldo insert(Saldo saldo) {
		saldo.setId(null);

		saldo = saldoRepository.save(saldo);

		return saldo;
	}

	public List<SaldoDto> convertToDto(List<Saldo> saldos) {
		return saldos.stream().map(SaldoDto::new).collect(Collectors.toList());
	}

}
