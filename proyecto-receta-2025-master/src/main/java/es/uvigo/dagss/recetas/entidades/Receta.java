package es.uvigo.dagss.recetas.entidades;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

import es.uvigo.dagss.recetas.utils.EstadoReceta;

@Entity
@Table(name = "RECETA")
public class Receta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "prescripcion_id", nullable = false)
    private Prescripcion prescripcion;

    @ManyToOne
    @JoinColumn(name = "farmacia_id") // Nullable, inicialmente no tiene farmacia
    private Farmacia farmacia;

    @Temporal(TemporalType.DATE)
    private Date fechaValidezInicio;

    @Temporal(TemporalType.DATE)
    private Date fechaValidezFin;

    private Integer cantidad; // Número de cajas

    @Enumerated(EnumType.STRING)
    private EstadoReceta estado = EstadoReceta.PLANIFICADA;

    public Receta() {
    }

    // Getters y Setters...
    public Prescripcion getPrescripcion() {
        return prescripcion;
    }

    public void setPrescripcion(Prescripcion prescripcion) {
        this.prescripcion = prescripcion;
    }
    // ... resto
}