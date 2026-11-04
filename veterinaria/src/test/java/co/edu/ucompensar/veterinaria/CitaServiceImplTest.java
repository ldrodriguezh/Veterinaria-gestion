package co.edu.ucompensar.veterinaria;

import co.edu.ucompensar.veterinaria.model.Cita;
import co.edu.ucompensar.veterinaria.model.Mascota;
import co.edu.ucompensar.veterinaria.repository.CitaRepository;
import co.edu.ucompensar.veterinaria.repository.MascotaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CitaServiceImplTest {

    @Mock
    private MascotaRepository mascotaRepository;

    @Mock
    private CitaRepository citaRepository;

    @InjectMocks
    private CitaServiceImpl citaService;

    private Cita citaFutura(Long mascotaId) {
        Mascota mascota = new Mascota();
        mascota.setId(mascotaId);
        Cita cita = new Cita();
        cita.setMascota(mascota);
        cita.setFecha(LocalDateTime.now().plusDays(1));
        cita.setMotivo("vacunación");
        cita.setVeterinarioAsignado("Dra. Rojas");
        return cita;
    }

    @Test
    void agendar_mascotaExisteFechaFuturaHorarioLibre_guardaCitaPendiente() {
        // Arrange
        Cita cita = citaFutura(1L);
        when(mascotaRepository.findById(1L)).thenReturn(Optional.of(cita.getMascota()));
        when(citaRepository.save(cita)).thenReturn(cita);

        // Act
        Cita resultado = citaService.agendar(cita);

        // Assert
        assertEquals("PENDIENTE", resultado.getEstado());
        verify(citaRepository).save(cita);
    }
}