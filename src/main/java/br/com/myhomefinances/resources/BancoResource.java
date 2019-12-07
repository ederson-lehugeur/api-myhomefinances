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

import br.com.myhomefinances.domain.Banco;
import br.com.myhomefinances.services.BancoService;

@RestController
@RequestMapping(value="bancos")
public class BancoResource {

	@Autowired
	BancoService bancoService;

	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<Banco>> findAll() {

		List<Banco> listaBancos = bancoService.findAll();

		return ResponseEntity.ok().body(listaBancos);
	}

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Banco> find(@PathVariable Integer id) {

		Banco banco = bancoService.find(id);

		return ResponseEntity.ok().body(banco);
	}

	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<Banco>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy,
			@RequestParam(value="direction", defaultValue="ASC") String direction) {

		Page<Banco> listaBancos = bancoService.findPage(page, linesPerPage, orderBy, direction);

		return ResponseEntity.ok().body(listaBancos);
	}

	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Banco> insert(@Valid @RequestBody Banco banco) {

		banco = bancoService.insert(banco);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().
				path("/{id}").buildAndExpand(banco.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@PathVariable Integer id,
			@Valid @RequestBody Banco banco) {

		banco.setId(id);
		banco = bancoService.update(banco);

		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {

		bancoService.delete(id);

		return ResponseEntity.noContent().build();
	}

}
