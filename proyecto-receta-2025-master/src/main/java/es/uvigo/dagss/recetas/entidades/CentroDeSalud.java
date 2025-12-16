package es.uvigo.dagss.recetas.entidades;

import jakarta.persistence.*;
import java.io.Serializable;

import es.uvigo.dagss.recetas.utils.Direccion;

@Entity
@Table(name = "CENTRO_SALUD")
public class CentroDeSalud implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private String telefono;
    private String email;
    private Boolean activo = true;

    @Embedded
    private Direccion direccion;

    public CentroDeSalud() {
    }

    public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}

public String getNombre() {
    return nombre;
}

public void setNombre(String nombre) {
    this.nombre = nombre;
}

public String getTelefono() {
    return telefono;
}

public void setTelefono(String telefono) {
    this.telefono = telefono;
}

public String getEmail() {
    return email;
}

public void setEmail(String email) {
    this.email = email;
}

public Boolean getActivo() {
    return activo;
}

public void setActivo(Boolean activo) {
    this.activo = activo;
}

public Direccion getDireccion() {
    return direccion;
}

public void setDireccion(Direccion direccion) {
    this.direccion = direccion;
}

}