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

import br.com.myhomefinances.domain.TipoConta;
import br.com.myhomefinances.services.TipoContaService;

@RestController
@RequestMapping(value="tiposContas")
public class TipoContaResource {

	@Autowired
	TipoContaService tipoContaService;

	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<TipoConta>> findAll() {

		List<TipoConta> listaTiposContas = tipoContaService.findAll();

		return ResponseEntity.ok().body(listaTiposContas);
	}

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<TipoConta> find(@PathVariable Integer id) {

		TipoConta tipoConta = tipoContaService.find(id);

		return ResponseEntity.ok().body(tipoConta);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody TipoConta tipoConta) {

		tipoConta = tipoContaService.insert(tipoConta);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().
				path("/{id}").buildAndExpand(tipoConta.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@PathVariable Integer id,
			@Valid @RequestBody TipoConta tipoConta) {

		tipoConta.setId(id);
		tipoConta = tipoContaService.update(tipoConta);

		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {

		tipoContaService.delete(id);

		return ResponseEntity.noContent().build();
	}

}
