package es.uvigo.dagss.recetas.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "MEDICO") //revisar
@DiscriminatorValue(value = "MEDICO")
public class Medico extends Usuario {

    private String nombre;
    private String apellidos;
    private String dni;
    
    @Column(name = "num_colegiado", unique = true)
    private String numColegiado;
    
    private String telefono;
    private String email;

    @ManyToOne
    @JoinColumn(name = "centro_salud_id")
    private CentroDeSalud centroSalud;
    
    public Medico() {
        super(TipoUsuario.MEDICO);
    }


}
