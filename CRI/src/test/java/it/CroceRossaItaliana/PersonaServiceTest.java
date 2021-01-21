package it.CroceRossaItaliana;

import static org.junit.jupiter.api.Assertions.assertEquals;

import it.CroceRossaItaliana.model.Persona;
import it.CroceRossaItaliana.repository.PersonaRepository;
import it.CroceRossaItaliana.service.PersonaService;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class PersonaServiceTest {

    private static Persona p1;
    private static Persona p2;

    @Mock
    private PersonaRepository<Persona> personaRepository;

    @InjectMocks
    private PersonaService personaService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeAll
    public static void init() {
    	p1 = new Persona("Nome1", "Cognome1");
        p2 = new Persona("Nome2", "Cognome2");
    }

    @Test
    public void findAllTest_WhenNoRecord() {
    	Mockito.when(personaRepository.findAll()).thenReturn(new ArrayList<Persona>());
    	assertEquals(personaService.findAll().size(), 0);
    	Mockito.verify(personaRepository, Mockito.times(1)).findAll();

    }

    @Test
    public void findAllTest_WhenRecord() {
        Mockito.when(personaRepository.findAll()).thenReturn(Arrays.asList(p1, p2));
        assertEquals(personaService.findAll().size(), 2);
        assertEquals(personaService.findAll().get(0), p1);
        assertEquals(personaService.findAll().get(1), p2);
        Mockito.verify(personaRepository, Mockito.times(3)).findAll();

    }

    @Test
    public void findById() {
        Mockito.when(personaRepository.findById(1L)).thenReturn(Optional.of(p1));
        assertEquals(personaService.findById(1L), Optional.of(p1));
        Mockito.verify(personaRepository, Mockito.times(1)).findById(1L);
    }
    
    @Test
    public void findByNome() {
        Mockito.when(personaRepository.findByNome("Nome1")).thenReturn(Arrays.asList(p1));
        assertEquals(personaService.findByNome("Nome1"), Arrays.asList(p1));
        Mockito.verify(personaRepository, Mockito.times(1)).findByNome("Nome1");
    }
    
    @Test
    public void findByCognome() {
        Mockito.when(personaRepository.findByNome("Cognome1")).thenReturn(Arrays.asList(p1));
        assertEquals(personaService.findByNome("Cognome1"), Arrays.asList(p1));
        Mockito.verify(personaRepository, Mockito.times(1)).findByNome("Cognome1");
    }

    @Test
    void createOrUpdate() {
        Mockito.when(personaRepository.save(p1)).thenReturn(p1);
        assertEquals(personaService.createOrUpdate(p1), p1);
        Mockito.verify(personaRepository, Mockito.times(1)).save(p1);

        Mockito.when(personaRepository.save(p2)).thenReturn(p2);
        assertEquals(personaService.createOrUpdate(p2).getNome().compareTo(p2.getNome()), 0);
        Mockito.verify(personaRepository, Mockito.times(2)).save(p2);
    }

    @Test
    void deleteById() {
        personaService.deleteById(1L);
        Mockito.verify(personaRepository, Mockito.times(1)).deleteById(1L);
    }
    
}