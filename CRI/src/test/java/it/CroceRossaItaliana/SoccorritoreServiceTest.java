package it.CroceRossaItaliana;

import static org.junit.jupiter.api.Assertions.assertEquals;

import it.CroceRossaItaliana.model.Persona;
import it.CroceRossaItaliana.model.Soccorritore;
import it.CroceRossaItaliana.repository.SoccorritoreRepository;
import it.CroceRossaItaliana.service.SoccorritoreService;

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
class SoccorritoreServiceTest {

    private static Soccorritore s1;
    private static Soccorritore s2;

    @Mock
    private SoccorritoreRepository soccorritoreRepository;

    @InjectMocks
    private SoccorritoreService soccorritoreService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeAll
    public static void init() {
    	s1 = new Soccorritore("Nome1", "Cognome1", "Ruolo1");
        s2 = new Soccorritore("Nome2", "Cognome2", "Ruolo2");
    }

    @Test
    public void findAllTest_WhenNoRecord() {
    	Mockito.when(soccorritoreRepository.findAll()).thenReturn(new ArrayList<Soccorritore>());
    	assertEquals(soccorritoreService.findAll().size(), 0);
    	Mockito.verify(soccorritoreRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void findAllTest_WhenRecord() {
        Mockito.when(soccorritoreRepository.findAll()).thenReturn(Arrays.asList(s1, s2));
        assertEquals(soccorritoreService.findAll().size(), 2);
        assertEquals(soccorritoreService.findAll().get(0), s1);
        assertEquals(soccorritoreService.findAll().get(1), s2);
        Mockito.verify(soccorritoreRepository, Mockito.times(3)).findAll();
    }

    @Test
    public void findById() {
        Mockito.when(soccorritoreRepository.findById(1L)).thenReturn(Optional.of(s1));
        assertEquals(soccorritoreService.findById(1L), Optional.of(s1));
        Mockito.verify(soccorritoreRepository, Mockito.times(1)).findById(1L);
    }
    
    @Test
    public void findByNome() {
        Mockito.when(soccorritoreRepository.findByNome("Nome1")).thenReturn(Arrays.asList((Persona)s1));
        assertEquals(soccorritoreService.findByNome("Nome1"), Arrays.asList(s1));
        Mockito.verify(soccorritoreRepository, Mockito.times(1)).findByNome("Nome1");
    }
    
    @Test
    public void findByCognome() {
        Mockito.when(soccorritoreRepository.findByNome("Cognome1")).thenReturn(Arrays.asList((Persona)s1));
        assertEquals(soccorritoreService.findByNome("Cognome1"), Arrays.asList(s1));
        Mockito.verify(soccorritoreRepository, Mockito.times(1)).findByNome("Cognome1");
    }
    
    @Test
    public void findBySpecializzazione() {
        Mockito.when(soccorritoreRepository.findByRuolo("Ruolo1")).thenReturn(Arrays.asList(s1));
        assertEquals(soccorritoreService.findByRuolo("Ruolo1"), Arrays.asList(s1));
        Mockito.verify(soccorritoreRepository, Mockito.times(1)).findByRuolo("Ruolo1");
    }

    @Test
    void createOrUpdate() {
        Mockito.when(soccorritoreRepository.save(s1)).thenReturn(s1);
        assertEquals(soccorritoreService.createOrUpdate(s1), s1);
        Mockito.verify(soccorritoreRepository, Mockito.times(1)).save(s1);

        Mockito.when(soccorritoreRepository.save(s2)).thenReturn(s2);
        assertEquals(soccorritoreService.createOrUpdate(s2).getNome().compareTo(s2.getNome()), 0);
        Mockito.verify(soccorritoreRepository, Mockito.times(2)).save(s2);
    }

    @Test
    void deleteById() {
        soccorritoreService.deleteById(1L);
        Mockito.verify(soccorritoreRepository, Mockito.times(1)).deleteById(1L);
    }
    
}