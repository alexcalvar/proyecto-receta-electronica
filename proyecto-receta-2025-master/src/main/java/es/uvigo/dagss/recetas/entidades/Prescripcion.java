package es.uvigo.dagss.recetas.entidades;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "PRESCRIPCION")
public class Prescripcion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;

    @ManyToOne
    @JoinColumn(name = "medicamento_id", nullable = false)
    private Medicamento medicamento;

    private Double dosisDiaria;
    private String indicaciones;

    @Temporal(TemporalType.DATE)
    private Date fechaInicio;

    @Temporal(TemporalType.DATE)
    private Date fechaFin;

    private Boolean activa = true;

    // Relación Maestro-Detalle con Recetas
    @OneToMany(mappedBy = "prescripcion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Receta> recetas = new ArrayList<>();

    public Prescripcion() {}
    
    // Método helper para mantener la coherencia bidireccional (Buena práctica JPA)
    public void addReceta(Receta receta) {
        recetas.add(receta);
        receta.setPrescripcion(this);
    }

    // Getters y Setters...
}