package it.CroceRossaItaliana.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.CroceRossaItaliana.model.Medico;
import it.CroceRossaItaliana.model.Persona;
import it.CroceRossaItaliana.repository.MedicoRepository;

@Service
public class MedicoService {
	
	@Autowired
    private MedicoRepository medicoRepository;

    public List<Medico> findAll() {
        return medicoRepository.findAll();
    }

    public Optional<Medico> findById(long id) {
        return medicoRepository.findById(id);
    }
    
    public Iterable<Persona> findByNome(String nome) {
        return medicoRepository.findByNome(nome);
    }
    
    public Iterable<Persona> findByCognome(String cognome) {
        return medicoRepository.findByCognome(cognome);
    }
    
    public Iterable<Medico> findBySpecializzazione(String specializzazione) {
        return medicoRepository.findBySpecializzazione(specializzazione);
    }

    public Medico createOrUpdate(Medico medico) {
        return medicoRepository.save(medico);
    }

    public void deleteById(long id) {
    	medicoRepository.deleteById(id);
    }

}
