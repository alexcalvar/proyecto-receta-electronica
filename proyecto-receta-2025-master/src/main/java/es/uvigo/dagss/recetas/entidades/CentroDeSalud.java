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

    // Getters y Setters...
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

}