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
import it.CroceRossaItaliana.exception.VeicoloNotFoundException;
import it.CroceRossaItaliana.model.Sede;
import it.CroceRossaItaliana.model.Veicolo;
import it.CroceRossaItaliana.repository.VeicoloRepository;
import it.CroceRossaItaliana.service.SedeService;
import it.CroceRossaItaliana.service.VeicoloService;

@RestController
@RequestMapping("/veicolo")
public class VeicoloController {
	
	@Autowired
	private VeicoloService veicoloService;
	@Autowired
	private VeicoloRepository veicoloRepository;
	@Autowired
	private SedeService sedeService;
	
	// Operazione READ di tutti gli oggetti presenti nel DB
	@GetMapping("/readAll")
	public ModelAndView veicoli() {
		ModelAndView modelAndView = new ModelAndView();
		
		Iterable<Veicolo> veicoli = veicoloService.findAll();

	    modelAndView.addObject("veicoli", veicoli);
	    modelAndView.setViewName("veicoli");

	    return modelAndView;
	}
	
	// Operazione READ di un oggetto tramite id presente nel DB
	@GetMapping("/read/{id}")
	public ModelAndView veicolo(@PathVariable("id") Long id) {
		ModelAndView modelAndView = new ModelAndView();
		
		Optional<Veicolo> veicolo = veicoloService.findById(id);
		
		// controllo che ci sia il veicolo richiesto
		if(veicolo.isPresent()) {
			modelAndView.addObject("veicolo", veicolo.get());
	    	modelAndView.setViewName("veicolo");

	    	return modelAndView;
		}
		else
			throw new VeicoloNotFoundException();
	}
	
	// Operazione CREATE dell'oggetto Veicolo
	@PostMapping("/create")
	public ModelAndView veicoloCreate(@Valid Veicolo veicolo, BindingResult result, Model model) {
		ModelAndView modelAndView = new ModelAndView();
		
		// creazione dell'oggetto, attualmente solo in memoria volatile
		Veicolo vC = new Veicolo(veicolo.getNumeroPosti(), veicolo.getTarga());

		try {
			// provo a rendere persistente l'oggetto ricevuto tramite richiesta POST
			veicoloService.createOrUpdate(vC);
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		modelAndView.addObject("veicolo", vC);
	    modelAndView.setViewName("veicolo");

	    return modelAndView;
	}
	
	// Operazione DELETE dell'oggetto Veicolo
	@GetMapping("/delete/{id}")
	public ModelAndView veicoloDelete(@PathVariable("id") Long id) {
		try {
			// provo a cancellare l'oggetto (se presente nel DB) ricevuto tramite richiesta DELETE
			veicoloService.deleteById(id);
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		// ritorno la lista dei veicoli rimasti nel DB a seguito della cancellazione
		return this.veicoli();
	}
	
	// Operazione UPDATE dell'oggetto Veicolo
	@PostMapping("/update")
	public ModelAndView veicoloUpdate(@Valid Veicolo veicolo, BindingResult result, Model model) {		
		try {
			// controllo l'esistenza dell'oggetto che si intende aggiornare
			Optional<Veicolo>vTest = veicoloService.findById(veicolo.getId());
			if(vTest.isPresent()) {
				veicoloService.createOrUpdate(veicolo);
			}
			else
				throw new VeicoloNotFoundException();
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		return this.veicolo(veicolo.getId());
	}
	
	// Operazione SEARCH per targa dei veicoli presenti nel DB
	@GetMapping("/search/targa")
	public ModelAndView searchByTarga(@RequestParam String targa) {
		ModelAndView modelAndView = new ModelAndView();
		
		Iterable<Veicolo> veicoli = veicoloRepository.findByTarga(targa);

		// controllo che ci sia almeno un veicolo
		if(veicoli instanceof Collection) {
			if(((Collection<Veicolo>) veicoli).size() > 0) {
				modelAndView.addObject("veicoli", veicoli);
	    		modelAndView.setViewName("searchveicoli");
				return modelAndView;
			}
		}
		throw new VeicoloNotFoundException();
	}
	
	// Operazione READ di tutte le relazioni "mezzo" presenti nel DB
	@GetMapping("/readAllMezzi")
	public ModelAndView mezziRead() {
		ModelAndView modelAndView = new ModelAndView();

		Iterable<Veicolo> veicoli = veicoloService.findAll();

		modelAndView.addObject("veicoli", veicoli);
		modelAndView.setViewName("mezzi");

		return modelAndView;
	}
	
	// Operazione CREATE della relazione "mezzo"
	@PostMapping("/create/mezzo")
	public ModelAndView mezzoCreate(@RequestParam Long id_veicolo, @RequestParam Long id_sede) {
		Optional<Veicolo> veicolo = veicoloService.findById(id_veicolo);
		Optional<Sede> sede = sedeService.findById(id_sede);

		// controllo che ci siano il veicolo e la sede necessarie
		if(veicolo.isPresent()) {
			if(sede.isPresent()) {
				veicolo.get().setSede(sede.get());

				try {
					// provo a rendere persistente l'oggetto ricevuto tramite richiesta POST
					veicoloService.createOrUpdate(veicolo.get());
					sedeService.createOrUpdate(sede.get());
				}
				catch(Exception e) {
					System.out.println(e);
				}
				return this.mezziRead();
			}
			else
				throw new SedeNotFoundException();
		}
		else
			throw new VeicoloNotFoundException();
	}

	// Operazione DELETE della relazione "mezzo"
	@GetMapping("/delete/mezzo/{id_veicolo}")
	public ModelAndView mezzoDelete(@PathVariable("id_veicolo") Long id_veicolo) {		
		Optional<Veicolo> veicolo = veicoloService.findById(id_veicolo);

		// controllo che ci siano il veicolo e la sede necessarie
		if(veicolo.isPresent()) {
			Sede sedeT = veicolo.get().removeSede();
			Optional<Sede> sede = sedeService.findById(sedeT.getId());

			try {
				veicoloService.createOrUpdate(veicolo.get());
				sedeService.createOrUpdate(sede.get());
			}
			catch(Exception e) {
				System.out.println(e);
			}

			return this.mezziRead();
		}
		else
			throw new SedeNotFoundException();
	}

}