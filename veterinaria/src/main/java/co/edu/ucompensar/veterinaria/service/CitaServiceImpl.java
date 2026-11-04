package co.edu.ucompensar.veterinaria.service;

import co.edu.ucompensar.veterinaria.model.Cita;
import co.edu.ucompensar.veterinaria.model.Mascota;
import co.edu.ucompensar.veterinaria.repository.CitaRepository;
import co.edu.ucompensar.veterinaria.repository.MascotaRepository;
import co.edu.ucompensar.veterinaria.exception.RecursoNoEncontradoException;
import co.edu.ucompensar.veterinaria.exception.ReglaNegocioException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CitaServiceImpl implements CitaService{

    private static final String PENDIENTE = "PENDIENTE";
    private static final String ATENDIDA = "ATENDIDA";
    private static final String CANCELADA = "CANCELADA";

    private final MascotaRepository mascotaRepository;
    private final CitaRepository citaRepository;

    public CitaServiceImpl(MascotaRepository mascotaRepository, CitaRepository citaRepository) {
        this.mascotaRepository = mascotaRepository;
        this.citaRepository = citaRepository;
    }

    @Override
    public Cita registrarCita(Cita cita) {
        return agendar(cita);
    }

    @Override
    public Cita actualizarCita(Cita cita) {
        Cita existente = buscarPorId(cita.getId());
        validarMascota(cita.getMascota());
        existente.setMascota(cita.getMascota());
        existente.setFecha(cita.getFecha());
        existente.setMotivo(cita.getMotivo());
        existente.setVeterinarioAsignado(cita.getVeterinarioAsignado());
        existente.setEstado(cita.getEstado());
        return citaRepository.save(existente);
    }

    @Override
    public boolean eliminar(Long id) {
        citaRepository.delete(buscarPorId(id));
        return true;
    }

    @Override
    public Cita consultarPorId(Long id) {
        return buscarPorId(id);
    }

    @Override
    public List<Cita> consultarTodas() {
        return citaRepository.findAll();
    }

    @Override
    public Cita agendar(Cita cita) {
        Mascota mascota = validarMascota(cita.getMascota());
        Long mascotaId = mascota.getId();
        if (cita.getFecha() == null || !cita.getFecha().isAfter(LocalDateTime.now())) {
            throw new ReglaNegocioException("La fecha de la cita debe ser futura");
        }
        if (citaRepository.existsByMascotaIdAndFecha(mascotaId, cita.getFecha())) {
            throw new ReglaNegocioException("La mascota ya tiene una cita en ese horario");
        }
        cita.setMascota(mascota);
        cita.setEstado(PENDIENTE);
        return citaRepository.save(cita);
    }

    @Override
    public Cita cambiarEstado(Long id, String nuevoEstado) {
        Cita cita = buscarPorId(id);
        if (ATENDIDA.equals(cita.getEstado()) && CANCELADA.equals(nuevoEstado)) {
            throw new ReglaNegocioException("Una cita atendida no puede cancelarse");
        }
        cita.setEstado(nuevoEstado);
        return citaRepository.save(cita);
    }

    private Mascota validarMascota(Mascota mascota) {
        if (mascota == null || mascota.getId() == null) {
            throw new RecursoNoEncontradoException("La cita debe estar asociada a una mascota existente");
        }
        return mascotaRepository.findById(mascota.getId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Mascota no existe: " + mascota.getId()));
    }

    private Cita buscarPorId(Long id) {
        return citaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cita no existe: " + id));
    }
}
