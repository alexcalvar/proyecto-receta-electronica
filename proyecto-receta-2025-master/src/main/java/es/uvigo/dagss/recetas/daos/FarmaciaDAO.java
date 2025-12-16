package es.uvigo.dagss.recetas.daos;

import es.uvigo.dagss.recetas.entidades.Farmacia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface FarmaciaDAO extends JpaRepository<Farmacia, Long> {
    
    // HU-A6: Búsqueda de farmacias por nombre de establecimiento (filtro aproximado)
    List<Farmacia> findByNombreEstablecimientoContainingIgnoreCase(String nombre);
    
    // HU-A6: Búsqueda por localidad (Navegando por el objeto embebido Direccion)
    List<Farmacia> findByDireccionLocalidadContainingIgnoreCase(String localidad);
    
    // Búsqueda por NIF (Clave de negocio única)
    Optional<Farmacia> findByNif(String nif);
}