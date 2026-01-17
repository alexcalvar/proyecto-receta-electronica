package es.uvigo.dagss.recetas.dtos;

import es.uvigo.dagss.recetas.entidades.Administrador;

public class AdministradorDTO {
    private Long id;
    private String nombre;
    private String email;
    // no se envia ni login

    public AdministradorDTO(Administrador admin) {
        this.id = admin.getId();
        this.nombre = admin.getNombre();
        this.email = admin.getEmail();
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }
}