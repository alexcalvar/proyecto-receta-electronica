package es.uvigo.dagss.recetas.daos;

import es.uvigo.dagss.recetas.entidades.CentroDeSalud;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CentroDeSaludDAO extends JpaRepository<CentroDeSalud, Long> {

    //necesario por el borrado logico, findAll mostraria la lista con todos 
    List<CentroDeSalud> findByActivoTrue();

    // (HU-A3) nombre o localidad
    List<CentroDeSalud> findByNombreContainingIgnoreCase(String nombre);

    @Query("SELECT c FROM CentroDeSalud c WHERE c.direccion.localidad LIKE CONCAT(:localidad,'%') " )
    List<CentroDeSalud> findByLocalidad(@Param("localidad") String localidad);

}
