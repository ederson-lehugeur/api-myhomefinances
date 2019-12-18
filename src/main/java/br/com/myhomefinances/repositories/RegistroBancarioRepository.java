package br.com.myhomefinances.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.myhomefinances.domain.Conta;
import br.com.myhomefinances.domain.RegistroBancario;

@Repository
public interface RegistroBancarioRepository extends JpaRepository<RegistroBancario, Integer> {

	@Transactional(readOnly=true)
	List<RegistroBancario> findByConta(Conta conta);

	@Transactional(readOnly=true)
	Optional<RegistroBancario> findByIdAndConta(Integer id, Conta conta);

	@Transactional(readOnly=true)
	Page<RegistroBancario> findAllByConta(Conta conta, Pageable pageRequest);
}
