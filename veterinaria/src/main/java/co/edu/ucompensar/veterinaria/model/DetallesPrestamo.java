package co.edu.ucompensar.veterinaria.model;

import jakarta.persistence.*;


@Entity
public class DetallesPrestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Prestamo prestamo;
    private String ejemplarCodigo;
}