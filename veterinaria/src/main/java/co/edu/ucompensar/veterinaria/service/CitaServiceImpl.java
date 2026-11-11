package co.edu.ucompensar.veterinaria.service;

import co.edu.ucompensar.veterinaria.Exception.RecursoNoEncontradoException;
import co.edu.ucompensar.veterinaria.Exception.ReglaNegocioException;
import co.edu.ucompensar.veterinaria.Exception.ValidacionException;
import co.edu.ucompensar.veterinaria.model.Cita;
import co.edu.ucompensar.veterinaria.model.Mascota;
import co.edu.ucompensar.veterinaria.repository.CitaRepository;
import co.edu.ucompensar.veterinaria.repository.MascotaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CitaServiceImpl implements CitaService {

    private final MascotaRepository mascotaRepository;
    private final CitaRepository citaRepository;

    public CitaServiceImpl(MascotaRepository mascotaRepository, CitaRepository citaRepository) {
        this.mascotaRepository = mascotaRepository;
        this.citaRepository = citaRepository;
    }

    /** Orden: 1) la mascota existe, 2) la fecha es futura, 3) el horario está libre. */
    @Override
    public Cita agendar(Cita cita) {
        if (cita.getMascota() == null || cita.getMascota().getId() == null) {
            throw new ValidacionException("La mascota es obligatoria");
        }
        if (cita.getFecha() == null) {
            throw new ValidacionException("La fecha es obligatoria");
        }

        Long mascotaId = cita.getMascota().getId();
        Mascota mascota = mascotaRepository.findById(mascotaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Mascota no existe: " + mascotaId));

        if (!cita.getFecha().isAfter(LocalDateTime.now())) {
            throw new ReglaNegocioException("La fecha de la cita debe ser futura");
        }
        if (citaRepository.existsByMascotaIdAndFecha(mascotaId, cita.getFecha())) {
            throw new ReglaNegocioException("La mascota ya tiene una cita en ese horario");
        }

        cita.setMascota(mascota);
        cita.setEstado("PENDIENTE");
        return citaRepository.save(cita);
    }

    @Override
    public Cita cambiarEstado(Long id, String nuevoEstado) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cita no existe: " + id));

        if (!"PENDIENTE".equals(nuevoEstado) && !"ATENDIDA".equals(nuevoEstado)
                && !"CANCELADA".equals(nuevoEstado)) {
            throw new ValidacionException("Estado no válido: " + nuevoEstado);
        }
        if ("ATENDIDA".equals(cita.getEstado()) && "CANCELADA".equals(nuevoEstado)) {
            throw new ReglaNegocioException("Una cita ATENDIDA no puede cancelarse");
        }

        cita.setEstado(nuevoEstado);
        return citaRepository.save(cita);
    }
}
