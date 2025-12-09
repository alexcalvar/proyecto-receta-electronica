package es.uvigo.dagss.recetas.entidades;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "MEDICAMENTO")
public class Medicamento implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombreComercial;

    private String principioActivo;
    private String fabricante;
    private String familia;
    private Integer numeroDosis; // Número de dosis por caja
    private Boolean activo = true;

    public Medicamento(){}

    public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}

public String getNombreComercial() {
    return nombreComercial;
}

public void setNombreComercial(String nombreComercial) {
    this.nombreComercial = nombreComercial;
}

public String getPrincipioActivo() {
    return principioActivo;
}

public void setPrincipioActivo(String principioActivo) {
    this.principioActivo = principioActivo;
}

public String getFabricante() {
    return fabricante;
}

public void setFabricante(String fabricante) {
    this.fabricante = fabricante;
}

public String getFamilia() {
    return familia;
}

public void setFamilia(String familia) {
    this.familia = familia;
}

public Integer getNumeroDosis() {
    return numeroDosis;
}

public void setNumeroDosis(Integer numeroDosis) {
    this.numeroDosis = numeroDosis;
}

public Boolean getActivo() {
    return activo;
}

public void setActivo(Boolean activo) {
    this.activo = activo;
}
}
