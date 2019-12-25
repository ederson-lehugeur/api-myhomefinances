package br.com.myhomefinances.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.myhomefinances.domain.Banco;
import br.com.myhomefinances.repositories.BancoRepository;
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

	public Banco find(Integer id) {
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

	public Banco update(Banco banco) {
		Banco novoBanco = find(banco.getId());

		updateData(novoBanco, banco);

		return bancoRepository.save(novoBanco);
	}

	public void delete(Integer id) {
		find(id);

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

}
