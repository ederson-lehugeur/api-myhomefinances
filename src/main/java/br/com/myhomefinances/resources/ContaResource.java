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

import br.com.myhomefinances.domain.Conta;
import br.com.myhomefinances.dto.ContaDTO;
import br.com.myhomefinances.services.ContaService;

@RestController
@RequestMapping(value="contas")
public class ContaResource {

	@Autowired
	ContaService contaService;

	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<Conta>> findAll() {

		List<Conta> listaContas = contaService.findAll();

		return ResponseEntity.ok().body(listaContas);
	}

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Conta> find(@PathVariable Integer id) {

		Conta conta = contaService.find(id);

		return ResponseEntity.ok().body(conta);
	}

	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<Conta>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy,
			@RequestParam(value="direction", defaultValue="ASC") String direction) {

		Page<Conta> listaContas = contaService.findPage(page, linesPerPage, orderBy, direction);

		return ResponseEntity.ok().body(listaContas);
	}

	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ContaDTO contaDto) {

		Conta conta = contaService.fromDTO(contaDto);

		conta = contaService.insert(conta);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().
				path("/{id}").buildAndExpand(conta.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@PathVariable Integer id,
			@Valid @RequestBody ContaDTO contaDto) {

		Conta conta = contaService.fromDTO(contaDto);

		conta.setId(id);
		conta = contaService.update(conta);

		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {

		contaService.delete(id);

		return ResponseEntity.noContent().build();
	}

}
