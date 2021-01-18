package br.com.myhomefinances.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.myhomefinances.domain.Registro;

@Repository
public interface RegistroRepository extends JpaRepository<Registro, Long> {

	List<Registro> findByUsuarioId(Long idUsuario);

	Optional<Registro> findByIdAndUsuarioId(Long id, Long idUsuario);

	Page<Registro> findByUsuarioId(Long idUsuario, Pageable pageRequest);

}
