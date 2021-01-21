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

import it.CroceRossaItaliana.exception.SedeNotFoundException;
import it.CroceRossaItaliana.model.Sede;
import it.CroceRossaItaliana.repository.SedeRepository;
import it.CroceRossaItaliana.service.SedeService;

@RestController
@RequestMapping("/sede")
public class SedeController {

	@Autowired
	private SedeService sedeService;
	@Autowired
	private SedeRepository sedeRepository;
	
	// Operazione READ di tutti gli oggetti presenti nel DB
	@GetMapping("/readAll")
	public ModelAndView sedi() {
		ModelAndView modelAndView = new ModelAndView();
		
		Iterable<Sede> sedi = sedeService.findAll();

		modelAndView.addObject("sedi", sedi);
	    modelAndView.setViewName("sedi");

	    return modelAndView;
	}
	
	// Operazione READ di un oggetto tramite id presente nel DB
	@GetMapping("/read/{id}")
	public ModelAndView sede(@PathVariable("id") Long id) {
		ModelAndView modelAndView = new ModelAndView();
		
		Optional<Sede> sede = sedeService.findById(id);
		
		// controllo che ci sia la sede richiesta
		if(sede.isPresent()) {
			modelAndView.addObject("sede", sede.get());
			modelAndView.setViewName("sede");

    		return modelAndView;
		}
		else
			throw new SedeNotFoundException();
	}
	
	// Operazione CREATE dell'oggetto Sede
	@PostMapping("/create")
	public ModelAndView sedeCreate(@Valid Sede sede, BindingResult result, Model model) {
		ModelAndView modelAndView = new ModelAndView();
		
		// creazione dell'oggetto, attualmente solo in memoria volatile
		Sede sC = new Sede(sede.getNome(), sede.getCitta());

		try {
			// provo a rendere persistente l'oggetto ricevuto tramite richiesta POST
			sedeService.createOrUpdate(sC);
		}
		catch(Exception e) {
			System.out.println(e);
		}

		modelAndView.addObject("sede", sC);
	    modelAndView.setViewName("sede");

	    return modelAndView;
	}
	
	// Operazione DELETE dell'oggetto Sede
	@GetMapping("/delete/{id}")
	public ModelAndView sedeDelete(@PathVariable("id") Long id) {
		try {
			// provo a cancellare l'oggetto (se presente nel DB) ricevuto tramite richiesta DELETE
			sedeService.deleteById(id);
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		// ritorno la lista delle sedi rimaste nel DB a seguito della cancellazione
		return this.sedi();
	}
	
	// Operazione UPDATE dell'oggetto Sede
	@PostMapping("/update")
	public ModelAndView sedeUpdate(@Valid Sede sede, BindingResult result, Model model) {		
		try {
			// controllo l'esistenza dell'oggetto che si intende aggiornare
			Optional<Sede>sTest = sedeService.findById(sede.getId());
			if(sTest.isPresent()) {
				sede.setPersone(sTest.get().getPersone());
				sedeService.createOrUpdate(sede);
			}
			else
				throw new SedeNotFoundException();
		}
		catch(Exception e) {
			System.out.println(e);
		}

		return this.sede(sede.getId());
	}

	// Operazione SEARCH per nome delle sedi presenti nel DB
	@GetMapping("/search/nome")
	public ModelAndView searchByNome(@RequestParam String nome) {
		ModelAndView modelAndView = new ModelAndView();
		
		Iterable<Sede> sedi = sedeRepository.findByNome(nome);

		// controllo che ci sia almeno una sede
		if(sedi instanceof Collection) {
			if(((Collection<Sede>) sedi).size() > 0) {
				modelAndView.addObject("sedi", sedi);
    			modelAndView.setViewName("searchsedi");
    			return modelAndView;
			}
		}
		throw new SedeNotFoundException();
	}
	
	// Operazione SEARCH per città delle sedi presenti nel DB
	@GetMapping("/search/citta")
	public ModelAndView searchByCitta(@RequestParam String citta) {
		ModelAndView modelAndView = new ModelAndView();
		
		Iterable<Sede> sedi = sedeRepository.findByCitta(citta);

		// controllo che ci sia almeno una sede
		if(sedi instanceof Collection) {
			if(((Collection<Sede>) sedi).size() > 0) {
				modelAndView.addObject("sedi", sedi);
    			modelAndView.setViewName("searchsedi");
    			return modelAndView;
			}
		}
		throw new SedeNotFoundException();
	}
	
}