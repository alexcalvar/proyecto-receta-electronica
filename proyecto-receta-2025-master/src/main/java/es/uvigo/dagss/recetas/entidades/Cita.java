package es.uvigo.dagss.recetas.entidades;

import java.io.Serializable;
import java.util.Date;

import es.uvigo.dagss.recetas.utils.EstadoCita;
import jakarta.persistence.*;

@Entity
@Table(name = "CITA", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"medico_id", "fecha", "hora"})
})
public class Cita implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date fecha;

    @Temporal(TemporalType.TIME)
    @Column(nullable = false)
    private Date hora;

    private Integer duracion = 15; // Por defecto 15 min

    @Enumerated(EnumType.STRING)
    private EstadoCita estado = EstadoCita.PLANIFICADA;

    public Cita() {}
}