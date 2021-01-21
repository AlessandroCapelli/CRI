package it.CroceRossaItaliana.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.CroceRossaItaliana.model.Persona;
import it.CroceRossaItaliana.repository.PersonaRepository;

@Service
public class PersonaService {
	
	@Autowired
    private PersonaRepository<Persona> personaRepository;

    public List<Persona> findAll() {
        return personaRepository.findAll();
    }

    public Optional<Persona> findById(long id) {
        return personaRepository.findById(id);
    }
    
    public Iterable<Persona> findByNome(String nome) {
        return personaRepository.findByNome(nome);
    }
    
    public Iterable<Persona> findByCognome(String cognome) {
        return personaRepository.findByCognome(cognome);
    }

    public Persona createOrUpdate(Persona persona) {
        return personaRepository.save(persona);
    }

    public void deleteById(long id) {
        personaRepository.deleteById(id);
    }
	
}