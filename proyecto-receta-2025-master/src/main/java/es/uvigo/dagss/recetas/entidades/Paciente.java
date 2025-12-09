package es.uvigo.dagss.recetas.entidades;

import java.time.LocalDate;

import es.uvigo.dagss.recetas.utils.Direccion;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "PACIENTE")
@DiscriminatorValue(value = "PACIENTE")
public class Paciente extends Usuario {

	private String nombre;
    private String apellidos;
    private String dni;
    
    @Column(name = "num_tarjeta_sanitaria", unique = true)
    private String numTarjetaSanitaria;
    
    private String nss; // Numero Seguridad Social
    private String telefono;
    private String email;
    
    @Temporal(TemporalType.DATE)
    private LocalDate fechaNacimiento;

    @Embedded
    private Direccion direccion;

    @ManyToOne
    @JoinColumn(name = "centro_salud_id")
    private CentroDeSalud centroDeSalud;

    @ManyToOne
    @JoinColumn(name = "medico_id")
    private Medico medico;
   

    public Paciente() {
        super(TipoUsuario.PACIENTE);        
    }


}
