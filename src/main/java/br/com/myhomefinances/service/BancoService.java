package br.com.myhomefinances.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.myhomefinances.domain.Banco;
import br.com.myhomefinances.dto.BancoDto;
import br.com.myhomefinances.form.BancoForm;
import br.com.myhomefinances.repository.BancoRepository;
import br.com.myhomefinances.security.UserDetailsSpringSecurity;
import br.com.myhomefinances.services.exception.AuthorizationException;
import br.com.myhomefinances.services.exception.DataIntegrityException;
import br.com.myhomefinances.services.exception.ObjectNotFoundException;

@Service
public class BancoService {

	@Autowired
	BancoRepository bancoRepository;

	public List<Banco> findAll() {
		List<Banco> listaBancos = bancoRepository.findAll();

		return listaBancos;
	}

	public Banco findById(Long id) {
		Optional<Banco> banco = bancoRepository.findById(id);

		return banco.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!",
				Banco.class.getName()));
	}

	public Page<Banco> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

		return bancoRepository.findAll(pageRequest);
	}

	public Banco insert(Banco banco) {
		banco.setId(null);

		return bancoRepository.save(banco);
	}

	public Banco convertFormToEntity(BancoForm bancoForm) {
		getUserAuthenticated();

		return new Banco(bancoForm.getNome());
	}

	public List<BancoDto> convertEntityToDto(List<Banco> bancos) {
		return bancos.stream().map(BancoDto::new).collect(Collectors.toList());
	}

	public Page<BancoDto> convertEntityToDto(Page<Banco> bancosPage) {
		return bancosPage.map(BancoDto::new);
	}

	public Banco update(Banco banco) {
		Banco novoBanco = findById(banco.getId());

		updateData(novoBanco, banco);

		return bancoRepository.save(novoBanco);
	}

	public void delete(Long id) {
		findById(id);

		try {
			bancoRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma banco que está associado"
					+ " a uma conta.");
		}
	}

	private void updateData(Banco novoBanco, Banco banco) {
		novoBanco.setNome(banco.getNome());
	}

	// Criar anotação para injetar usuário logado.
	private UserDetailsSpringSecurity getUserAuthenticated() {
		UserDetailsSpringSecurity user = UserService.authenticated();

		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}

		return user;
	}

}
