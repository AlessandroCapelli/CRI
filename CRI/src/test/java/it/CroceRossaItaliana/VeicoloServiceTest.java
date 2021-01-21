package it.CroceRossaItaliana;

import static org.junit.jupiter.api.Assertions.assertEquals;

import it.CroceRossaItaliana.model.Veicolo;
import it.CroceRossaItaliana.repository.VeicoloRepository;
import it.CroceRossaItaliana.service.VeicoloService;

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
class VeicoloServiceTest {

    private static Veicolo v1;
    private static Veicolo v2;

    @Mock
    private VeicoloRepository veicoloRepository;

    @InjectMocks
    private VeicoloService veicoloService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeAll
    public static void init() {
    	v1 = new Veicolo(1, "Targa1");
        v2 = new Veicolo(1, "Targa2");
    }

    @Test
    public void findAllTest_WhenNoRecord() {
    	Mockito.when(veicoloRepository.findAll()).thenReturn(new ArrayList<Veicolo>());
    	assertEquals(veicoloService.findAll().size(), 0);
    	Mockito.verify(veicoloRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void findAllTest_WhenRecord() {
        Mockito.when(veicoloRepository.findAll()).thenReturn(Arrays.asList(v1, v2));
        assertEquals(veicoloService.findAll().size(), 2);
        assertEquals(veicoloService.findAll().get(0), v1);
        assertEquals(veicoloService.findAll().get(1), v2);
        Mockito.verify(veicoloRepository, Mockito.times(3)).findAll();
    }

    @Test
    public void findById() {
        Mockito.when(veicoloRepository.findById(1L)).thenReturn(Optional.of(v1));
        assertEquals(veicoloService.findById(1L), Optional.of(v1));
        Mockito.verify(veicoloRepository, Mockito.times(1)).findById(1L);
    }
    

    @Test
    void createOrUpdate() {
        Mockito.when(veicoloRepository.save(v1)).thenReturn(v1);
        assertEquals(veicoloService.createOrUpdate(v1), v1);
        Mockito.verify(veicoloRepository, Mockito.times(1)).save(v1);

        Mockito.when(veicoloRepository.save(v2)).thenReturn(v2);
        assertEquals(veicoloService.createOrUpdate(v2).getTarga().compareTo(v2.getTarga()), 0);
        Mockito.verify(veicoloRepository, Mockito.times(2)).save(v2);
    }

    @Test
    void deleteById() {
        veicoloService.deleteById(1L);
        Mockito.verify(veicoloRepository, Mockito.times(1)).deleteById(1L);
    }
    
}