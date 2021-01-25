package br.com.myhomefinances.service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.myhomefinances.domain.Perfil;
import br.com.myhomefinances.repository.PerfilRepository;
import br.com.myhomefinances.resource.dto.PerfilDto;
import br.com.myhomefinances.resource.form.PerfilForm;
import br.com.myhomefinances.service.exception.InvalidPerfilException;
import br.com.myhomefinances.service.exception.ObjectNotFoundException;

@Service
public class PerfilService {

	private static final String REGEX_PERFIL = "^ROLE_[A-Z_0-9]+$";

	@Autowired
	PerfilRepository perfilRepository;

	public List<Perfil> findAll() {
		List<Perfil> perfis = perfilRepository.findAll();

		return perfis;
	}

	public Perfil findById(Long id) {
		Optional<Perfil> perfil = perfilRepository.findById(id);

		return perfil.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!",
				Perfil.class.getName()));
	}

	public Perfil findByNome(String nome) {
		Optional<Perfil> perfil = perfilRepository.findByNome(nome);

		return perfil.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!",
				Perfil.class.getName()));
	}

	public Page<Perfil> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

		return perfilRepository.findAll(pageRequest);
	}

	public Perfil insert(Perfil perfil) {
		if (!validarPerfil(perfil.getNome())) {
			throw new InvalidPerfilException("Perfil inválido.");
		}

		perfil.setId(null);

		return perfilRepository.save(perfil);
	}

	public Perfil update(Perfil perfil) {
		if (!validarPerfil(perfil.getNome())) {
			throw new InvalidPerfilException("Perfil inválido.");
		}

		Perfil novoPerfil = findById(perfil.getId());

		updateData(novoPerfil, perfil);

		return perfilRepository.save(novoPerfil);
	}

	public void delete(Long id) {
		findById(id);

		perfilRepository.deleteById(id);
	}

	public Perfil convertToEntity(PerfilForm perfilForm) {
		return new Perfil(null, perfilForm.getNome());
	}

	public List<PerfilDto> convertToDto(List<Perfil> perfis) {
		return perfis.stream().map(PerfilDto::new).collect(Collectors.toList());
	}

	private void updateData(Perfil novoPerfil, Perfil perfil) {
		novoPerfil.setNome(perfil.getNome());
	}

	private boolean validarPerfil(String perfil) {
		Pattern p = Pattern.compile(REGEX_PERFIL);
		Matcher m = p.matcher(perfil);

		return m.matches();
	}

}
