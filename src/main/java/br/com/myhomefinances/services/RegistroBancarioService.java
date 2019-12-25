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
import br.com.myhomefinances.domain.TipoRegistro;
import br.com.myhomefinances.dto.RegistroBancarioDTO;
import br.com.myhomefinances.repositories.RegistroBancarioRepository;
import br.com.myhomefinances.security.UserDetailsSpringSecurity;
import br.com.myhomefinances.services.exception.AuthorizationException;
import br.com.myhomefinances.services.exception.ObjectNotFoundException;

@Service
public class RegistroBancarioService {

	@Autowired
	RegistroBancarioRepository registroBancarioRepository;

	@Autowired
	RegistroService registroService;

	@Autowired
	TipoRegistroService tipoRegistroService;

	@Autowired
	SaldoBancarioService saldoBancarioService;

	@Autowired
	ContaService contaService;

	@Autowired
	ItemService itemService;

	@Autowired
	UsuarioService usuarioService;

	public List<RegistroBancario> findByConta(Integer idConta) {
		Conta conta = contaService.findByIdAndUsuario(idConta);

		List<RegistroBancario> listaRegistrosBancarios = registroBancarioRepository.findByConta(conta);

		return listaRegistrosBancarios;
	}

	public RegistroBancario findByIdAndConta(Integer idRegistroBancario, Integer idConta) {
		Conta conta = contaService.findByIdAndUsuario(idConta);

		Optional<RegistroBancario> registroBancario = registroBancarioRepository.findByIdAndConta(idRegistroBancario, conta);

		return registroBancario.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!",
				Registro.class.getName()));
	}

	public Page<RegistroBancario> findPageByConta(Integer page, Integer linesPerPage,
			String orderBy, String direction, Integer idConta) {

		Conta conta = contaService.findByIdAndUsuario(idConta);

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

		return registroBancarioRepository.findAllByConta(conta, pageRequest);
	}

	@Transactional
	public RegistroBancario insert(RegistroBancario registroBancario) {
		UserDetailsSpringSecurity user = UserService.authenticated();

		if (user == null || user.getId() != registroBancario.getConta().getUsuario().getId()) {
			throw new AuthorizationException("Acesso negado");
		}

		registroBancario.setId(null);

		registroBancario = registroBancarioRepository.save(registroBancario);

		SaldoBancario saldoBancario = saldoBancarioService.findFirstByContaOrderByDataHoraDesc(registroBancario.getConta());

		Double valor = saldoBancario.getSaldo() + registroBancario.getValor();

		SaldoBancario novoSaldoBancario = new SaldoBancario(null, valor, new Date(), registroBancario.getConta());

		saldoBancarioService.insert(novoSaldoBancario);

		// Adição automática de registro se a operação for uma saque.
		if (registroBancario.getItem().getNome().equals("Saque")) {
			TipoRegistro tipoRegistro = tipoRegistroService.findByNome("Saque");

			registroService.insert(new Registro(null, registroBancario.getValor(),
					registroBancario.getDataHora(), tipoRegistro,
					registroBancario.getConta().getUsuario(), registroBancario.getItem()));
		}

		return registroBancario;
	}

	public RegistroBancario fromDTO(RegistroBancarioDTO registroBancarioDto) {
		Conta conta = contaService.findByIdAndUsuario(registroBancarioDto.getContaId());

		Item item = itemService.find(registroBancarioDto.getItemId());

		return new RegistroBancario(registroBancarioDto.getId(), registroBancarioDto.getValor(),
				registroBancarioDto.getDataHora(), conta, item);
	}

}
