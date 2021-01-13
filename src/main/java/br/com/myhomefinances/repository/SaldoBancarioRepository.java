package br.com.myhomefinances.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.myhomefinances.domain.Conta;
import br.com.myhomefinances.domain.SaldoBancario;

@Repository
public interface SaldoBancarioRepository extends JpaRepository<SaldoBancario, Long> {

	List<SaldoBancario> findByContaOrderByDataHoraDesc(Conta conta);

	Optional<SaldoBancario> findByIdAndConta(Long id, Conta conta);

	SaldoBancario findFirstByContaOrderByDataHoraDesc(Conta conta);

}
