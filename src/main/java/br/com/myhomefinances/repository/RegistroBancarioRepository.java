package br.com.myhomefinances.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.myhomefinances.domain.Conta;
import br.com.myhomefinances.domain.RegistroBancario;

@Repository
public interface RegistroBancarioRepository extends JpaRepository<RegistroBancario, Long> {

	List<RegistroBancario> findByConta(Conta conta);

	Optional<RegistroBancario> findByIdAndConta(Long id, Conta conta);

	Page<RegistroBancario> findAllByConta(Conta conta, Pageable pageRequest);

}
