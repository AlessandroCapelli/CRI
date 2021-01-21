package it.CroceRossaItaliana;

import static org.junit.jupiter.api.Assertions.assertEquals;

import it.CroceRossaItaliana.model.Medico;
import it.CroceRossaItaliana.model.Persona;
import it.CroceRossaItaliana.repository.MedicoRepository;
import it.CroceRossaItaliana.service.MedicoService;

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
class MedicoServiceTest {

    private static Medico m1;
    private static Medico m2;

    @Mock
    private MedicoRepository medicoRepository;

    @InjectMocks
    private MedicoService medicoService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeAll
    public static void init() {
    	m1 = new Medico("Nome1", "Cognome1", "Specializzazione1");
        m2 = new Medico("Nome2", "Cognome2", "Specializzazione2");
    }

    @Test
    public void findAllTest_WhenNoRecord() {
    	Mockito.when(medicoRepository.findAll()).thenReturn(new ArrayList<Medico>());
    	assertEquals(medicoService.findAll().size(), 0);
    	Mockito.verify(medicoRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void findAllTest_WhenRecord() {
        Mockito.when(medicoRepository.findAll()).thenReturn(Arrays.asList(m1, m2));
        assertEquals(medicoService.findAll().size(), 2);
        assertEquals(medicoService.findAll().get(0), m1);
        assertEquals(medicoService.findAll().get(1), m2);
        Mockito.verify(medicoRepository, Mockito.times(3)).findAll();
    }

    @Test
    public void findById() {
        Mockito.when(medicoRepository.findById(1L)).thenReturn(Optional.of(m1));
        assertEquals(medicoService.findById(1L), Optional.of(m1));
        Mockito.verify(medicoRepository, Mockito.times(1)).findById(1L);
    }
    
    @Test
    public void findByNome() {
        Mockito.when(medicoRepository.findByNome("Nome1")).thenReturn(Arrays.asList((Persona)m1));
        assertEquals(medicoService.findByNome("Nome1"), Arrays.asList(m1));
        Mockito.verify(medicoRepository, Mockito.times(1)).findByNome("Nome1");
    }
    
    @Test
    public void findByCognome() {
        Mockito.when(medicoRepository.findByNome("Cognome1")).thenReturn(Arrays.asList((Persona)m1));
        assertEquals(medicoService.findByNome("Cognome1"), Arrays.asList(m1));
        Mockito.verify(medicoRepository, Mockito.times(1)).findByNome("Cognome1");
    }
    
    @Test
    public void findBySpecializzazione() {
        Mockito.when(medicoRepository.findBySpecializzazione("Specializzazione1")).thenReturn(Arrays.asList(m1));
        assertEquals(medicoService.findBySpecializzazione("Specializzazione1"), Arrays.asList(m1));
        Mockito.verify(medicoRepository, Mockito.times(1)).findBySpecializzazione("Specializzazione1");
    }

    @Test
    void createOrUpdate() {
        Mockito.when(medicoRepository.save(m1)).thenReturn(m1);
        assertEquals(medicoService.createOrUpdate(m1), m1);
        Mockito.verify(medicoRepository, Mockito.times(1)).save(m1);

        Mockito.when(medicoRepository.save(m2)).thenReturn(m2);
        assertEquals(medicoService.createOrUpdate(m2).getNome().compareTo(m2.getNome()), 0);
        Mockito.verify(medicoRepository, Mockito.times(2)).save(m2);
    }

    @Test
    void deleteById() {
        medicoService.deleteById(1L);
        Mockito.verify(medicoRepository, Mockito.times(1)).deleteById(1L);
    }
    
}