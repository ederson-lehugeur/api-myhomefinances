package br.com.myhomefinances.resource;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.myhomefinances.domain.Saldo;
import br.com.myhomefinances.dto.SaldoDto;
import br.com.myhomefinances.service.SaldoService;

@RestController
@RequestMapping(value="saldos")
public class SaldoResource {

	@Autowired
	SaldoService saldoService;

	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<SaldoDto>> find() {

		List<Saldo> listaSaldos = saldoService.find();

		List<SaldoDto> listaSaldosDto = listaSaldos.stream()
				.map(saldo -> new SaldoDto(saldo))
				.collect(Collectors.toList());

		return ResponseEntity.ok().body(listaSaldosDto);
	}

	@RequestMapping(value="/last", method=RequestMethod.GET)
	public ResponseEntity<SaldoDto> findFirstOrderByDataHoraDesc() {

		Saldo saldo = saldoService.findFirstOrderByDataHoraDesc();

		SaldoDto saldoDto = saldoService.toDTO(saldo);

		return ResponseEntity.ok().body(saldoDto);
	}

}
