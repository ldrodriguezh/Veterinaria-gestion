package co.edu.ucompensar.veterinaria.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Cita {

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Mascota mascota;

    private LocalDateTime fecha;
    private String motivo;
    private String veterinarioAsignado;
    private String estado;

    public Long getId() { return id; }    public void setId(Long id) { this.id = id; }
    public Mascota getMascota() { return mascota; }    public void setMascota(Mascota mascota) { this.mascota = mascota; }
    public LocalDateTime getFecha() { return fecha; }    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
    public String getMotivo() { return motivo; }    public void setMotivo(String motivo) { this.motivo = motivo; }
    public String getVeterinarioAsignado() { return veterinarioAsignado; }    public void setVeterinarioAsignado(String veterinarioAsignado) { this.veterinarioAsignado = veterinarioAsignado; }
    public String getEstado() { return estado; }    public void setEstado(String estado) { this.estado = estado; }
}
