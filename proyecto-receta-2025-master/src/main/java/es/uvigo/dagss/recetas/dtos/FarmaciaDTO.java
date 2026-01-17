package es.uvigo.dagss.recetas.dtos;

import es.uvigo.dagss.recetas.entidades.Farmacia;

public class FarmaciaDTO {
    private Long id;
    private String nombreEstablecimiento;
    private String nif;
    private String direccion;

    public FarmaciaDTO(Farmacia f) {
        this.id = f.getId();
        this.nombreEstablecimiento = f.getNombreEstablecimiento();
        this.nif = f.getNif();
        if (f.getDireccion() != null) {
            this.direccion = f.getDireccion().getDomicilio() + ", " + f.getDireccion().getLocalidad();
        }
    }

    // GETTERS
    public Long getId() {
        return id;
    }

    public String getNombreEstablecimiento() {
        return nombreEstablecimiento;
    }

    public String getNif() {
        return nif;
    }

    public String getDireccion() {
        return direccion;
    }
}