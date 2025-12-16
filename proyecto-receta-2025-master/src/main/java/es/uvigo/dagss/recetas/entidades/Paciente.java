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

     @Column(unique = true)
    private String dni;
    
    @Column(name = "num_tarjeta_sanitaria", unique = true)
    private String numTarjetaSanitaria;
    
    @Column(unique = true)
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

    
public String getNombre() {
    return nombre;
}

public void setNombre(String nombre) {
    this.nombre = nombre;
}

public String getApellidos() {
    return apellidos;
}

public void setApellidos(String apellidos) {
    this.apellidos = apellidos;
}

public String getDni() {
    return dni;
}

public void setDni(String dni) {
    this.dni = dni;
}

public String getNumTarjetaSanitaria() {
    return numTarjetaSanitaria;
}

public void setNumTarjetaSanitaria(String numTarjetaSanitaria) {
    this.numTarjetaSanitaria = numTarjetaSanitaria;
}

public String getNss() {
    return nss;
}

public void setNss(String nss) {
    this.nss = nss;
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

public LocalDate getFechaNacimiento() {
    return fechaNacimiento;
}

public void setFechaNacimiento(LocalDate fechaNacimiento) {
    this.fechaNacimiento = fechaNacimiento;
}

public Direccion getDireccion() {
    return direccion;
}

public void setDireccion(Direccion direccion) {
    this.direccion = direccion;
}

public CentroDeSalud getCentroSalud() {
    return centroDeSalud;
}

public void setCentroSalud(CentroDeSalud centroSalud) {
    this.centroDeSalud = centroSalud;
}

public Medico getMedico() {
    return medico;
}

public void setMedico(Medico medico) {
    this.medico = medico;
}

}
