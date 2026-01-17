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

        List<Receta> disponibles = recetaDAO.findAvailableByCardNumber(numeroTarjeta, LocalDate.now());
        
        if (disponibles != null) {
            return disponibles;
        }
        return Collections.emptyList();
    }

    @Override
    @Transactional
    public void dispensar(Long idReceta) {
       
        Receta receta = recetaDAO.findById(idReceta)
                .orElseThrow(() -> new RuntimeException("Receta no encontrada con ID: " + idReceta));

      
        if (receta.getEstado() != EstadoReceta.PLANIFICADA) {
            throw new RuntimeException("No se puede dispensar. La receta no está en estado PLANIFICADA.");
        }

        receta.setEstado(EstadoReceta.SERVIDA);
        recetaDAO.save(receta);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Receta> listarPorPaciente(Long idPaciente) {
    
        List<Receta> recetas = recetaDAO.findByPrescripcionPacienteIdOrderByFechaValidezInicioDesc(idPaciente);
        
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
        
        if (recetas != null && !recetas.isEmpty()) {
            recetaDAO.saveAll(recetas);
        }
    }
}
