package co.edu.ucompensar.veterinaria;

import co.edu.ucompensar.veterinaria.model.Mascota;
import co.edu.ucompensar.veterinaria.repository.MascotaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import co.edu.ucompensar.veterinaria.exception.ValidacionException;
import co.edu.ucompensar.veterinaria.model.Cita;
import java.util.List;
import java.util.Optional;
import co.edu.ucompensar.veterinaria.exception.RecursoNoEncontradoException;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MascotaServiceImplTest {

    @Mock
    private MascotaRepository mascotaRepository;

    @InjectMocks
    private MascotaServiceImpl mascotaService;

    private Mascota mascotaValida() {
        Mascota mascota = new Mascota();
        mascota.setNombre("Firulais");
        mascota.setEspecie("perro");
        mascota.setNombrePropietario("Ana Gómez");
        mascota.setTelefonoPropietario("3001234567");
        return mascota;
    }

    @Test
    void registrar_datosValidos_guardaMascota() {
        // Arrange
        Mascota entrada = mascotaValida();
        Mascota guardada = mascotaValida();
        guardada.setId(1L);
        when(mascotaRepository.save(entrada)).thenReturn(guardada);

        // Act
        Mascota resultado = mascotaService.registrar(entrada);

        // Assert
        assertEquals(1L, resultado.getId());
        verify(mascotaRepository).save(entrada);
    }

    @Test
    void registrar_nombreVacio_lanzaValidacionYNoGuarda() {
        // Arrange
        Mascota mascota = mascotaValida();
        mascota.setNombre("");

        // Act + Assert
        assertThrows(ValidacionException.class, () -> mascotaService.registrar(mascota));
        verify(mascotaRepository, never()).save(any());
    }

    @Test
    void registrar_especieVacia_lanzaValidacionYNoGuarda() {
        // Arrange
        Mascota mascota = mascotaValida();
        mascota.setEspecie("");

        // Act + Assert
        assertThrows(ValidacionException.class, () -> mascotaService.registrar(mascota));
        verify(mascotaRepository, never()).save(any());
    }

    @Test
    void consultar_mascotaExistente_devuelveMascotaConCitas() {
        // Arrange
        Mascota mascota = mascotaValida();
        mascota.setId(1L);
        mascota.setCitas(List.of(new Cita()));
        when(mascotaRepository.findById(1L)).thenReturn(Optional.of(mascota));

        // Act
        Mascota resultado = mascotaService.consultar(1L);

        // Assert
        assertSame(mascota, resultado);
        assertEquals(1, resultado.getCitas().size());
    }

    @Test
    void consultar_idInexistente_lanzaNoEncontrado() {
        // Arrange
        when(mascotaRepository.findById(99L)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(RecursoNoEncontradoException.class, () -> mascotaService.consultar(99L));
    }

    @Test
    void actualizarPropietario_datosValidos_guardaCambios() {
        // Arrange
        Mascota existente = mascotaValida();
        existente.setId(1L);
        when(mascotaRepository.findById(1L)).thenReturn(Optional.of(existente));

        // Act
        mascotaService.actualizarPropietario(1L, "Luis Pérez", "3109876543");

        // Assert
        ArgumentCaptor<Mascota> captor = ArgumentCaptor.forClass(Mascota.class);
        verify(mascotaRepository).save(captor.capture());
        assertEquals("Luis Pérez", captor.getValue().getNombrePropietario());
        assertEquals("3109876543", captor.getValue().getTelefonoPropietario());
    }

    @Test
    void actualizarPropietario_idInexistente_lanzaNoEncontradoYNoGuarda() {
        // Arrange
        when(mascotaRepository.findById(99L)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(RecursoNoEncontradoException.class,
                () -> mascotaService.actualizarPropietario(99L, "Luis Pérez", "3109876543"));
        verify(mascotaRepository, never()).save(any());
    }
}