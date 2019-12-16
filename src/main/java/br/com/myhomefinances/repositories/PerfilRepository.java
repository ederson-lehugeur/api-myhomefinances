package br.com.myhomefinances.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.myhomefinances.domain.Perfil;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Integer> {

}
