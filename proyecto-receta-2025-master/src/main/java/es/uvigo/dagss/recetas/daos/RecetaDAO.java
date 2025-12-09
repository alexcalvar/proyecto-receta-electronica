package es.uvigo.dagss.recetas.daos;

import es.uvigo.dagss.recetas.entidades.Receta;
import es.uvigo.dagss.recetas.entidades.Paciente; // Para uso directo si hiciera falta
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RecetaDAO extends JpaRepository<Receta, Long> {

    // HU-F2: Farmacia busca recetas por Tarjeta Sanitaria del paciente.
    // Spring Data permite "navegar" por las entidades:
    // Receta -> Prescripcion -> Paciente -> NumTarjetaSanitaria
    List<Receta> findByPrescripcionPacienteNumTarjetaSanitaria(String numTarjetaSanitaria);

    // HU-P4: Paciente ve sus recetas (navegando por el objeto paciente)
    List<Receta> findByPrescripcionPaciente(Paciente paciente);
}