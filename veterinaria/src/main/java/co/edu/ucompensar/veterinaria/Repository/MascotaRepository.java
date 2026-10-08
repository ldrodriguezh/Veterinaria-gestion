package co.edu.ucompensar.veterinaria.Repository;


import co.edu.ucompensar.veterinaria.model.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MascotaRepository extends JpaRepository<Mascota,Long> {
}
