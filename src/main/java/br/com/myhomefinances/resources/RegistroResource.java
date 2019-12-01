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

import br.com.myhomefinances.domain.Registro;
import br.com.myhomefinances.dto.RegistroDTO;
import br.com.myhomefinances.services.RegistroService;

@RestController
@RequestMapping(value="registros")
public class RegistroResource {

	@Autowired
	RegistroService registroService;

	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<Registro>> findAll() {

		List<Registro> listaRegistros = registroService.findAll();

		return ResponseEntity.ok().body(listaRegistros);
	}

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Registro> find(@PathVariable Integer id) {

		Registro registro = registroService.find(id);

		return ResponseEntity.ok().body(registro);
	}

	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<Registro>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy,
			@RequestParam(value="direction", defaultValue="ASC") String direction) {

		Page<Registro> listaRegistros = registroService.findPage(page, linesPerPage, orderBy, direction);

		return ResponseEntity.ok().body(listaRegistros);
	}

	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody RegistroDTO registroDto) {

		Registro registro = registroService.fromDTO(registroDto);

		registro = registroService.insert(registro);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().
				path("/{id}").buildAndExpand(registro.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}

}
