package es.uvigo.dagss.recetas.servicios;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.uvigo.dagss.recetas.daos.CitaDAO;
import es.uvigo.dagss.recetas.entidades.CentroDeSalud;
import es.uvigo.dagss.recetas.entidades.Cita;
import es.uvigo.dagss.recetas.entidades.Medico;
import es.uvigo.dagss.recetas.entidades.Paciente;
import es.uvigo.dagss.recetas.utils.EstadoCita;

@Service
public class CitaServiceImpl implements CitaService {

    @Autowired
    private CitaDAO citaDAO;

    // Configuración del horario del centro 
    private static final LocalTime HORA_INICIO = LocalTime.of(9, 0); // 09:00
    private static final LocalTime HORA_FIN = LocalTime.of(14, 0);   // 14:00
    private static final int DURACION_CITA_MINUTOS = 15;

    @Override
    @Transactional
    public Cita crear(Cita cita) {
        // 1. Validar que el hueco está libre antes de guardar (Evitar solapamientos)
        
        
        List<Cita> citasDelDia = citaDAO.findByMedicoAndFecha(cita.getMedico(), cita.getFecha());
        
        boolean huecoOcupado = citasDelDia.stream()
            .anyMatch(c -> c.getHora().equals(cita.getHora()) && c.getEstado() == EstadoCita.PLANIFICADA);

        if (huecoOcupado) {
            throw new RuntimeException("El hueco seleccionado ya está ocupado.");
        }

        // 2. Estado inicial
        cita.setEstado(EstadoCita.PLANIFICADA);
        
        return citaDAO.save(cita);
    }


    @Override
    @Transactional
    public void modificar(Long citaId, EstadoCita estado) {
        Cita cita = citaDAO.findById(citaId)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        
        cita.setEstado(estado);
        citaDAO.save(cita);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cita> obtenerCitasMedicoDia(Medico medico, LocalDate fecha) {
        List<Cita> citas = citaDAO.findByMedicoAndFecha(medico, fecha);

        if (citas != null) {
            return citas;
        }
        return Collections.emptyList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cita> obtenerCitasPendientesPaciente(Paciente paciente) {
       
         return citaDAO.findPendingByPatient(paciente);
        
    }

    @Override
    @Transactional(readOnly = true)
    public List<LocalTime> obtenerHuecosDisponibles(Medico medico, LocalDate fecha) { 
        
        List<Cita> citasOcupadas = citaDAO.findByMedicoAndFecha(medico, fecha);
        
        
        List<LocalTime> horasOcupadas = new ArrayList<>();
        if (citasOcupadas != null) {
            for (Cita c : citasOcupadas) {
                if (c.getEstado() == EstadoCita.PLANIFICADA) {
                    horasOcupadas.add(c.getHora());
                }
            }
        }

        
        List<LocalTime> huecosLibres = new ArrayList<>();
        LocalTime horaActual = HORA_INICIO;

        while (horaActual.isBefore(HORA_FIN)) {
            
            if (!horasOcupadas.contains(horaActual)) {
                huecosLibres.add(horaActual);
            }
            // Avanzamos 15 minutos
            horaActual = horaActual.plusMinutes(DURACION_CITA_MINUTOS);
        }

        return huecosLibres;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cita> buscarPorId(Long id) {
        return citaDAO.findById(id);
    }
}