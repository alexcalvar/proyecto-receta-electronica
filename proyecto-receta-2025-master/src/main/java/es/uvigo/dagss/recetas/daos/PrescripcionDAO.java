package es.uvigo.dagss.recetas.daos;

import es.uvigo.dagss.recetas.entidades.Paciente;
import es.uvigo.dagss.recetas.entidades.Prescripcion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface PrescripcionDAO extends JpaRepository<Prescripcion, Long> {
    
    // (HU-M3) prescripciones en vigor (Fecha fin posterior a hoy)
    List<Prescripcion> findByPacienteAndFechaFinAfterOrderByFechaInicio(Paciente paciente, LocalDate fechaActual);
    
    // Historial completo  
    List<Prescripcion> findByPaciente(Paciente paciente);
}