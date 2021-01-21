package it.CroceRossaItaliana.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.CroceRossaItaliana.model.Sede;

@Repository
public interface SedeRepository extends JpaRepository<Sede, Long> {

	@Query("SELECT s FROM Sede s WHERE s.nome = ?1")
	Iterable<Sede> findByNome(String nome);
	
	@Query("SELECT s FROM Sede s WHERE s.citta = ?1")
	Iterable<Sede> findByCitta(String citta);
	
}