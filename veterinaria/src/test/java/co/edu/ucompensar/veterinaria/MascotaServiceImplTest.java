package co.edu.ucompensar.veterinaria;

import co.edu.ucompensar.veterinaria.Exception.ValidacionException;
import co.edu.ucompensar.veterinaria.model.Cita;
import co.edu.ucompensar.veterinaria.model.Mascota;
import co.edu.ucompensar.veterinaria.repository.MascotaRepository;
import co.edu.ucompensar.veterinaria.service.MascotaServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
    void registrar_mascota_nombre_vacio() {
        Mascota mascota = new Mascota();

        mascota.setEspecie("Dalmata");
        mascota.setNombrePropietario("Jhon");
        mascota.setTelefonoPropietario("3051234567");


        assertThrows(ValidacionException.class, () -> mascotaService.registrar(mascota));
        verify(mascotaRepository, never()).save(any());
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

    @Test
    void consultar_mascotaExistente_devuelveMascotaConCitas() {
        // Arrange
        Mascota mascota = crearMascota();
        mascota.setId(1L);
        mascota.setCitas(List.of(new Cita()));
        when(mascotaRepository.findById(1L)).thenReturn(Optional.of(mascota));

        // Act
        Mascota resultado = mascotaService.obtenerPorId(1L);

        // Assert
        assertSame(mascota, resultado);
        assertEquals(1, resultado.getCitas().size());
    }
}