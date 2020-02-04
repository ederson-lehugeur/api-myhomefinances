package br.com.myhomefinances.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.myhomefinances.domain.Saldo;
import br.com.myhomefinances.dto.SaldoDTO;
import br.com.myhomefinances.services.SaldoService;

@RestController
@RequestMapping(value="saldos")
public class SaldoResource {

	@Autowired
	SaldoService saldoService;

	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<SaldoDTO>> find() {

		List<Saldo> listaSaldos = saldoService.find();

		List<SaldoDTO> listaSaldosDto = listaSaldos.stream()
				.map(saldo -> new SaldoDTO(saldo))
				.collect(Collectors.toList());

		return ResponseEntity.ok().body(listaSaldosDto);
	}

	@RequestMapping(value="/last", method=RequestMethod.GET)
	public ResponseEntity<SaldoDTO> findFirstOrderByDataHoraDesc() {

		Saldo saldo = saldoService.findFirstOrderByDataHoraDesc();

		SaldoDTO saldoDto = saldoService.toDTO(saldo);

		return ResponseEntity.ok().body(saldoDto);
	}

}
