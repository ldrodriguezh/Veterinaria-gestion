package co.edu.ucompensar.veterinaria;

import co.edu.ucompensar.veterinaria.model.Mascota;
import co.edu.ucompensar.veterinaria.repository.MascotaRepository;
import co.edu.ucompensar.veterinaria.service.MascotaServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MascotaServiceImplTest {

    @Mock
    private MascotaRepository mascotaRepository;

    @InjectMocks
    private MascotaServiceImpl mascotaService;


    Mascota crearMascota() {
        Mascota mascota = new Mascota();
        mascota.setNombre("Pecas");
        mascota.setEspecie("Dalmata");
        mascota.setNombrePropietario("Jhon");
        mascota.setTelefonoPropietario("3051234567");
        return mascota;
    }

    @Test
    void registrar_mascota() {
        Mascota mascota = crearMascota();
        Mascota mascota2 = crearMascota();
        mascota2.setId(22L);

        when(mascotaRepository.save(mascota)).thenReturn(mascota2);
        Mascota mascotaBD = mascotaService.registrar(mascota);

        assertEquals(22L, mascotaBD.getId());
        verify(mascotaRepository).save(mascota);
    }

    @Test
    void actualizar_mascota() {
        Mascota mascotaBD = crearMascota();
        mascotaBD.setId(22L);

        Mascota nuevaMascota = new Mascota();
        nuevaMascota.setNombre("Pecueca");

        when(mascotaRepository.findById(22L)).thenReturn(Optional.of(mascotaBD));
        when(mascotaRepository.save(any(Mascota.class))).thenReturn(mascotaBD);

        Mascota mascotaActualizada = mascotaService.actualizar(nuevaMascota, 22L);

        assertEquals("Pecueca", mascotaActualizada.getNombre());
        verify(mascotaRepository).save(mascotaBD);
    }
}