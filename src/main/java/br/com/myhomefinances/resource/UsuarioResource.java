package br.com.myhomefinances.resource;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.myhomefinances.domain.Usuario;
import br.com.myhomefinances.dto.UsuarioDto;
import br.com.myhomefinances.form.UsuarioNewForm;
import br.com.myhomefinances.form.UsuarioUpdateForm;
import br.com.myhomefinances.service.UsuarioService;

@RestController
@RequestMapping(value="usuarios")
public class UsuarioResource {

	@Autowired
	UsuarioService usuarioService;

	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping
	public ResponseEntity<Page<UsuarioDto>> findAll(
			@PageableDefault(page=0, size=12, sort="nome", direction=Direction.ASC) Pageable paginacao) {

		Page<Usuario> usuarios = usuarioService.findAll(paginacao);

		return ResponseEntity.ok().body(usuarioService.convertToDto(usuarios));
	}

	@GetMapping(value="/{id}")
	public ResponseEntity<UsuarioDto> findById(@PathVariable Long id) {
		Usuario usuario = usuarioService.findById(id);

		return ResponseEntity.ok().body(new UsuarioDto(usuario));
	}

	@GetMapping(value="/email")
	public ResponseEntity<UsuarioDto> findByEmail(@RequestParam(value="email") String email) {
		Usuario usuario = usuarioService.findByEmail(email);

		return ResponseEntity.ok().body(new UsuarioDto(usuario));
	}

	@PostMapping
	@Transactional
	public ResponseEntity<UsuarioDto> insert(@Valid @RequestBody UsuarioNewForm usuarioNewForm,
			UriComponentsBuilder uriBuilder) {

		Usuario usuario = usuarioService.convertToEntity(usuarioNewForm);

		usuario = usuarioService.insert(usuario);

		URI	uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();

		return ResponseEntity.created(uri).body(new UsuarioDto(usuario));
	}

	@PutMapping(value="/{id}")
	@Transactional
	public ResponseEntity<UsuarioDto> update(@PathVariable Long id,
			@Valid @RequestBody UsuarioUpdateForm usuarioUpdateForm) {

		Usuario usuario = usuarioService.convertToEntity(usuarioUpdateForm);

		usuario.setId(id);
		usuario = usuarioService.update(usuario);

		return ResponseEntity.ok(new UsuarioDto(usuario));
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping(value="/{id}")
	@Transactional
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		usuarioService.delete(id);

		return ResponseEntity.noContent().build();
	}

}
