package es.uvigo.dagss.recetas.servicios;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.uvigo.dagss.recetas.daos.MedicamentoDAO;
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

    @Autowired
    private MedicamentoDAO medicamentoDAO;

    @Override
    @Transactional
    public Prescripcion crear(Prescripcion prescripcion) {
        // 1. Ajustar fecha de inicio si viene nula
        if (prescripcion.getFechaInicio() == null) {
            prescripcion.setFechaInicio(LocalDate.now());
        }
        
        // asegurar que nace activa
        prescripcion.setActiva(true);

        // 2. Guardar la Prescripción 
        Prescripcion prescripcionGuardada = prescripcionDAO.save(prescripcion);

        // 3. Generar el Plan de Recetas (HU-M5)
        generarRecetasParaPrescripcion(prescripcionGuardada);

        return prescripcionGuardada;
    }

    
    private void generarRecetasParaPrescripcion(Prescripcion p) {
        
        Medicamento m = medicamentoDAO.findById(p.getMedicamento().getId()).orElse(null);
        
        
        if (m == null) m = p.getMedicamento(); 

        Double dosisDiaria = p.getDosisDiaria(); 
        
        Integer dosisPorCaja = m.getNumeroDosis(); 

        if (dosisDiaria == null || dosisDiaria <= 0) dosisDiaria = 1.0;
        if (dosisPorCaja == null || dosisPorCaja <= 0) dosisPorCaja = 1;

    
        int diasDuracionCaja = (int) Math.ceil(dosisPorCaja / dosisDiaria);
        
      
        LocalDate fechaValidezActual = p.getFechaInicio();
        LocalDate fechaFinTratamiento = p.getFechaFin();

        
        while (!fechaValidezActual.isAfter(fechaFinTratamiento)) {
            
            Receta receta = new Receta();
            receta.setPrescripcion(p);
            receta.setCantidad(1); 
            receta.setEstado(EstadoReceta.PLANIFICADA);
            

            receta.setFechaValidezInicio(fechaValidezActual.minusDays(7));
            
          
            LocalDate finTeoricoCaja = fechaValidezActual.plusDays(diasDuracionCaja);
            receta.setFechaValidezFin(finTeoricoCaja.plusDays(7));
            
       
            recetaDAO.save(receta);

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
        // Borrado lógico
        prescripcion.setActiva(false);
        prescripcionDAO.save(prescripcion);
        
      
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Prescripcion> buscarPorId(Long id) {
        return prescripcionDAO.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Prescripcion> buscarHistorialPorPaciente(Long idPaciente) {
      
        return pacienteDAO.findById(idPaciente)
                .map(paciente -> prescripcionDAO.findByPaciente(paciente))
                .orElse(Collections.emptyList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Prescripcion> buscarVigentesPorPaciente(Long idPaciente) {
        
        return pacienteDAO.findById(idPaciente)
                .map(paciente -> prescripcionDAO.findByPacienteAndFechaFinAfterOrderByFechaInicio(paciente, LocalDate.now()))
                .orElse(Collections.emptyList());
    }
}