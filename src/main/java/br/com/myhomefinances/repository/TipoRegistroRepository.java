package br.com.myhomefinances.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.myhomefinances.domain.TipoRegistro;

@Repository
public interface TipoRegistroRepository extends JpaRepository<TipoRegistro, Long> {

	Optional<TipoRegistro> findByNome(String nome);

}
