package es.uvigo.dagss.recetas.servicios;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.uvigo.dagss.recetas.daos.RecetaDAO;
import es.uvigo.dagss.recetas.entidades.Receta;
import es.uvigo.dagss.recetas.utils.EstadoReceta;

@Service
public class RecetaServiceImpl implements RecetaService {

    @Autowired
    private RecetaDAO recetaDAO;

    @Override
    @Transactional(readOnly = true)
    public List<Receta> buscarDisponibles(String numeroTarjeta) {
        // (HU-F2) Buscamos recetas PLANIFICADAS y vigentes a día de HOY
        // Pasamos LocalDate.now() para que la query compare con la fecha actual
        List<Receta> disponibles = recetaDAO.findAvailableByCardNumber(numeroTarjeta, LocalDate.now());
        
        if (disponibles != null) {
            return disponibles;
        }
        return Collections.emptyList();
    }

    @Override
    @Transactional
    public void dispensar(Long idReceta) {
        // 1. Buscamos la receta
        Receta receta = recetaDAO.findById(idReceta)
                .orElseThrow(() -> new RuntimeException("Receta no encontrada con ID: " + idReceta));

        // 2. COMPROBACIÓN DE SEGURIDAD 
        // Solo podemos dispensar si está PLANIFICADA.
        // (Si usas String en vez de Enum, usa: "PLANIFICADA".equals(receta.getEstado()))
        if (receta.getEstado() != EstadoReceta.PLANIFICADA) {
            throw new RuntimeException("No se puede dispensar. La receta no está en estado PLANIFICADA.");
        }

        // 3. Cambiamos estado y guardamos
        receta.setEstado(EstadoReceta.SERVIDA);
        recetaDAO.save(receta);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Receta> listarPorPaciente(Long idPaciente) {
        // (HU-P4) Historial de recetas del paciente
        List<Receta> recetas = recetaDAO.findByPrescripcionPacienteIdOrderByFechaDesc(idPaciente);
        
        if (recetas != null) {
            return recetas;
        }
        return Collections.emptyList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Receta> buscarPorId(Long id) {
        return recetaDAO.findById(id);
    }

    @Override
    @Transactional
    public void generarRecetas(List<Receta> recetas) {
        // Guardado masivo (Batch insert) - Más eficiente que guardar una a una
        if (recetas != null && !recetas.isEmpty()) {
            recetaDAO.saveAll(recetas);
        }
    }
}
