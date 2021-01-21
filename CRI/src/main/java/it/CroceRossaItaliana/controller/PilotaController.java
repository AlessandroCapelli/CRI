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
import it.CroceRossaItaliana.model.Persona;
import it.CroceRossaItaliana.model.Pilota;
import it.CroceRossaItaliana.service.PilotaService;

@RestController
@RequestMapping("/pilota")
public class PilotaController {
	
	@Autowired
    private PilotaService pilotaService;
	
	// Operazione READ di tutti gli oggetti presenti nel DB
	@GetMapping("/readAll")
	public ModelAndView piloti() {
	    ModelAndView modelAndView = new ModelAndView();
	    	    
	    Iterable<Pilota> piloti = pilotaService.findAll();

	    modelAndView.addObject("piloti", piloti);
	    modelAndView.setViewName("piloti");

	    return modelAndView;
	}
	
	// Operazione READ di un oggetto tramite id presente nel DB
	@GetMapping("/read/{id}")
	public ModelAndView pilota(@PathVariable("id") Long id) {
		ModelAndView modelAndView = new ModelAndView();
		
		Optional<Pilota> pilota = pilotaService.findById(id);
				
		// controllo che ci sia il pilota richiesto
		if(pilota.isPresent()) {
			modelAndView.addObject("pilota", pilota.get());
	    	modelAndView.setViewName("pilota");

	    	return modelAndView;
		}
		else
			throw new PersonaNotFoundException();
	}	
	
	// Operazione CREATE dell'oggetto Pilota
	@PostMapping("/create")
	public ModelAndView pilotaCreate(@Valid Pilota pilota, BindingResult result, Model model) {
		ModelAndView modelAndView = new ModelAndView();
		
		// creazione dell'oggetto, attualmente solo in memoria volatile
		Pilota pC = new Pilota(pilota.getNome(), pilota.getCognome(), pilota.getPatente());

		try {
			// provo a rendere persistente l'oggetto ricevuto tramite richiesta POST
			pilotaService.createOrUpdate(pC);
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		modelAndView.addObject("pilota", pC);
	    modelAndView.setViewName("pilota");

	    return modelAndView;
	}
	
	// Operazione DELETE dell'oggetto Pilota
	@GetMapping("/delete/{id}")
	public ModelAndView pilotaDelete(@PathVariable("id") Long id) {		
		try {
			// provo a cancellare l'oggetto (se presente nel DB) ricevuto tramite richiesta DELETE
			pilotaService.deleteById(id);
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		// ritorno la lista dei piloti rimasti nel DB a seguito della cancellazione
		return this.piloti();
	}
	
	// Operazione UPDATE dell'oggetto Pilota
	@PostMapping("/update")
	public ModelAndView pilotaUpdate(@Valid Pilota pilota, BindingResult result, Model model) {		
		try {
			// controllo l'esistenza dell'oggetto che si intende aggiornare
			Optional<Pilota>pTest = pilotaService.findById(pilota.getId());
			if(pTest.isPresent()) {
				pilota.setResponsabile(pTest.get().getResponsabile());
				pilota.setSedi(pTest.get().getSedi());
				pilotaService.createOrUpdate(pilota);
			}
			else
				throw new PersonaNotFoundException();
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		return this.pilota(pilota.getId());
	}
	
	// Operazione SEARCH per nome dei piloti presenti nel DB
	@GetMapping("/search/nome")
	public ModelAndView searchByNome(@RequestParam String nome) {
		ModelAndView modelAndView = new ModelAndView();

		Iterable<Persona> piloti = pilotaService.findByNome(nome);

		// controllo che ci sia almeno un pilota
		if(piloti instanceof Collection) {
			if(((Collection<Persona>) piloti).size() > 0) {
				modelAndView.addObject("piloti", piloti);
				modelAndView.setViewName("searchpiloti");
				return modelAndView;
			}
		}
		throw new PersonaNotFoundException();
	}
		
	// Operazione SEARCH per cognome dei piloti presenti nel DB
	@GetMapping("/search/cognome")
	public ModelAndView searchByCognome(@RequestParam String cognome) {
		ModelAndView modelAndView = new ModelAndView();

		Iterable<Persona> piloti = pilotaService.findByCognome(cognome);

		// controllo che ci sia almeno un pilota
		if(piloti instanceof Collection) {
			if(((Collection<Persona>) piloti).size() > 0) {
				modelAndView.addObject("piloti", piloti);
				modelAndView.setViewName("searchpiloti");
				return modelAndView;
			}
		}
		throw new PersonaNotFoundException();
	}
	
	// Operazione SEARCH per patente dei piloti presenti nel DB
	@GetMapping("/search/patente")
	public ModelAndView searchByPatente(@RequestParam String patente) {
		ModelAndView modelAndView = new ModelAndView();
		
		Iterable<Pilota> piloti = pilotaService.findByPatente(patente);
		
		// controllo che ci sia almeno un pilota
		if(piloti instanceof Collection) {
			if(((Collection<Pilota>) piloti).size() > 0) {
				modelAndView.addObject("piloti", piloti);
	    		modelAndView.setViewName("searchpiloti");
				return modelAndView;
			}
		}
		throw new PersonaNotFoundException();
	}

}
