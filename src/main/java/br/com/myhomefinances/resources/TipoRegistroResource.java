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

import br.com.myhomefinances.domain.TipoRegistro;
import br.com.myhomefinances.dto.TipoRegistroDTO;
import br.com.myhomefinances.services.TipoRegistroService;

@RestController
@RequestMapping(value="tiposRegistros")
public class TipoRegistroResource {

	@Autowired
	TipoRegistroService tipoRegistroService;

	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<TipoRegistro>> find() {

		List<TipoRegistro> listaTipoRegistro = tipoRegistroService.find();

		return ResponseEntity.ok().body(listaTipoRegistro);
	}

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<TipoRegistro> findById(@PathVariable Integer id) {

		TipoRegistro tipoRegistro = tipoRegistroService.findById(id);

		return ResponseEntity.ok().body(tipoRegistro);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody TipoRegistroDTO tipoRegistroDto) {

		TipoRegistro tipoRegistro = tipoRegistroService.fromDTO(tipoRegistroDto);

		tipoRegistro = tipoRegistroService.insert(tipoRegistro);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().
				path("/{id}").buildAndExpand(tipoRegistro.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@PathVariable Integer id,
			@Valid @RequestBody TipoRegistroDTO tipoRegistroDto) {

		TipoRegistro tipoRegistro = tipoRegistroService.fromDTO(tipoRegistroDto);

		tipoRegistro.setId(id);
		tipoRegistro = tipoRegistroService.update(tipoRegistro);

		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {

		tipoRegistroService.delete(id);

		return ResponseEntity.noContent().build();
	}

}
