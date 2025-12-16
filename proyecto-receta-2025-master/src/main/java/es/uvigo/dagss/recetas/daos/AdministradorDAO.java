package es.uvigo.dagss.recetas.daos;

import es.uvigo.dagss.recetas.entidades.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AdministradorDAO extends JpaRepository<Administrador, Long> { //al extender de jpa ya tenemos las operacions CRUD
    
    // HU-A2: Listar y buscar administradores por nombre
    List<Administrador> findByNombreContainingIgnoreCase(String nombre);
    
    // HU-A2: Buscar por email (si se usa como criterio de búsqueda)
    List<Administrador> findByEmailContainingIgnoreCase(String email);
}