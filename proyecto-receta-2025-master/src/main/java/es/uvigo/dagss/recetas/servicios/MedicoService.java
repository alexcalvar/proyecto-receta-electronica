package es.uvigo.dagss.recetas.servicios;

import java.util.List;
import java.util.Optional;

import es.uvigo.dagss.recetas.entidades.CentroDeSalud;
import es.uvigo.dagss.recetas.entidades.Medico;


public interface MedicoService {
    
    public Medico crear(Medico Medico);
    public Medico modificar(Medico Medico);
    public void eliminar(Medico Medico);
    public List<Medico> buscarPorNombre(String nombre);
    public Optional<Medico> buscarPorId(Long id);
    public List<Medico> buscarPorCds(CentroDeSalud centroDeSalud);
	public List<Medico> buscarPorLocalidad(String localidad);
	public List<Medico> buscarTodos();

}
