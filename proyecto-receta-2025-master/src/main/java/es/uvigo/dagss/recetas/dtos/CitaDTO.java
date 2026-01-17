package es.uvigo.dagss.recetas.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

import es.uvigo.dagss.recetas.entidades.Cita;

public class CitaDTO {
    private Long id;
    private LocalDate fecha;
    private LocalTime hora;
    private String estado; 
    private Integer duracionMinutos;

    
    private Long pacienteId;
    private String nombrePaciente;

    private Long medicoId;
    private String nombreMedico;

    public CitaDTO(Cita cita) {
        this.id = cita.getId();
        this.fecha = cita.getFecha(); 
        this.hora = cita.getHora();
        this.duracionMinutos = cita.getDuracion(); 

        if (cita.getEstado() != null) {
            this.estado = cita.getEstado().toString();
        }

        // Mapeo PACIENTE -> ID
        if (cita.getPaciente() != null) {
            this.pacienteId = cita.getPaciente().getId();
            this.nombrePaciente = cita.getPaciente().getNombre() + " " + cita.getPaciente().getApellidos();
        }

        // Mapeo MEDICO -> ID
        if (cita.getMedico() != null) {
            this.medicoId = cita.getMedico().getId();
            this.nombreMedico = cita.getMedico().getNombre() + " " + cita.getMedico().getApellidos();
        }
    }

    // Getters
    public Long getId() {
        return id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public String getEstado() {
        return estado;
    }

    public Integer getDuracionMinutos() {
        return duracionMinutos;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public Long getMedicoId() {
        return medicoId;
    }

    public String getNombreMedico() {
        return nombreMedico;
    }
}