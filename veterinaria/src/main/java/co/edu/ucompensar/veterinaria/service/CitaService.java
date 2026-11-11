package co.edu.ucompensar.veterinaria.service;

import co.edu.ucompensar.veterinaria.model.Cita;

public interface CitaService {
    Cita agendar(Cita cita);
    Cita cambiarEstado(Long id, String nuevoEstado);
}
