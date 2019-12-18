package br.com.myhomefinances.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.myhomefinances.domain.Perfil;
import br.com.myhomefinances.dto.PerfilDTO;
import br.com.myhomefinances.services.PerfilService;

@RestController
@RequestMapping(value="perfis")
public class PerfilResource {

	@Autowired
	PerfilService perfilService;

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<Perfil>> findAll() {

		List<Perfil> listaPerfil = perfilService.findAll();

		return ResponseEntity.ok().body(listaPerfil);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Perfil> find(@PathVariable Integer id) {

		Perfil perfil = perfilService.find(id);

		return ResponseEntity.ok().body(perfil);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody PerfilDTO perfilDto) {

		Perfil perfil = perfilService.fromDTO(perfilDto);

		perfil = perfilService.insert(perfil);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().
				path("/{id}").buildAndExpand(perfil.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@PathVariable Integer id,
			@Valid @RequestBody PerfilDTO perfilDto) {

		Perfil perfil = perfilService.fromDTO(perfilDto);

		perfil.setId(id);
		perfil = perfilService.update(perfil);

		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {

		perfilService.delete(id);

		return ResponseEntity.noContent().build();
	}

}
