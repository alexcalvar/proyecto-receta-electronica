package es.uvigo.dagss.recetas.servicios;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.uvigo.dagss.recetas.daos.CitaDAO;
import es.uvigo.dagss.recetas.entidades.Cita;
import es.uvigo.dagss.recetas.entidades.Medico;
import es.uvigo.dagss.recetas.entidades.Paciente;
import es.uvigo.dagss.recetas.utils.EstadoCita;

@Service
public class CitaServiceImpl implements CitaService {

    @Autowired
    private CitaDAO citaDAO;

    // Configuración del horario del centro (Hardcoded para la práctica)
    private static final LocalTime HORA_INICIO = LocalTime.of(9, 0); // 09:00
    private static final LocalTime HORA_FIN = LocalTime.of(14, 0);   // 14:00
    private static final int DURACION_CITA_MINUTOS = 15;

    @Override
    @Transactional
    public Cita crear(Cita cita) {
        // 1. Validar que el hueco está libre antes de guardar (Evitar solapamientos)
        // Buscamos si ya hay una cita PLANIFICADA para ese médico, fecha y hora
        // NOTA: Debes tener un método en el DAO o usar un Example/Query para esto.
        // Aquí asumo una validación básica en memoria para no complicar el DAO.
        
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
    public void anularCita(Long citaId) {
        Cita cita = citaDAO.findById(citaId)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        
        cita.setEstado(EstadoCita.ANULADA);
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
        // Asumiendo que definiste 'findPendingByPatient' en el DAO como hablamos
        // Si no, usa el método que tengas para filtrar por Paciente y Estado = PLANIFICADA
        // Aquí uso una llamada genérica que deberías adaptar a tu DAO:
        
        // Opción A: Si tienes el método específico en DAO
         return citaDAO.findPendingByPatient(paciente);
        
        // Opción B (Más compatible con lo que tienes): Filtrar en memoria
        /*List<Cita> todas = citaDAO.findPendingByPatient(paciente); // Asumo que este existe
        return todas.stream()
                    .filter(c -> c.getEstado() == EstadoCita.PLANIFICADA)
                    .collect(Collectors.toList());*/
    }

    @Override
    @Transactional(readOnly = true)
    public List<LocalTime> obtenerHuecosDisponibles(Medico medico, LocalDate fecha) { 
        // 1. Obtener las citas OCUPADAS de la BD
        List<Cita> citasOcupadas = citaDAO.findByMedicoAndFecha(medico, fecha);
        
        // Extraemos solo las horas de las citas que están PLANIFICADAS
        List<LocalTime> horasOcupadas = new ArrayList<>();
        if (citasOcupadas != null) {
            for (Cita c : citasOcupadas) {
                if (c.getEstado() == EstadoCita.PLANIFICADA) {
                    horasOcupadas.add(c.getHora());
                }
            }
        }

        // 2. Generar TODAS las horas posibles (El horario ideal)
        List<LocalTime> huecosLibres = new ArrayList<>();
        LocalTime horaActual = HORA_INICIO;

        while (horaActual.isBefore(HORA_FIN)) {
            // 3. LA RESTA: Si la hora actual NO está en la lista de ocupadas, es un hueco libre
            if (!horasOcupadas.contains(horaActual)) {
                huecosLibres.add(horaActual);
            }
            // Avanzamos 15 minutos
            horaActual = horaActual.plusMinutes(DURACION_CITA_MINUTOS);
        }

        return huecosLibres;
    }
}