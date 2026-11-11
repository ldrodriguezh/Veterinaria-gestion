package co.edu.ucompensar.veterinaria.repository;

import co.edu.ucompensar.veterinaria.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface CitaRepository extends JpaRepository<Cita, Long> {

    boolean existsByMascotaIdAndFecha(Long mascotaId, LocalDateTime fecha);
}
