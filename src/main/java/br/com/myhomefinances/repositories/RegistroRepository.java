package br.com.myhomefinances.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.myhomefinances.domain.Registro;

@Repository
public interface RegistroRepository extends JpaRepository<Registro, Integer> {

}
