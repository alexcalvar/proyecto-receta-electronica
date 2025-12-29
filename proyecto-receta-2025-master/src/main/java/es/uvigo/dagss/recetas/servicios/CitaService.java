package es.uvigo.dagss.recetas.servicios;

import es.uvigo.dagss.recetas.entidades.Cita;
import es.uvigo.dagss.recetas.entidades.Medico;
import es.uvigo.dagss.recetas.entidades.Paciente;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface CitaService {
    
    public Cita crear(Cita cita);
    public void anularCita(Long citaId);
    public List<Cita> obtenerCitasMedicoDia(Medico medico, LocalDate fecha);
    public List<Cita> obtenerCitasPendientesPaciente(Paciente paciente);
    public List<LocalTime> obtenerHuecosDisponibles(Medico medico, LocalDate fecha);
    
}
