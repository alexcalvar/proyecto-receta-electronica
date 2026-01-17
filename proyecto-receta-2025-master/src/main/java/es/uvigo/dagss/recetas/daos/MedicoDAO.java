package es.uvigo.dagss.recetas.daos;

import es.uvigo.dagss.recetas.entidades.Farmacia;
import es.uvigo.dagss.recetas.entidades.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MedicoDAO extends JpaRepository<Medico, Long> {
    
    //necesario por el borrado logico, findAll mostraria la lista con todos 
    List<Farmacia> findByActivoTrue();
    
    // (HU-A4)   por localidad 
    @Query("SELECT m FROM Medico m WHERE m.centroSalud.direccion.localidad LIKE CONCAT('%', :localidad, '%')")
    List<Medico> findByLocation(@Param("localidad") String localidad);
    
    // (HU-A4) Filtrar médicos por nombre 
    List<Medico> findByNombreContainingIgnoreCase(String nombre);

    //para cuando se seleccione el centro de salud de la lista para filtrar medicos, asi ya se busca 
    //el id del centro directamten
    List<Medico> findByCentroSaludId(Long id);


}
