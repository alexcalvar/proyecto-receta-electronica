package es.uvigo.dagss.recetas.servicios;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.uvigo.dagss.recetas.daos.PacienteDAO;
import es.uvigo.dagss.recetas.daos.PrescripcionDAO;
import es.uvigo.dagss.recetas.daos.RecetaDAO;
import es.uvigo.dagss.recetas.entidades.Medicamento;
import es.uvigo.dagss.recetas.entidades.Prescripcion;
import es.uvigo.dagss.recetas.entidades.Receta;
import es.uvigo.dagss.recetas.utils.EstadoReceta; // Asegúrate de que el paquete sea correcto

@Service
public class PrescripcionServiceImpl implements PrescripcionService {

    @Autowired
    private PrescripcionDAO prescripcionDAO;

    @Autowired
    private PacienteDAO pacienteDAO;

    @Autowired
    private RecetaDAO recetaDAO;

    @Override
    @Transactional
    public Prescripcion crear(Prescripcion prescripcion) {
        // 1. Ajustar fecha de inicio si viene nula
        if (prescripcion.getFechaInicio() == null) {
            prescripcion.setFechaInicio(LocalDate.now());
        }
        
        // Aseguramos que nace activa
        prescripcion.setActiva(true);

        // 2. Guardar la Prescripción (necesario para tener ID antes de crear recetas)
        Prescripcion prescripcionGuardada = prescripcionDAO.save(prescripcion);

        // 3. Generar el Plan de Recetas (HU-M5)
        generarRecetasParaPrescripcion(prescripcionGuardada);

        return prescripcionGuardada;
    }

    /**
     * Lógica de la HU-M5: Generación automática de recetas basada en dosis.
     */
    private void generarRecetasParaPrescripcion(Prescripcion p) {
        Medicamento m = p.getMedicamento();
        
        // --- CÁLCULO DE DURACIÓN ---
        Double dosisDiaria = p.getDosisDiaria();       // Ej: 2.0 pastillas al día
        Integer dosisPorCaja = m.getNumeroDosis();     // Ej: 40 pastillas por caja

        // Validación anti-crash (por si dosis es 0 o null)
        if (dosisDiaria == null || dosisDiaria <= 0) dosisDiaria = 1.0;
        if (dosisPorCaja == null || dosisPorCaja <= 0) dosisPorCaja = 1;

        // ¿Cuántos días dura una caja? (Ej: 40 / 2 = 20 días)
        int diasDuracionCaja = (int) Math.ceil(dosisPorCaja / dosisDiaria);
        
        // --- BUCLE DE GENERACIÓN ---
        LocalDate fechaValidezActual = p.getFechaInicio();
        LocalDate fechaFinTratamiento = p.getFechaFin();

        // Mientras la fecha actual no supere el fin del tratamiento...
        while (!fechaValidezActual.isAfter(fechaFinTratamiento)) {
            
            Receta receta = new Receta();
            receta.setPrescripcion(p);
            receta.setCantidad(1); // Normalmente 1 caja por receta
            receta.setEstado(EstadoReceta.PLANIFICADA);
            
            // FECHAS (HU-M5 pide márgenes)
            // Puede ir a la farmacia 7 días antes de que le toque
            receta.setFechaValidezInicio(fechaValidezActual.minusDays(7));
            
            // La receta caduca 7 días después de que se le haya acabado teóricamente la caja
            LocalDate finTeoricoCaja = fechaValidezActual.plusDays(diasDuracionCaja);
            receta.setFechaValidezFin(finTeoricoCaja.plusDays(7));
            
            // Guardamos la receta
            recetaDAO.save(receta);

            // Avanzamos el calendario para la siguiente caja
            fechaValidezActual = fechaValidezActual.plusDays(diasDuracionCaja);
        }
    }

    @Override
    @Transactional
    public Prescripcion modificar(Prescripcion prescripcion) {
        return prescripcionDAO.save(prescripcion);
    }

    @Override
    @Transactional
    public void eliminar(Prescripcion prescripcion) {
        // Borrado lógico (recomendado en sanidad)
        prescripcion.setActiva(false);
        prescripcionDAO.save(prescripcion);
        
        // Opcional: Aquí podrías buscar las recetas futuras y anularlas
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Prescripcion> buscarPorId(Long id) {
        return prescripcionDAO.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Prescripcion> buscarHistorialPorPaciente(Long idPaciente) {
        // Buscamos el paciente primero para asegurar que existe
        return pacienteDAO.findById(idPaciente)
                .map(paciente -> prescripcionDAO.findByPaciente(paciente))
                .orElse(Collections.emptyList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Prescripcion> buscarVigentesPorPaciente(Long idPaciente) {
        // Buscamos prescripciones cuya fecha de fin sea POSTERIOR a hoy (LocalDate.now())
        return pacienteDAO.findById(idPaciente)
                .map(paciente -> prescripcionDAO.findByPacienteAndFechaFinAfterOrderByFechaInicio(paciente, LocalDate.now()))
                .orElse(Collections.emptyList());
    }
}