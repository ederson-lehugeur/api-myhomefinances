package br.com.myhomefinances.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.myhomefinances.domain.TipoConta;

@Repository
public interface TipoContaRepository extends JpaRepository<TipoConta, Long> {

}
