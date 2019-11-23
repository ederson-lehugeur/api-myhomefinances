package br.com.myhomefinances.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.myhomefinances.domain.Status;
import br.com.myhomefinances.services.StatusService;

@RestController
@RequestMapping(value="status")
public class StatusResource {

	@Autowired
	StatusService statusService;

	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<?> findAll() {

		List<Status> listaStatus = statusService.findAll();

		return ResponseEntity.ok().body(listaStatus);
	}

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {

		Status status = statusService.find(id);

		return ResponseEntity.ok().body(status);
	}

}
