package es.uvigo.dagss.recetas.servicios;

import java.util.List;
import java.util.Optional;

import es.uvigo.dagss.recetas.entidades.Administrador;

public interface AdministradorService {

    public Administrador crear(Administrador admin);
    public Administrador modificar(Administrador admin);
    public void eliminar(Administrador admin);
    
    public Optional<Administrador> buscarPorId(Long id);
	public List<Administrador> buscarTodos();
	

    
} 
