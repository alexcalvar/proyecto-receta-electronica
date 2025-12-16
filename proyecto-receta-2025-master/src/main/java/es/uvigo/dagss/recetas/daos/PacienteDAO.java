package es.uvigo.dagss.recetas.daos;

import es.uvigo.dagss.recetas.entidades.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PacienteDAO extends JpaRepository<Paciente, Long> {
    
    // HU-A5: Busquedas por nombre/apellidos
    List<Paciente> findByNombreContainingIgnoreCase(String nombre);
    List<Paciente> findByApellidosContainingIgnoreCase(String apellidos);
    
    // HU-A5: Busquedas por localidad
    List<Paciente> findByDireccionLocalidadContainingIgnoreCase(String localidad);

    // HU-F2: Buscar paciente por tarjeta sanitaria para dispensar recetas
    Optional<Paciente> findByNumTarjetaSanitaria(String numTarjetaSanitaria);
}