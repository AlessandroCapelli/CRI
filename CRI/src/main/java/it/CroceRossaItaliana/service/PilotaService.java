package it.CroceRossaItaliana.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.CroceRossaItaliana.model.Persona;
import it.CroceRossaItaliana.model.Pilota;
import it.CroceRossaItaliana.repository.PilotaRepository;

@Service
public class PilotaService {
	
	@Autowired
    private PilotaRepository pilotaRepository;

    public List<Pilota> findAll() {
        return pilotaRepository.findAll();
    }

    public Optional<Pilota> findById(long id) {
        return pilotaRepository.findById(id);
    }
    
    public Iterable<Persona> findByNome(String nome) {
        return pilotaRepository.findByNome(nome);
    }
    
    public Iterable<Persona> findByCognome(String cognome) {
        return pilotaRepository.findByCognome(cognome);
    }
    
    public Iterable<Pilota> findByPatente(String patente) {
        return pilotaRepository.findByPatente(patente);
    }

    public Pilota createOrUpdate(Pilota pilota) {
        return pilotaRepository.save(pilota);
    }

    public void deleteById(long id) {
    	pilotaRepository.deleteById(id);
    }

}
