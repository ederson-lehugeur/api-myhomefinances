package br.com.myhomefinances.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.myhomefinances.domain.Banco;

@Repository
public interface BancoRepository extends JpaRepository<Banco, Long> {

}
