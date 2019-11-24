package br.com.myhomefinances.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.myhomefinances.domain.TipoRegistro;
import br.com.myhomefinances.services.TipoRegistroService;

@RestController
@RequestMapping(value="tiposRegistros")
public class TipoRegistroResource {

	@Autowired
	TipoRegistroService tipoRegistroService;

	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<?> findAll() {

		List<TipoRegistro> listaTipoRegistro = tipoRegistroService.findAll();

		return ResponseEntity.ok().body(listaTipoRegistro);
	}

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {

		TipoRegistro tipoRegistro = tipoRegistroService.find(id);

		return ResponseEntity.ok().body(tipoRegistro);
	}

}
