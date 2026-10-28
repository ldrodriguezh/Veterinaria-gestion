package co.edu.ucompensar.veterinaria.service;

import co.edu.ucompensar.veterinaria.model.Mascota;
import co.edu.ucompensar.veterinaria.repository.MascotaRepository;
import org.springframework.stereotype.Service;
import co.edu.ucompensar.veterinaria.Exception.ValidacionException;
import java.util.List;

@Service
public class MascotaServiceImpl implements MascotaService {

    private  MascotaRepository mascotaRepository;

    public MascotaServiceImpl(MascotaRepository mascotaRepository) {

        this.mascotaRepository = mascotaRepository;
    }

    @Override
    public Mascota registrar(Mascota mascota) {
        if (mascota.getNombre() == null || mascota.getNombre().isBlank()){
            throw new ValidacionException("El nombre es obligatorio");
        }
        if(mascota.getEspecie() == null || mascota.getEspecie().isBlank()) {
            throw new ValidacionException("La especie es obligatoria");
        }

        return mascotaRepository.save(mascota);
    }

    @Override
    public Mascota actualizar(Mascota mascota, Long id) {
        Mascota mascota1 = obtenerPorId(id);
        mascota1.setNombre(mascota.getNombre());
        mascota1.setEspecie(mascota.getEspecie());
        mascota1.setTelefonoPropietario(mascota.getTelefonoPropietario());
        mascota1.setNombrePropietario(mascota.getNombrePropietario());

        return mascotaRepository.save(mascota1);
    }

    @Override
    public boolean eliminar(Long id) {
        Mascota mascota1 = obtenerPorId(id);
        if(mascota1 != null){
            mascotaRepository.delete(mascota1);
            return true;
        }
        return false;
    }

    @Override
    public Mascota obtenerPorId(Long id) {
        return mascotaRepository.findById(id).get();

    }

    @Override
    public List<Mascota> listarTodo() {
        return mascotaRepository.findAll();
    }
}
