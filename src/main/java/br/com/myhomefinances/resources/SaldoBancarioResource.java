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

	@RequestMapping(value="/conta/{idConta}", method=RequestMethod.GET)
	public ResponseEntity<List<SaldoBancario>> findByContaOrderByDataHoraDesc(@PathVariable Integer idConta) {

		List<SaldoBancario> listaSaldosBancarios = saldoBancarioService.findByContaOrderByDataHoraDesc(idConta);

		return ResponseEntity.ok().body(listaSaldosBancarios);
	}

	@RequestMapping(value="/{idSaldoBancario}/conta/{idConta}", method=RequestMethod.GET)
	public ResponseEntity<SaldoBancario> findByIdAndConta(@PathVariable Integer idSaldoBancario,
			@PathVariable Integer idConta) {

		SaldoBancario saldoBancario = saldoBancarioService.findByIdAndConta(idSaldoBancario, idConta);

		return ResponseEntity.ok().body(saldoBancario);
	}

}
