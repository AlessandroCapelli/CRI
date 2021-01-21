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
import it.CroceRossaItaliana.exception.SedeNotFoundException;
import it.CroceRossaItaliana.model.Persona;
import it.CroceRossaItaliana.model.Sede;
import it.CroceRossaItaliana.service.PersonaService;
import it.CroceRossaItaliana.service.SedeService;

@RestController
@RequestMapping("/persona")
public class PersonaController {
	
	@Autowired
    private PersonaService personaService;
	@Autowired
	private SedeService sedeService;
	
	// Operazione READ di tutti gli oggetti presenti nel DB
	@GetMapping("/readAll")
	public ModelAndView persone() {
	    ModelAndView modelAndView = new ModelAndView();
	    	    
	    Iterable<Persona> persone = personaService.findAll();

	    modelAndView.addObject("persone", persone);
	    modelAndView.setViewName("persone");

	    return modelAndView;
	}
	
	// Operazione READ di un oggetto tramite id presente nel DB
	@GetMapping("/read/{id}")
	public ModelAndView persona(@PathVariable("id") Long id) {
		ModelAndView modelAndView = new ModelAndView();
		
		Optional<Persona> persona = personaService.findById(id);
				
		// controllo che ci sia la persona richiesta
		if(persona.isPresent()) {
			modelAndView.addObject("persona", persona.get());
	    	modelAndView.setViewName("persona");

	    	return modelAndView;
		}
		else
			throw new PersonaNotFoundException();
	}	
	
