package br.com.myhomefinances.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.myhomefinances.domain.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, Integer> {

}
