package es.uvigo.dagss.recetas.utils;

import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class Direccion implements Serializable {
    private String domicilio;
    private String localidad;
    private String codigoPostal;
    private String provincia;

    public Direccion() {
    }

    public Direccion(String domicilio, String localidad, String codigoPostal, String provincia) {
        this.domicilio = domicilio;
        this.localidad = localidad;
        this.codigoPostal = codigoPostal;
        this.provincia = provincia;
    }

    // Getters y Setters
    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }
}