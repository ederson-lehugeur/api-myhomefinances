package br.com.myhomefinances.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.myhomefinances.domain.Saldo;

@Repository
public interface SaldoRepository extends JpaRepository<Saldo, Integer> {

}
