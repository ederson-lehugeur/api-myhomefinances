package br.com.myhomefinances.resource;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.myhomefinances.domain.Usuario;
import br.com.myhomefinances.form.UsuarioNewForm;
import br.com.myhomefinances.form.UsuarioUpdateForm;
import br.com.myhomefinances.service.UsuarioService;

@RestController
@RequestMapping(value="usuarios")
public class UsuarioResource {

	@Autowired
	UsuarioService usuarioService;

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<Usuario>> findAll() {

		List<Usuario> listaUsuarios = usuarioService.findAll();

		return ResponseEntity.ok().body(listaUsuarios);
	}

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Usuario> find(@PathVariable Long id) {

		Usuario usuario = usuarioService.find(id);

		return ResponseEntity.ok().body(usuario);
	}

	@RequestMapping(value="/email", method=RequestMethod.GET)
	public ResponseEntity<Usuario> findByEmail(@RequestParam(value="email") String email) {

		Usuario usuario = usuarioService.findByEmail(email);

		return ResponseEntity.ok().body(usuario);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<Usuario>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy,
			@RequestParam(value="direction", defaultValue="ASC") String direction) {

		Page<Usuario> listaUsuarios = usuarioService.findPage(page, linesPerPage, orderBy, direction);

		return ResponseEntity.ok().body(listaUsuarios);
	}

	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody UsuarioNewForm usuarioNewDto) {

		Usuario usuario = usuarioService.fromNewDTO(usuarioNewDto);

		usuario = usuarioService.insert(usuario);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().
				path("/{id}").buildAndExpand(usuario.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@PathVariable Long id,
			@Valid @RequestBody UsuarioUpdateForm usuarioUpdateDto) {

		Usuario usuario = usuarioService.fromUpdateDTO(usuarioUpdateDto);

		usuario.setId(id);
		usuario = usuarioService.update(usuario);

		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Long id) {

		usuarioService.delete(id);

		return ResponseEntity.noContent().build();
	}

}
