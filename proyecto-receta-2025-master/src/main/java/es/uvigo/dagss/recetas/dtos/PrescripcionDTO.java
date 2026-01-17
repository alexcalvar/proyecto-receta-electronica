package es.uvigo.dagss.recetas.dtos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import es.uvigo.dagss.recetas.entidades.Prescripcion;
import es.uvigo.dagss.recetas.entidades.Receta;

public class PrescripcionDTO {
    private Long id;
    private Double dosisDiaria;
    private String indicaciones;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private boolean activa;


    private String nombrePaciente;
    private String tarjetaSanitaria;
    private String nombreMedico;
    private String nombreMedicamento;

    private List<RecetaDTO> listaRecetas = new ArrayList<>();

    public PrescripcionDTO(Prescripcion p) {
        this.id = p.getId();
        this.dosisDiaria = p.getDosisDiaria();
        this.indicaciones = p.getIndicaciones();
        this.fechaInicio = p.getFechaInicio();
        this.fechaFin = p.getFechaFin();
        this.activa = p.getActiva();

        if (p.getPaciente() != null) {
            this.nombrePaciente = p.getPaciente().getNombre() + " " + p.getPaciente().getApellidos();
            this.tarjetaSanitaria = p.getPaciente().getNumTarjetaSanitaria();
        }
        if (p.getMedico() != null) {
            this.nombreMedico = p.getMedico().getNombre() + " " + p.getMedico().getApellidos();
        }
        if (p.getMedicamento() != null) {
            this.nombreMedicamento = p.getMedicamento().getNombreComercial();
        }

  
        if (p.getRecetas() != null) {

            this.listaRecetas = new ArrayList<>();

           
            for (Receta recetaEntidad : p.getRecetas()) {
                
                RecetaDTO dto = new RecetaDTO(recetaEntidad);
                this.listaRecetas.add(dto);
            }
        }
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Double getDosisDiaria() {
        return dosisDiaria;
    }

    public String getIndicaciones() {
        return indicaciones;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public boolean isActiva() {
        return activa;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public String getTarjetaSanitaria() {
        return tarjetaSanitaria;
    }

    public String getNombreMedico() {
        return nombreMedico;
    }

    public String getNombreMedicamento() {
        return nombreMedicamento;
    }

    public List<RecetaDTO> getListaRecetas() {
        return listaRecetas;
    }
}