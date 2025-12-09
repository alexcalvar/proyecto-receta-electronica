package es.uvigo.dagss.recetas.daos;

import es.uvigo.dagss.recetas.entidades.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MedicoDAO extends JpaRepository<Medico, Long> {
    // HU-A4: Filtrar médicos por localidad (Búsqueda aproximada LIKE)
    // Spring navegará: Medico -> CentroSalud -> Direccion -> Localidad
    List<Medico> findByCentroSaludDireccionLocalidadContainingIgnoreCase(String localidad);
    
    // HU-A4: Filtrar médicos por nombre (Búsqueda aproximada)
    List<Medico> findByNombreContainingIgnoreCase(String nombre);
}
