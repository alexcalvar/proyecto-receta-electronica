package es.uvigo.dagss.recetas.daos;

import es.uvigo.dagss.recetas.entidades.CentroDeSalud;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CentroDeSaludDAO extends JpaRepository<CentroDeSalud, Long> {
    // HU-A3: Filtrar centros por nombre o localidad
    List<CentroDeSalud> findByNombreContainingIgnoreCase(String nombre);
    List<CentroDeSalud> findByDireccionLocalidadContainingIgnoreCase(String localidad);
}
