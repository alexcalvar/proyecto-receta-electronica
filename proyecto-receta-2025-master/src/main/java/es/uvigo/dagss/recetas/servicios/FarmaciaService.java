package es.uvigo.dagss.recetas.servicios;

import es.uvigo.dagss.recetas.entidades.Farmacia;
import java.util.List;
import java.util.Optional;

public interface FarmaciaService {
    
    public Farmacia crear(Farmacia farmacia);
    public Farmacia modificar(Farmacia farmacia);
    public void eliminar(Farmacia farmacia);
    public List<Farmacia> buscarPorNombre(String nombre);
    public Optional<Farmacia> buscarPorId(Long id);
	public List<Farmacia> buscarPorLocalidad(String localidad);
	public List<Farmacia> buscarTodos();
    
}
