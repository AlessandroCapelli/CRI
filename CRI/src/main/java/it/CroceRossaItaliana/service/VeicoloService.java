package it.CroceRossaItaliana.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.CroceRossaItaliana.model.Veicolo;
import it.CroceRossaItaliana.repository.VeicoloRepository;

@Service
public class VeicoloService {
	
	@Autowired
    private VeicoloRepository veicoloRepository;

    public List<Veicolo> findAll() {
        return veicoloRepository.findAll();
    }

    public Optional<Veicolo> findById(long id) {
        return veicoloRepository.findById(id);
    }

    public Veicolo createOrUpdate(Veicolo veicolo) {
        return veicoloRepository.save(veicolo);
    }

    public void deleteById(long id) {
        veicoloRepository.deleteById(id);
    }

}
