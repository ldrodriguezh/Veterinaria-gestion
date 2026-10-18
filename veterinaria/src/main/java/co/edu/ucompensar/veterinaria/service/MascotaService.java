package co.edu.ucompensar.veterinaria.service;

import co.edu.ucompensar.veterinaria.model.Mascota;

import java.util.List;

public interface MascotaService {
    public Mascota registrar(Mascota mascota);

    public Mascota actualizar(Mascota mascota, Integer id);

    public boolean eliminar(Integer id);

    public Mascota obtenerPorId(Integer id);

    public List<Mascota> listar();
}