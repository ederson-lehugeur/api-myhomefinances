package br.com.myhomefinances.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.myhomefinances.domain.Usuario;
import br.com.myhomefinances.services.UsuarioService;

@RestController
@RequestMapping(value="usuarios")
public class UsuarioResource {

	@Autowired
	UsuarioService usuarioService;

	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<?> findAll() {

		List<Usuario> listaUsuarios = usuarioService.findAll();

		return ResponseEntity.ok().body(listaUsuarios);
	}

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {

		Usuario usuario = usuarioService.find(id);

		return ResponseEntity.ok().body(usuario);
	}

}
