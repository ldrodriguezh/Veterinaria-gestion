package co.edu.ucompensar.veterinaria.service;

import co.edu.ucompensar.veterinaria.model.Mascota;
import co.edu.ucompensar.veterinaria.repository.MascotaRepository;
import co.edu.ucompensar.veterinaria.exception.RecursoNoEncontradoException;
import co.edu.ucompensar.veterinaria.exception.ValidacionException;
import org.springframework.stereotype.Service;

@Service
public class MascotaServiceImpl implements MascotaService {

    private final MascotaRepository mascotaRepository;

    public MascotaServiceImpl(MascotaRepository mascotaRepository) {
        this.mascotaRepository = mascotaRepository;
    }

    @Override
    public Mascota registrar(Mascota mascota) {
        exigirTexto(mascota.getNombre(), "El nombre es obligatorio");
        exigirTexto(mascota.getEspecie(), "La especie es obligatoria");
        return mascotaRepository.save(mascota);
    }

    @Override
    public Mascota actualizar(Mascota mascota, Long id) {

        Mascota mascotaBD = buscarPorId(id);
        exigirTexto(mascota.getNombre(), "El nombre es obligatorio");
        exigirTexto(mascota.getEspecie(), "La especie es obligatoria");
        mascotaBD.setNombre(mascota.getNombre());
        mascotaBD.setEspecie(mascota.getEspecie());
        mascotaBD.setTelefonoPropietario(mascota.getTelefonoPropietario());
        mascotaBD.setNombrePropietario(mascota.getNombrePropietario());
        return mascotaRepository.save(mascotaBD);
    }

    @Override
    public boolean eliminar(Long id) {
        mascotaRepository.delete(buscarPorId(id));
        return true;
    }

    @Override
    public Mascota obtenerPorId(Long id) {
        return buscarPorId(id);
    }

    @Override
    public Mascota consultar(Long id) {
        return buscarPorId(id);
    }

    @Override
    public Mascota actualizarPropietario(Long id, String nombrePropietario, String telefonoPropietario) {
        exigirTexto(nombrePropietario, "El nombre del propietario es obligatorio");
        exigirTexto(telefonoPropietario, "El teléfono del propietario es obligatorio");
        Mascota mascota = buscarPorId(id);
        mascota.setNombrePropietario(nombrePropietario);
        mascota.setTelefonoPropietario(telefonoPropietario);
        return mascotaRepository.save(mascota);
    }

    private Mascota buscarPorId(Long id) {
        return mascotaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Mascota no existe: " + id));
    }

    private void exigirTexto(String valor, String mensaje) {
        if (valor == null || valor.isBlank()) {
            throw new ValidacionException(mensaje);
        }
    }
}
