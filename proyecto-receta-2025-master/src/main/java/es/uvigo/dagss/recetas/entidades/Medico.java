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

public CentroDeSalud getCentroDeSalud() {
    return centroSalud;
}

public void setCentroDeSalud(CentroDeSalud centroSalud) {
    this.centroSalud = centroSalud;
}

}
