package es.uvigo.dagss.recetas.dtos;

import java.time.LocalDate;
import es.uvigo.dagss.recetas.entidades.Receta;

public class RecetaDTO {
    private Long id;
    private String medicamento;
    private String indicaciones;
    private Long prescripcionId;
    private String nombreMedico;
    private String farmacia;
    private LocalDate validezInicio;
    private LocalDate validezFin;
    private String estado;

    public RecetaDTO(Receta r) {
        this.id = r.getId();
        this.validezInicio = r.getFechaValidezInicio();
        this.validezFin = r.getFechaValidezFin();
        this.estado = r.getEstado().toString();

        // Datos aplanados de la prescripción
        if (r.getPrescripcion() != null) {
            this.prescripcionId = r.getPrescripcion().getId();
            this.indicaciones = r.getPrescripcion().getIndicaciones();
            if (r.getPrescripcion().getMedicamento() != null) {
                this.medicamento = r.getPrescripcion().getMedicamento().getNombreComercial();
            }
            if (r.getPrescripcion().getMedico() != null) {
                this.nombreMedico = r.getPrescripcion().getMedico().getNombre() + " " +
                        r.getPrescripcion().getMedico().getApellidos();
            }
        }

        // Datos de la farmacia
        if (r.getFarmacia() != null) {
            this.farmacia = r.getFarmacia().getNombreEstablecimiento();
        }
    }

    public Long getId() {
        return id;
    }

    public String getMedicamento() {
        return medicamento;
    }

    public Long getPrescripcionId() {
        return prescripcionId;
    }

    public String getIndicaciones() {
        return indicaciones;
    }

    public String getNombreMedico() {
        return nombreMedico;
    }

    public String getFarmacia() {
        return farmacia;
    }

    public LocalDate getValidezInicio() {
        return validezInicio;
    }

    public LocalDate getValidezFin() {
        return validezFin;
    }

    public String getEstado() {
        return estado;
    }
}