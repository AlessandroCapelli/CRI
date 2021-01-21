package it.CroceRossaItaliana.repository;

import org.springframework.stereotype.Repository;

import it.CroceRossaItaliana.model.Pilota;

@Repository
public interface PilotaRepository extends PersonaRepository<Pilota> {
	
	Iterable<Pilota> findByPatente(String patente);

}
