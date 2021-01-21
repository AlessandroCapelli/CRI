package it.CroceRossaItaliana.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.CroceRossaItaliana.model.Sede;
import it.CroceRossaItaliana.repository.SedeRepository;

@Service
public class SedeService {
	
	@Autowired
	private SedeRepository sedeRepository;
	
	public List<Sede> findAll() {
		return sedeRepository.findAll();
	}
	
	public Optional<Sede> findById(long id) {
        return sedeRepository.findById(id);
    }

    public Sede createOrUpdate(Sede sede) {
        return sedeRepository.save(sede);
    }

    public void deleteById(long id) {
        sedeRepository.deleteById(id);
    }

}
