package it.CroceRossaItaliana.repository;

import org.springframework.stereotype.Repository;

import it.CroceRossaItaliana.model.Soccorritore;

@Repository
public interface SoccorritoreRepository extends PersonaRepository<Soccorritore> {

	Iterable<Soccorritore> findByRuolo(String ruolo);
	
}
