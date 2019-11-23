package br.com.myhomefinances.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.myhomefinances.domain.Status;
import br.com.myhomefinances.repositories.StatusRepository;
import br.com.myhomefinances.services.exception.ObjectNotFoundException;

@Service
public class StatusService {

	@Autowired
	StatusRepository statusRepository;

	public List<Status> findAll() {
		List<Status> listaStatus = statusRepository.findAll();

		return listaStatus;
	}

	public Status find(Integer id) {
		Optional<Status> status = statusRepository.findById(id);

		return status.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!",
				id, Status.class.getName()));
	}

}
