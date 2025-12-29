package es.uvigo.dagss.recetas.daos;

import es.uvigo.dagss.recetas.entidades.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface AdministradorDAO extends JpaRepository<Administrador, Long> { //al extender de jpa ya tenemos las 
                                                                            // operacions CRUD
    //administradores activos, aunq no se pide directamente , repasar si quitar
    List<Administrador> findByActivoTrue();

}