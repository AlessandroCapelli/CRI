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
import it.CroceRossaItaliana.model.Soccorritore;
import it.CroceRossaItaliana.service.SoccorritoreService;

@RestController
@RequestMapping("soccorritore")
public class SoccorritoreController {

	@Autowired
    private SoccorritoreService soccorritoreService;
	
	// Operazione READ di tutti gli oggetti presenti nel DB
	@GetMapping("/readAll")
	public ModelAndView soccorritori() {
	    ModelAndView modelAndView = new ModelAndView();
	    	    
	    Iterable<Soccorritore> soccorritori = soccorritoreService.findAll();

	    modelAndView.addObject("soccorritori", soccorritori);
	    modelAndView.setViewName("soccorritori");

	    return modelAndView;
	}
	
	// Operazione READ di un oggetto tramite id presente nel DB
	@GetMapping("/read/{id}")
	public ModelAndView soccorritore(@PathVariable("id") Long id) {
		ModelAndView modelAndView = new ModelAndView();
		
		Optional<Soccorritore> soccorritore = soccorritoreService.findById(id);
				
		// controllo che ci sia il soccorritore richiesto
		if(soccorritore.isPresent()) {
			modelAndView.addObject("soccorritore", soccorritore.get());
	    	modelAndView.setViewName("soccorritore");

	    	return modelAndView;
		}
		else
			throw new PersonaNotFoundException();
	}	
	
	// Operazione CREATE dell'oggetto Soccorritore
	@PostMapping("/create")
	public ModelAndView soccorritoreCreate(@Valid Soccorritore soccorritore, BindingResult result, Model model) {
		ModelAndView modelAndView = new ModelAndView();
		
		// creazione dell'oggetto, attualmente solo in memoria volatile
		Soccorritore sC = new Soccorritore(soccorritore.getNome(), soccorritore.getCognome(), soccorritore.getRuolo());

		try {
			// provo a rendere persistente l'oggetto ricevuto tramite richiesta POST
			soccorritoreService.createOrUpdate(sC);
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		modelAndView.addObject("soccorritore", sC);
	    modelAndView.setViewName("soccorritore");

	    return modelAndView;
	}
	
	// Operazione DELETE dell'oggetto Soccorritore
	@GetMapping("/delete/{id}")
	public ModelAndView soccorritoreDelete(@PathVariable("id") Long id) {		
		try {
			// provo a cancellare l'oggetto (se presente nel DB) ricevuto tramite richiesta DELETE
			soccorritoreService.deleteById(id);
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		// ritorno la lista dei soccorritori rimasti nel DB a seguito della cancellazione
		return this.soccorritori();
	}
	
	// Operazione UPDATE dell'oggetto Soccorritore
	@PostMapping("/update")
	public ModelAndView soccorritoreUpdate(@Valid Soccorritore soccorritore, BindingResult result, Model model) {		
		try {
			// controllo l'esistenza dell'oggetto che si intende aggiornare
			Optional<Soccorritore>sTest = soccorritoreService.findById(soccorritore.getId());
			if(sTest.isPresent()) {
				soccorritore.setResponsabile(sTest.get().getResponsabile());
				soccorritore.setSedi(sTest.get().getSedi());
				soccorritoreService.createOrUpdate(soccorritore);
			}
			else
				throw new PersonaNotFoundException();
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		return this.soccorritore(soccorritore.getId());
	}
	
	// Operazione SEARCH per nome dei soccorritori presenti nel DB
	@GetMapping("/search/nome")
	public ModelAndView searchByNome(@RequestParam String nome) {
		ModelAndView modelAndView = new ModelAndView();

		Iterable<Persona> soccorritori = soccorritoreService.findByNome(nome);

		// controllo che ci sia almeno un soccorritore
		if(soccorritori instanceof Collection) {
			if(((Collection<Persona>) soccorritori).size() > 0) {
				modelAndView.addObject("soccorritori", soccorritori);
				modelAndView.setViewName("searchsoccorritori");
				return modelAndView;
			}
		}
		throw new PersonaNotFoundException();
	}
		
	// Operazione SEARCH per cognome dei soccorritori presenti nel DB
	@GetMapping("/search/cognome")
	public ModelAndView searchByCognome(@RequestParam String cognome) {
		ModelAndView modelAndView = new ModelAndView();

		Iterable<Persona> soccorritori = soccorritoreService.findByCognome(cognome);

		// controllo che ci sia almeno un soccorritore
		if(soccorritori instanceof Collection) {
			if(((Collection<Persona>) soccorritori).size() > 0) {
				modelAndView.addObject("soccorritori", soccorritori);
				modelAndView.setViewName("searchsoccorritori");
				return modelAndView;
			}
		}
		throw new PersonaNotFoundException();
	}
	
	// Operazione SEARCH per ruolo dei soccorritori presenti nel DB
	@GetMapping("/search/ruolo")
	public ModelAndView searchByRuolo(@RequestParam String ruolo) {
		ModelAndView modelAndView = new ModelAndView();
		
		Iterable<Soccorritore> soccorritori = soccorritoreService.findByRuolo(ruolo);
		
		// controllo che ci sia almeno un soccorritore
		if(soccorritori instanceof Collection) {
			if(((Collection<Soccorritore>) soccorritori).size() > 0) {
				modelAndView.addObject("soccorritori", soccorritori);
	    		modelAndView.setViewName("searchsoccorritori");
				return modelAndView;
			}
		}
		throw new PersonaNotFoundException();
	}
	
}
