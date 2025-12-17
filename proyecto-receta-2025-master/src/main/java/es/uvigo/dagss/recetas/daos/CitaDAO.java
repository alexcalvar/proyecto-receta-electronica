package es.uvigo.dagss.recetas.daos;

import es.uvigo.dagss.recetas.entidades.Cita;
import es.uvigo.dagss.recetas.entidades.Medico;
import es.uvigo.dagss.recetas.entidades.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date; // O java.time.LocalDate si cambiaste el tipo
import java.util.List;

public interface CitaDAO extends JpaRepository<Cita, Long> {

    // (HU-M2) lista de citas del medico para un dia, tambien se usara para calcular
    // huecos libres
    List<Cita> findByMedicoAndFecha(Medico medico, Date fecha);

    // (HU-P2) lista de citas del paciente
    @Query("SELECT c FROM Cita c WHERE c.paciente = :paciente AND c.estado = 'PLANIFICADA'")
    List<Cita> findPendingByPatient(@Param("paciente") Paciente paciente);

}