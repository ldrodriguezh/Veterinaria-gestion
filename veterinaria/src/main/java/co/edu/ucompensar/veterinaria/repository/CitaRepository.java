package co.edu.ucompensar.veterinaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.ucompensar.veterinaria.model.Cita;
import java.time.LocalDateTime;

public interface CitaRepository extends JpaRepository<Cita, Long>{
    boolean existsByMascotaIdAndFecha(Long mascotaId, LocalDateTime fecha);
}
