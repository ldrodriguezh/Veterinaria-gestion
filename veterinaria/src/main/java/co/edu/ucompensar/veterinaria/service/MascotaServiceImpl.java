package co.edu.ucompensar.veterinaria.service;

import co.edu.ucompensar.veterinaria.model.Mascota;
import co.edu.ucompensar.veterinaria.repository.MascotaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MascotaServiceImpl implements MascotaService {

    private final MascotaRepository mascotaRepository;

    public MascotaServiceImpl(MascotaRepository mascotaRepository) {
        this.mascotaRepository = mascotaRepository;
    }

    @Override
    public Mascota registrar(Mascota mascota) {
        try{
           mascota= mascotaRepository.save(mascota);
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("No se pudo registrar la mascota");
        }

        return mascota;
    }

    @Override
    public Mascota actualizar(Mascota mascota, Integer id) {
        return null;
    }

    @Override
    public boolean eliminar(Integer id) {
        return false;
    }

    @Override
    public Mascota obtenerPorId(Integer id) {
        return null;
    }

    @Override
    public List<Mascota> listar() {
        return List.of();
    }
}
