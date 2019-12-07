package br.com.myhomefinances.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.myhomefinances.domain.RegistroBancario;

@Repository
public interface RegistroBancarioRepository extends JpaRepository<RegistroBancario, Integer> {

}
