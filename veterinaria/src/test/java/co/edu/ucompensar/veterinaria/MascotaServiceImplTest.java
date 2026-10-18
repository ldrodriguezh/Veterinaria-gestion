package co.edu.ucompensar.veterinaria;

import co.edu.ucompensar.veterinaria.model.Mascota;
import co.edu.ucompensar.veterinaria.repository.MascotaRepository;
import co.edu.ucompensar.veterinaria.service.MascotaServiceImpl;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class MascotaServiceImplTest {

    @Mock
    MascotaRepository mascotaRepository;

    @InjectMocks
    MascotaServiceImpl mascotaService;

    @BeforeEach
    void init() {
        mascotaRepository = Mockito.mock(MascotaRepository.class);
        mascotaService = new MascotaServiceImpl(mascotaRepository);
    }

    @Test
    void registrarDatosMascota() {
        Mascota gato=new Mascota();
        gato.setId(1l);
        gato.setNombre("Gato");
        gato.setEspecie("Gato");
        gato.setNombrePropietario("Jhon");
        gato.setTelefonoPropietario("12345");

        //emular el registro en la base de datos
        when(mascotaRepository.save(gato)).thenReturn(gato);


        Mascota p= mascotaService.registrar(gato);
        Assertions.assertEquals(1l,p.getId());
        verify(mascotaRepository).save(gato);

    }

}
