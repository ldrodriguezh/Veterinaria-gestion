package co.edu.ucompensar.veterinaria.model;

import jakarta.persistence.*;


import javax.annotation.processing.Generated;

public class Cita {

    @id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(optional = false)
    private Mascota mascota;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }
}
