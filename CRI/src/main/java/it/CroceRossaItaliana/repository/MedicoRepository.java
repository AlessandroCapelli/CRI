package it.CroceRossaItaliana.repository;

import org.springframework.stereotype.Repository;

import it.CroceRossaItaliana.model.Medico;

@Repository
public interface MedicoRepository extends PersonaRepository<Medico> {

	Iterable<Medico> findBySpecializzazione(String specializzazione);
	
}