	// Operazione CREATE dell'oggetto Persona
	@PostMapping("/create")
	public ModelAndView personaCreate(@Valid Persona persona, BindingResult result, Model model) {
		ModelAndView modelAndView = new ModelAndView();
		
		// creazione dell'oggetto, attualmente solo in memoria volatile
		Persona pC = new Persona(persona.getNome(), persona.getCognome());

		try {
			// provo a rendere persistente l'oggetto ricevuto tramite richiesta POST
			personaService.createOrUpdate(pC);
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		modelAndView.addObject("persona", pC);
	    modelAndView.setViewName("persona");

	    return modelAndView;
	}
	
	// Operazione DELETE dell'oggetto Persona
	@GetMapping("/delete/{id}")
	public ModelAndView personaDelete(@PathVariable("id") Long id) {		
		try {
			// provo a cancellare l'oggetto (se presente nel DB) ricevuto tramite richiesta DELETE
			personaService.deleteById(id);
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		// ritorno la lista delle persone rimaste nel DB a seguito della cancellazione
		return this.persone();
	}
	
	// Operazione UPDATE dell'oggetto Persona
	@PostMapping("/update")
	public ModelAndView personaUpdate(@Valid Persona persona, BindingResult result, Model model) {		
		try {
			// controllo l'esistenza dell'oggetto che si intende aggiornare
			Optional<Persona>pTest = personaService.findById(persona.getId());
			if(pTest.isPresent()) {
				persona.setResponsabile(pTest.get().getResponsabile());
				persona.setSedi(pTest.get().getSedi());
				personaService.createOrUpdate(persona);
			}
			else
				throw new PersonaNotFoundException();
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		return this.persona(persona.getId());
	}
	
	// Operazione SEARCH per nome delle persone presenti nel DB
	@GetMapping("/search/nome")
	public ModelAndView searchByNome(@RequestParam String nome) {
		ModelAndView modelAndView = new ModelAndView();
		
		Iterable<Persona> persone = personaService.findByNome(nome);
		
		// controllo che ci sia almeno una persona
		if(persone instanceof Collection) {
			if(((Collection<Persona>) persone).size() > 0) {
				modelAndView.addObject("persone", persone);
	    		modelAndView.setViewName("searchpersone");
				return modelAndView;
			}
		}
		throw new PersonaNotFoundException();
	}
	
	// Operazione SEARCH per cognome delle persone presenti nel DB
	@GetMapping("/search/cognome")
	public ModelAndView searchByCognome(@RequestParam String cognome) {
		ModelAndView modelAndView = new ModelAndView();
		
		Iterable<Persona> persone = personaService.findByCognome(cognome);
		
		// controllo che ci sia almeno una persona
		if(persone instanceof Collection) {
			if(((Collection<Persona>) persone).size() > 0) {
				modelAndView.addObject("persone", persone);
	    		modelAndView.setViewName("searchpersone");
				return modelAndView;
			}
		}
		throw new PersonaNotFoundException();
	}
	
	// Operazione READ di tutte le relazioni "responsabile" presenti nel DB
	@GetMapping("/readAllResponsabili")
	public ModelAndView responsabiliRead() {
		ModelAndView modelAndView = new ModelAndView();

		Iterable<Persona> persone = personaService.findAll();
		
		modelAndView.addObject("persone", persone);
		modelAndView.setViewName("responsabili");

		return modelAndView;
	}
	
	// Operazione CREATE della relazione "responsabile"
	@PostMapping("/create/responsabile")
	public ModelAndView responsabileCreate(@RequestParam Long id_responsabile, @RequestParam Long id_subordinato) {		
		Optional<Persona> personaR = personaService.findById(id_responsabile);
		Optional<Persona> personaS= personaService.findById(id_subordinato);
		
		// controllo che ci siano le persone necessarie
		if(personaR.isPresent() && personaS.isPresent()) {
			personaS.get().setResponsabile(personaR.get());
			
			try {
				// provo a rendere persistente l'oggetto ricevuto tramite richiesta POST
				personaService.createOrUpdate(personaS.get());
			}
			catch(Exception e) {
				System.out.println(e);
			}
	    	return this.responsabiliRead();
		}
		else
			throw new PersonaNotFoundException();
	}
	
	// Operazione DELETE della relazione "responsabile"
	@GetMapping("/delete/responsabile/{id_subordinato}")
	public ModelAndView responsabileDelete(@PathVariable("id_subordinato") Long id_subordinato) {		
		Optional<Persona> personaS= personaService.findById(id_subordinato);
		
		// controllo che ci siano le persone necessarie
		if(personaS.isPresent()) {
			personaS.get().removeResponsabile();
			
			try {
				personaService.createOrUpdate(personaS.get());
			}
			catch(Exception e) {
				System.out.println(e);
			}
	    	return this.responsabiliRead();
		}
		else
			throw new PersonaNotFoundException();
	}
	
	// Operazione READ di tutte le relazioni "turno" presenti nel DB
	@GetMapping("/readAllTurni")
	public ModelAndView turniRead() {
		ModelAndView modelAndView = new ModelAndView();

		Iterable<Persona> persone = personaService.findAll();
		
		modelAndView.addObject("persone", persone);
		modelAndView.setViewName("turni");

		return modelAndView;
	}
	
	// Operazione CREATE della relazione "turno"
	@PostMapping("/create/turno")
	public ModelAndView turnoCreate(@RequestParam Long id_persona, @RequestParam Long id_sede) {
		Optional<Persona> persona = personaService.findById(id_persona);
		Optional<Sede> sede = sedeService.findById(id_sede);

		// controllo che ci siano le persone e sedi necessarie
		if(persona.isPresent()) {
			if(sede.isPresent()) {
				persona.get().addSede(sede.get());

				try {
					// provo a rendere persistente l'oggetto ricevuto tramite richiesta POST
					personaService.createOrUpdate(persona.get());
					sedeService.createOrUpdate(sede.get());
				}
				catch(Exception e) {
					System.out.println(e);
				}
				return this.turniRead();
			}
			else
				throw new SedeNotFoundException();
		}
		else
			throw new PersonaNotFoundException();
	}
	
	// Operazione DELETE della relazione "turno"
	@PostMapping("/delete/turno")
	public ModelAndView turnoDelete(@RequestParam Long id_persona, @RequestParam Long id_sede) {		
		Optional<Persona> persona = personaService.findById(id_persona);
		Optional<Sede> sede = sedeService.findById(id_sede);
		
		// controllo che ci siano le persone e le sedi necessarie
		if(persona.isPresent()) {
			if(sede.isPresent()) {
				persona.get().removeSede(sede.get());
				
				try {
					personaService.createOrUpdate(persona.get());
					sedeService.createOrUpdate(sede.get());
				}
				catch(Exception e) {
					System.out.println(e);
				}
		    	
				return this.turniRead();
			}
			else
				throw new SedeNotFoundException();
		}
		else
			throw new PersonaNotFoundException();
	}
	
}
