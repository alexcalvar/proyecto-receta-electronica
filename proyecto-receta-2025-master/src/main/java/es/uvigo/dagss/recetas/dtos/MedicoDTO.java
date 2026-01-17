package es.uvigo.dagss.recetas.dtos;

import es.uvigo.dagss.recetas.entidades.Medico;

public class MedicoDTO {
    private Long id;
    private String nombre;
    private String apellidos;
    private String dni;
    private String numColegiado;

    private Long centroSaludId;
    private String centroSaludNombre;

    public MedicoDTO(Medico m) {
        this.id = m.getId();
        this.nombre = m.getNombre();
        this.apellidos = m.getApellidos();
        this.dni = m.getDni();
        this.numColegiado = m.getNumColegiado();

        if (m.getCentroDeSalud() != null) {
            this.centroSaludId = m.getCentroDeSalud().getId();
            this.centroSaludNombre = m.getCentroDeSalud().getNombre();
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

    public String getNumColegiado() {
        return numColegiado;
    }

    public Long getCentroSaludId() {
        return centroSaludId;
    }

    public String getCentroSaludNombre() {
        return centroSaludNombre;
    }
}