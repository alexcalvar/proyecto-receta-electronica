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
    
    public String getNombreEstablecimiento() {
        return nombreEstablecimiento;
    }
    
    public void setNombreEstablecimiento(String nombreEstablecimiento) {
        this.nombreEstablecimiento = nombreEstablecimiento;
    }
    
    public String getNombreFarmaceutico() {
        return nombreFarmaceutico;
    }
    
    public void setNombreFarmaceutico(String nombreFarmaceutico) {
        this.nombreFarmaceutico = nombreFarmaceutico;
    }
    
    public String getApellidosFarmaceutico() {
        return apellidosFarmaceutico;
    }
    
    public void setApellidosFarmaceutico(String apellidosFarmaceutico) {
        this.apellidosFarmaceutico = apellidosFarmaceutico;
    }
    
    public String getNif() {
        return nif;
    }
    
    public void setNif(String nif) {
        this.nif = nif;
    }
    
    public String getNumColegiado() {
        return numColegiado;
    }
    
    public void setNumColegiado(String numColegiado) {
        this.numColegiado = numColegiado;
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
    
    public Direccion getDireccion() {
        return direccion;
    }
    
    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }
    
}
