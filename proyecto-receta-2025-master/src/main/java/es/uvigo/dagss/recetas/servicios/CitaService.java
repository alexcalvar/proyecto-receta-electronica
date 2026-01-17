package es.uvigo.dagss.recetas.servicios;

import es.uvigo.dagss.recetas.entidades.Cita;
import es.uvigo.dagss.recetas.entidades.Medico;
import es.uvigo.dagss.recetas.entidades.Paciente;
import es.uvigo.dagss.recetas.utils.EstadoCita;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface CitaService {
    
    public Cita crear(Cita cita);
    public void modificar(Long citaId, EstadoCita estado);
    public List<Cita> obtenerCitasMedicoDia(Medico medico, LocalDate fecha);
    public List<Cita> obtenerCitasPendientesPaciente(Paciente paciente);
    public List<LocalTime> obtenerHuecosDisponibles(Medico medico, LocalDate fecha);
    public Optional<Cita> buscarPorId(Long id);
    
}
