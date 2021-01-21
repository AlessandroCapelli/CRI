package it.CroceRossaItaliana;

import static org.junit.jupiter.api.Assertions.assertEquals;

import it.CroceRossaItaliana.model.Pilota;
import it.CroceRossaItaliana.model.Persona;
import it.CroceRossaItaliana.repository.PilotaRepository;
import it.CroceRossaItaliana.service.PilotaService;

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
class PilotaServiceTest {

    private static Pilota p1;
    private static Pilota p2;

    @Mock
    private PilotaRepository pilotaRepository;

    @InjectMocks
    private PilotaService pilotaService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeAll
    public static void init() {
    	p1 = new Pilota("Nome1", "Cognome1", "Specializzazione1");
        p2 = new Pilota("Nome2", "Cognome2", "Specializzazione2");
    }

    @Test
    public void findAllTest_WhenNoRecord() {
    	Mockito.when(pilotaRepository.findAll()).thenReturn(new ArrayList<Pilota>());
       	assertEquals(pilotaService.findAll().size(), 0);
       	Mockito.verify(pilotaRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void findAllTest_WhenRecord() {
        Mockito.when(pilotaRepository.findAll()).thenReturn(Arrays.asList(p1, p2));
        assertEquals(pilotaService.findAll().size(), 2);
        assertEquals(pilotaService.findAll().get(0), p1);
        assertEquals(pilotaService.findAll().get(1), p2);
        Mockito.verify(pilotaRepository, Mockito.times(3)).findAll();
    }

    @Test
    public void findById() {
        Mockito.when(pilotaRepository.findById(1L)).thenReturn(Optional.of(p1));
        assertEquals(pilotaService.findById(1L), Optional.of(p1));
        Mockito.verify(pilotaRepository, Mockito.times(1)).findById(1L);
    }
    
    @Test
    public void findByNome() {
        Mockito.when(pilotaRepository.findByNome("Nome1")).thenReturn((Arrays.asList((Persona)p1)));
        assertEquals(pilotaService.findByNome("Nome1"), Arrays.asList(p1));
        Mockito.verify(pilotaRepository, Mockito.times(1)).findByNome("Nome1");
    }
    
    @Test
    public void findByCognome() {
        Mockito.when(pilotaRepository.findByNome("Cognome1")).thenReturn(Arrays.asList((Persona)p1));
        assertEquals(pilotaService.findByNome("Cognome1"), Arrays.asList(p1));
        Mockito.verify(pilotaRepository, Mockito.times(1)).findByNome("Cognome1");
    }
    
    @Test
    public void findBySpecializzazione() {
        Mockito.when(pilotaRepository.findByPatente("Patente1")).thenReturn(Arrays.asList(p1));
        assertEquals(pilotaService.findByPatente("Patente1"), Arrays.asList(p1));
        Mockito.verify(pilotaRepository, Mockito.times(1)).findByPatente("Patente1");
    }

    @Test
    void createOrUpdate() {
        Mockito.when(pilotaRepository.save(p1)).thenReturn(p1);
        assertEquals(pilotaService.createOrUpdate(p1), p1);
        Mockito.verify(pilotaRepository, Mockito.times(1)).save(p1);

        Mockito.when(pilotaRepository.save(p2)).thenReturn(p2);
        assertEquals(pilotaService.createOrUpdate(p2).getNome().compareTo(p2.getNome()), 0);
        Mockito.verify(pilotaRepository, Mockito.times(2)).save(p2);
    }

    @Test
    void deleteById() {
        pilotaService.deleteById(1L);
        Mockito.verify(pilotaRepository, Mockito.times(1)).deleteById(1L);
    }
    
}