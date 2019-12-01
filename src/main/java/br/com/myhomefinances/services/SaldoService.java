package br.com.myhomefinances.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.myhomefinances.domain.Saldo;
import br.com.myhomefinances.domain.Usuario;
import br.com.myhomefinances.repositories.SaldoRepository;
import br.com.myhomefinances.services.exception.ObjectNotFoundException;

@Service
public class SaldoService {

	@Autowired
	SaldoRepository saldoRepository;

	public List<Saldo> findAll() {
		List<Saldo> listaSaldos = saldoRepository.findAll();

		return listaSaldos;
	}

	public Saldo find(Integer id) {
		Optional<Saldo> saldo = saldoRepository.findById(id);

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
