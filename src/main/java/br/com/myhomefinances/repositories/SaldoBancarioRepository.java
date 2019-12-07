package br.com.myhomefinances.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.myhomefinances.domain.Conta;
import br.com.myhomefinances.domain.SaldoBancario;

@Repository
public interface SaldoBancarioRepository extends JpaRepository<SaldoBancario, Integer> {

	@Transactional(readOnly=true)
	SaldoBancario findFirstByContaOrderByDataHoraDesc(Conta conta);
}
