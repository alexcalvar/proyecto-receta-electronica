package es.uvigo.dagss.recetas.entidades;

import es.uvigo.dagss.recetas.utils.Direccion;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "FARMACIA")
@DiscriminatorValue(value = "FARMACIA")
public class Farmacia extends Usuario {

    private String nombreEstablecimiento;
    private String nombreFarmaceutico;
    private String apellidosFarmaceutico;
    
    @Column(unique = true)
    private String nif;
    
    private String numColegiado;
    private String telefono;
    private String email;

    @Embedded
    private Direccion direccion;
	
    public Farmacia() {
        super(TipoUsuario.FARMACIA);
    }
    
}
