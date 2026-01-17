package es.uvigo.dagss.recetas.dtos;

import java.time.LocalDate;
import es.uvigo.dagss.recetas.entidades.Paciente;

public class PacienteDTO {
    private Long id;
    private String nombre;
    private String apellidos;
    private String dni;
    private String numTarjetaSanitaria;
    private String nss;
    private LocalDate fechaNacimiento;
    private String direccion;

    private Long medicoId;
    private String medicoNombre;
    private Long centroSaludId;

    public PacienteDTO(Paciente p) {
        this.id = p.getId();
        this.nombre = p.getNombre();
        this.apellidos = p.getApellidos();
        this.dni = p.getDni();
        this.numTarjetaSanitaria = p.getNumTarjetaSanitaria();
        this.nss = p.getNss();
        this.fechaNacimiento = p.getFechaNacimiento();

        if (p.getDireccion() != null) {
            this.direccion = p.getDireccion().getDomicilio() + ", " + p.getDireccion().getLocalidad();
        }

        if (p.getMedico() != null) {
            this.medicoId = p.getMedico().getId();
            this.medicoNombre = p.getMedico().getNombre() + " " + p.getMedico().getApellidos();
        }

        if (p.getCentroSalud() != null) {
            this.centroSaludId = p.getCentroSalud().getId();
        }
    }

    // GETTERS
    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getDni() {
        return dni;
    }

    public String getNumTarjetaSanitaria() {
        return numTarjetaSanitaria;
    }

    public String getNss() {
        return nss;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getDireccion() {
        return direccion;
    }

    public Long getMedicoId() {
        return medicoId;
    }

    public String getMedicoNombre() {
        return medicoNombre;
    }

    public Long getCentroSaludId() {
        return centroSaludId;
    }
}