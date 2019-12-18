package br.com.myhomefinances.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.myhomefinances.domain.RegistroBancario;
import br.com.myhomefinances.dto.RegistroBancarioDTO;
import br.com.myhomefinances.services.RegistroBancarioService;

@RestController
@RequestMapping(value="registrosBancarios")
public class RegistroBancarioResource {

	@Autowired
	RegistroBancarioService registroBancarioService;

	@RequestMapping(value="/conta/{idConta}", method=RequestMethod.GET)
	public ResponseEntity<List<RegistroBancario>> findByConta(@PathVariable Integer idConta) {

		List<RegistroBancario> listaRegistroBancarios = registroBancarioService.findByConta(idConta);

		return ResponseEntity.ok().body(listaRegistroBancarios);
	}

	@RequestMapping(value="/{idRegistroBancario}/conta/{idConta}", method=RequestMethod.GET)
	public ResponseEntity<RegistroBancario> find(@PathVariable Integer idRegistroBancario,
			@PathVariable Integer idConta) {

		RegistroBancario registroBancario = registroBancarioService.findByIdAndConta(idRegistroBancario,
				idConta);

		return ResponseEntity.ok().body(registroBancario);
	}

	@RequestMapping(value="/page/conta/{idConta}", method=RequestMethod.GET)
	public ResponseEntity<Page<RegistroBancario>> findPageByConta(
			@PathVariable Integer idConta,
			@RequestParam(value="page", defaultValue="0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy,
			@RequestParam(value="direction", defaultValue="ASC") String direction) {

		Page<RegistroBancario> listaRegistroBancarios = registroBancarioService.findPageByConta(page,
				linesPerPage, orderBy, direction, idConta);

		return ResponseEntity.ok().body(listaRegistroBancarios);
	}

	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody RegistroBancarioDTO registroBancarioDto) {

		RegistroBancario registroBancario = registroBancarioService.fromDTO(registroBancarioDto);

		registroBancario = registroBancarioService.insert(registroBancario);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().
				path("/{id}").buildAndExpand(registroBancario.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}

}
