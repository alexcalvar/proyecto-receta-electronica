package es.uvigo.dagss.recetas.servicios;

import java.util.List;
import java.util.Optional;

import es.uvigo.dagss.recetas.entidades.Prescripcion;

public interface PrescripcionService {
    
    public Prescripcion crear(Prescripcion prescripcion);
    public Prescripcion modificar(Prescripcion prescripcion);
    public void eliminar(Prescripcion prescripcion);


    Optional<Prescripcion> buscarPorId(Long id);

    List<Prescripcion> buscarHistorialPorPaciente(Long idPaciente);

    List<Prescripcion> buscarVigentesPorPaciente(Long idPaciente);
    
}
