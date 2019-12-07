package br.com.myhomefinances.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.myhomefinances.domain.SaldoBancario;
import br.com.myhomefinances.services.SaldoBancarioService;

@RestController
@RequestMapping(value="saldosBancarios")
public class SaldoBancarioResource {

	@Autowired
	SaldoBancarioService saldoBancarioService;

	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<SaldoBancario>> findAll() {

		List<SaldoBancario> listaSaldosBancarios = saldoBancarioService.findAll();

		return ResponseEntity.ok().body(listaSaldosBancarios);
	}

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<SaldoBancario> find(@PathVariable Integer id) {

		SaldoBancario saldoBancario = saldoBancarioService.find(id);

		return ResponseEntity.ok().body(saldoBancario);
	}

}
