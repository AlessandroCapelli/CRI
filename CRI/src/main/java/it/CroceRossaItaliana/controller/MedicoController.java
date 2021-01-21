package it.CroceRossaItaliana.controller;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import it.CroceRossaItaliana.exception.PersonaNotFoundException;
import it.CroceRossaItaliana.model.Medico;
import it.CroceRossaItaliana.model.Persona;
import it.CroceRossaItaliana.service.MedicoService;

@RestController
@RequestMapping("/medico")
public class MedicoController {
	
	@Autowired
    private MedicoService medicoService;
	
	// Operazione READ di tutti gli oggetti presenti nel DB
	@GetMapping("/readAll")
	public ModelAndView medici() {
	    ModelAndView modelAndView = new ModelAndView();
	    	    
	    Iterable<Medico> medici = medicoService.findAll();

	    modelAndView.addObject("medici", medici);
	    modelAndView.setViewName("medici");

	    return modelAndView;
	}
	
	// Operazione READ di un oggetto tramite id presente nel DB
	@GetMapping("/read/{id}")
	public ModelAndView medico(@PathVariable("id") Long id) {
		ModelAndView modelAndView = new ModelAndView();
		
		Optional<Medico> medico = medicoService.findById(id);
				
		// controllo che ci sia il medico richiesto
		if(medico.isPresent()) {
			modelAndView.addObject("medico", medico.get());
	    	modelAndView.setViewName("medico");

	    	return modelAndView;
		}
		else
			throw new PersonaNotFoundException();
	}	
	
	// Operazione CREATE dell'oggetto Medico
	@PostMapping("/create")
	public ModelAndView medicoCreate(@Valid Medico medico, BindingResult result, Model model) {
		ModelAndView modelAndView = new ModelAndView();
		
		// creazione dell'oggetto, attualmente solo in memoria volatile
		Medico mC = new Medico(medico.getNome(), medico.getCognome(), medico.getSpecializzazione());

		try {
			// provo a rendere persistente l'oggetto ricevuto tramite richiesta POST
			medicoService.createOrUpdate(mC);
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		modelAndView.addObject("medico", mC);
	    modelAndView.setViewName("medico");

	    return modelAndView;
	}
	
	// Operazione DELETE dell'oggetto Medico
	@GetMapping("/delete/{id}")
	public ModelAndView medicoDelete(@PathVariable("id") Long id) {		
		try {
			// provo a cancellare l'oggetto (se presente nel DB) ricevuto tramite richiesta DELETE
			medicoService.deleteById(id);
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		// ritorno la lista dei medici rimasti nel DB a seguito della cancellazione
		return this.medici();
	}
	
	// Operazione UPDATE dell'oggetto Medico
	@PostMapping("/update")
	public ModelAndView medicoUpdate(@Valid Medico medico, BindingResult result, Model model) {		
		try {
			// controllo l'esistenza dell'oggetto che si intende aggiornare
			Optional<Medico>mTest = medicoService.findById(medico.getId());
			if(mTest.isPresent()) {
				medico.setResponsabile(mTest.get().getResponsabile());
				medico.setSedi(mTest.get().getSedi());
				medicoService.createOrUpdate(medico);
			}
			else
				throw new PersonaNotFoundException();
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		return this.medico(medico.getId());
	}
	
	// Operazione SEARCH per nome dei medici presenti nel DB
	@GetMapping("/search/nome")
	public ModelAndView searchByNome(@RequestParam String nome) {
		ModelAndView modelAndView = new ModelAndView();

		Iterable<Persona> medici = medicoService.findByNome(nome);

		// controllo che ci sia almeno un medico
		if(medici instanceof Collection) {
			if(((Collection<Persona>) medici).size() > 0) {
				modelAndView.addObject("medici", medici);
				modelAndView.setViewName("searchmedici");
				return modelAndView;
			}
		}
		throw new PersonaNotFoundException();
	}
		
	// Operazione SEARCH per cognome dei medici presenti nel DB
	@GetMapping("/search/cognome")
	public ModelAndView searchByCognome(@RequestParam String cognome) {
		ModelAndView modelAndView = new ModelAndView();

		Iterable<Persona> medici = medicoService.findByCognome(cognome);

		// controllo che ci sia almeno un medico
		if(medici instanceof Collection) {
			if(((Collection<Persona>) medici).size() > 0) {
				modelAndView.addObject("medici", medici);
				modelAndView.setViewName("searchmedici");
				return modelAndView;
			}
		}
		throw new PersonaNotFoundException();
	}
	
	// Operazione SEARCH per specializzazione dei medici presenti nel DB
	@GetMapping("/search/specializzazione")
	public ModelAndView searchBySpecializzazione(@RequestParam String specializzazione) {
		ModelAndView modelAndView = new ModelAndView();
		
		Iterable<Medico> medici = medicoService.findBySpecializzazione(specializzazione);
		
		// controllo che ci sia almeno un medico
		if(medici instanceof Collection) {
			if(((Collection<Medico>) medici).size() > 0) {
				modelAndView.addObject("medici", medici);
	    		modelAndView.setViewName("searchmedici");
				return modelAndView;
			}
		}
		throw new PersonaNotFoundException();
	}

}
