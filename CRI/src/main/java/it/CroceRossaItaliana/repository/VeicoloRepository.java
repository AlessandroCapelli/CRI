package it.CroceRossaItaliana.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.CroceRossaItaliana.model.Veicolo;

@Repository
public interface VeicoloRepository extends JpaRepository<Veicolo, Long> {
	
	@Query("SELECT v FROM Veicolo v WHERE v.targa = ?1")
	Iterable<Veicolo> findByTarga(String targa);
	
}