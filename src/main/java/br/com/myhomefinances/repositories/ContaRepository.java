package br.com.myhomefinances.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.myhomefinances.domain.Conta;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Integer> {

}
