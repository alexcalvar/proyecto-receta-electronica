package es.uvigo.dagss.recetas.daos;

import es.uvigo.dagss.recetas.entidades.Cita;
import es.uvigo.dagss.recetas.entidades.Medico;
import es.uvigo.dagss.recetas.entidades.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Date; // O java.time.LocalDate si cambiaste el tipo
import java.util.List;

public interface CitaDAO extends JpaRepository<Cita, Long> {

    // HU-M2: Visualización de agenda del médico para un día concreto
    // SELECT c FROM Cita c WHERE c.medico = ?1 AND c.fecha = ?2
    List<Cita> findByMedicoAndFecha(Medico medico, Date fecha);

    // HU-P2: Visualización de citas futuras del paciente
    // Usamos 'After' para buscar fechas posteriores a hoy
    List<Cita> findByPacienteAndFechaAfter(Paciente paciente, Date fecha);

    // HU-P3: Comprobación de huecos (buscar si ya existe cita a esa hora)
    long countByMedicoAndFechaAndHora(Medico medico, Date fecha, Date hora);
}