package es.uvigo.dagss.recetas.daos;

import es.uvigo.dagss.recetas.entidades.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


public interface PacienteDAO extends JpaRepository<Paciente, Long >, JpaSpecificationExecutor<Paciente> {
    
    // (HU-A5)  por nombre/apellidos
    List<Paciente> findByNombreContainingIgnoreCase(String nombre);
    
    // (HU-A5) por localidad
    List<Paciente> findByDireccionLocalidadContainingIgnoreCase(String localidad);

    // (HU-F2) Buscar paciente por tarjeta sanitaria para dispensar recetas
    Paciente findByNumTarjetaSanitaria(String numTarjetaSanitaria);

    //para cuando se seleccione el centro de salud de la lista para filtrar paciente, asi ya se busca 
    //el id del centro directamten
    List<Paciente> findByCentroSaludId(Long id);

    //para cuando se seleccione el medico de la lista para filtrar paciente, asi ya se busca 
    //el id del medico directamten
    List<Paciente> findByMedicoId(Long id);
}