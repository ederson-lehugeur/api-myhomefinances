package br.com.myhomefinances.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.myhomefinances.domain.TipoRegistro;

@Repository
public interface TipoRegistroRepository extends JpaRepository<TipoRegistro, Integer> {

}
