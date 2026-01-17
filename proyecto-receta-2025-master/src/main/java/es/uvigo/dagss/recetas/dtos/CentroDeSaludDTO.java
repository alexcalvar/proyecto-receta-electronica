package es.uvigo.dagss.recetas.dtos;

import es.uvigo.dagss.recetas.entidades.CentroDeSalud;

public class CentroDeSaludDTO {
    private Long id;
    private String nombre;
    private String telefono;
    private String email;
    private String direccionCompleta;
    private boolean activo;

    public CentroDeSaludDTO(CentroDeSalud cs) {
        this.id = cs.getId();
        this.nombre = cs.getNombre();
        this.telefono = cs.getTelefono();
        this.email = cs.getEmail();
        this.activo = cs.getActivo();

        if (cs.getDireccion() != null) {
            this.direccionCompleta = cs.getDireccion().getDomicilio() + ", " +
                    cs.getDireccion().getLocalidad() + " (" +
                    cs.getDireccion().getProvincia() + ")";
        }
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public String getDireccionCompleta() {
        return direccionCompleta;
    }

    public boolean isActivo() {
        return activo;
    }
}