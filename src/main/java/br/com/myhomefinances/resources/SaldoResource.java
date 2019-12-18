package br.com.myhomefinances.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.myhomefinances.domain.Saldo;
import br.com.myhomefinances.services.SaldoService;

@RestController
@RequestMapping(value="saldos")
public class SaldoResource {

	@Autowired
	SaldoService saldoService;

	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<?> findByUsuario() {

		List<Saldo> listaSaldos = saldoService.findByUsuario();

		return ResponseEntity.ok().body(listaSaldos);
	}

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> findByIdAndUsuario(@PathVariable Integer id) {

		Saldo saldo = saldoService.findByIdAndUsuario(id);

		return ResponseEntity.ok().body(saldo);
	}

}
