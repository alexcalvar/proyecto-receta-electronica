package es.uvigo.dagss.recetas.daos;

import es.uvigo.dagss.recetas.entidades.Farmacia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface FarmaciaDAO extends JpaRepository<Farmacia, Long> {
    
    //necesario por el borrado logico, findAll mostraria la lista con todos 
    List<Farmacia> findByActivoTrue();

    // (HU-A6)  nombre de establecimiento 
    List<Farmacia> findByNombreEstablecimientoContainingIgnoreCase(String nombre);
    
    // (HU-A6) Búsqueda por localidad 
    @Query("SELECT f FROM Farmacia f WHERE f.direccion.localidad LIKE CONCAT('%', :localidad, '%')")
    List<Farmacia> buscarPorLocalidad(@Param("localidad") String localidad);
    
   
}