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
}
