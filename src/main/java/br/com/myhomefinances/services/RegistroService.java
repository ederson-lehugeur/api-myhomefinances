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

import br.com.myhomefinances.domain.Item;
import br.com.myhomefinances.domain.Registro;
import br.com.myhomefinances.domain.Saldo;
import br.com.myhomefinances.domain.TipoRegistro;
import br.com.myhomefinances.domain.Usuario;
import br.com.myhomefinances.dto.RegistroDTO;
import br.com.myhomefinances.repositories.RegistroRepository;
import br.com.myhomefinances.security.UserDetailsSpringSecurity;
import br.com.myhomefinances.services.exception.AuthorizationException;
import br.com.myhomefinances.services.exception.NegativeBalanceException;
import br.com.myhomefinances.services.exception.ObjectNotFoundException;

@Service
public class RegistroService {

	@Autowired
	RegistroRepository registroRepository;

	@Autowired
	SaldoService saldoService;

	@Autowired
	TipoRegistroService tipoRegistroService;

	@Autowired
	UsuarioService usuarioService;

	@Autowired
	ItemService itemService;

	public List<Registro> findByUsuario() {
		UserDetailsSpringSecurity user = UserService.authenticated();

		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}

		Usuario usuario = usuarioService.find(user.getId());

		List<Registro> listaRegistros = registroRepository.findByUsuario(usuario);

		return listaRegistros;
	}

	public Registro findByIdAndUsuario(Integer id) {
		UserDetailsSpringSecurity user = UserService.authenticated();

		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}

		Usuario usuario = usuarioService.find(user.getId());

		Optional<Registro> registro = registroRepository.findByIdAndUsuario(id, usuario);

		return registro.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!",
				Registro.class.getName()));
	}

	public Page<Registro> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

		UserDetailsSpringSecurity user = UserService.authenticated();

		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}

		Usuario usuario = usuarioService.find(user.getId());

		return registroRepository.findByUsuario(usuario, pageRequest);
	}

	@Transactional
	public Registro insert(Registro registro) {
		UserDetailsSpringSecurity user = UserService.authenticated();

		if (user == null || !registro.getUsuario().getId().equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}

		registro.setId(null);

		registro = registroRepository.save(registro);

		Saldo saldo = saldoService.findFirstByUsuarioOrderByDataHoraDesc(registro.getUsuario());

		Double valor = saldo.getSaldo() + registro.getValor();

		if (valor < 0) {
			throw new NegativeBalanceException("Saldo com valor negativo");
		}

		Saldo novoSaldo = new Saldo(null, valor, new Date(), registro.getUsuario());

		saldoService.insert(novoSaldo);

		return registro;
	}

	public Registro fromDTO(RegistroDTO registroDto) {
		TipoRegistro tipoRegistro = tipoRegistroService.find(registroDto.getTipoRegistroId());

		Usuario usuario = usuarioService.find(registroDto.getUsuarioId());

		Item item = itemService.find(registroDto.getItemId());

		return new Registro(registroDto.getId(), registroDto.getValor(), registroDto.getDataHora(),
				tipoRegistro, usuario, item);
	}

}
