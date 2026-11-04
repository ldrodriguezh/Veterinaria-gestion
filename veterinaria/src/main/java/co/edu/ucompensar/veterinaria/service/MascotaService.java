package co.edu.ucompensar.veterinaria.service;

import co.edu.ucompensar.veterinaria.model.Mascota;

public interface MascotaService {

    public Mascota registrar(Mascota mascota);
    public Mascota actualizar(Mascota mascota, Long id);
    public boolean eliminar(Long id);

    public Mascota obtenerPorId(Long id);

    Mascota consultar(Long id);

    Mascota actualizarPropietario(Long id, String nombrePropietario, String telefonoPropietario);
}
