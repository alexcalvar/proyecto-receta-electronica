package es.uvigo.dagss.recetas.daos;

import es.uvigo.dagss.recetas.entidades.Receta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RecetaDAO extends JpaRepository<Receta, Long> {

    // HU-F2: Buscamos por tarjeta, que estén planificadas y que sean válidas hoy.
    @Query("SELECT r FROM Receta r WHERE " +
           "r.prescripcion.paciente.numTarjetaSanitaria = :tarjeta " +
           "AND r.estado = 'PLANIFICADA' " +
           "AND :fechaActual BETWEEN r.prescripcion.fechaInicio AND r.prescripcion.fechaFin")
    List<Receta> findAvailableByCardNumber(@Param("tarjeta") String tarjeta, 
                                           @Param("fechaActual") LocalDate fechaActual);

    // (HU-P4) ver recetas 
    List<Receta> findByPrescripcionPacienteIdOrderByFechaDesc(Long idPaciente);
}