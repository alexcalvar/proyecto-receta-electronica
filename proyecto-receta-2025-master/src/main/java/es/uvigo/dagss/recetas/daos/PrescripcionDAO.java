package es.uvigo.dagss.recetas.daos;

import es.uvigo.dagss.recetas.entidades.Paciente;
import es.uvigo.dagss.recetas.entidades.Prescripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Date;
import java.util.List;

public interface PrescripcionDAO extends JpaRepository<Prescripcion, Long> {
    
    // HU-M3: Ver prescripciones "en vigor" (Fecha fin posterior a hoy)
    List<Prescripcion> findByPacienteAndFechaFinAfter(Paciente paciente, Date fechaActual);
    
    // Historial completo del paciente
    List<Prescripcion> findByPaciente(Paciente paciente);
}