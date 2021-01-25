package br.com.myhomefinances.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.myhomefinances.domain.Banco;
import br.com.myhomefinances.repository.BancoRepository;
import br.com.myhomefinances.resource.dto.BancoDto;
import br.com.myhomefinances.resource.form.BancoForm;
import br.com.myhomefinances.service.exception.ObjectNotFoundException;

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

	public Banco insert(Banco banco) {
		banco.setId(null);

		return bancoRepository.save(banco);
	}

	public Banco convertToEntity(BancoForm bancoForm) {
		return new Banco(bancoForm.getCodigo(), bancoForm.getNome());
	}

	public List<BancoDto> convertToDto(List<Banco> bancos) {
		return bancos.stream().map(BancoDto::new).collect(Collectors.toList());
	}

	public Banco update(Banco banco) {
		Banco novoBanco = findById(banco.getId());

		updateData(novoBanco, banco);

		return bancoRepository.save(novoBanco);
	}

	public void delete(Long id) {
		findById(id);

		bancoRepository.deleteById(id);
	}

	private void updateData(Banco novoBanco, Banco banco) {
		novoBanco.setCodigo(banco.getCodigo());
		novoBanco.setNome(banco.getNome());
	}

}
