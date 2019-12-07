package br.com.myhomefinances.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.myhomefinances.domain.Conta;
import br.com.myhomefinances.domain.Item;
import br.com.myhomefinances.domain.Registro;
import br.com.myhomefinances.domain.RegistroBancario;
import br.com.myhomefinances.domain.SaldoBancario;
import br.com.myhomefinances.dto.RegistroBancarioDTO;
import br.com.myhomefinances.repositories.RegistroBancarioRepository;
import br.com.myhomefinances.services.exception.NegativeBalanceException;
import br.com.myhomefinances.services.exception.ObjectNotFoundException;

@Service
public class RegistroBancarioService {

	@Autowired
	RegistroBancarioRepository registroBancarioRepository;

	@Autowired
	SaldoBancarioService saldoBancarioService;

	@Autowired
	ContaService contaService;

	@Autowired
	ItemService itemService;

	public List<RegistroBancario> findAll() {
		List<RegistroBancario> listaRegistrosBancarios = registroBancarioRepository.findAll();

		return listaRegistrosBancarios;
	}

	public RegistroBancario find(Integer id) {
		Optional<RegistroBancario> registroBancario = registroBancarioRepository.findById(id);

		return registroBancario.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!",
				id, Registro.class.getName()));
	}

	public Page<RegistroBancario> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

		return registroBancarioRepository.findAll(pageRequest);
	}

	@Transactional
	public RegistroBancario insert(RegistroBancario registroBancario) {
		registroBancario.setId(null);

		// Validação: verificar se a conta pertence ao usuário logado.

		registroBancario = registroBancarioRepository.save(registroBancario);

		SaldoBancario saldoBancario = saldoBancarioService.findFirstByContaOrderByDataHoraDesc(registroBancario.getConta());

		Double valor = saldoBancario.getSaldo() + registroBancario.getValor();

		if (valor < 0) {
			throw new NegativeBalanceException("Saldo com valor negativo");
		}

		SaldoBancario novoSaldoBancario = new SaldoBancario(null, valor, new Date(), registroBancario.getConta());

		saldoBancarioService.insert(novoSaldoBancario);

		return registroBancario;
	}

	public RegistroBancario fromDTO(RegistroBancarioDTO registroBancarioDto) {
		Conta conta = contaService.find(registroBancarioDto.getContaId());

		Item item = itemService.find(registroBancarioDto.getItemId());

		return new RegistroBancario(registroBancarioDto.getId(), registroBancarioDto.getValor(),
				registroBancarioDto.getDataHora(), conta, item);
	}

}
