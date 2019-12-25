package br.com.myhomefinances.services;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.myhomefinances.domain.Perfil;
import br.com.myhomefinances.dto.PerfilDTO;
import br.com.myhomefinances.repositories.PerfilRepository;
import br.com.myhomefinances.services.exception.DataIntegrityException;
import br.com.myhomefinances.services.exception.InvalidPerfilException;
import br.com.myhomefinances.services.exception.ObjectNotFoundException;

@Service
public class PerfilService {

	private static final String REGEX_PERFIL = "^ROLE_[A-Z_0-9]+$";

	@Autowired
	PerfilRepository perfilRepository;

	public List<Perfil> findAll() {
		List<Perfil> listaPerfis = perfilRepository.findAll();

		return listaPerfis;
	}

	public Perfil find(Integer id) {
		Optional<Perfil> perfil = perfilRepository.findById(id);

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

		Perfil novoPerfil = find(perfil.getId());

		updateData(novoPerfil, perfil);

		return perfilRepository.save(novoPerfil);
	}

	public void delete(Integer id) {
		find(id);

		try {
			perfilRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um perfil que está relacionado com um usuário.");
		}
	}

	public Perfil fromDTO(PerfilDTO perfilDto) {
		return new Perfil(perfilDto.getId(), perfilDto.getNome());
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
