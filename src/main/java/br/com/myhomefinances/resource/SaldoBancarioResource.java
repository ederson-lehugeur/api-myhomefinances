package br.com.myhomefinances.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.myhomefinances.domain.SaldoBancario;
import br.com.myhomefinances.dto.SaldoBancarioDto;
import br.com.myhomefinances.service.SaldoBancarioService;

@RestController
@RequestMapping(value="saldos-bancarios")
public class SaldoBancarioResource {

	@Autowired
	SaldoBancarioService saldoBancarioService;

	@GetMapping(value="/conta/{idConta}")
	public ResponseEntity<List<SaldoBancarioDto>> findByConta(@PathVariable Long idConta) {
		List<SaldoBancario> saldosBancarios = saldoBancarioService.findByContaOrderByDataHoraDesc(idConta);

		return ResponseEntity.ok().body(saldoBancarioService.convertToDto(saldosBancarios));
	}

	@GetMapping(value="/{idSaldoBancario}/conta/{idConta}")
	public ResponseEntity<SaldoBancarioDto> findByIdAndConta(@PathVariable Long idSaldoBancario,
			@PathVariable Long idConta) {

		SaldoBancario saldoBancario = saldoBancarioService.findByIdAndConta(idSaldoBancario, idConta);

		return ResponseEntity.ok().body(new SaldoBancarioDto(saldoBancario));
	}

}
