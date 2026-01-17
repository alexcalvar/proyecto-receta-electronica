package es.uvigo.dagss.recetas.servicios;

import java.util.List;
import java.util.Optional;

import es.uvigo.dagss.recetas.entidades.Receta;

public interface RecetaService {
   
    List<Receta> buscarDisponibles(String numeroTarjeta);


    void dispensar(Long idReceta);

    List<Receta> listarPorPaciente(Long idPaciente);

    Optional<Receta> buscarPorId(Long id);
    
    void generarRecetas(List<Receta> recetas);
}
