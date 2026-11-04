package co.edu.ucompensar.veterinaria.service;

import co.edu.ucompensar.veterinaria.model.Cita;
import java.util.List;

public interface CitaService {
    Cita registrarCita(Cita cita);
    Cita actualizarCita(Cita cita);
    boolean eliminar(Long id);

    Cita consultarPorId(Long id);
    List<Cita> consultarTodas();

    Cita agendar(Cita cita);

    Cita cambiarEstado(Long id, String nuevoEstado);
}
