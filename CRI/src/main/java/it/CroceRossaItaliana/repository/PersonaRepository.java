package it.CroceRossaItaliana.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.CroceRossaItaliana.model.Persona;

@Repository
public interface PersonaRepository<T extends Persona> extends JpaRepository<T, Long> {
	
	Iterable<Persona> findByNome(String nome);
	Iterable<Persona> findByCognome(String cognome);

}