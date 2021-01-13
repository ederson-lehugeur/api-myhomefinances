package br.com.myhomefinances.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.myhomefinances.domain.Perfil;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long> {

}
