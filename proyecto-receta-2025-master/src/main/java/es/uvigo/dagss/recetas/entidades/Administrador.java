package es.uvigo.dagss.recetas.entidades;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "ADMINISTRADOR")
@DiscriminatorValue(value = "ADMINISTRADOR") //redundante para la implementacion de table_per_class?
public class Administrador extends Usuario {

    private String nombre;

    private String email;

    //private boolean activo; ya lo obtinene a partir de usuario

    public Administrador() {
        super(TipoUsuario.ADMINISTRADOR);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
