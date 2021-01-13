package br.com.myhomefinances.resource;

import java.net.URI;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.myhomefinances.domain.Perfil;
import br.com.myhomefinances.dto.PerfilDto;
import br.com.myhomefinances.form.PerfilForm;
import br.com.myhomefinances.service.PerfilService;

@RestController
@RequestMapping(value="perfis")
public class PerfilResource {

	@Autowired
	PerfilService perfilService;

	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping
	public ResponseEntity<List<PerfilDto>> findAll() {
		List<Perfil> perfis = perfilService.findAll();

		return ResponseEntity.ok().body(perfilService.convertToPerfilDto(perfis));
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping(value="/{id}")
	public ResponseEntity<PerfilDto> findById(@PathVariable Long id) {
		Perfil perfil = perfilService.findById(id);

		return ResponseEntity.ok().body(new PerfilDto(perfil));
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping
	public ResponseEntity<PerfilDto> insert(@Valid @RequestBody PerfilForm perfilForm,
			UriComponentsBuilder uriBuilder) {

		Perfil perfil = perfilService.convertToPerfil(perfilForm);

		perfil = perfilService.insert(perfil);

		URI	uri = uriBuilder.path("/categorias/{id}").buildAndExpand(perfil.getId()).toUri();

		return ResponseEntity.created(uri).body(new PerfilDto(perfil));
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping(value="/{id}")
	@Transactional
	public ResponseEntity<PerfilDto> update(@PathVariable Long id,
			@Valid @RequestBody PerfilForm perfilForm) {

		Perfil perfil = perfilService.convertToPerfil(perfilForm);

		perfil.setId(id);
		perfil = perfilService.update(perfil);

		return ResponseEntity.ok(new PerfilDto(perfil));
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping(value="/{id}")
	@Transactional
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		perfilService.delete(id);

		return ResponseEntity.noContent().build();
	}

}
