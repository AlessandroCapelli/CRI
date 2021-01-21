package it.CroceRossaItaliana.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.CroceRossaItaliana.model.Persona;
import it.CroceRossaItaliana.model.Soccorritore;
import it.CroceRossaItaliana.repository.SoccorritoreRepository;

@Service
public class SoccorritoreService {
	
	@Autowired
    private SoccorritoreRepository soccorritoreRepository;

    public List<Soccorritore> findAll() {
        return soccorritoreRepository.findAll();
    }

    public Optional<Soccorritore> findById(long id) {
        return soccorritoreRepository.findById(id);
    }
    
    public Iterable<Persona> findByNome(String nome) {
        return soccorritoreRepository.findByNome(nome);
    }
    
    public Iterable<Persona> findByCognome(String cognome) {
        return soccorritoreRepository.findByCognome(cognome);
    }
    
    public Iterable<Soccorritore> findByRuolo(String ruolo) {
        return soccorritoreRepository.findByRuolo(ruolo);
    }

    public Soccorritore createOrUpdate(Soccorritore soccorritore) {
        return soccorritoreRepository.save(soccorritore);
    }

    public void deleteById(long id) {
    	soccorritoreRepository.deleteById(id);
    }

}
