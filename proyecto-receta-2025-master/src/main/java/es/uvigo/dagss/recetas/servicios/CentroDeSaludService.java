package es.uvigo.dagss.recetas.servicios;

import java.util.List;
import java.util.Optional;

import es.uvigo.dagss.recetas.entidades.CentroDeSalud;

public interface CentroDeSaludService {
    
    public CentroDeSalud crear(CentroDeSalud centro);
    public CentroDeSalud modificar(CentroDeSalud centro);
    public void eliminar(CentroDeSalud centro);
    
    public Optional<CentroDeSalud> buscarPorId(Long id);
    public List<CentroDeSalud> buscarPorNombre(String nombre);
	public List<CentroDeSalud> buscarPorLocalidad(String localidad);
	public List<CentroDeSalud> buscarTodos();



}
