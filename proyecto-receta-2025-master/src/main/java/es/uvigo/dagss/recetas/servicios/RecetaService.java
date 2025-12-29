package es.uvigo.dagss.recetas.servicios;

import java.util.List;
import java.util.Optional;

import es.uvigo.dagss.recetas.entidades.Receta;

public interface RecetaService {
    // 1. (HU-F2) BUSCAR DISPONIBLES (La pantalla principal del Farmacéutico)
    // El farmacéutico escanea la tarjeta y el sistema le dice: "Puede llevarse esto hoy".
    // Internamente llamará a la query compleja del DAO con el BETWEEN de fechas.
    List<Receta> buscarDisponibles(String numeroTarjeta);

    // 2. (HU-F3) DISPENSAR (La acción de venta) 
    // Este método es pura lógica de negocio:
    //  - Busca la receta.
    //  - Verifica que esté "PLANIFICADA".
    //  - Cambia el estado a "DISPENSADA".
    //  - Guarda los cambios.
    void dispensar(Long idReceta);

    List<Receta> listarPorPaciente(Long idPaciente);

    Optional<Receta> buscarPorId(Long id);
    
    // Opcional: Generación masiva
    // Este método lo llamará el PrescripcionService cuando se crea un tratamiento.
    // Recibe una lista de recetas calculadas y las guarda en la BD.
    void generarRecetas(List<Receta> recetas);
}
