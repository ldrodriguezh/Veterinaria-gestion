package co.edu.ucompensar.veterinaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.ucompensar.veterinaria.model.Mascota;

public interface MascotaRepository extends JpaRepository<Mascota,Long> {
}
