package it.CroceRossaItaliana;

import static org.junit.jupiter.api.Assertions.assertEquals;

import it.CroceRossaItaliana.model.Sede;
import it.CroceRossaItaliana.repository.SedeRepository;
import it.CroceRossaItaliana.service.SedeService;

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
class SedeServiceTest {

    private static Sede s1;
    private static Sede s2;

    @Mock
    private SedeRepository sedeRepository;

    @InjectMocks
    private SedeService sedeService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeAll
    public static void init() {
    	s1 = new Sede("Nome1", "Citta1");
        s2 = new Sede("Nome2", "Citta2");
    }

    @Test
    public void findAllTest_WhenNoRecord() {
    	Mockito.when(sedeRepository.findAll()).thenReturn(new ArrayList<Sede>());
    	assertEquals(sedeService.findAll().size(), 0);
       	Mockito.verify(sedeRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void findAllTest_WhenRecord() {
        Mockito.when(sedeRepository.findAll()).thenReturn(Arrays.asList(s1, s2));
        assertEquals(sedeService.findAll().size(), 2);
        assertEquals(sedeService.findAll().get(0), s1);
        assertEquals(sedeService.findAll().get(1), s2);
        Mockito.verify(sedeRepository, Mockito.times(3)).findAll();

    }

    @Test
    public void findById() {
        Mockito.when(sedeRepository.findById(1L)).thenReturn(Optional.of(s1));
        assertEquals(sedeService.findById(1L), Optional.of(s1));
        Mockito.verify(sedeRepository, Mockito.times(1)).findById(1L);
    }
    

    @Test
    void createOrUpdate() {
        Mockito.when(sedeRepository.save(s1)).thenReturn(s1);
        assertEquals(sedeService.createOrUpdate(s1), s1);
        Mockito.verify(sedeRepository, Mockito.times(1)).save(s1);

        Mockito.when(sedeRepository.save(s2)).thenReturn(s2);
        assertEquals(sedeService.createOrUpdate(s2).getNome().compareTo(s2.getNome()), 0);
        Mockito.verify(sedeRepository, Mockito.times(2)).save(s2);
    }

    @Test
    void deleteById() {
        sedeService.deleteById(1L);
        Mockito.verify(sedeRepository, Mockito.times(1)).deleteById(1L);
    }
    
}