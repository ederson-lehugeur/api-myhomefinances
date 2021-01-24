package br.com.myhomefinances.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.myhomefinances.domain.Saldo;
import br.com.myhomefinances.dto.SaldoDto;
import br.com.myhomefinances.service.SaldoService;

@RestController
@RequestMapping(value="saldos")
public class SaldoResource {

	@Autowired
	SaldoService saldoService;

	@GetMapping(value="/last")
	public ResponseEntity<SaldoDto> findLast() {
		Saldo saldo = saldoService.find();

		return ResponseEntity.ok().body(new SaldoDto(saldo));
	}

}
