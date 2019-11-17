package br.com.myhomefinances.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="categorias")
public class CategoriaResource {

	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<?> categoriaREST() {

		return ResponseEntity.ok().body("REST Categoria funcionando!");
	}

}
